package com.job.web.rest;

import com.job.JobApp;
import com.job.domain.UserEdu;
import com.job.repository.UserEduRepository;
import com.job.service.UserEduService;
import com.job.service.dto.UserEduCriteria;
import com.job.service.UserEduQueryService;

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
 * Integration tests for the {@link UserEduResource} REST controller.
 */
@SpringBootTest(classes = JobApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserEduResourceIT {

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_GRADE = "AAAAAAAAAA";
    private static final String UPDATED_GRADE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_START_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_END_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_SCHOOL = "AAAAAAAAAA";
    private static final String UPDATED_SCHOOL = "BBBBBBBBBB";

    @Autowired
    private UserEduRepository userEduRepository;

    @Autowired
    private UserEduService userEduService;

    @Autowired
    private UserEduQueryService userEduQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserEduMockMvc;

    private UserEdu userEdu;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserEdu createEntity(EntityManager em) {
        UserEdu userEdu = new UserEdu()
            .login(DEFAULT_LOGIN)
            .grade(DEFAULT_GRADE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .school(DEFAULT_SCHOOL);
        return userEdu;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserEdu createUpdatedEntity(EntityManager em) {
        UserEdu userEdu = new UserEdu()
            .login(UPDATED_LOGIN)
            .grade(UPDATED_GRADE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .school(UPDATED_SCHOOL);
        return userEdu;
    }

    @BeforeEach
    public void initTest() {
        userEdu = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserEdu() throws Exception {
        int databaseSizeBeforeCreate = userEduRepository.findAll().size();
        // Create the UserEdu
        restUserEduMockMvc.perform(post("/api/user-edus")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userEdu)))
            .andExpect(status().isCreated());

        // Validate the UserEdu in the database
        List<UserEdu> userEduList = userEduRepository.findAll();
        assertThat(userEduList).hasSize(databaseSizeBeforeCreate + 1);
        UserEdu testUserEdu = userEduList.get(userEduList.size() - 1);
        assertThat(testUserEdu.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testUserEdu.getGrade()).isEqualTo(DEFAULT_GRADE);
        assertThat(testUserEdu.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testUserEdu.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testUserEdu.getSchool()).isEqualTo(DEFAULT_SCHOOL);
    }

    @Test
    @Transactional
    public void createUserEduWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userEduRepository.findAll().size();

        // Create the UserEdu with an existing ID
        userEdu.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserEduMockMvc.perform(post("/api/user-edus")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userEdu)))
            .andExpect(status().isBadRequest());

        // Validate the UserEdu in the database
        List<UserEdu> userEduList = userEduRepository.findAll();
        assertThat(userEduList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserEdus() throws Exception {
        // Initialize the database
        userEduRepository.saveAndFlush(userEdu);

        // Get all the userEduList
        restUserEduMockMvc.perform(get("/api/user-edus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userEdu.getId().intValue())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN)))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].school").value(hasItem(DEFAULT_SCHOOL)));
    }
    
    @Test
    @Transactional
    public void getUserEdu() throws Exception {
        // Initialize the database
        userEduRepository.saveAndFlush(userEdu);

        // Get the userEdu
        restUserEduMockMvc.perform(get("/api/user-edus/{id}", userEdu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userEdu.getId().intValue()))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN))
            .andExpect(jsonPath("$.grade").value(DEFAULT_GRADE))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.school").value(DEFAULT_SCHOOL));
    }


    @Test
    @Transactional
    public void getUserEdusByIdFiltering() throws Exception {
        // Initialize the database
        userEduRepository.saveAndFlush(userEdu);

        Long id = userEdu.getId();

        defaultUserEduShouldBeFound("id.equals=" + id);
        defaultUserEduShouldNotBeFound("id.notEquals=" + id);

        defaultUserEduShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUserEduShouldNotBeFound("id.greaterThan=" + id);

        defaultUserEduShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUserEduShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllUserEdusByLoginIsEqualToSomething() throws Exception {
        // Initialize the database
        userEduRepository.saveAndFlush(userEdu);

        // Get all the userEduList where login equals to DEFAULT_LOGIN
        defaultUserEduShouldBeFound("login.equals=" + DEFAULT_LOGIN);

        // Get all the userEduList where login equals to UPDATED_LOGIN
        defaultUserEduShouldNotBeFound("login.equals=" + UPDATED_LOGIN);
    }

    @Test
    @Transactional
    public void getAllUserEdusByLoginIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userEduRepository.saveAndFlush(userEdu);

        // Get all the userEduList where login not equals to DEFAULT_LOGIN
        defaultUserEduShouldNotBeFound("login.notEquals=" + DEFAULT_LOGIN);

        // Get all the userEduList where login not equals to UPDATED_LOGIN
        defaultUserEduShouldBeFound("login.notEquals=" + UPDATED_LOGIN);
    }

    @Test
    @Transactional
    public void getAllUserEdusByLoginIsInShouldWork() throws Exception {
        // Initialize the database
        userEduRepository.saveAndFlush(userEdu);

        // Get all the userEduList where login in DEFAULT_LOGIN or UPDATED_LOGIN
        defaultUserEduShouldBeFound("login.in=" + DEFAULT_LOGIN + "," + UPDATED_LOGIN);

        // Get all the userEduList where login equals to UPDATED_LOGIN
        defaultUserEduShouldNotBeFound("login.in=" + UPDATED_LOGIN);
    }

    @Test
    @Transactional
    public void getAllUserEdusByLoginIsNullOrNotNull() throws Exception {
        // Initialize the database
        userEduRepository.saveAndFlush(userEdu);

        // Get all the userEduList where login is not null
        defaultUserEduShouldBeFound("login.specified=true");

        // Get all the userEduList where login is null
        defaultUserEduShouldNotBeFound("login.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserEdusByLoginContainsSomething() throws Exception {
        // Initialize the database
        userEduRepository.saveAndFlush(userEdu);

        // Get all the userEduList where login contains DEFAULT_LOGIN
        defaultUserEduShouldBeFound("login.contains=" + DEFAULT_LOGIN);

        // Get all the userEduList where login contains UPDATED_LOGIN
        defaultUserEduShouldNotBeFound("login.contains=" + UPDATED_LOGIN);
    }

    @Test
    @Transactional
    public void getAllUserEdusByLoginNotContainsSomething() throws Exception {
        // Initialize the database
        userEduRepository.saveAndFlush(userEdu);

        // Get all the userEduList where login does not contain DEFAULT_LOGIN
        defaultUserEduShouldNotBeFound("login.doesNotContain=" + DEFAULT_LOGIN);

        // Get all the userEduList where login does not contain UPDATED_LOGIN
        defaultUserEduShouldBeFound("login.doesNotContain=" + UPDATED_LOGIN);
    }


    @Test
    @Transactional
    public void getAllUserEdusByGradeIsEqualToSomething() throws Exception {
        // Initialize the database
        userEduRepository.saveAndFlush(userEdu);

        // Get all the userEduList where grade equals to DEFAULT_GRADE
        defaultUserEduShouldBeFound("grade.equals=" + DEFAULT_GRADE);

        // Get all the userEduList where grade equals to UPDATED_GRADE
        defaultUserEduShouldNotBeFound("grade.equals=" + UPDATED_GRADE);
    }

    @Test
    @Transactional
    public void getAllUserEdusByGradeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userEduRepository.saveAndFlush(userEdu);

        // Get all the userEduList where grade not equals to DEFAULT_GRADE
        defaultUserEduShouldNotBeFound("grade.notEquals=" + DEFAULT_GRADE);

        // Get all the userEduList where grade not equals to UPDATED_GRADE
        defaultUserEduShouldBeFound("grade.notEquals=" + UPDATED_GRADE);
    }

    @Test
    @Transactional
    public void getAllUserEdusByGradeIsInShouldWork() throws Exception {
        // Initialize the database
        userEduRepository.saveAndFlush(userEdu);

        // Get all the userEduList where grade in DEFAULT_GRADE or UPDATED_GRADE
        defaultUserEduShouldBeFound("grade.in=" + DEFAULT_GRADE + "," + UPDATED_GRADE);

        // Get all the userEduList where grade equals to UPDATED_GRADE
        defaultUserEduShouldNotBeFound("grade.in=" + UPDATED_GRADE);
    }

    @Test
    @Transactional
    public void getAllUserEdusByGradeIsNullOrNotNull() throws Exception {
        // Initialize the database
        userEduRepository.saveAndFlush(userEdu);

        // Get all the userEduList where grade is not null
        defaultUserEduShouldBeFound("grade.specified=true");

        // Get all the userEduList where grade is null
        defaultUserEduShouldNotBeFound("grade.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserEdusByGradeContainsSomething() throws Exception {
        // Initialize the database
        userEduRepository.saveAndFlush(userEdu);

        // Get all the userEduList where grade contains DEFAULT_GRADE
        defaultUserEduShouldBeFound("grade.contains=" + DEFAULT_GRADE);

        // Get all the userEduList where grade contains UPDATED_GRADE
        defaultUserEduShouldNotBeFound("grade.contains=" + UPDATED_GRADE);
    }

    @Test
    @Transactional
    public void getAllUserEdusByGradeNotContainsSomething() throws Exception {
        // Initialize the database
        userEduRepository.saveAndFlush(userEdu);

        // Get all the userEduList where grade does not contain DEFAULT_GRADE
        defaultUserEduShouldNotBeFound("grade.doesNotContain=" + DEFAULT_GRADE);

        // Get all the userEduList where grade does not contain UPDATED_GRADE
        defaultUserEduShouldBeFound("grade.doesNotContain=" + UPDATED_GRADE);
    }


    @Test
    @Transactional
    public void getAllUserEdusByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        userEduRepository.saveAndFlush(userEdu);

        // Get all the userEduList where startDate equals to DEFAULT_START_DATE
        defaultUserEduShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the userEduList where startDate equals to UPDATED_START_DATE
        defaultUserEduShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllUserEdusByStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userEduRepository.saveAndFlush(userEdu);

        // Get all the userEduList where startDate not equals to DEFAULT_START_DATE
        defaultUserEduShouldNotBeFound("startDate.notEquals=" + DEFAULT_START_DATE);

        // Get all the userEduList where startDate not equals to UPDATED_START_DATE
        defaultUserEduShouldBeFound("startDate.notEquals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllUserEdusByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        userEduRepository.saveAndFlush(userEdu);

        // Get all the userEduList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultUserEduShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the userEduList where startDate equals to UPDATED_START_DATE
        defaultUserEduShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllUserEdusByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        userEduRepository.saveAndFlush(userEdu);

        // Get all the userEduList where startDate is not null
        defaultUserEduShouldBeFound("startDate.specified=true");

        // Get all the userEduList where startDate is null
        defaultUserEduShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserEdusByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userEduRepository.saveAndFlush(userEdu);

        // Get all the userEduList where startDate is greater than or equal to DEFAULT_START_DATE
        defaultUserEduShouldBeFound("startDate.greaterThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the userEduList where startDate is greater than or equal to UPDATED_START_DATE
        defaultUserEduShouldNotBeFound("startDate.greaterThanOrEqual=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllUserEdusByStartDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userEduRepository.saveAndFlush(userEdu);

        // Get all the userEduList where startDate is less than or equal to DEFAULT_START_DATE
        defaultUserEduShouldBeFound("startDate.lessThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the userEduList where startDate is less than or equal to SMALLER_START_DATE
        defaultUserEduShouldNotBeFound("startDate.lessThanOrEqual=" + SMALLER_START_DATE);
    }

    @Test
    @Transactional
    public void getAllUserEdusByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        userEduRepository.saveAndFlush(userEdu);

        // Get all the userEduList where startDate is less than DEFAULT_START_DATE
        defaultUserEduShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

        // Get all the userEduList where startDate is less than UPDATED_START_DATE
        defaultUserEduShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllUserEdusByStartDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userEduRepository.saveAndFlush(userEdu);

        // Get all the userEduList where startDate is greater than DEFAULT_START_DATE
        defaultUserEduShouldNotBeFound("startDate.greaterThan=" + DEFAULT_START_DATE);

        // Get all the userEduList where startDate is greater than SMALLER_START_DATE
        defaultUserEduShouldBeFound("startDate.greaterThan=" + SMALLER_START_DATE);
    }


    @Test
    @Transactional
    public void getAllUserEdusByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        userEduRepository.saveAndFlush(userEdu);

        // Get all the userEduList where endDate equals to DEFAULT_END_DATE
        defaultUserEduShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the userEduList where endDate equals to UPDATED_END_DATE
        defaultUserEduShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllUserEdusByEndDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userEduRepository.saveAndFlush(userEdu);

        // Get all the userEduList where endDate not equals to DEFAULT_END_DATE
        defaultUserEduShouldNotBeFound("endDate.notEquals=" + DEFAULT_END_DATE);

        // Get all the userEduList where endDate not equals to UPDATED_END_DATE
        defaultUserEduShouldBeFound("endDate.notEquals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllUserEdusByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        userEduRepository.saveAndFlush(userEdu);

        // Get all the userEduList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultUserEduShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the userEduList where endDate equals to UPDATED_END_DATE
        defaultUserEduShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllUserEdusByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        userEduRepository.saveAndFlush(userEdu);

        // Get all the userEduList where endDate is not null
        defaultUserEduShouldBeFound("endDate.specified=true");

        // Get all the userEduList where endDate is null
        defaultUserEduShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserEdusByEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userEduRepository.saveAndFlush(userEdu);

        // Get all the userEduList where endDate is greater than or equal to DEFAULT_END_DATE
        defaultUserEduShouldBeFound("endDate.greaterThanOrEqual=" + DEFAULT_END_DATE);

        // Get all the userEduList where endDate is greater than or equal to UPDATED_END_DATE
        defaultUserEduShouldNotBeFound("endDate.greaterThanOrEqual=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllUserEdusByEndDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userEduRepository.saveAndFlush(userEdu);

        // Get all the userEduList where endDate is less than or equal to DEFAULT_END_DATE
        defaultUserEduShouldBeFound("endDate.lessThanOrEqual=" + DEFAULT_END_DATE);

        // Get all the userEduList where endDate is less than or equal to SMALLER_END_DATE
        defaultUserEduShouldNotBeFound("endDate.lessThanOrEqual=" + SMALLER_END_DATE);
    }

    @Test
    @Transactional
    public void getAllUserEdusByEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        userEduRepository.saveAndFlush(userEdu);

        // Get all the userEduList where endDate is less than DEFAULT_END_DATE
        defaultUserEduShouldNotBeFound("endDate.lessThan=" + DEFAULT_END_DATE);

        // Get all the userEduList where endDate is less than UPDATED_END_DATE
        defaultUserEduShouldBeFound("endDate.lessThan=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllUserEdusByEndDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userEduRepository.saveAndFlush(userEdu);

        // Get all the userEduList where endDate is greater than DEFAULT_END_DATE
        defaultUserEduShouldNotBeFound("endDate.greaterThan=" + DEFAULT_END_DATE);

        // Get all the userEduList where endDate is greater than SMALLER_END_DATE
        defaultUserEduShouldBeFound("endDate.greaterThan=" + SMALLER_END_DATE);
    }


    @Test
    @Transactional
    public void getAllUserEdusBySchoolIsEqualToSomething() throws Exception {
        // Initialize the database
        userEduRepository.saveAndFlush(userEdu);

        // Get all the userEduList where school equals to DEFAULT_SCHOOL
        defaultUserEduShouldBeFound("school.equals=" + DEFAULT_SCHOOL);

        // Get all the userEduList where school equals to UPDATED_SCHOOL
        defaultUserEduShouldNotBeFound("school.equals=" + UPDATED_SCHOOL);
    }

    @Test
    @Transactional
    public void getAllUserEdusBySchoolIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userEduRepository.saveAndFlush(userEdu);

        // Get all the userEduList where school not equals to DEFAULT_SCHOOL
        defaultUserEduShouldNotBeFound("school.notEquals=" + DEFAULT_SCHOOL);

        // Get all the userEduList where school not equals to UPDATED_SCHOOL
        defaultUserEduShouldBeFound("school.notEquals=" + UPDATED_SCHOOL);
    }

    @Test
    @Transactional
    public void getAllUserEdusBySchoolIsInShouldWork() throws Exception {
        // Initialize the database
        userEduRepository.saveAndFlush(userEdu);

        // Get all the userEduList where school in DEFAULT_SCHOOL or UPDATED_SCHOOL
        defaultUserEduShouldBeFound("school.in=" + DEFAULT_SCHOOL + "," + UPDATED_SCHOOL);

        // Get all the userEduList where school equals to UPDATED_SCHOOL
        defaultUserEduShouldNotBeFound("school.in=" + UPDATED_SCHOOL);
    }

    @Test
    @Transactional
    public void getAllUserEdusBySchoolIsNullOrNotNull() throws Exception {
        // Initialize the database
        userEduRepository.saveAndFlush(userEdu);

        // Get all the userEduList where school is not null
        defaultUserEduShouldBeFound("school.specified=true");

        // Get all the userEduList where school is null
        defaultUserEduShouldNotBeFound("school.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserEdusBySchoolContainsSomething() throws Exception {
        // Initialize the database
        userEduRepository.saveAndFlush(userEdu);

        // Get all the userEduList where school contains DEFAULT_SCHOOL
        defaultUserEduShouldBeFound("school.contains=" + DEFAULT_SCHOOL);

        // Get all the userEduList where school contains UPDATED_SCHOOL
        defaultUserEduShouldNotBeFound("school.contains=" + UPDATED_SCHOOL);
    }

    @Test
    @Transactional
    public void getAllUserEdusBySchoolNotContainsSomething() throws Exception {
        // Initialize the database
        userEduRepository.saveAndFlush(userEdu);

        // Get all the userEduList where school does not contain DEFAULT_SCHOOL
        defaultUserEduShouldNotBeFound("school.doesNotContain=" + DEFAULT_SCHOOL);

        // Get all the userEduList where school does not contain UPDATED_SCHOOL
        defaultUserEduShouldBeFound("school.doesNotContain=" + UPDATED_SCHOOL);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUserEduShouldBeFound(String filter) throws Exception {
        restUserEduMockMvc.perform(get("/api/user-edus?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userEdu.getId().intValue())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN)))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].school").value(hasItem(DEFAULT_SCHOOL)));

        // Check, that the count call also returns 1
        restUserEduMockMvc.perform(get("/api/user-edus/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUserEduShouldNotBeFound(String filter) throws Exception {
        restUserEduMockMvc.perform(get("/api/user-edus?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUserEduMockMvc.perform(get("/api/user-edus/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingUserEdu() throws Exception {
        // Get the userEdu
        restUserEduMockMvc.perform(get("/api/user-edus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserEdu() throws Exception {
        // Initialize the database
        userEduService.save(userEdu);

        int databaseSizeBeforeUpdate = userEduRepository.findAll().size();

        // Update the userEdu
        UserEdu updatedUserEdu = userEduRepository.findById(userEdu.getId()).get();
        // Disconnect from session so that the updates on updatedUserEdu are not directly saved in db
        em.detach(updatedUserEdu);
        updatedUserEdu
            .login(UPDATED_LOGIN)
            .grade(UPDATED_GRADE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .school(UPDATED_SCHOOL);

        restUserEduMockMvc.perform(put("/api/user-edus")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserEdu)))
            .andExpect(status().isOk());

        // Validate the UserEdu in the database
        List<UserEdu> userEduList = userEduRepository.findAll();
        assertThat(userEduList).hasSize(databaseSizeBeforeUpdate);
        UserEdu testUserEdu = userEduList.get(userEduList.size() - 1);
        assertThat(testUserEdu.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testUserEdu.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testUserEdu.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testUserEdu.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testUserEdu.getSchool()).isEqualTo(UPDATED_SCHOOL);
    }

    @Test
    @Transactional
    public void updateNonExistingUserEdu() throws Exception {
        int databaseSizeBeforeUpdate = userEduRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserEduMockMvc.perform(put("/api/user-edus")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userEdu)))
            .andExpect(status().isBadRequest());

        // Validate the UserEdu in the database
        List<UserEdu> userEduList = userEduRepository.findAll();
        assertThat(userEduList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserEdu() throws Exception {
        // Initialize the database
        userEduService.save(userEdu);

        int databaseSizeBeforeDelete = userEduRepository.findAll().size();

        // Delete the userEdu
        restUserEduMockMvc.perform(delete("/api/user-edus/{id}", userEdu.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserEdu> userEduList = userEduRepository.findAll();
        assertThat(userEduList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
