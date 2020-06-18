package com.job.web.rest;

import com.job.JobApp;
import com.job.domain.ApplyInfo;
import com.job.repository.ApplyInfoRepository;
import com.job.service.ApplyInfoService;
import com.job.service.dto.ApplyInfoCriteria;
import com.job.service.ApplyInfoQueryService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ApplyInfoResource} REST controller.
 */
@SpringBootTest(classes = JobApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ApplyInfoResourceIT {

    private static final String DEFAULT_USER = "AAAAAAAAAA";
    private static final String UPDATED_USER = "BBBBBBBBBB";

    private static final String DEFAULT_POST = "AAAAAAAAAA";
    private static final String UPDATED_POST = "BBBBBBBBBB";

    private static final String DEFAULT_EXP_CITY = "AAAAAAAAAA";
    private static final String UPDATED_EXP_CITY = "BBBBBBBBBB";

    private static final Double DEFAULT_EXP_SALARY = 1D;
    private static final Double UPDATED_EXP_SALARY = 2D;
    private static final Double SMALLER_EXP_SALARY = 1D - 1D;

    private static final Instant DEFAULT_SUBMIT_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SUBMIT_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_EDUCATION = "AAAAAAAAAA";
    private static final String UPDATED_EDUCATION = "BBBBBBBBBB";

    private static final String DEFAULT_SPECIALTY = "AAAAAAAAAA";
    private static final String UPDATED_SPECIALTY = "BBBBBBBBBB";

    private static final Integer DEFAULT_GRADUATION = 1;
    private static final Integer UPDATED_GRADUATION = 2;
    private static final Integer SMALLER_GRADUATION = 1 - 1;

    @Autowired
    private ApplyInfoRepository applyInfoRepository;

    @Autowired
    private ApplyInfoService applyInfoService;

    @Autowired
    private ApplyInfoQueryService applyInfoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApplyInfoMockMvc;

    private ApplyInfo applyInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplyInfo createEntity(EntityManager em) {
        ApplyInfo applyInfo = new ApplyInfo()
            .user(DEFAULT_USER)
            .post(DEFAULT_POST)
            .expCity(DEFAULT_EXP_CITY)
            .expSalary(DEFAULT_EXP_SALARY)
            .submitTime(DEFAULT_SUBMIT_TIME)
            .status(DEFAULT_STATUS)
            .education(DEFAULT_EDUCATION)
            .specialty(DEFAULT_SPECIALTY)
            .graduation(DEFAULT_GRADUATION);
        return applyInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplyInfo createUpdatedEntity(EntityManager em) {
        ApplyInfo applyInfo = new ApplyInfo()
            .user(UPDATED_USER)
            .post(UPDATED_POST)
            .expCity(UPDATED_EXP_CITY)
            .expSalary(UPDATED_EXP_SALARY)
            .submitTime(UPDATED_SUBMIT_TIME)
            .status(UPDATED_STATUS)
            .education(UPDATED_EDUCATION)
            .specialty(UPDATED_SPECIALTY)
            .graduation(UPDATED_GRADUATION);
        return applyInfo;
    }

    @BeforeEach
    public void initTest() {
        applyInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplyInfo() throws Exception {
        int databaseSizeBeforeCreate = applyInfoRepository.findAll().size();
        // Create the ApplyInfo
        restApplyInfoMockMvc.perform(post("/api/apply-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applyInfo)))
            .andExpect(status().isCreated());

        // Validate the ApplyInfo in the database
        List<ApplyInfo> applyInfoList = applyInfoRepository.findAll();
        assertThat(applyInfoList).hasSize(databaseSizeBeforeCreate + 1);
        ApplyInfo testApplyInfo = applyInfoList.get(applyInfoList.size() - 1);
        assertThat(testApplyInfo.getUser()).isEqualTo(DEFAULT_USER);
        assertThat(testApplyInfo.getPost()).isEqualTo(DEFAULT_POST);
        assertThat(testApplyInfo.getExpCity()).isEqualTo(DEFAULT_EXP_CITY);
        assertThat(testApplyInfo.getExpSalary()).isEqualTo(DEFAULT_EXP_SALARY);
        assertThat(testApplyInfo.getSubmitTime()).isEqualTo(DEFAULT_SUBMIT_TIME);
        assertThat(testApplyInfo.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testApplyInfo.getEducation()).isEqualTo(DEFAULT_EDUCATION);
        assertThat(testApplyInfo.getSpecialty()).isEqualTo(DEFAULT_SPECIALTY);
        assertThat(testApplyInfo.getGraduation()).isEqualTo(DEFAULT_GRADUATION);
    }

    @Test
    @Transactional
    public void createApplyInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applyInfoRepository.findAll().size();

        // Create the ApplyInfo with an existing ID
        applyInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplyInfoMockMvc.perform(post("/api/apply-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applyInfo)))
            .andExpect(status().isBadRequest());

        // Validate the ApplyInfo in the database
        List<ApplyInfo> applyInfoList = applyInfoRepository.findAll();
        assertThat(applyInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllApplyInfos() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList
        restApplyInfoMockMvc.perform(get("/api/apply-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applyInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER)))
            .andExpect(jsonPath("$.[*].post").value(hasItem(DEFAULT_POST)))
            .andExpect(jsonPath("$.[*].expCity").value(hasItem(DEFAULT_EXP_CITY)))
            .andExpect(jsonPath("$.[*].expSalary").value(hasItem(DEFAULT_EXP_SALARY.doubleValue())))
            .andExpect(jsonPath("$.[*].submitTime").value(hasItem(DEFAULT_SUBMIT_TIME.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].education").value(hasItem(DEFAULT_EDUCATION)))
            .andExpect(jsonPath("$.[*].specialty").value(hasItem(DEFAULT_SPECIALTY)))
            .andExpect(jsonPath("$.[*].graduation").value(hasItem(DEFAULT_GRADUATION)));
    }
    
    @Test
    @Transactional
    public void getApplyInfo() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get the applyInfo
        restApplyInfoMockMvc.perform(get("/api/apply-infos/{id}", applyInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(applyInfo.getId().intValue()))
            .andExpect(jsonPath("$.user").value(DEFAULT_USER))
            .andExpect(jsonPath("$.post").value(DEFAULT_POST))
            .andExpect(jsonPath("$.expCity").value(DEFAULT_EXP_CITY))
            .andExpect(jsonPath("$.expSalary").value(DEFAULT_EXP_SALARY.doubleValue()))
            .andExpect(jsonPath("$.submitTime").value(DEFAULT_SUBMIT_TIME.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.education").value(DEFAULT_EDUCATION))
            .andExpect(jsonPath("$.specialty").value(DEFAULT_SPECIALTY))
            .andExpect(jsonPath("$.graduation").value(DEFAULT_GRADUATION));
    }


    @Test
    @Transactional
    public void getApplyInfosByIdFiltering() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        Long id = applyInfo.getId();

        defaultApplyInfoShouldBeFound("id.equals=" + id);
        defaultApplyInfoShouldNotBeFound("id.notEquals=" + id);

        defaultApplyInfoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultApplyInfoShouldNotBeFound("id.greaterThan=" + id);

        defaultApplyInfoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultApplyInfoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllApplyInfosByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where user equals to DEFAULT_USER
        defaultApplyInfoShouldBeFound("user.equals=" + DEFAULT_USER);

        // Get all the applyInfoList where user equals to UPDATED_USER
        defaultApplyInfoShouldNotBeFound("user.equals=" + UPDATED_USER);
    }

    @Test
    @Transactional
    public void getAllApplyInfosByUserIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where user not equals to DEFAULT_USER
        defaultApplyInfoShouldNotBeFound("user.notEquals=" + DEFAULT_USER);

        // Get all the applyInfoList where user not equals to UPDATED_USER
        defaultApplyInfoShouldBeFound("user.notEquals=" + UPDATED_USER);
    }

    @Test
    @Transactional
    public void getAllApplyInfosByUserIsInShouldWork() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where user in DEFAULT_USER or UPDATED_USER
        defaultApplyInfoShouldBeFound("user.in=" + DEFAULT_USER + "," + UPDATED_USER);

        // Get all the applyInfoList where user equals to UPDATED_USER
        defaultApplyInfoShouldNotBeFound("user.in=" + UPDATED_USER);
    }

    @Test
    @Transactional
    public void getAllApplyInfosByUserIsNullOrNotNull() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where user is not null
        defaultApplyInfoShouldBeFound("user.specified=true");

        // Get all the applyInfoList where user is null
        defaultApplyInfoShouldNotBeFound("user.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplyInfosByUserContainsSomething() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where user contains DEFAULT_USER
        defaultApplyInfoShouldBeFound("user.contains=" + DEFAULT_USER);

        // Get all the applyInfoList where user contains UPDATED_USER
        defaultApplyInfoShouldNotBeFound("user.contains=" + UPDATED_USER);
    }

    @Test
    @Transactional
    public void getAllApplyInfosByUserNotContainsSomething() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where user does not contain DEFAULT_USER
        defaultApplyInfoShouldNotBeFound("user.doesNotContain=" + DEFAULT_USER);

        // Get all the applyInfoList where user does not contain UPDATED_USER
        defaultApplyInfoShouldBeFound("user.doesNotContain=" + UPDATED_USER);
    }


    @Test
    @Transactional
    public void getAllApplyInfosByPostIsEqualToSomething() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where post equals to DEFAULT_POST
        defaultApplyInfoShouldBeFound("post.equals=" + DEFAULT_POST);

        // Get all the applyInfoList where post equals to UPDATED_POST
        defaultApplyInfoShouldNotBeFound("post.equals=" + UPDATED_POST);
    }

    @Test
    @Transactional
    public void getAllApplyInfosByPostIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where post not equals to DEFAULT_POST
        defaultApplyInfoShouldNotBeFound("post.notEquals=" + DEFAULT_POST);

        // Get all the applyInfoList where post not equals to UPDATED_POST
        defaultApplyInfoShouldBeFound("post.notEquals=" + UPDATED_POST);
    }

    @Test
    @Transactional
    public void getAllApplyInfosByPostIsInShouldWork() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where post in DEFAULT_POST or UPDATED_POST
        defaultApplyInfoShouldBeFound("post.in=" + DEFAULT_POST + "," + UPDATED_POST);

        // Get all the applyInfoList where post equals to UPDATED_POST
        defaultApplyInfoShouldNotBeFound("post.in=" + UPDATED_POST);
    }

    @Test
    @Transactional
    public void getAllApplyInfosByPostIsNullOrNotNull() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where post is not null
        defaultApplyInfoShouldBeFound("post.specified=true");

        // Get all the applyInfoList where post is null
        defaultApplyInfoShouldNotBeFound("post.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplyInfosByPostContainsSomething() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where post contains DEFAULT_POST
        defaultApplyInfoShouldBeFound("post.contains=" + DEFAULT_POST);

        // Get all the applyInfoList where post contains UPDATED_POST
        defaultApplyInfoShouldNotBeFound("post.contains=" + UPDATED_POST);
    }

    @Test
    @Transactional
    public void getAllApplyInfosByPostNotContainsSomething() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where post does not contain DEFAULT_POST
        defaultApplyInfoShouldNotBeFound("post.doesNotContain=" + DEFAULT_POST);

        // Get all the applyInfoList where post does not contain UPDATED_POST
        defaultApplyInfoShouldBeFound("post.doesNotContain=" + UPDATED_POST);
    }


    @Test
    @Transactional
    public void getAllApplyInfosByExpCityIsEqualToSomething() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where expCity equals to DEFAULT_EXP_CITY
        defaultApplyInfoShouldBeFound("expCity.equals=" + DEFAULT_EXP_CITY);

        // Get all the applyInfoList where expCity equals to UPDATED_EXP_CITY
        defaultApplyInfoShouldNotBeFound("expCity.equals=" + UPDATED_EXP_CITY);
    }

    @Test
    @Transactional
    public void getAllApplyInfosByExpCityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where expCity not equals to DEFAULT_EXP_CITY
        defaultApplyInfoShouldNotBeFound("expCity.notEquals=" + DEFAULT_EXP_CITY);

        // Get all the applyInfoList where expCity not equals to UPDATED_EXP_CITY
        defaultApplyInfoShouldBeFound("expCity.notEquals=" + UPDATED_EXP_CITY);
    }

    @Test
    @Transactional
    public void getAllApplyInfosByExpCityIsInShouldWork() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where expCity in DEFAULT_EXP_CITY or UPDATED_EXP_CITY
        defaultApplyInfoShouldBeFound("expCity.in=" + DEFAULT_EXP_CITY + "," + UPDATED_EXP_CITY);

        // Get all the applyInfoList where expCity equals to UPDATED_EXP_CITY
        defaultApplyInfoShouldNotBeFound("expCity.in=" + UPDATED_EXP_CITY);
    }

    @Test
    @Transactional
    public void getAllApplyInfosByExpCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where expCity is not null
        defaultApplyInfoShouldBeFound("expCity.specified=true");

        // Get all the applyInfoList where expCity is null
        defaultApplyInfoShouldNotBeFound("expCity.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplyInfosByExpCityContainsSomething() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where expCity contains DEFAULT_EXP_CITY
        defaultApplyInfoShouldBeFound("expCity.contains=" + DEFAULT_EXP_CITY);

        // Get all the applyInfoList where expCity contains UPDATED_EXP_CITY
        defaultApplyInfoShouldNotBeFound("expCity.contains=" + UPDATED_EXP_CITY);
    }

    @Test
    @Transactional
    public void getAllApplyInfosByExpCityNotContainsSomething() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where expCity does not contain DEFAULT_EXP_CITY
        defaultApplyInfoShouldNotBeFound("expCity.doesNotContain=" + DEFAULT_EXP_CITY);

        // Get all the applyInfoList where expCity does not contain UPDATED_EXP_CITY
        defaultApplyInfoShouldBeFound("expCity.doesNotContain=" + UPDATED_EXP_CITY);
    }


    @Test
    @Transactional
    public void getAllApplyInfosByExpSalaryIsEqualToSomething() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where expSalary equals to DEFAULT_EXP_SALARY
        defaultApplyInfoShouldBeFound("expSalary.equals=" + DEFAULT_EXP_SALARY);

        // Get all the applyInfoList where expSalary equals to UPDATED_EXP_SALARY
        defaultApplyInfoShouldNotBeFound("expSalary.equals=" + UPDATED_EXP_SALARY);
    }

    @Test
    @Transactional
    public void getAllApplyInfosByExpSalaryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where expSalary not equals to DEFAULT_EXP_SALARY
        defaultApplyInfoShouldNotBeFound("expSalary.notEquals=" + DEFAULT_EXP_SALARY);

        // Get all the applyInfoList where expSalary not equals to UPDATED_EXP_SALARY
        defaultApplyInfoShouldBeFound("expSalary.notEquals=" + UPDATED_EXP_SALARY);
    }

    @Test
    @Transactional
    public void getAllApplyInfosByExpSalaryIsInShouldWork() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where expSalary in DEFAULT_EXP_SALARY or UPDATED_EXP_SALARY
        defaultApplyInfoShouldBeFound("expSalary.in=" + DEFAULT_EXP_SALARY + "," + UPDATED_EXP_SALARY);

        // Get all the applyInfoList where expSalary equals to UPDATED_EXP_SALARY
        defaultApplyInfoShouldNotBeFound("expSalary.in=" + UPDATED_EXP_SALARY);
    }

    @Test
    @Transactional
    public void getAllApplyInfosByExpSalaryIsNullOrNotNull() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where expSalary is not null
        defaultApplyInfoShouldBeFound("expSalary.specified=true");

        // Get all the applyInfoList where expSalary is null
        defaultApplyInfoShouldNotBeFound("expSalary.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplyInfosByExpSalaryIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where expSalary is greater than or equal to DEFAULT_EXP_SALARY
        defaultApplyInfoShouldBeFound("expSalary.greaterThanOrEqual=" + DEFAULT_EXP_SALARY);

        // Get all the applyInfoList where expSalary is greater than or equal to UPDATED_EXP_SALARY
        defaultApplyInfoShouldNotBeFound("expSalary.greaterThanOrEqual=" + UPDATED_EXP_SALARY);
    }

    @Test
    @Transactional
    public void getAllApplyInfosByExpSalaryIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where expSalary is less than or equal to DEFAULT_EXP_SALARY
        defaultApplyInfoShouldBeFound("expSalary.lessThanOrEqual=" + DEFAULT_EXP_SALARY);

        // Get all the applyInfoList where expSalary is less than or equal to SMALLER_EXP_SALARY
        defaultApplyInfoShouldNotBeFound("expSalary.lessThanOrEqual=" + SMALLER_EXP_SALARY);
    }

    @Test
    @Transactional
    public void getAllApplyInfosByExpSalaryIsLessThanSomething() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where expSalary is less than DEFAULT_EXP_SALARY
        defaultApplyInfoShouldNotBeFound("expSalary.lessThan=" + DEFAULT_EXP_SALARY);

        // Get all the applyInfoList where expSalary is less than UPDATED_EXP_SALARY
        defaultApplyInfoShouldBeFound("expSalary.lessThan=" + UPDATED_EXP_SALARY);
    }

    @Test
    @Transactional
    public void getAllApplyInfosByExpSalaryIsGreaterThanSomething() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where expSalary is greater than DEFAULT_EXP_SALARY
        defaultApplyInfoShouldNotBeFound("expSalary.greaterThan=" + DEFAULT_EXP_SALARY);

        // Get all the applyInfoList where expSalary is greater than SMALLER_EXP_SALARY
        defaultApplyInfoShouldBeFound("expSalary.greaterThan=" + SMALLER_EXP_SALARY);
    }


    @Test
    @Transactional
    public void getAllApplyInfosBySubmitTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where submitTime equals to DEFAULT_SUBMIT_TIME
        defaultApplyInfoShouldBeFound("submitTime.equals=" + DEFAULT_SUBMIT_TIME);

        // Get all the applyInfoList where submitTime equals to UPDATED_SUBMIT_TIME
        defaultApplyInfoShouldNotBeFound("submitTime.equals=" + UPDATED_SUBMIT_TIME);
    }

    @Test
    @Transactional
    public void getAllApplyInfosBySubmitTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where submitTime not equals to DEFAULT_SUBMIT_TIME
        defaultApplyInfoShouldNotBeFound("submitTime.notEquals=" + DEFAULT_SUBMIT_TIME);

        // Get all the applyInfoList where submitTime not equals to UPDATED_SUBMIT_TIME
        defaultApplyInfoShouldBeFound("submitTime.notEquals=" + UPDATED_SUBMIT_TIME);
    }

    @Test
    @Transactional
    public void getAllApplyInfosBySubmitTimeIsInShouldWork() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where submitTime in DEFAULT_SUBMIT_TIME or UPDATED_SUBMIT_TIME
        defaultApplyInfoShouldBeFound("submitTime.in=" + DEFAULT_SUBMIT_TIME + "," + UPDATED_SUBMIT_TIME);

        // Get all the applyInfoList where submitTime equals to UPDATED_SUBMIT_TIME
        defaultApplyInfoShouldNotBeFound("submitTime.in=" + UPDATED_SUBMIT_TIME);
    }

    @Test
    @Transactional
    public void getAllApplyInfosBySubmitTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where submitTime is not null
        defaultApplyInfoShouldBeFound("submitTime.specified=true");

        // Get all the applyInfoList where submitTime is null
        defaultApplyInfoShouldNotBeFound("submitTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplyInfosByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where status equals to DEFAULT_STATUS
        defaultApplyInfoShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the applyInfoList where status equals to UPDATED_STATUS
        defaultApplyInfoShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllApplyInfosByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where status not equals to DEFAULT_STATUS
        defaultApplyInfoShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the applyInfoList where status not equals to UPDATED_STATUS
        defaultApplyInfoShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllApplyInfosByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultApplyInfoShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the applyInfoList where status equals to UPDATED_STATUS
        defaultApplyInfoShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllApplyInfosByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where status is not null
        defaultApplyInfoShouldBeFound("status.specified=true");

        // Get all the applyInfoList where status is null
        defaultApplyInfoShouldNotBeFound("status.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplyInfosByStatusContainsSomething() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where status contains DEFAULT_STATUS
        defaultApplyInfoShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the applyInfoList where status contains UPDATED_STATUS
        defaultApplyInfoShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllApplyInfosByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where status does not contain DEFAULT_STATUS
        defaultApplyInfoShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the applyInfoList where status does not contain UPDATED_STATUS
        defaultApplyInfoShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }


    @Test
    @Transactional
    public void getAllApplyInfosByEducationIsEqualToSomething() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where education equals to DEFAULT_EDUCATION
        defaultApplyInfoShouldBeFound("education.equals=" + DEFAULT_EDUCATION);

        // Get all the applyInfoList where education equals to UPDATED_EDUCATION
        defaultApplyInfoShouldNotBeFound("education.equals=" + UPDATED_EDUCATION);
    }

    @Test
    @Transactional
    public void getAllApplyInfosByEducationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where education not equals to DEFAULT_EDUCATION
        defaultApplyInfoShouldNotBeFound("education.notEquals=" + DEFAULT_EDUCATION);

        // Get all the applyInfoList where education not equals to UPDATED_EDUCATION
        defaultApplyInfoShouldBeFound("education.notEquals=" + UPDATED_EDUCATION);
    }

    @Test
    @Transactional
    public void getAllApplyInfosByEducationIsInShouldWork() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where education in DEFAULT_EDUCATION or UPDATED_EDUCATION
        defaultApplyInfoShouldBeFound("education.in=" + DEFAULT_EDUCATION + "," + UPDATED_EDUCATION);

        // Get all the applyInfoList where education equals to UPDATED_EDUCATION
        defaultApplyInfoShouldNotBeFound("education.in=" + UPDATED_EDUCATION);
    }

    @Test
    @Transactional
    public void getAllApplyInfosByEducationIsNullOrNotNull() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where education is not null
        defaultApplyInfoShouldBeFound("education.specified=true");

        // Get all the applyInfoList where education is null
        defaultApplyInfoShouldNotBeFound("education.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplyInfosByEducationContainsSomething() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where education contains DEFAULT_EDUCATION
        defaultApplyInfoShouldBeFound("education.contains=" + DEFAULT_EDUCATION);

        // Get all the applyInfoList where education contains UPDATED_EDUCATION
        defaultApplyInfoShouldNotBeFound("education.contains=" + UPDATED_EDUCATION);
    }

    @Test
    @Transactional
    public void getAllApplyInfosByEducationNotContainsSomething() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where education does not contain DEFAULT_EDUCATION
        defaultApplyInfoShouldNotBeFound("education.doesNotContain=" + DEFAULT_EDUCATION);

        // Get all the applyInfoList where education does not contain UPDATED_EDUCATION
        defaultApplyInfoShouldBeFound("education.doesNotContain=" + UPDATED_EDUCATION);
    }


    @Test
    @Transactional
    public void getAllApplyInfosBySpecialtyIsEqualToSomething() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where specialty equals to DEFAULT_SPECIALTY
        defaultApplyInfoShouldBeFound("specialty.equals=" + DEFAULT_SPECIALTY);

        // Get all the applyInfoList where specialty equals to UPDATED_SPECIALTY
        defaultApplyInfoShouldNotBeFound("specialty.equals=" + UPDATED_SPECIALTY);
    }

    @Test
    @Transactional
    public void getAllApplyInfosBySpecialtyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where specialty not equals to DEFAULT_SPECIALTY
        defaultApplyInfoShouldNotBeFound("specialty.notEquals=" + DEFAULT_SPECIALTY);

        // Get all the applyInfoList where specialty not equals to UPDATED_SPECIALTY
        defaultApplyInfoShouldBeFound("specialty.notEquals=" + UPDATED_SPECIALTY);
    }

    @Test
    @Transactional
    public void getAllApplyInfosBySpecialtyIsInShouldWork() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where specialty in DEFAULT_SPECIALTY or UPDATED_SPECIALTY
        defaultApplyInfoShouldBeFound("specialty.in=" + DEFAULT_SPECIALTY + "," + UPDATED_SPECIALTY);

        // Get all the applyInfoList where specialty equals to UPDATED_SPECIALTY
        defaultApplyInfoShouldNotBeFound("specialty.in=" + UPDATED_SPECIALTY);
    }

    @Test
    @Transactional
    public void getAllApplyInfosBySpecialtyIsNullOrNotNull() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where specialty is not null
        defaultApplyInfoShouldBeFound("specialty.specified=true");

        // Get all the applyInfoList where specialty is null
        defaultApplyInfoShouldNotBeFound("specialty.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplyInfosBySpecialtyContainsSomething() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where specialty contains DEFAULT_SPECIALTY
        defaultApplyInfoShouldBeFound("specialty.contains=" + DEFAULT_SPECIALTY);

        // Get all the applyInfoList where specialty contains UPDATED_SPECIALTY
        defaultApplyInfoShouldNotBeFound("specialty.contains=" + UPDATED_SPECIALTY);
    }

    @Test
    @Transactional
    public void getAllApplyInfosBySpecialtyNotContainsSomething() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where specialty does not contain DEFAULT_SPECIALTY
        defaultApplyInfoShouldNotBeFound("specialty.doesNotContain=" + DEFAULT_SPECIALTY);

        // Get all the applyInfoList where specialty does not contain UPDATED_SPECIALTY
        defaultApplyInfoShouldBeFound("specialty.doesNotContain=" + UPDATED_SPECIALTY);
    }


    @Test
    @Transactional
    public void getAllApplyInfosByGraduationIsEqualToSomething() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where graduation equals to DEFAULT_GRADUATION
        defaultApplyInfoShouldBeFound("graduation.equals=" + DEFAULT_GRADUATION);

        // Get all the applyInfoList where graduation equals to UPDATED_GRADUATION
        defaultApplyInfoShouldNotBeFound("graduation.equals=" + UPDATED_GRADUATION);
    }

    @Test
    @Transactional
    public void getAllApplyInfosByGraduationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where graduation not equals to DEFAULT_GRADUATION
        defaultApplyInfoShouldNotBeFound("graduation.notEquals=" + DEFAULT_GRADUATION);

        // Get all the applyInfoList where graduation not equals to UPDATED_GRADUATION
        defaultApplyInfoShouldBeFound("graduation.notEquals=" + UPDATED_GRADUATION);
    }

    @Test
    @Transactional
    public void getAllApplyInfosByGraduationIsInShouldWork() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where graduation in DEFAULT_GRADUATION or UPDATED_GRADUATION
        defaultApplyInfoShouldBeFound("graduation.in=" + DEFAULT_GRADUATION + "," + UPDATED_GRADUATION);

        // Get all the applyInfoList where graduation equals to UPDATED_GRADUATION
        defaultApplyInfoShouldNotBeFound("graduation.in=" + UPDATED_GRADUATION);
    }

    @Test
    @Transactional
    public void getAllApplyInfosByGraduationIsNullOrNotNull() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where graduation is not null
        defaultApplyInfoShouldBeFound("graduation.specified=true");

        // Get all the applyInfoList where graduation is null
        defaultApplyInfoShouldNotBeFound("graduation.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplyInfosByGraduationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where graduation is greater than or equal to DEFAULT_GRADUATION
        defaultApplyInfoShouldBeFound("graduation.greaterThanOrEqual=" + DEFAULT_GRADUATION);

        // Get all the applyInfoList where graduation is greater than or equal to UPDATED_GRADUATION
        defaultApplyInfoShouldNotBeFound("graduation.greaterThanOrEqual=" + UPDATED_GRADUATION);
    }

    @Test
    @Transactional
    public void getAllApplyInfosByGraduationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where graduation is less than or equal to DEFAULT_GRADUATION
        defaultApplyInfoShouldBeFound("graduation.lessThanOrEqual=" + DEFAULT_GRADUATION);

        // Get all the applyInfoList where graduation is less than or equal to SMALLER_GRADUATION
        defaultApplyInfoShouldNotBeFound("graduation.lessThanOrEqual=" + SMALLER_GRADUATION);
    }

    @Test
    @Transactional
    public void getAllApplyInfosByGraduationIsLessThanSomething() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where graduation is less than DEFAULT_GRADUATION
        defaultApplyInfoShouldNotBeFound("graduation.lessThan=" + DEFAULT_GRADUATION);

        // Get all the applyInfoList where graduation is less than UPDATED_GRADUATION
        defaultApplyInfoShouldBeFound("graduation.lessThan=" + UPDATED_GRADUATION);
    }

    @Test
    @Transactional
    public void getAllApplyInfosByGraduationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        applyInfoRepository.saveAndFlush(applyInfo);

        // Get all the applyInfoList where graduation is greater than DEFAULT_GRADUATION
        defaultApplyInfoShouldNotBeFound("graduation.greaterThan=" + DEFAULT_GRADUATION);

        // Get all the applyInfoList where graduation is greater than SMALLER_GRADUATION
        defaultApplyInfoShouldBeFound("graduation.greaterThan=" + SMALLER_GRADUATION);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultApplyInfoShouldBeFound(String filter) throws Exception {
        restApplyInfoMockMvc.perform(get("/api/apply-infos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applyInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER)))
            .andExpect(jsonPath("$.[*].post").value(hasItem(DEFAULT_POST)))
            .andExpect(jsonPath("$.[*].expCity").value(hasItem(DEFAULT_EXP_CITY)))
            .andExpect(jsonPath("$.[*].expSalary").value(hasItem(DEFAULT_EXP_SALARY.doubleValue())))
            .andExpect(jsonPath("$.[*].submitTime").value(hasItem(DEFAULT_SUBMIT_TIME.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].education").value(hasItem(DEFAULT_EDUCATION)))
            .andExpect(jsonPath("$.[*].specialty").value(hasItem(DEFAULT_SPECIALTY)))
            .andExpect(jsonPath("$.[*].graduation").value(hasItem(DEFAULT_GRADUATION)));

        // Check, that the count call also returns 1
        restApplyInfoMockMvc.perform(get("/api/apply-infos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultApplyInfoShouldNotBeFound(String filter) throws Exception {
        restApplyInfoMockMvc.perform(get("/api/apply-infos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restApplyInfoMockMvc.perform(get("/api/apply-infos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingApplyInfo() throws Exception {
        // Get the applyInfo
        restApplyInfoMockMvc.perform(get("/api/apply-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplyInfo() throws Exception {
        // Initialize the database
        applyInfoService.save(applyInfo);

        int databaseSizeBeforeUpdate = applyInfoRepository.findAll().size();

        // Update the applyInfo
        ApplyInfo updatedApplyInfo = applyInfoRepository.findById(applyInfo.getId()).get();
        // Disconnect from session so that the updates on updatedApplyInfo are not directly saved in db
        em.detach(updatedApplyInfo);
        updatedApplyInfo
            .user(UPDATED_USER)
            .post(UPDATED_POST)
            .expCity(UPDATED_EXP_CITY)
            .expSalary(UPDATED_EXP_SALARY)
            .submitTime(UPDATED_SUBMIT_TIME)
            .status(UPDATED_STATUS)
            .education(UPDATED_EDUCATION)
            .specialty(UPDATED_SPECIALTY)
            .graduation(UPDATED_GRADUATION);

        restApplyInfoMockMvc.perform(put("/api/apply-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedApplyInfo)))
            .andExpect(status().isOk());

        // Validate the ApplyInfo in the database
        List<ApplyInfo> applyInfoList = applyInfoRepository.findAll();
        assertThat(applyInfoList).hasSize(databaseSizeBeforeUpdate);
        ApplyInfo testApplyInfo = applyInfoList.get(applyInfoList.size() - 1);
        assertThat(testApplyInfo.getUser()).isEqualTo(UPDATED_USER);
        assertThat(testApplyInfo.getPost()).isEqualTo(UPDATED_POST);
        assertThat(testApplyInfo.getExpCity()).isEqualTo(UPDATED_EXP_CITY);
        assertThat(testApplyInfo.getExpSalary()).isEqualTo(UPDATED_EXP_SALARY);
        assertThat(testApplyInfo.getSubmitTime()).isEqualTo(UPDATED_SUBMIT_TIME);
        assertThat(testApplyInfo.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testApplyInfo.getEducation()).isEqualTo(UPDATED_EDUCATION);
        assertThat(testApplyInfo.getSpecialty()).isEqualTo(UPDATED_SPECIALTY);
        assertThat(testApplyInfo.getGraduation()).isEqualTo(UPDATED_GRADUATION);
    }

    @Test
    @Transactional
    public void updateNonExistingApplyInfo() throws Exception {
        int databaseSizeBeforeUpdate = applyInfoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplyInfoMockMvc.perform(put("/api/apply-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applyInfo)))
            .andExpect(status().isBadRequest());

        // Validate the ApplyInfo in the database
        List<ApplyInfo> applyInfoList = applyInfoRepository.findAll();
        assertThat(applyInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApplyInfo() throws Exception {
        // Initialize the database
        applyInfoService.save(applyInfo);

        int databaseSizeBeforeDelete = applyInfoRepository.findAll().size();

        // Delete the applyInfo
        restApplyInfoMockMvc.perform(delete("/api/apply-infos/{id}", applyInfo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApplyInfo> applyInfoList = applyInfoRepository.findAll();
        assertThat(applyInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
