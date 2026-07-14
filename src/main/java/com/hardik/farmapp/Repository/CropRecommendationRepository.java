package com.hardik.farmapp.Repository;

import com.hardik.farmapp.Entity.CropRecommendation;
import com.hardik.farmapp.Entity.FarmAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CropRecommendationRepository extends JpaRepository<CropRecommendation, Long> {
    Optional<CropRecommendation> findByAnalysis(FarmAnalysis analysis);
}
