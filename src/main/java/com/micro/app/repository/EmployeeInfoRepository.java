package com.micro.app.repository;

import com.micro.app.domain.EmployeeInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmployeeInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeInfoRepository extends JpaRepository<EmployeeInfo, Long> {
}
