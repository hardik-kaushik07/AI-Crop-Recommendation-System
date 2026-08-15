package com.hardik.farmapp.DTO;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FarmRequest {

    @NotBlank(message = "Location cannot be empty")
    private String location;

    @NotBlank(message = "Soil Type cannot be empty")
    private String soilType;

    @NotBlank(message = "Season is required")
    private String season;
}
