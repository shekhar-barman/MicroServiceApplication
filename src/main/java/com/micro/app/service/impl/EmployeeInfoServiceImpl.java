package com.micro.app.service.impl;

import com.micro.app.service.EmployeeInfoService;
import com.micro.app.domain.EmployeeInfo;
import com.micro.app.repository.EmployeeInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EmployeeInfo}.
 */
@Service
@Transactional
public class EmployeeInfoServiceImpl implements EmployeeInfoService {

    private final Logger log = LoggerFactory.getLogger(EmployeeInfoServiceImpl.class);

    private final EmployeeInfoRepository employeeInfoRepository;

    public EmployeeInfoServiceImpl(EmployeeInfoRepository employeeInfoRepository) {
        this.employeeInfoRepository = employeeInfoRepository;
    }

    @Override
    public EmployeeInfo save(EmployeeInfo employeeInfo) {
        log.debug("Request to save EmployeeInfo : {}", employeeInfo);
        return employeeInfoRepository.save(employeeInfo);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeInfo> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeInfos");
        return employeeInfoRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<EmployeeInfo> findOne(Long id) {
        log.debug("Request to get EmployeeInfo : {}", id);
        return employeeInfoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EmployeeInfo : {}", id);
        employeeInfoRepository.deleteById(id);
    }
}
