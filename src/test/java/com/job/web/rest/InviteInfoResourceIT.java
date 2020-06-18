package com.job.web.rest;

import com.job.JobApp;
import com.job.domain.InviteInfo;
import com.job.repository.InviteInfoRepository;
import com.job.service.InviteInfoService;
import com.job.service.dto.InviteInfoCriteria;
import com.job.service.InviteInfoQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link InviteInfoResource} REST controller.
 */
@SpringBootTest(classes = JobApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class InviteInfoResourceIT {

    private static final String DEFAULT_USER = "AAAAAAAAAA";
    private static final String UPDATED_USER = "BBBBBBBBBB";

    private static final String DEFAULT_POST = "AAAAAAAAAA";
    private static final String UPDATED_POST = "BBBBBBBBBB";

    private static final String DEFAULT_WORK_CITY = "AAAAAAAAAA";
    private static final String UPDATED_WORK_CITY = "BBBBBBBBBB";

    private static final Double DEFAULT_MIN_SALARY = 1D;
    private static final Double UPDATED_MIN_SALARY = 2D;
    private static final Double SMALLER_MIN_SALARY = 1D - 1D;

    private static final Double DEFAULT_MAX_SALARY = 1D;
    private static final Double UPDATED_MAX_SALARY = 2D;
    private static final Double SMALLER_MAX_SALARY = 1D - 1D;

    private static final String DEFAULT_EDUCATION = "AAAAAAAAAA";
    private static final String UPDATED_EDUCATION = "BBBBBBBBBB";

    private static final String DEFAULT_SPECIALTY = "AAAAAAAAAA";
    private static final String UPDATED_SPECIALTY = "BBBBBBBBBB";

    private static final Integer DEFAULT_GRADUATION = 1;
    private static final Integer UPDATED_GRADUATION = 2;
    private static final Integer SMALLER_GRADUATION = 1 - 1;

    private static final String DEFAULT_COMPANY = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY = "BBBBBBBBBB";

    private static final String DEFAULT_MAIL = "AAAAAAAAAA";
    private static final String UPDATED_MAIL = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_SUBMIT_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SUBMIT_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_RAQUIREMENT = "AAAAAAAAAA";
    private static final String UPDATED_RAQUIREMENT = "BBBBBBBBBB";

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    @Autowired
    private InviteInfoRepository inviteInfoRepository;

    @Autowired
    private InviteInfoService inviteInfoService;

    @Autowired
    private InviteInfoQueryService inviteInfoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInviteInfoMockMvc;

    private InviteInfo inviteInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InviteInfo createEntity(EntityManager em) {
        InviteInfo inviteInfo = new InviteInfo()
            .user(DEFAULT_USER)
            .post(DEFAULT_POST)
            .workCity(DEFAULT_WORK_CITY)
            .minSalary(DEFAULT_MIN_SALARY)
            .maxSalary(DEFAULT_MAX_SALARY)
            .education(DEFAULT_EDUCATION)
            .specialty(DEFAULT_SPECIALTY)
            .graduation(DEFAULT_GRADUATION)
            .company(DEFAULT_COMPANY)
            .mail(DEFAULT_MAIL)
            .status(DEFAULT_STATUS)
            .submitTime(DEFAULT_SUBMIT_TIME)
            .raquirement(DEFAULT_RAQUIREMENT)
            .remark(DEFAULT_REMARK);
        return inviteInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InviteInfo createUpdatedEntity(EntityManager em) {
        InviteInfo inviteInfo = new InviteInfo()
            .user(UPDATED_USER)
            .post(UPDATED_POST)
            .workCity(UPDATED_WORK_CITY)
            .minSalary(UPDATED_MIN_SALARY)
            .maxSalary(UPDATED_MAX_SALARY)
            .education(UPDATED_EDUCATION)
            .specialty(UPDATED_SPECIALTY)
            .graduation(UPDATED_GRADUATION)
            .company(UPDATED_COMPANY)
            .mail(UPDATED_MAIL)
            .status(UPDATED_STATUS)
            .submitTime(UPDATED_SUBMIT_TIME)
            .raquirement(UPDATED_RAQUIREMENT)
            .remark(UPDATED_REMARK);
        return inviteInfo;
    }

    @BeforeEach
    public void initTest() {
        inviteInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createInviteInfo() throws Exception {
        int databaseSizeBeforeCreate = inviteInfoRepository.findAll().size();
        // Create the InviteInfo
        restInviteInfoMockMvc.perform(post("/api/invite-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inviteInfo)))
            .andExpect(status().isCreated());

        // Validate the InviteInfo in the database
        List<InviteInfo> inviteInfoList = inviteInfoRepository.findAll();
        assertThat(inviteInfoList).hasSize(databaseSizeBeforeCreate + 1);
        InviteInfo testInviteInfo = inviteInfoList.get(inviteInfoList.size() - 1);
        assertThat(testInviteInfo.getUser()).isEqualTo(DEFAULT_USER);
        assertThat(testInviteInfo.getPost()).isEqualTo(DEFAULT_POST);
        assertThat(testInviteInfo.getWorkCity()).isEqualTo(DEFAULT_WORK_CITY);
        assertThat(testInviteInfo.getMinSalary()).isEqualTo(DEFAULT_MIN_SALARY);
        assertThat(testInviteInfo.getMaxSalary()).isEqualTo(DEFAULT_MAX_SALARY);
        assertThat(testInviteInfo.getEducation()).isEqualTo(DEFAULT_EDUCATION);
        assertThat(testInviteInfo.getSpecialty()).isEqualTo(DEFAULT_SPECIALTY);
        assertThat(testInviteInfo.getGraduation()).isEqualTo(DEFAULT_GRADUATION);
        assertThat(testInviteInfo.getCompany()).isEqualTo(DEFAULT_COMPANY);
        assertThat(testInviteInfo.getMail()).isEqualTo(DEFAULT_MAIL);
        assertThat(testInviteInfo.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testInviteInfo.getSubmitTime()).isEqualTo(DEFAULT_SUBMIT_TIME);
        assertThat(testInviteInfo.getRaquirement()).isEqualTo(DEFAULT_RAQUIREMENT);
        assertThat(testInviteInfo.getRemark()).isEqualTo(DEFAULT_REMARK);
    }

    @Test
    @Transactional
    public void createInviteInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inviteInfoRepository.findAll().size();

        // Create the InviteInfo with an existing ID
        inviteInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInviteInfoMockMvc.perform(post("/api/invite-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inviteInfo)))
            .andExpect(status().isBadRequest());

        // Validate the InviteInfo in the database
        List<InviteInfo> inviteInfoList = inviteInfoRepository.findAll();
        assertThat(inviteInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInviteInfos() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList
        restInviteInfoMockMvc.perform(get("/api/invite-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inviteInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER)))
            .andExpect(jsonPath("$.[*].post").value(hasItem(DEFAULT_POST)))
            .andExpect(jsonPath("$.[*].workCity").value(hasItem(DEFAULT_WORK_CITY)))
            .andExpect(jsonPath("$.[*].minSalary").value(hasItem(DEFAULT_MIN_SALARY.doubleValue())))
            .andExpect(jsonPath("$.[*].maxSalary").value(hasItem(DEFAULT_MAX_SALARY.doubleValue())))
            .andExpect(jsonPath("$.[*].education").value(hasItem(DEFAULT_EDUCATION)))
            .andExpect(jsonPath("$.[*].specialty").value(hasItem(DEFAULT_SPECIALTY)))
            .andExpect(jsonPath("$.[*].graduation").value(hasItem(DEFAULT_GRADUATION)))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY)))
            .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].submitTime").value(hasItem(DEFAULT_SUBMIT_TIME.toString())))
            .andExpect(jsonPath("$.[*].raquirement").value(hasItem(DEFAULT_RAQUIREMENT.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)));
    }
    
    @Test
    @Transactional
    public void getInviteInfo() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get the inviteInfo
        restInviteInfoMockMvc.perform(get("/api/invite-infos/{id}", inviteInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inviteInfo.getId().intValue()))
            .andExpect(jsonPath("$.user").value(DEFAULT_USER))
            .andExpect(jsonPath("$.post").value(DEFAULT_POST))
            .andExpect(jsonPath("$.workCity").value(DEFAULT_WORK_CITY))
            .andExpect(jsonPath("$.minSalary").value(DEFAULT_MIN_SALARY.doubleValue()))
            .andExpect(jsonPath("$.maxSalary").value(DEFAULT_MAX_SALARY.doubleValue()))
            .andExpect(jsonPath("$.education").value(DEFAULT_EDUCATION))
            .andExpect(jsonPath("$.specialty").value(DEFAULT_SPECIALTY))
            .andExpect(jsonPath("$.graduation").value(DEFAULT_GRADUATION))
            .andExpect(jsonPath("$.company").value(DEFAULT_COMPANY))
            .andExpect(jsonPath("$.mail").value(DEFAULT_MAIL))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.submitTime").value(DEFAULT_SUBMIT_TIME.toString()))
            .andExpect(jsonPath("$.raquirement").value(DEFAULT_RAQUIREMENT.toString()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK));
    }


    @Test
    @Transactional
    public void getInviteInfosByIdFiltering() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        Long id = inviteInfo.getId();

        defaultInviteInfoShouldBeFound("id.equals=" + id);
        defaultInviteInfoShouldNotBeFound("id.notEquals=" + id);

        defaultInviteInfoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultInviteInfoShouldNotBeFound("id.greaterThan=" + id);

        defaultInviteInfoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultInviteInfoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllInviteInfosByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where user equals to DEFAULT_USER
        defaultInviteInfoShouldBeFound("user.equals=" + DEFAULT_USER);

        // Get all the inviteInfoList where user equals to UPDATED_USER
        defaultInviteInfoShouldNotBeFound("user.equals=" + UPDATED_USER);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByUserIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where user not equals to DEFAULT_USER
        defaultInviteInfoShouldNotBeFound("user.notEquals=" + DEFAULT_USER);

        // Get all the inviteInfoList where user not equals to UPDATED_USER
        defaultInviteInfoShouldBeFound("user.notEquals=" + UPDATED_USER);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByUserIsInShouldWork() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where user in DEFAULT_USER or UPDATED_USER
        defaultInviteInfoShouldBeFound("user.in=" + DEFAULT_USER + "," + UPDATED_USER);

        // Get all the inviteInfoList where user equals to UPDATED_USER
        defaultInviteInfoShouldNotBeFound("user.in=" + UPDATED_USER);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByUserIsNullOrNotNull() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where user is not null
        defaultInviteInfoShouldBeFound("user.specified=true");

        // Get all the inviteInfoList where user is null
        defaultInviteInfoShouldNotBeFound("user.specified=false");
    }
                @Test
    @Transactional
    public void getAllInviteInfosByUserContainsSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where user contains DEFAULT_USER
        defaultInviteInfoShouldBeFound("user.contains=" + DEFAULT_USER);

        // Get all the inviteInfoList where user contains UPDATED_USER
        defaultInviteInfoShouldNotBeFound("user.contains=" + UPDATED_USER);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByUserNotContainsSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where user does not contain DEFAULT_USER
        defaultInviteInfoShouldNotBeFound("user.doesNotContain=" + DEFAULT_USER);

        // Get all the inviteInfoList where user does not contain UPDATED_USER
        defaultInviteInfoShouldBeFound("user.doesNotContain=" + UPDATED_USER);
    }


    @Test
    @Transactional
    public void getAllInviteInfosByPostIsEqualToSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where post equals to DEFAULT_POST
        defaultInviteInfoShouldBeFound("post.equals=" + DEFAULT_POST);

        // Get all the inviteInfoList where post equals to UPDATED_POST
        defaultInviteInfoShouldNotBeFound("post.equals=" + UPDATED_POST);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByPostIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where post not equals to DEFAULT_POST
        defaultInviteInfoShouldNotBeFound("post.notEquals=" + DEFAULT_POST);

        // Get all the inviteInfoList where post not equals to UPDATED_POST
        defaultInviteInfoShouldBeFound("post.notEquals=" + UPDATED_POST);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByPostIsInShouldWork() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where post in DEFAULT_POST or UPDATED_POST
        defaultInviteInfoShouldBeFound("post.in=" + DEFAULT_POST + "," + UPDATED_POST);

        // Get all the inviteInfoList where post equals to UPDATED_POST
        defaultInviteInfoShouldNotBeFound("post.in=" + UPDATED_POST);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByPostIsNullOrNotNull() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where post is not null
        defaultInviteInfoShouldBeFound("post.specified=true");

        // Get all the inviteInfoList where post is null
        defaultInviteInfoShouldNotBeFound("post.specified=false");
    }
                @Test
    @Transactional
    public void getAllInviteInfosByPostContainsSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where post contains DEFAULT_POST
        defaultInviteInfoShouldBeFound("post.contains=" + DEFAULT_POST);

        // Get all the inviteInfoList where post contains UPDATED_POST
        defaultInviteInfoShouldNotBeFound("post.contains=" + UPDATED_POST);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByPostNotContainsSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where post does not contain DEFAULT_POST
        defaultInviteInfoShouldNotBeFound("post.doesNotContain=" + DEFAULT_POST);

        // Get all the inviteInfoList where post does not contain UPDATED_POST
        defaultInviteInfoShouldBeFound("post.doesNotContain=" + UPDATED_POST);
    }


    @Test
    @Transactional
    public void getAllInviteInfosByWorkCityIsEqualToSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where workCity equals to DEFAULT_WORK_CITY
        defaultInviteInfoShouldBeFound("workCity.equals=" + DEFAULT_WORK_CITY);

        // Get all the inviteInfoList where workCity equals to UPDATED_WORK_CITY
        defaultInviteInfoShouldNotBeFound("workCity.equals=" + UPDATED_WORK_CITY);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByWorkCityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where workCity not equals to DEFAULT_WORK_CITY
        defaultInviteInfoShouldNotBeFound("workCity.notEquals=" + DEFAULT_WORK_CITY);

        // Get all the inviteInfoList where workCity not equals to UPDATED_WORK_CITY
        defaultInviteInfoShouldBeFound("workCity.notEquals=" + UPDATED_WORK_CITY);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByWorkCityIsInShouldWork() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where workCity in DEFAULT_WORK_CITY or UPDATED_WORK_CITY
        defaultInviteInfoShouldBeFound("workCity.in=" + DEFAULT_WORK_CITY + "," + UPDATED_WORK_CITY);

        // Get all the inviteInfoList where workCity equals to UPDATED_WORK_CITY
        defaultInviteInfoShouldNotBeFound("workCity.in=" + UPDATED_WORK_CITY);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByWorkCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where workCity is not null
        defaultInviteInfoShouldBeFound("workCity.specified=true");

        // Get all the inviteInfoList where workCity is null
        defaultInviteInfoShouldNotBeFound("workCity.specified=false");
    }
                @Test
    @Transactional
    public void getAllInviteInfosByWorkCityContainsSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where workCity contains DEFAULT_WORK_CITY
        defaultInviteInfoShouldBeFound("workCity.contains=" + DEFAULT_WORK_CITY);

        // Get all the inviteInfoList where workCity contains UPDATED_WORK_CITY
        defaultInviteInfoShouldNotBeFound("workCity.contains=" + UPDATED_WORK_CITY);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByWorkCityNotContainsSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where workCity does not contain DEFAULT_WORK_CITY
        defaultInviteInfoShouldNotBeFound("workCity.doesNotContain=" + DEFAULT_WORK_CITY);

        // Get all the inviteInfoList where workCity does not contain UPDATED_WORK_CITY
        defaultInviteInfoShouldBeFound("workCity.doesNotContain=" + UPDATED_WORK_CITY);
    }


    @Test
    @Transactional
    public void getAllInviteInfosByMinSalaryIsEqualToSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where minSalary equals to DEFAULT_MIN_SALARY
        defaultInviteInfoShouldBeFound("minSalary.equals=" + DEFAULT_MIN_SALARY);

        // Get all the inviteInfoList where minSalary equals to UPDATED_MIN_SALARY
        defaultInviteInfoShouldNotBeFound("minSalary.equals=" + UPDATED_MIN_SALARY);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByMinSalaryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where minSalary not equals to DEFAULT_MIN_SALARY
        defaultInviteInfoShouldNotBeFound("minSalary.notEquals=" + DEFAULT_MIN_SALARY);

        // Get all the inviteInfoList where minSalary not equals to UPDATED_MIN_SALARY
        defaultInviteInfoShouldBeFound("minSalary.notEquals=" + UPDATED_MIN_SALARY);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByMinSalaryIsInShouldWork() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where minSalary in DEFAULT_MIN_SALARY or UPDATED_MIN_SALARY
        defaultInviteInfoShouldBeFound("minSalary.in=" + DEFAULT_MIN_SALARY + "," + UPDATED_MIN_SALARY);

        // Get all the inviteInfoList where minSalary equals to UPDATED_MIN_SALARY
        defaultInviteInfoShouldNotBeFound("minSalary.in=" + UPDATED_MIN_SALARY);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByMinSalaryIsNullOrNotNull() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where minSalary is not null
        defaultInviteInfoShouldBeFound("minSalary.specified=true");

        // Get all the inviteInfoList where minSalary is null
        defaultInviteInfoShouldNotBeFound("minSalary.specified=false");
    }

    @Test
    @Transactional
    public void getAllInviteInfosByMinSalaryIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where minSalary is greater than or equal to DEFAULT_MIN_SALARY
        defaultInviteInfoShouldBeFound("minSalary.greaterThanOrEqual=" + DEFAULT_MIN_SALARY);

        // Get all the inviteInfoList where minSalary is greater than or equal to UPDATED_MIN_SALARY
        defaultInviteInfoShouldNotBeFound("minSalary.greaterThanOrEqual=" + UPDATED_MIN_SALARY);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByMinSalaryIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where minSalary is less than or equal to DEFAULT_MIN_SALARY
        defaultInviteInfoShouldBeFound("minSalary.lessThanOrEqual=" + DEFAULT_MIN_SALARY);

        // Get all the inviteInfoList where minSalary is less than or equal to SMALLER_MIN_SALARY
        defaultInviteInfoShouldNotBeFound("minSalary.lessThanOrEqual=" + SMALLER_MIN_SALARY);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByMinSalaryIsLessThanSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where minSalary is less than DEFAULT_MIN_SALARY
        defaultInviteInfoShouldNotBeFound("minSalary.lessThan=" + DEFAULT_MIN_SALARY);

        // Get all the inviteInfoList where minSalary is less than UPDATED_MIN_SALARY
        defaultInviteInfoShouldBeFound("minSalary.lessThan=" + UPDATED_MIN_SALARY);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByMinSalaryIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where minSalary is greater than DEFAULT_MIN_SALARY
        defaultInviteInfoShouldNotBeFound("minSalary.greaterThan=" + DEFAULT_MIN_SALARY);

        // Get all the inviteInfoList where minSalary is greater than SMALLER_MIN_SALARY
        defaultInviteInfoShouldBeFound("minSalary.greaterThan=" + SMALLER_MIN_SALARY);
    }


    @Test
    @Transactional
    public void getAllInviteInfosByMaxSalaryIsEqualToSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where maxSalary equals to DEFAULT_MAX_SALARY
        defaultInviteInfoShouldBeFound("maxSalary.equals=" + DEFAULT_MAX_SALARY);

        // Get all the inviteInfoList where maxSalary equals to UPDATED_MAX_SALARY
        defaultInviteInfoShouldNotBeFound("maxSalary.equals=" + UPDATED_MAX_SALARY);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByMaxSalaryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where maxSalary not equals to DEFAULT_MAX_SALARY
        defaultInviteInfoShouldNotBeFound("maxSalary.notEquals=" + DEFAULT_MAX_SALARY);

        // Get all the inviteInfoList where maxSalary not equals to UPDATED_MAX_SALARY
        defaultInviteInfoShouldBeFound("maxSalary.notEquals=" + UPDATED_MAX_SALARY);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByMaxSalaryIsInShouldWork() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where maxSalary in DEFAULT_MAX_SALARY or UPDATED_MAX_SALARY
        defaultInviteInfoShouldBeFound("maxSalary.in=" + DEFAULT_MAX_SALARY + "," + UPDATED_MAX_SALARY);

        // Get all the inviteInfoList where maxSalary equals to UPDATED_MAX_SALARY
        defaultInviteInfoShouldNotBeFound("maxSalary.in=" + UPDATED_MAX_SALARY);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByMaxSalaryIsNullOrNotNull() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where maxSalary is not null
        defaultInviteInfoShouldBeFound("maxSalary.specified=true");

        // Get all the inviteInfoList where maxSalary is null
        defaultInviteInfoShouldNotBeFound("maxSalary.specified=false");
    }

    @Test
    @Transactional
    public void getAllInviteInfosByMaxSalaryIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where maxSalary is greater than or equal to DEFAULT_MAX_SALARY
        defaultInviteInfoShouldBeFound("maxSalary.greaterThanOrEqual=" + DEFAULT_MAX_SALARY);

        // Get all the inviteInfoList where maxSalary is greater than or equal to UPDATED_MAX_SALARY
        defaultInviteInfoShouldNotBeFound("maxSalary.greaterThanOrEqual=" + UPDATED_MAX_SALARY);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByMaxSalaryIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where maxSalary is less than or equal to DEFAULT_MAX_SALARY
        defaultInviteInfoShouldBeFound("maxSalary.lessThanOrEqual=" + DEFAULT_MAX_SALARY);

        // Get all the inviteInfoList where maxSalary is less than or equal to SMALLER_MAX_SALARY
        defaultInviteInfoShouldNotBeFound("maxSalary.lessThanOrEqual=" + SMALLER_MAX_SALARY);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByMaxSalaryIsLessThanSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where maxSalary is less than DEFAULT_MAX_SALARY
        defaultInviteInfoShouldNotBeFound("maxSalary.lessThan=" + DEFAULT_MAX_SALARY);

        // Get all the inviteInfoList where maxSalary is less than UPDATED_MAX_SALARY
        defaultInviteInfoShouldBeFound("maxSalary.lessThan=" + UPDATED_MAX_SALARY);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByMaxSalaryIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where maxSalary is greater than DEFAULT_MAX_SALARY
        defaultInviteInfoShouldNotBeFound("maxSalary.greaterThan=" + DEFAULT_MAX_SALARY);

        // Get all the inviteInfoList where maxSalary is greater than SMALLER_MAX_SALARY
        defaultInviteInfoShouldBeFound("maxSalary.greaterThan=" + SMALLER_MAX_SALARY);
    }


    @Test
    @Transactional
    public void getAllInviteInfosByEducationIsEqualToSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where education equals to DEFAULT_EDUCATION
        defaultInviteInfoShouldBeFound("education.equals=" + DEFAULT_EDUCATION);

        // Get all the inviteInfoList where education equals to UPDATED_EDUCATION
        defaultInviteInfoShouldNotBeFound("education.equals=" + UPDATED_EDUCATION);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByEducationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where education not equals to DEFAULT_EDUCATION
        defaultInviteInfoShouldNotBeFound("education.notEquals=" + DEFAULT_EDUCATION);

        // Get all the inviteInfoList where education not equals to UPDATED_EDUCATION
        defaultInviteInfoShouldBeFound("education.notEquals=" + UPDATED_EDUCATION);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByEducationIsInShouldWork() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where education in DEFAULT_EDUCATION or UPDATED_EDUCATION
        defaultInviteInfoShouldBeFound("education.in=" + DEFAULT_EDUCATION + "," + UPDATED_EDUCATION);

        // Get all the inviteInfoList where education equals to UPDATED_EDUCATION
        defaultInviteInfoShouldNotBeFound("education.in=" + UPDATED_EDUCATION);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByEducationIsNullOrNotNull() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where education is not null
        defaultInviteInfoShouldBeFound("education.specified=true");

        // Get all the inviteInfoList where education is null
        defaultInviteInfoShouldNotBeFound("education.specified=false");
    }
                @Test
    @Transactional
    public void getAllInviteInfosByEducationContainsSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where education contains DEFAULT_EDUCATION
        defaultInviteInfoShouldBeFound("education.contains=" + DEFAULT_EDUCATION);

        // Get all the inviteInfoList where education contains UPDATED_EDUCATION
        defaultInviteInfoShouldNotBeFound("education.contains=" + UPDATED_EDUCATION);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByEducationNotContainsSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where education does not contain DEFAULT_EDUCATION
        defaultInviteInfoShouldNotBeFound("education.doesNotContain=" + DEFAULT_EDUCATION);

        // Get all the inviteInfoList where education does not contain UPDATED_EDUCATION
        defaultInviteInfoShouldBeFound("education.doesNotContain=" + UPDATED_EDUCATION);
    }


    @Test
    @Transactional
    public void getAllInviteInfosBySpecialtyIsEqualToSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where specialty equals to DEFAULT_SPECIALTY
        defaultInviteInfoShouldBeFound("specialty.equals=" + DEFAULT_SPECIALTY);

        // Get all the inviteInfoList where specialty equals to UPDATED_SPECIALTY
        defaultInviteInfoShouldNotBeFound("specialty.equals=" + UPDATED_SPECIALTY);
    }

    @Test
    @Transactional
    public void getAllInviteInfosBySpecialtyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where specialty not equals to DEFAULT_SPECIALTY
        defaultInviteInfoShouldNotBeFound("specialty.notEquals=" + DEFAULT_SPECIALTY);

        // Get all the inviteInfoList where specialty not equals to UPDATED_SPECIALTY
        defaultInviteInfoShouldBeFound("specialty.notEquals=" + UPDATED_SPECIALTY);
    }

    @Test
    @Transactional
    public void getAllInviteInfosBySpecialtyIsInShouldWork() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where specialty in DEFAULT_SPECIALTY or UPDATED_SPECIALTY
        defaultInviteInfoShouldBeFound("specialty.in=" + DEFAULT_SPECIALTY + "," + UPDATED_SPECIALTY);

        // Get all the inviteInfoList where specialty equals to UPDATED_SPECIALTY
        defaultInviteInfoShouldNotBeFound("specialty.in=" + UPDATED_SPECIALTY);
    }

    @Test
    @Transactional
    public void getAllInviteInfosBySpecialtyIsNullOrNotNull() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where specialty is not null
        defaultInviteInfoShouldBeFound("specialty.specified=true");

        // Get all the inviteInfoList where specialty is null
        defaultInviteInfoShouldNotBeFound("specialty.specified=false");
    }
                @Test
    @Transactional
    public void getAllInviteInfosBySpecialtyContainsSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where specialty contains DEFAULT_SPECIALTY
        defaultInviteInfoShouldBeFound("specialty.contains=" + DEFAULT_SPECIALTY);

        // Get all the inviteInfoList where specialty contains UPDATED_SPECIALTY
        defaultInviteInfoShouldNotBeFound("specialty.contains=" + UPDATED_SPECIALTY);
    }

    @Test
    @Transactional
    public void getAllInviteInfosBySpecialtyNotContainsSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where specialty does not contain DEFAULT_SPECIALTY
        defaultInviteInfoShouldNotBeFound("specialty.doesNotContain=" + DEFAULT_SPECIALTY);

        // Get all the inviteInfoList where specialty does not contain UPDATED_SPECIALTY
        defaultInviteInfoShouldBeFound("specialty.doesNotContain=" + UPDATED_SPECIALTY);
    }


    @Test
    @Transactional
    public void getAllInviteInfosByGraduationIsEqualToSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where graduation equals to DEFAULT_GRADUATION
        defaultInviteInfoShouldBeFound("graduation.equals=" + DEFAULT_GRADUATION);

        // Get all the inviteInfoList where graduation equals to UPDATED_GRADUATION
        defaultInviteInfoShouldNotBeFound("graduation.equals=" + UPDATED_GRADUATION);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByGraduationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where graduation not equals to DEFAULT_GRADUATION
        defaultInviteInfoShouldNotBeFound("graduation.notEquals=" + DEFAULT_GRADUATION);

        // Get all the inviteInfoList where graduation not equals to UPDATED_GRADUATION
        defaultInviteInfoShouldBeFound("graduation.notEquals=" + UPDATED_GRADUATION);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByGraduationIsInShouldWork() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where graduation in DEFAULT_GRADUATION or UPDATED_GRADUATION
        defaultInviteInfoShouldBeFound("graduation.in=" + DEFAULT_GRADUATION + "," + UPDATED_GRADUATION);

        // Get all the inviteInfoList where graduation equals to UPDATED_GRADUATION
        defaultInviteInfoShouldNotBeFound("graduation.in=" + UPDATED_GRADUATION);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByGraduationIsNullOrNotNull() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where graduation is not null
        defaultInviteInfoShouldBeFound("graduation.specified=true");

        // Get all the inviteInfoList where graduation is null
        defaultInviteInfoShouldNotBeFound("graduation.specified=false");
    }

    @Test
    @Transactional
    public void getAllInviteInfosByGraduationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where graduation is greater than or equal to DEFAULT_GRADUATION
        defaultInviteInfoShouldBeFound("graduation.greaterThanOrEqual=" + DEFAULT_GRADUATION);

        // Get all the inviteInfoList where graduation is greater than or equal to UPDATED_GRADUATION
        defaultInviteInfoShouldNotBeFound("graduation.greaterThanOrEqual=" + UPDATED_GRADUATION);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByGraduationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where graduation is less than or equal to DEFAULT_GRADUATION
        defaultInviteInfoShouldBeFound("graduation.lessThanOrEqual=" + DEFAULT_GRADUATION);

        // Get all the inviteInfoList where graduation is less than or equal to SMALLER_GRADUATION
        defaultInviteInfoShouldNotBeFound("graduation.lessThanOrEqual=" + SMALLER_GRADUATION);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByGraduationIsLessThanSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where graduation is less than DEFAULT_GRADUATION
        defaultInviteInfoShouldNotBeFound("graduation.lessThan=" + DEFAULT_GRADUATION);

        // Get all the inviteInfoList where graduation is less than UPDATED_GRADUATION
        defaultInviteInfoShouldBeFound("graduation.lessThan=" + UPDATED_GRADUATION);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByGraduationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where graduation is greater than DEFAULT_GRADUATION
        defaultInviteInfoShouldNotBeFound("graduation.greaterThan=" + DEFAULT_GRADUATION);

        // Get all the inviteInfoList where graduation is greater than SMALLER_GRADUATION
        defaultInviteInfoShouldBeFound("graduation.greaterThan=" + SMALLER_GRADUATION);
    }


    @Test
    @Transactional
    public void getAllInviteInfosByCompanyIsEqualToSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where company equals to DEFAULT_COMPANY
        defaultInviteInfoShouldBeFound("company.equals=" + DEFAULT_COMPANY);

        // Get all the inviteInfoList where company equals to UPDATED_COMPANY
        defaultInviteInfoShouldNotBeFound("company.equals=" + UPDATED_COMPANY);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByCompanyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where company not equals to DEFAULT_COMPANY
        defaultInviteInfoShouldNotBeFound("company.notEquals=" + DEFAULT_COMPANY);

        // Get all the inviteInfoList where company not equals to UPDATED_COMPANY
        defaultInviteInfoShouldBeFound("company.notEquals=" + UPDATED_COMPANY);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByCompanyIsInShouldWork() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where company in DEFAULT_COMPANY or UPDATED_COMPANY
        defaultInviteInfoShouldBeFound("company.in=" + DEFAULT_COMPANY + "," + UPDATED_COMPANY);

        // Get all the inviteInfoList where company equals to UPDATED_COMPANY
        defaultInviteInfoShouldNotBeFound("company.in=" + UPDATED_COMPANY);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByCompanyIsNullOrNotNull() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where company is not null
        defaultInviteInfoShouldBeFound("company.specified=true");

        // Get all the inviteInfoList where company is null
        defaultInviteInfoShouldNotBeFound("company.specified=false");
    }
                @Test
    @Transactional
    public void getAllInviteInfosByCompanyContainsSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where company contains DEFAULT_COMPANY
        defaultInviteInfoShouldBeFound("company.contains=" + DEFAULT_COMPANY);

        // Get all the inviteInfoList where company contains UPDATED_COMPANY
        defaultInviteInfoShouldNotBeFound("company.contains=" + UPDATED_COMPANY);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByCompanyNotContainsSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where company does not contain DEFAULT_COMPANY
        defaultInviteInfoShouldNotBeFound("company.doesNotContain=" + DEFAULT_COMPANY);

        // Get all the inviteInfoList where company does not contain UPDATED_COMPANY
        defaultInviteInfoShouldBeFound("company.doesNotContain=" + UPDATED_COMPANY);
    }


    @Test
    @Transactional
    public void getAllInviteInfosByMailIsEqualToSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where mail equals to DEFAULT_MAIL
        defaultInviteInfoShouldBeFound("mail.equals=" + DEFAULT_MAIL);

        // Get all the inviteInfoList where mail equals to UPDATED_MAIL
        defaultInviteInfoShouldNotBeFound("mail.equals=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByMailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where mail not equals to DEFAULT_MAIL
        defaultInviteInfoShouldNotBeFound("mail.notEquals=" + DEFAULT_MAIL);

        // Get all the inviteInfoList where mail not equals to UPDATED_MAIL
        defaultInviteInfoShouldBeFound("mail.notEquals=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByMailIsInShouldWork() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where mail in DEFAULT_MAIL or UPDATED_MAIL
        defaultInviteInfoShouldBeFound("mail.in=" + DEFAULT_MAIL + "," + UPDATED_MAIL);

        // Get all the inviteInfoList where mail equals to UPDATED_MAIL
        defaultInviteInfoShouldNotBeFound("mail.in=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByMailIsNullOrNotNull() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where mail is not null
        defaultInviteInfoShouldBeFound("mail.specified=true");

        // Get all the inviteInfoList where mail is null
        defaultInviteInfoShouldNotBeFound("mail.specified=false");
    }
                @Test
    @Transactional
    public void getAllInviteInfosByMailContainsSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where mail contains DEFAULT_MAIL
        defaultInviteInfoShouldBeFound("mail.contains=" + DEFAULT_MAIL);

        // Get all the inviteInfoList where mail contains UPDATED_MAIL
        defaultInviteInfoShouldNotBeFound("mail.contains=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByMailNotContainsSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where mail does not contain DEFAULT_MAIL
        defaultInviteInfoShouldNotBeFound("mail.doesNotContain=" + DEFAULT_MAIL);

        // Get all the inviteInfoList where mail does not contain UPDATED_MAIL
        defaultInviteInfoShouldBeFound("mail.doesNotContain=" + UPDATED_MAIL);
    }


    @Test
    @Transactional
    public void getAllInviteInfosByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where status equals to DEFAULT_STATUS
        defaultInviteInfoShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the inviteInfoList where status equals to UPDATED_STATUS
        defaultInviteInfoShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where status not equals to DEFAULT_STATUS
        defaultInviteInfoShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the inviteInfoList where status not equals to UPDATED_STATUS
        defaultInviteInfoShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultInviteInfoShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the inviteInfoList where status equals to UPDATED_STATUS
        defaultInviteInfoShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where status is not null
        defaultInviteInfoShouldBeFound("status.specified=true");

        // Get all the inviteInfoList where status is null
        defaultInviteInfoShouldNotBeFound("status.specified=false");
    }
                @Test
    @Transactional
    public void getAllInviteInfosByStatusContainsSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where status contains DEFAULT_STATUS
        defaultInviteInfoShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the inviteInfoList where status contains UPDATED_STATUS
        defaultInviteInfoShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where status does not contain DEFAULT_STATUS
        defaultInviteInfoShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the inviteInfoList where status does not contain UPDATED_STATUS
        defaultInviteInfoShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }


    @Test
    @Transactional
    public void getAllInviteInfosBySubmitTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where submitTime equals to DEFAULT_SUBMIT_TIME
        defaultInviteInfoShouldBeFound("submitTime.equals=" + DEFAULT_SUBMIT_TIME);

        // Get all the inviteInfoList where submitTime equals to UPDATED_SUBMIT_TIME
        defaultInviteInfoShouldNotBeFound("submitTime.equals=" + UPDATED_SUBMIT_TIME);
    }

    @Test
    @Transactional
    public void getAllInviteInfosBySubmitTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where submitTime not equals to DEFAULT_SUBMIT_TIME
        defaultInviteInfoShouldNotBeFound("submitTime.notEquals=" + DEFAULT_SUBMIT_TIME);

        // Get all the inviteInfoList where submitTime not equals to UPDATED_SUBMIT_TIME
        defaultInviteInfoShouldBeFound("submitTime.notEquals=" + UPDATED_SUBMIT_TIME);
    }

    @Test
    @Transactional
    public void getAllInviteInfosBySubmitTimeIsInShouldWork() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where submitTime in DEFAULT_SUBMIT_TIME or UPDATED_SUBMIT_TIME
        defaultInviteInfoShouldBeFound("submitTime.in=" + DEFAULT_SUBMIT_TIME + "," + UPDATED_SUBMIT_TIME);

        // Get all the inviteInfoList where submitTime equals to UPDATED_SUBMIT_TIME
        defaultInviteInfoShouldNotBeFound("submitTime.in=" + UPDATED_SUBMIT_TIME);
    }

    @Test
    @Transactional
    public void getAllInviteInfosBySubmitTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where submitTime is not null
        defaultInviteInfoShouldBeFound("submitTime.specified=true");

        // Get all the inviteInfoList where submitTime is null
        defaultInviteInfoShouldNotBeFound("submitTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllInviteInfosByRemarkIsEqualToSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where remark equals to DEFAULT_REMARK
        defaultInviteInfoShouldBeFound("remark.equals=" + DEFAULT_REMARK);

        // Get all the inviteInfoList where remark equals to UPDATED_REMARK
        defaultInviteInfoShouldNotBeFound("remark.equals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByRemarkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where remark not equals to DEFAULT_REMARK
        defaultInviteInfoShouldNotBeFound("remark.notEquals=" + DEFAULT_REMARK);

        // Get all the inviteInfoList where remark not equals to UPDATED_REMARK
        defaultInviteInfoShouldBeFound("remark.notEquals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByRemarkIsInShouldWork() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where remark in DEFAULT_REMARK or UPDATED_REMARK
        defaultInviteInfoShouldBeFound("remark.in=" + DEFAULT_REMARK + "," + UPDATED_REMARK);

        // Get all the inviteInfoList where remark equals to UPDATED_REMARK
        defaultInviteInfoShouldNotBeFound("remark.in=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByRemarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where remark is not null
        defaultInviteInfoShouldBeFound("remark.specified=true");

        // Get all the inviteInfoList where remark is null
        defaultInviteInfoShouldNotBeFound("remark.specified=false");
    }
                @Test
    @Transactional
    public void getAllInviteInfosByRemarkContainsSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where remark contains DEFAULT_REMARK
        defaultInviteInfoShouldBeFound("remark.contains=" + DEFAULT_REMARK);

        // Get all the inviteInfoList where remark contains UPDATED_REMARK
        defaultInviteInfoShouldNotBeFound("remark.contains=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllInviteInfosByRemarkNotContainsSomething() throws Exception {
        // Initialize the database
        inviteInfoRepository.saveAndFlush(inviteInfo);

        // Get all the inviteInfoList where remark does not contain DEFAULT_REMARK
        defaultInviteInfoShouldNotBeFound("remark.doesNotContain=" + DEFAULT_REMARK);

        // Get all the inviteInfoList where remark does not contain UPDATED_REMARK
        defaultInviteInfoShouldBeFound("remark.doesNotContain=" + UPDATED_REMARK);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInviteInfoShouldBeFound(String filter) throws Exception {
        restInviteInfoMockMvc.perform(get("/api/invite-infos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inviteInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER)))
            .andExpect(jsonPath("$.[*].post").value(hasItem(DEFAULT_POST)))
            .andExpect(jsonPath("$.[*].workCity").value(hasItem(DEFAULT_WORK_CITY)))
            .andExpect(jsonPath("$.[*].minSalary").value(hasItem(DEFAULT_MIN_SALARY.doubleValue())))
            .andExpect(jsonPath("$.[*].maxSalary").value(hasItem(DEFAULT_MAX_SALARY.doubleValue())))
            .andExpect(jsonPath("$.[*].education").value(hasItem(DEFAULT_EDUCATION)))
            .andExpect(jsonPath("$.[*].specialty").value(hasItem(DEFAULT_SPECIALTY)))
            .andExpect(jsonPath("$.[*].graduation").value(hasItem(DEFAULT_GRADUATION)))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY)))
            .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].submitTime").value(hasItem(DEFAULT_SUBMIT_TIME.toString())))
            .andExpect(jsonPath("$.[*].raquirement").value(hasItem(DEFAULT_RAQUIREMENT.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)));

        // Check, that the count call also returns 1
        restInviteInfoMockMvc.perform(get("/api/invite-infos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInviteInfoShouldNotBeFound(String filter) throws Exception {
        restInviteInfoMockMvc.perform(get("/api/invite-infos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInviteInfoMockMvc.perform(get("/api/invite-infos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingInviteInfo() throws Exception {
        // Get the inviteInfo
        restInviteInfoMockMvc.perform(get("/api/invite-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInviteInfo() throws Exception {
        // Initialize the database
        inviteInfoService.save(inviteInfo);

        int databaseSizeBeforeUpdate = inviteInfoRepository.findAll().size();

        // Update the inviteInfo
        InviteInfo updatedInviteInfo = inviteInfoRepository.findById(inviteInfo.getId()).get();
        // Disconnect from session so that the updates on updatedInviteInfo are not directly saved in db
        em.detach(updatedInviteInfo);
        updatedInviteInfo
            .user(UPDATED_USER)
            .post(UPDATED_POST)
            .workCity(UPDATED_WORK_CITY)
            .minSalary(UPDATED_MIN_SALARY)
            .maxSalary(UPDATED_MAX_SALARY)
            .education(UPDATED_EDUCATION)
            .specialty(UPDATED_SPECIALTY)
            .graduation(UPDATED_GRADUATION)
            .company(UPDATED_COMPANY)
            .mail(UPDATED_MAIL)
            .status(UPDATED_STATUS)
            .submitTime(UPDATED_SUBMIT_TIME)
            .raquirement(UPDATED_RAQUIREMENT)
            .remark(UPDATED_REMARK);

        restInviteInfoMockMvc.perform(put("/api/invite-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedInviteInfo)))
            .andExpect(status().isOk());

        // Validate the InviteInfo in the database
        List<InviteInfo> inviteInfoList = inviteInfoRepository.findAll();
        assertThat(inviteInfoList).hasSize(databaseSizeBeforeUpdate);
        InviteInfo testInviteInfo = inviteInfoList.get(inviteInfoList.size() - 1);
        assertThat(testInviteInfo.getUser()).isEqualTo(UPDATED_USER);
        assertThat(testInviteInfo.getPost()).isEqualTo(UPDATED_POST);
        assertThat(testInviteInfo.getWorkCity()).isEqualTo(UPDATED_WORK_CITY);
        assertThat(testInviteInfo.getMinSalary()).isEqualTo(UPDATED_MIN_SALARY);
        assertThat(testInviteInfo.getMaxSalary()).isEqualTo(UPDATED_MAX_SALARY);
        assertThat(testInviteInfo.getEducation()).isEqualTo(UPDATED_EDUCATION);
        assertThat(testInviteInfo.getSpecialty()).isEqualTo(UPDATED_SPECIALTY);
        assertThat(testInviteInfo.getGraduation()).isEqualTo(UPDATED_GRADUATION);
        assertThat(testInviteInfo.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testInviteInfo.getMail()).isEqualTo(UPDATED_MAIL);
        assertThat(testInviteInfo.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testInviteInfo.getSubmitTime()).isEqualTo(UPDATED_SUBMIT_TIME);
        assertThat(testInviteInfo.getRaquirement()).isEqualTo(UPDATED_RAQUIREMENT);
        assertThat(testInviteInfo.getRemark()).isEqualTo(UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void updateNonExistingInviteInfo() throws Exception {
        int databaseSizeBeforeUpdate = inviteInfoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInviteInfoMockMvc.perform(put("/api/invite-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inviteInfo)))
            .andExpect(status().isBadRequest());

        // Validate the InviteInfo in the database
        List<InviteInfo> inviteInfoList = inviteInfoRepository.findAll();
        assertThat(inviteInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInviteInfo() throws Exception {
        // Initialize the database
        inviteInfoService.save(inviteInfo);

        int databaseSizeBeforeDelete = inviteInfoRepository.findAll().size();

        // Delete the inviteInfo
        restInviteInfoMockMvc.perform(delete("/api/invite-infos/{id}", inviteInfo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InviteInfo> inviteInfoList = inviteInfoRepository.findAll();
        assertThat(inviteInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
