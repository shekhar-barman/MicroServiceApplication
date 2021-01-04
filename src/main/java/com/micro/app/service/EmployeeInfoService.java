package com.micro.app.service;

import com.micro.app.domain.EmployeeInfo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link EmployeeInfo}.
 */
public interface EmployeeInfoService {

    /**
     * Save a employeeInfo.
     *
     * @param employeeInfo the entity to save.
     * @return the persisted entity.
     */
    EmployeeInfo save(EmployeeInfo employeeInfo);

    /**
     * Get all the employeeInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EmployeeInfo> findAll(Pageable pageable);


    /**
     * Get the "id" employeeInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmployeeInfo> findOne(Long id);

    /**
     * Delete the "id" employeeInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
