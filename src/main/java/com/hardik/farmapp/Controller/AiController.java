package com.hardik.farmapp.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hardik.farmapp.Entity.CropRecommendation;
import com.hardik.farmapp.Request.FarmRequest;
import com.hardik.farmapp.Service.AiService;
import jakarta.validation.Valid;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/ai")
public class AiController {

    @Autowired
    private AiService aiService;



    @PostMapping("/analyze")
    public CropRecommendation analyze( @Valid @RequestBody FarmRequest request)  {

        return aiService.analyzeFarm(request);

    }

}
