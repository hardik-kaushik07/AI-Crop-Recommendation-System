package com.hardik.farmapp.Request;

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

    @DecimalMin(value = "0.0", message = "pH must be at least 0")
    @DecimalMax(value = "14.0", message = "pH cannot be greater than 14")
    private double ph;

    @NotBlank(message = "Nitrogen level is required")
    private String nitrogen;

    @NotBlank(message = "Phosphorus level is required")
    private String phosphorus;

    @NotBlank(message = "Potassium level is required")
    private String potassium;

    @NotBlank(message = "Season is required")
    private String season;
}
