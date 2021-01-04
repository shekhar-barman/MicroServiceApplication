package com.micro.app.web.rest;

import com.micro.app.MicroAppsApp;
import com.micro.app.domain.EmployeeInfo;
import com.micro.app.repository.EmployeeInfoRepository;
import com.micro.app.service.EmployeeInfoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EmployeeInfoResource} REST controller.
 */
@SpringBootTest(classes = MicroAppsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmployeeInfoResourceIT {

    private static final String DEFAULT_EMP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EMP_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DOB = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOB = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    @Autowired
    private EmployeeInfoRepository employeeInfoRepository;

    @Autowired
    private EmployeeInfoService employeeInfoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeInfoMockMvc;

    private EmployeeInfo employeeInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeInfo createEntity(EntityManager em) {
        EmployeeInfo employeeInfo = new EmployeeInfo()
            .empName(DEFAULT_EMP_NAME)
            .designation(DEFAULT_DESIGNATION)
            .mobile(DEFAULT_MOBILE)
            .dob(DEFAULT_DOB)
            .remarks(DEFAULT_REMARKS);
        return employeeInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeInfo createUpdatedEntity(EntityManager em) {
        EmployeeInfo employeeInfo = new EmployeeInfo()
            .empName(UPDATED_EMP_NAME)
            .designation(UPDATED_DESIGNATION)
            .mobile(UPDATED_MOBILE)
            .dob(UPDATED_DOB)
            .remarks(UPDATED_REMARKS);
        return employeeInfo;
    }

    @BeforeEach
    public void initTest() {
        employeeInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeeInfo() throws Exception {
        int databaseSizeBeforeCreate = employeeInfoRepository.findAll().size();
        // Create the EmployeeInfo
        restEmployeeInfoMockMvc.perform(post("/api/employee-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeInfo)))
            .andExpect(status().isCreated());

        // Validate the EmployeeInfo in the database
        List<EmployeeInfo> employeeInfoList = employeeInfoRepository.findAll();
        assertThat(employeeInfoList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeInfo testEmployeeInfo = employeeInfoList.get(employeeInfoList.size() - 1);
        assertThat(testEmployeeInfo.getEmpName()).isEqualTo(DEFAULT_EMP_NAME);
        assertThat(testEmployeeInfo.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testEmployeeInfo.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testEmployeeInfo.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testEmployeeInfo.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void createEmployeeInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeInfoRepository.findAll().size();

        // Create the EmployeeInfo with an existing ID
        employeeInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeInfoMockMvc.perform(post("/api/employee-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeInfo)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeInfo in the database
        List<EmployeeInfo> employeeInfoList = employeeInfoRepository.findAll();
        assertThat(employeeInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEmployeeInfos() throws Exception {
        // Initialize the database
        employeeInfoRepository.saveAndFlush(employeeInfo);

        // Get all the employeeInfoList
        restEmployeeInfoMockMvc.perform(get("/api/employee-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].empName").value(hasItem(DEFAULT_EMP_NAME)))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)));
    }
    
    @Test
    @Transactional
    public void getEmployeeInfo() throws Exception {
        // Initialize the database
        employeeInfoRepository.saveAndFlush(employeeInfo);

        // Get the employeeInfo
        restEmployeeInfoMockMvc.perform(get("/api/employee-infos/{id}", employeeInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeInfo.getId().intValue()))
            .andExpect(jsonPath("$.empName").value(DEFAULT_EMP_NAME))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE))
            .andExpect(jsonPath("$.dob").value(DEFAULT_DOB.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS));
    }
    @Test
    @Transactional
    public void getNonExistingEmployeeInfo() throws Exception {
        // Get the employeeInfo
        restEmployeeInfoMockMvc.perform(get("/api/employee-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeInfo() throws Exception {
        // Initialize the database
        employeeInfoService.save(employeeInfo);

        int databaseSizeBeforeUpdate = employeeInfoRepository.findAll().size();

        // Update the employeeInfo
        EmployeeInfo updatedEmployeeInfo = employeeInfoRepository.findById(employeeInfo.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeInfo are not directly saved in db
        em.detach(updatedEmployeeInfo);
        updatedEmployeeInfo
            .empName(UPDATED_EMP_NAME)
            .designation(UPDATED_DESIGNATION)
            .mobile(UPDATED_MOBILE)
            .dob(UPDATED_DOB)
            .remarks(UPDATED_REMARKS);

        restEmployeeInfoMockMvc.perform(put("/api/employee-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeInfo)))
            .andExpect(status().isOk());

        // Validate the EmployeeInfo in the database
        List<EmployeeInfo> employeeInfoList = employeeInfoRepository.findAll();
        assertThat(employeeInfoList).hasSize(databaseSizeBeforeUpdate);
        EmployeeInfo testEmployeeInfo = employeeInfoList.get(employeeInfoList.size() - 1);
        assertThat(testEmployeeInfo.getEmpName()).isEqualTo(UPDATED_EMP_NAME);
        assertThat(testEmployeeInfo.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testEmployeeInfo.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testEmployeeInfo.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testEmployeeInfo.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeeInfo() throws Exception {
        int databaseSizeBeforeUpdate = employeeInfoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeInfoMockMvc.perform(put("/api/employee-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeInfo)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeInfo in the database
        List<EmployeeInfo> employeeInfoList = employeeInfoRepository.findAll();
        assertThat(employeeInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployeeInfo() throws Exception {
        // Initialize the database
        employeeInfoService.save(employeeInfo);

        int databaseSizeBeforeDelete = employeeInfoRepository.findAll().size();

        // Delete the employeeInfo
        restEmployeeInfoMockMvc.perform(delete("/api/employee-infos/{id}", employeeInfo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeInfo> employeeInfoList = employeeInfoRepository.findAll();
        assertThat(employeeInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
