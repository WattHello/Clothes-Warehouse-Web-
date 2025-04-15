package com.humber.distribution_center_manager.repositories;

import com.humber.distribution_center_manager.models.DistributionCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistributionCenterRepository extends JpaRepository<DistributionCenter, Long> {
} 