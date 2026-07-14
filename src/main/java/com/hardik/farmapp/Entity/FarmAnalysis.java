package com.hardik.farmapp.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "farm_analysis")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FarmAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location;

    private String soilType;

    private double ph;

    private String nitrogen;

    private String phosphorus;

    private String potassium;

    private String season;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    private double temperature;

    private int humidity;

    private String weather;

    @ManyToOne
    @JsonIgnore
    private Users user;

    @OneToOne(
            mappedBy = "analysis",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private CropRecommendation recommendation;

}
