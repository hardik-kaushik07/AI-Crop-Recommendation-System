    package com.hardik.farmapp.Controller;

    import com.hardik.farmapp.Entity.CropRecommendation;
    import com.hardik.farmapp.DTO.FarmRequest;
    import com.hardik.farmapp.Service.AiService;
    import jakarta.validation.Valid;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.access.prepost.PreAuthorize;
    import org.springframework.web.bind.annotation.*;

    @RestController
    @CrossOrigin(origins = "*")
    @RequestMapping("/api/ai")
    public class AiController {

        @Autowired
        private AiService aiService;



        @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
        @PostMapping("/analyze")
        public CropRecommendation analyze( @Valid @RequestBody FarmRequest request)  {

            return aiService.analyzeFarm(request);

        }

    }
