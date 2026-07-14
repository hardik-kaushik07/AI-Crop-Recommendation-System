package com.hardik.farmapp.Repository;

import com.hardik.farmapp.Entity.FarmAnalysis;
import com.hardik.farmapp.Entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FarmAnalysisRepository extends JpaRepository<FarmAnalysis, Long> {
    Page<FarmAnalysis> findByUser(Users user, Pageable pageable);

    Optional<FarmAnalysis> findByIdAndUser(Long id, Users user);

    void deleteByUser(Users user);


}
