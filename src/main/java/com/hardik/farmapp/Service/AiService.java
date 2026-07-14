package com.hardik.farmapp.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hardik.farmapp.Entity.CropRecommendation;
import com.hardik.farmapp.Entity.FarmAnalysis;
import com.hardik.farmapp.Entity.Users;
import com.hardik.farmapp.Repository.FarmAnalysisRepository;
import com.hardik.farmapp.Repository.UsersRepository;
import com.hardik.farmapp.Request.FarmRequest;
import com.hardik.farmapp.Response.WeatherResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AiService {


    @Autowired
    private WeatherService weatherService;

    @Autowired
    private FarmAnalysisRepository farmAnalysisRepository;

    private final ChatClient chatClient;

    @Autowired
    private UsersRepository usersRepository;

    public AiService(ChatClient.Builder builder){
        this.chatClient = builder.build();
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    public CropRecommendation analyzeFarm(FarmRequest request){

        WeatherResponse weather =
                weatherService.getWeather(request.getLocation());

        String prompt = String.format("""

You are an experienced agricultural scientist and crop advisor specializing in Indian agriculture.

Your task is to recommend ONLY ONE crop that is most suitable for the given farm conditions.

Analyze ALL of the following carefully before making a decision:

• Location
• Soil Type
• Soil pH
• Nitrogen level
• Phosphorus level
• Potassium level
• Current Season
• Temperature
• Humidity
• Current Weather

Farm Details

Location : %s
Soil Type : %s
Soil pH : %.2f
Nitrogen : %s
Phosphorus : %s
Potassium : %s
Season : %s
Temperature : %.2f °C
Humidity : %d%%
Weather : %s

Instructions

1. Recommend ONLY ONE crop.
2. The crop must change when the farm conditions change.
3. Use the location to infer regional crop suitability.
4. Estimate harvest time based on the selected crop.
5. Estimate expected yield according to soil fertility and weather.
6. Recommend fertilizer according to NPK levels.
7. Recommend pesticide only if required.
8. Recommend irrigation based on weather and crop water requirements.
9. Estimate disease risk using humidity and weather conditions.
10. Keep every recommendation practical for Indian farmers.
11. Never use fixed values.
12. Never copy previous answers.
13. If multiple crops are suitable, choose the BEST one and explain why.

                        Return ONLY ONE JSON object.
                        
                                               Every field below is REQUIRED.
                        
                                               Never omit any field.
                        
                                               If a value is uncertain, estimate it.
                        
                                               Return

{
  "crop":"",
  "reason":"",
  "fertilizer":"",
  "pesticide":"",
  "irrigation":"",
  "diseaseRisk":"",
  "harvestTime":"",
  "expectedYield":""
}

Keep every field concise.

reason: maximum 60 words
fertilizer: maximum 40 words
pesticide: maximum 40 words
irrigation: maximum 40 words
diseaseRisk: maximum 30 words
harvestTime: maximum 20 words
expectedYield: maximum 20 words

Rules

• Do NOT return markdown.
• Do NOT use ```json.
• Do NOT write any explanation.
• Do NOT write "Here is the JSON".
• Return ONLY the JSON object.

""",
                request.getLocation(),
                request.getSoilType(),
                request.getPh(),
                request.getNitrogen(),
                request.getPhosphorus(),
                request.getPotassium(),
                request.getSeason(),
                weather.getTemperature(),
                weather.getHumidity(),
                weather.getWeatherCondition()
        );

        String response= chatClient.prompt()
                .user(prompt)
                .call()
                .content();

        System.out.println("============== AI RESPONSE ==============");
        System.out.println(response);
        System.out.println("=========================================");

        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            String email = authentication.getName();

            Users user = usersRepository.findByEmail(email);

            if (user == null) {
                throw new UsernameNotFoundException("Sorry the user not Found");
            }

            CropRecommendation recommendation =
                    objectMapper.readValue(response, CropRecommendation.class);

            System.out.println("Harvest = " + recommendation.getHarvestTime());
            System.out.println("Yield = " + recommendation.getExpectedYield());

            recommendation.setTemperature(weather.getTemperature());
            recommendation.setHumidity(weather.getHumidity());
            recommendation.setWeatherCondition(weather.getWeatherCondition());


            FarmAnalysis analysis = new FarmAnalysis();

            analysis.setLocation(request.getLocation());
            analysis.setSoilType(request.getSoilType());
            analysis.setPh(request.getPh());
            analysis.setNitrogen(request.getNitrogen());
            analysis.setPhosphorus(request.getPhosphorus());
            analysis.setPotassium(request.getPotassium());
            analysis.setSeason(request.getSeason());
            analysis.setTemperature(weather.getTemperature());
            analysis.setHumidity(weather.getHumidity());
            analysis.setWeather(weather.getWeatherCondition());
            analysis.setUser(user);
            analysis.setRecommendation(recommendation);


            recommendation.setAnalysis(analysis);


            farmAnalysisRepository.save(analysis);

            return recommendation;
        }
        catch (Exception e) {
            System.out.println("========== AI ERROR ==========");
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}
