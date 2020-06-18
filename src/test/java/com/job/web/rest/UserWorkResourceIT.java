package com.job.web.rest;

import com.job.JobApp;
import com.job.domain.UserWork;
import com.job.repository.UserWorkRepository;
import com.job.service.UserWorkService;
import com.job.service.dto.UserWorkCriteria;
import com.job.service.UserWorkQueryService;

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
 * Integration tests for the {@link UserWorkResource} REST controller.
 */
@SpringBootTest(classes = JobApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserWorkResourceIT {

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_START_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_END_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_COMPANY = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY = "BBBBBBBBBB";

    private static final String DEFAULT_POST = "AAAAAAAAAA";
    private static final String UPDATED_POST = "BBBBBBBBBB";

    private static final Double DEFAULT_SALARY = 1D;
    private static final Double UPDATED_SALARY = 2D;
    private static final Double SMALLER_SALARY = 1D - 1D;

    @Autowired
    private UserWorkRepository userWorkRepository;

    @Autowired
    private UserWorkService userWorkService;

    @Autowired
    private UserWorkQueryService userWorkQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserWorkMockMvc;

    private UserWork userWork;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserWork createEntity(EntityManager em) {
        UserWork userWork = new UserWork()
            .login(DEFAULT_LOGIN)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .company(DEFAULT_COMPANY)
            .post(DEFAULT_POST)
            .salary(DEFAULT_SALARY);
        return userWork;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserWork createUpdatedEntity(EntityManager em) {
        UserWork userWork = new UserWork()
            .login(UPDATED_LOGIN)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .company(UPDATED_COMPANY)
            .post(UPDATED_POST)
            .salary(UPDATED_SALARY);
        return userWork;
    }

    @BeforeEach
    public void initTest() {
        userWork = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserWork() throws Exception {
        int databaseSizeBeforeCreate = userWorkRepository.findAll().size();
        // Create the UserWork
        restUserWorkMockMvc.perform(post("/api/user-works")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userWork)))
            .andExpect(status().isCreated());

        // Validate the UserWork in the database
        List<UserWork> userWorkList = userWorkRepository.findAll();
        assertThat(userWorkList).hasSize(databaseSizeBeforeCreate + 1);
        UserWork testUserWork = userWorkList.get(userWorkList.size() - 1);
        assertThat(testUserWork.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testUserWork.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testUserWork.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testUserWork.getCompany()).isEqualTo(DEFAULT_COMPANY);
        assertThat(testUserWork.getPost()).isEqualTo(DEFAULT_POST);
        assertThat(testUserWork.getSalary()).isEqualTo(DEFAULT_SALARY);
    }

    @Test
    @Transactional
    public void createUserWorkWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userWorkRepository.findAll().size();

        // Create the UserWork with an existing ID
        userWork.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserWorkMockMvc.perform(post("/api/user-works")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userWork)))
            .andExpect(status().isBadRequest());

        // Validate the UserWork in the database
        List<UserWork> userWorkList = userWorkRepository.findAll();
        assertThat(userWorkList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserWorks() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get all the userWorkList
        restUserWorkMockMvc.perform(get("/api/user-works?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userWork.getId().intValue())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY)))
            .andExpect(jsonPath("$.[*].post").value(hasItem(DEFAULT_POST)))
            .andExpect(jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getUserWork() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get the userWork
        restUserWorkMockMvc.perform(get("/api/user-works/{id}", userWork.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userWork.getId().intValue()))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.company").value(DEFAULT_COMPANY))
            .andExpect(jsonPath("$.post").value(DEFAULT_POST))
            .andExpect(jsonPath("$.salary").value(DEFAULT_SALARY.doubleValue()));
    }


    @Test
    @Transactional
    public void getUserWorksByIdFiltering() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        Long id = userWork.getId();

        defaultUserWorkShouldBeFound("id.equals=" + id);
        defaultUserWorkShouldNotBeFound("id.notEquals=" + id);

        defaultUserWorkShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUserWorkShouldNotBeFound("id.greaterThan=" + id);

        defaultUserWorkShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUserWorkShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllUserWorksByLoginIsEqualToSomething() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get all the userWorkList where login equals to DEFAULT_LOGIN
        defaultUserWorkShouldBeFound("login.equals=" + DEFAULT_LOGIN);

        // Get all the userWorkList where login equals to UPDATED_LOGIN
        defaultUserWorkShouldNotBeFound("login.equals=" + UPDATED_LOGIN);
    }

    @Test
    @Transactional
    public void getAllUserWorksByLoginIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get all the userWorkList where login not equals to DEFAULT_LOGIN
        defaultUserWorkShouldNotBeFound("login.notEquals=" + DEFAULT_LOGIN);

        // Get all the userWorkList where login not equals to UPDATED_LOGIN
        defaultUserWorkShouldBeFound("login.notEquals=" + UPDATED_LOGIN);
    }

    @Test
    @Transactional
    public void getAllUserWorksByLoginIsInShouldWork() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get all the userWorkList where login in DEFAULT_LOGIN or UPDATED_LOGIN
        defaultUserWorkShouldBeFound("login.in=" + DEFAULT_LOGIN + "," + UPDATED_LOGIN);

        // Get all the userWorkList where login equals to UPDATED_LOGIN
        defaultUserWorkShouldNotBeFound("login.in=" + UPDATED_LOGIN);
    }

    @Test
    @Transactional
    public void getAllUserWorksByLoginIsNullOrNotNull() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get all the userWorkList where login is not null
        defaultUserWorkShouldBeFound("login.specified=true");

        // Get all the userWorkList where login is null
        defaultUserWorkShouldNotBeFound("login.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserWorksByLoginContainsSomething() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get all the userWorkList where login contains DEFAULT_LOGIN
        defaultUserWorkShouldBeFound("login.contains=" + DEFAULT_LOGIN);

        // Get all the userWorkList where login contains UPDATED_LOGIN
        defaultUserWorkShouldNotBeFound("login.contains=" + UPDATED_LOGIN);
    }

    @Test
    @Transactional
    public void getAllUserWorksByLoginNotContainsSomething() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get all the userWorkList where login does not contain DEFAULT_LOGIN
        defaultUserWorkShouldNotBeFound("login.doesNotContain=" + DEFAULT_LOGIN);

        // Get all the userWorkList where login does not contain UPDATED_LOGIN
        defaultUserWorkShouldBeFound("login.doesNotContain=" + UPDATED_LOGIN);
    }


    @Test
    @Transactional
    public void getAllUserWorksByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get all the userWorkList where startDate equals to DEFAULT_START_DATE
        defaultUserWorkShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the userWorkList where startDate equals to UPDATED_START_DATE
        defaultUserWorkShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllUserWorksByStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get all the userWorkList where startDate not equals to DEFAULT_START_DATE
        defaultUserWorkShouldNotBeFound("startDate.notEquals=" + DEFAULT_START_DATE);

        // Get all the userWorkList where startDate not equals to UPDATED_START_DATE
        defaultUserWorkShouldBeFound("startDate.notEquals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllUserWorksByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get all the userWorkList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultUserWorkShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the userWorkList where startDate equals to UPDATED_START_DATE
        defaultUserWorkShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllUserWorksByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get all the userWorkList where startDate is not null
        defaultUserWorkShouldBeFound("startDate.specified=true");

        // Get all the userWorkList where startDate is null
        defaultUserWorkShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserWorksByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get all the userWorkList where startDate is greater than or equal to DEFAULT_START_DATE
        defaultUserWorkShouldBeFound("startDate.greaterThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the userWorkList where startDate is greater than or equal to UPDATED_START_DATE
        defaultUserWorkShouldNotBeFound("startDate.greaterThanOrEqual=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllUserWorksByStartDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get all the userWorkList where startDate is less than or equal to DEFAULT_START_DATE
        defaultUserWorkShouldBeFound("startDate.lessThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the userWorkList where startDate is less than or equal to SMALLER_START_DATE
        defaultUserWorkShouldNotBeFound("startDate.lessThanOrEqual=" + SMALLER_START_DATE);
    }

    @Test
    @Transactional
    public void getAllUserWorksByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get all the userWorkList where startDate is less than DEFAULT_START_DATE
        defaultUserWorkShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

        // Get all the userWorkList where startDate is less than UPDATED_START_DATE
        defaultUserWorkShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllUserWorksByStartDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get all the userWorkList where startDate is greater than DEFAULT_START_DATE
        defaultUserWorkShouldNotBeFound("startDate.greaterThan=" + DEFAULT_START_DATE);

        // Get all the userWorkList where startDate is greater than SMALLER_START_DATE
        defaultUserWorkShouldBeFound("startDate.greaterThan=" + SMALLER_START_DATE);
    }


    @Test
    @Transactional
    public void getAllUserWorksByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get all the userWorkList where endDate equals to DEFAULT_END_DATE
        defaultUserWorkShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the userWorkList where endDate equals to UPDATED_END_DATE
        defaultUserWorkShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllUserWorksByEndDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get all the userWorkList where endDate not equals to DEFAULT_END_DATE
        defaultUserWorkShouldNotBeFound("endDate.notEquals=" + DEFAULT_END_DATE);

        // Get all the userWorkList where endDate not equals to UPDATED_END_DATE
        defaultUserWorkShouldBeFound("endDate.notEquals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllUserWorksByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get all the userWorkList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultUserWorkShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the userWorkList where endDate equals to UPDATED_END_DATE
        defaultUserWorkShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllUserWorksByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get all the userWorkList where endDate is not null
        defaultUserWorkShouldBeFound("endDate.specified=true");

        // Get all the userWorkList where endDate is null
        defaultUserWorkShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserWorksByEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get all the userWorkList where endDate is greater than or equal to DEFAULT_END_DATE
        defaultUserWorkShouldBeFound("endDate.greaterThanOrEqual=" + DEFAULT_END_DATE);

        // Get all the userWorkList where endDate is greater than or equal to UPDATED_END_DATE
        defaultUserWorkShouldNotBeFound("endDate.greaterThanOrEqual=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllUserWorksByEndDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get all the userWorkList where endDate is less than or equal to DEFAULT_END_DATE
        defaultUserWorkShouldBeFound("endDate.lessThanOrEqual=" + DEFAULT_END_DATE);

        // Get all the userWorkList where endDate is less than or equal to SMALLER_END_DATE
        defaultUserWorkShouldNotBeFound("endDate.lessThanOrEqual=" + SMALLER_END_DATE);
    }

    @Test
    @Transactional
    public void getAllUserWorksByEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get all the userWorkList where endDate is less than DEFAULT_END_DATE
        defaultUserWorkShouldNotBeFound("endDate.lessThan=" + DEFAULT_END_DATE);

        // Get all the userWorkList where endDate is less than UPDATED_END_DATE
        defaultUserWorkShouldBeFound("endDate.lessThan=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllUserWorksByEndDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get all the userWorkList where endDate is greater than DEFAULT_END_DATE
        defaultUserWorkShouldNotBeFound("endDate.greaterThan=" + DEFAULT_END_DATE);

        // Get all the userWorkList where endDate is greater than SMALLER_END_DATE
        defaultUserWorkShouldBeFound("endDate.greaterThan=" + SMALLER_END_DATE);
    }


    @Test
    @Transactional
    public void getAllUserWorksByCompanyIsEqualToSomething() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get all the userWorkList where company equals to DEFAULT_COMPANY
        defaultUserWorkShouldBeFound("company.equals=" + DEFAULT_COMPANY);

        // Get all the userWorkList where company equals to UPDATED_COMPANY
        defaultUserWorkShouldNotBeFound("company.equals=" + UPDATED_COMPANY);
    }

    @Test
    @Transactional
    public void getAllUserWorksByCompanyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get all the userWorkList where company not equals to DEFAULT_COMPANY
        defaultUserWorkShouldNotBeFound("company.notEquals=" + DEFAULT_COMPANY);

        // Get all the userWorkList where company not equals to UPDATED_COMPANY
        defaultUserWorkShouldBeFound("company.notEquals=" + UPDATED_COMPANY);
    }

    @Test
    @Transactional
    public void getAllUserWorksByCompanyIsInShouldWork() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get all the userWorkList where company in DEFAULT_COMPANY or UPDATED_COMPANY
        defaultUserWorkShouldBeFound("company.in=" + DEFAULT_COMPANY + "," + UPDATED_COMPANY);

        // Get all the userWorkList where company equals to UPDATED_COMPANY
        defaultUserWorkShouldNotBeFound("company.in=" + UPDATED_COMPANY);
    }

    @Test
    @Transactional
    public void getAllUserWorksByCompanyIsNullOrNotNull() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get all the userWorkList where company is not null
        defaultUserWorkShouldBeFound("company.specified=true");

        // Get all the userWorkList where company is null
        defaultUserWorkShouldNotBeFound("company.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserWorksByCompanyContainsSomething() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get all the userWorkList where company contains DEFAULT_COMPANY
        defaultUserWorkShouldBeFound("company.contains=" + DEFAULT_COMPANY);

        // Get all the userWorkList where company contains UPDATED_COMPANY
        defaultUserWorkShouldNotBeFound("company.contains=" + UPDATED_COMPANY);
    }

    @Test
    @Transactional
    public void getAllUserWorksByCompanyNotContainsSomething() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get all the userWorkList where company does not contain DEFAULT_COMPANY
        defaultUserWorkShouldNotBeFound("company.doesNotContain=" + DEFAULT_COMPANY);

        // Get all the userWorkList where company does not contain UPDATED_COMPANY
        defaultUserWorkShouldBeFound("company.doesNotContain=" + UPDATED_COMPANY);
    }


    @Test
    @Transactional
    public void getAllUserWorksByPostIsEqualToSomething() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get all the userWorkList where post equals to DEFAULT_POST
        defaultUserWorkShouldBeFound("post.equals=" + DEFAULT_POST);

        // Get all the userWorkList where post equals to UPDATED_POST
        defaultUserWorkShouldNotBeFound("post.equals=" + UPDATED_POST);
    }

    @Test
    @Transactional
    public void getAllUserWorksByPostIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get all the userWorkList where post not equals to DEFAULT_POST
        defaultUserWorkShouldNotBeFound("post.notEquals=" + DEFAULT_POST);

        // Get all the userWorkList where post not equals to UPDATED_POST
        defaultUserWorkShouldBeFound("post.notEquals=" + UPDATED_POST);
    }

    @Test
    @Transactional
    public void getAllUserWorksByPostIsInShouldWork() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get all the userWorkList where post in DEFAULT_POST or UPDATED_POST
        defaultUserWorkShouldBeFound("post.in=" + DEFAULT_POST + "," + UPDATED_POST);

        // Get all the userWorkList where post equals to UPDATED_POST
        defaultUserWorkShouldNotBeFound("post.in=" + UPDATED_POST);
    }

    @Test
    @Transactional
    public void getAllUserWorksByPostIsNullOrNotNull() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get all the userWorkList where post is not null
        defaultUserWorkShouldBeFound("post.specified=true");

        // Get all the userWorkList where post is null
        defaultUserWorkShouldNotBeFound("post.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserWorksByPostContainsSomething() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get all the userWorkList where post contains DEFAULT_POST
        defaultUserWorkShouldBeFound("post.contains=" + DEFAULT_POST);

        // Get all the userWorkList where post contains UPDATED_POST
        defaultUserWorkShouldNotBeFound("post.contains=" + UPDATED_POST);
    }

    @Test
    @Transactional
    public void getAllUserWorksByPostNotContainsSomething() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get all the userWorkList where post does not contain DEFAULT_POST
        defaultUserWorkShouldNotBeFound("post.doesNotContain=" + DEFAULT_POST);

        // Get all the userWorkList where post does not contain UPDATED_POST
        defaultUserWorkShouldBeFound("post.doesNotContain=" + UPDATED_POST);
    }


    @Test
    @Transactional
    public void getAllUserWorksBySalaryIsEqualToSomething() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get all the userWorkList where salary equals to DEFAULT_SALARY
        defaultUserWorkShouldBeFound("salary.equals=" + DEFAULT_SALARY);

        // Get all the userWorkList where salary equals to UPDATED_SALARY
        defaultUserWorkShouldNotBeFound("salary.equals=" + UPDATED_SALARY);
    }

    @Test
    @Transactional
    public void getAllUserWorksBySalaryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get all the userWorkList where salary not equals to DEFAULT_SALARY
        defaultUserWorkShouldNotBeFound("salary.notEquals=" + DEFAULT_SALARY);

        // Get all the userWorkList where salary not equals to UPDATED_SALARY
        defaultUserWorkShouldBeFound("salary.notEquals=" + UPDATED_SALARY);
    }

    @Test
    @Transactional
    public void getAllUserWorksBySalaryIsInShouldWork() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get all the userWorkList where salary in DEFAULT_SALARY or UPDATED_SALARY
        defaultUserWorkShouldBeFound("salary.in=" + DEFAULT_SALARY + "," + UPDATED_SALARY);

        // Get all the userWorkList where salary equals to UPDATED_SALARY
        defaultUserWorkShouldNotBeFound("salary.in=" + UPDATED_SALARY);
    }

    @Test
    @Transactional
    public void getAllUserWorksBySalaryIsNullOrNotNull() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get all the userWorkList where salary is not null
        defaultUserWorkShouldBeFound("salary.specified=true");

        // Get all the userWorkList where salary is null
        defaultUserWorkShouldNotBeFound("salary.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserWorksBySalaryIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get all the userWorkList where salary is greater than or equal to DEFAULT_SALARY
        defaultUserWorkShouldBeFound("salary.greaterThanOrEqual=" + DEFAULT_SALARY);

        // Get all the userWorkList where salary is greater than or equal to UPDATED_SALARY
        defaultUserWorkShouldNotBeFound("salary.greaterThanOrEqual=" + UPDATED_SALARY);
    }

    @Test
    @Transactional
    public void getAllUserWorksBySalaryIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get all the userWorkList where salary is less than or equal to DEFAULT_SALARY
        defaultUserWorkShouldBeFound("salary.lessThanOrEqual=" + DEFAULT_SALARY);

        // Get all the userWorkList where salary is less than or equal to SMALLER_SALARY
        defaultUserWorkShouldNotBeFound("salary.lessThanOrEqual=" + SMALLER_SALARY);
    }

    @Test
    @Transactional
    public void getAllUserWorksBySalaryIsLessThanSomething() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get all the userWorkList where salary is less than DEFAULT_SALARY
        defaultUserWorkShouldNotBeFound("salary.lessThan=" + DEFAULT_SALARY);

        // Get all the userWorkList where salary is less than UPDATED_SALARY
        defaultUserWorkShouldBeFound("salary.lessThan=" + UPDATED_SALARY);
    }

    @Test
    @Transactional
    public void getAllUserWorksBySalaryIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get all the userWorkList where salary is greater than DEFAULT_SALARY
        defaultUserWorkShouldNotBeFound("salary.greaterThan=" + DEFAULT_SALARY);

        // Get all the userWorkList where salary is greater than SMALLER_SALARY
        defaultUserWorkShouldBeFound("salary.greaterThan=" + SMALLER_SALARY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUserWorkShouldBeFound(String filter) throws Exception {
        restUserWorkMockMvc.perform(get("/api/user-works?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userWork.getId().intValue())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY)))
            .andExpect(jsonPath("$.[*].post").value(hasItem(DEFAULT_POST)))
            .andExpect(jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY.doubleValue())));

        // Check, that the count call also returns 1
        restUserWorkMockMvc.perform(get("/api/user-works/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUserWorkShouldNotBeFound(String filter) throws Exception {
        restUserWorkMockMvc.perform(get("/api/user-works?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUserWorkMockMvc.perform(get("/api/user-works/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingUserWork() throws Exception {
        // Get the userWork
        restUserWorkMockMvc.perform(get("/api/user-works/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserWork() throws Exception {
        // Initialize the database
        userWorkService.save(userWork);

        int databaseSizeBeforeUpdate = userWorkRepository.findAll().size();

        // Update the userWork
        UserWork updatedUserWork = userWorkRepository.findById(userWork.getId()).get();
        // Disconnect from session so that the updates on updatedUserWork are not directly saved in db
        em.detach(updatedUserWork);
        updatedUserWork
            .login(UPDATED_LOGIN)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .company(UPDATED_COMPANY)
            .post(UPDATED_POST)
            .salary(UPDATED_SALARY);

        restUserWorkMockMvc.perform(put("/api/user-works")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserWork)))
            .andExpect(status().isOk());

        // Validate the UserWork in the database
        List<UserWork> userWorkList = userWorkRepository.findAll();
        assertThat(userWorkList).hasSize(databaseSizeBeforeUpdate);
        UserWork testUserWork = userWorkList.get(userWorkList.size() - 1);
        assertThat(testUserWork.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testUserWork.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testUserWork.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testUserWork.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testUserWork.getPost()).isEqualTo(UPDATED_POST);
        assertThat(testUserWork.getSalary()).isEqualTo(UPDATED_SALARY);
    }

    @Test
    @Transactional
    public void updateNonExistingUserWork() throws Exception {
        int databaseSizeBeforeUpdate = userWorkRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserWorkMockMvc.perform(put("/api/user-works")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userWork)))
            .andExpect(status().isBadRequest());

        // Validate the UserWork in the database
        List<UserWork> userWorkList = userWorkRepository.findAll();
        assertThat(userWorkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserWork() throws Exception {
        // Initialize the database
        userWorkService.save(userWork);

        int databaseSizeBeforeDelete = userWorkRepository.findAll().size();

        // Delete the userWork
        restUserWorkMockMvc.perform(delete("/api/user-works/{id}", userWork.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserWork> userWorkList = userWorkRepository.findAll();
        assertThat(userWorkList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
