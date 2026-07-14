package com.hardik.farmapp.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "crop_recommendation")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CropRecommendation {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String crop;

    @Column(columnDefinition = "TEXT")
    private String reason;

    @Column(columnDefinition = "TEXT")
    private String fertilizer;

    @Column(columnDefinition = "TEXT")
    private String pesticide;

    @Column(columnDefinition = "TEXT")
    private String irrigation;

    @Column(columnDefinition = "TEXT")
    private String diseaseRisk;

    @Column(columnDefinition = "TEXT")
    private String harvestTime;

    @Column(columnDefinition = "TEXT")
    private String expectedYield;

    private Double temperature;

    private Integer humidity;

    private String weatherCondition;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "analysis_id")
    @JsonIgnore
    private FarmAnalysis analysis;
}
