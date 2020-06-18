package com.job.web.rest;

import com.job.JobApp;
import com.job.domain.UserInfo;
import com.job.repository.UserInfoRepository;
import com.job.service.UserInfoService;
import com.job.service.dto.UserInfoCriteria;
import com.job.service.UserInfoQueryService;

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
 * Integration tests for the {@link UserInfoResource} REST controller.
 */
@SpringBootTest(classes = JobApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserInfoResourceIT {

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_GENDER = 1;
    private static final Integer UPDATED_GENDER = 2;
    private static final Integer SMALLER_GENDER = 1 - 1;

    private static final Instant DEFAULT_BIRTHDAY = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BIRTHDAY = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE = "BBBBBBBBBB";

    private static final String DEFAULT_MAIL = "AAAAAAAAAA";
    private static final String UPDATED_MAIL = "BBBBBBBBBB";

    private static final String DEFAULT_IDNO = "AAAAAAAAAA";
    private static final String UPDATED_IDNO = "BBBBBBBBBB";

    private static final String DEFAULT_SCHOOL = "AAAAAAAAAA";
    private static final String UPDATED_SCHOOL = "BBBBBBBBBB";

    private static final String DEFAULT_SPECIALTY = "AAAAAAAAAA";
    private static final String UPDATED_SPECIALTY = "BBBBBBBBBB";

    private static final String DEFAULT_EDUCATION = "AAAAAAAAAA";
    private static final String UPDATED_EDUCATION = "BBBBBBBBBB";

    private static final Integer DEFAULT_GRADUATION = 1;
    private static final Integer UPDATED_GRADUATION = 2;
    private static final Integer SMALLER_GRADUATION = 1 - 1;

    private static final String DEFAULT_COMPANY = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_MAIL = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_MAIL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_MAIL_VALIDATED = false;
    private static final Boolean UPDATED_MAIL_VALIDATED = true;

    private static final Boolean DEFAULT_COMPANY_MAIL_VALIDATED = false;
    private static final Boolean UPDATED_COMPANY_MAIL_VALIDATED = true;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserInfoQueryService userInfoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserInfoMockMvc;

    private UserInfo userInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserInfo createEntity(EntityManager em) {
        UserInfo userInfo = new UserInfo()
            .login(DEFAULT_LOGIN)
            .name(DEFAULT_NAME)
            .gender(DEFAULT_GENDER)
            .birthday(DEFAULT_BIRTHDAY)
            .mobile(DEFAULT_MOBILE)
            .mail(DEFAULT_MAIL)
            .idno(DEFAULT_IDNO)
            .school(DEFAULT_SCHOOL)
            .specialty(DEFAULT_SPECIALTY)
            .education(DEFAULT_EDUCATION)
            .graduation(DEFAULT_GRADUATION)
            .company(DEFAULT_COMPANY)
            .companyMail(DEFAULT_COMPANY_MAIL)
            .mailValidated(DEFAULT_MAIL_VALIDATED)
            .companyMailValidated(DEFAULT_COMPANY_MAIL_VALIDATED);
        return userInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserInfo createUpdatedEntity(EntityManager em) {
        UserInfo userInfo = new UserInfo()
            .login(UPDATED_LOGIN)
            .name(UPDATED_NAME)
            .gender(UPDATED_GENDER)
            .birthday(UPDATED_BIRTHDAY)
            .mobile(UPDATED_MOBILE)
            .mail(UPDATED_MAIL)
            .idno(UPDATED_IDNO)
            .school(UPDATED_SCHOOL)
            .specialty(UPDATED_SPECIALTY)
            .education(UPDATED_EDUCATION)
            .graduation(UPDATED_GRADUATION)
            .company(UPDATED_COMPANY)
            .companyMail(UPDATED_COMPANY_MAIL)
            .mailValidated(UPDATED_MAIL_VALIDATED)
            .companyMailValidated(UPDATED_COMPANY_MAIL_VALIDATED);
        return userInfo;
    }

    @BeforeEach
    public void initTest() {
        userInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserInfo() throws Exception {
        int databaseSizeBeforeCreate = userInfoRepository.findAll().size();
        // Create the UserInfo
        restUserInfoMockMvc.perform(post("/api/user-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userInfo)))
            .andExpect(status().isCreated());

        // Validate the UserInfo in the database
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeCreate + 1);
        UserInfo testUserInfo = userInfoList.get(userInfoList.size() - 1);
        assertThat(testUserInfo.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testUserInfo.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUserInfo.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testUserInfo.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
        assertThat(testUserInfo.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testUserInfo.getMail()).isEqualTo(DEFAULT_MAIL);
        assertThat(testUserInfo.getIdno()).isEqualTo(DEFAULT_IDNO);
        assertThat(testUserInfo.getSchool()).isEqualTo(DEFAULT_SCHOOL);
        assertThat(testUserInfo.getSpecialty()).isEqualTo(DEFAULT_SPECIALTY);
        assertThat(testUserInfo.getEducation()).isEqualTo(DEFAULT_EDUCATION);
        assertThat(testUserInfo.getGraduation()).isEqualTo(DEFAULT_GRADUATION);
        assertThat(testUserInfo.getCompany()).isEqualTo(DEFAULT_COMPANY);
        assertThat(testUserInfo.getCompanyMail()).isEqualTo(DEFAULT_COMPANY_MAIL);
        assertThat(testUserInfo.isMailValidated()).isEqualTo(DEFAULT_MAIL_VALIDATED);
        assertThat(testUserInfo.isCompanyMailValidated()).isEqualTo(DEFAULT_COMPANY_MAIL_VALIDATED);
    }

    @Test
    @Transactional
    public void createUserInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userInfoRepository.findAll().size();

        // Create the UserInfo with an existing ID
        userInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserInfoMockMvc.perform(post("/api/user-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userInfo)))
            .andExpect(status().isBadRequest());

        // Validate the UserInfo in the database
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserInfos() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList
        restUserInfoMockMvc.perform(get("/api/user-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL)))
            .andExpect(jsonPath("$.[*].idno").value(hasItem(DEFAULT_IDNO)))
            .andExpect(jsonPath("$.[*].school").value(hasItem(DEFAULT_SCHOOL)))
            .andExpect(jsonPath("$.[*].specialty").value(hasItem(DEFAULT_SPECIALTY)))
            .andExpect(jsonPath("$.[*].education").value(hasItem(DEFAULT_EDUCATION)))
            .andExpect(jsonPath("$.[*].graduation").value(hasItem(DEFAULT_GRADUATION)))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY)))
            .andExpect(jsonPath("$.[*].companyMail").value(hasItem(DEFAULT_COMPANY_MAIL)))
            .andExpect(jsonPath("$.[*].mailValidated").value(hasItem(DEFAULT_MAIL_VALIDATED.booleanValue())))
            .andExpect(jsonPath("$.[*].companyMailValidated").value(hasItem(DEFAULT_COMPANY_MAIL_VALIDATED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getUserInfo() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get the userInfo
        restUserInfoMockMvc.perform(get("/api/user-infos/{id}", userInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userInfo.getId().intValue()))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER))
            .andExpect(jsonPath("$.birthday").value(DEFAULT_BIRTHDAY.toString()))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE))
            .andExpect(jsonPath("$.mail").value(DEFAULT_MAIL))
            .andExpect(jsonPath("$.idno").value(DEFAULT_IDNO))
            .andExpect(jsonPath("$.school").value(DEFAULT_SCHOOL))
            .andExpect(jsonPath("$.specialty").value(DEFAULT_SPECIALTY))
            .andExpect(jsonPath("$.education").value(DEFAULT_EDUCATION))
            .andExpect(jsonPath("$.graduation").value(DEFAULT_GRADUATION))
            .andExpect(jsonPath("$.company").value(DEFAULT_COMPANY))
            .andExpect(jsonPath("$.companyMail").value(DEFAULT_COMPANY_MAIL))
            .andExpect(jsonPath("$.mailValidated").value(DEFAULT_MAIL_VALIDATED.booleanValue()))
            .andExpect(jsonPath("$.companyMailValidated").value(DEFAULT_COMPANY_MAIL_VALIDATED.booleanValue()));
    }


    @Test
    @Transactional
    public void getUserInfosByIdFiltering() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        Long id = userInfo.getId();

        defaultUserInfoShouldBeFound("id.equals=" + id);
        defaultUserInfoShouldNotBeFound("id.notEquals=" + id);

        defaultUserInfoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUserInfoShouldNotBeFound("id.greaterThan=" + id);

        defaultUserInfoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUserInfoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllUserInfosByLoginIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where login equals to DEFAULT_LOGIN
        defaultUserInfoShouldBeFound("login.equals=" + DEFAULT_LOGIN);

        // Get all the userInfoList where login equals to UPDATED_LOGIN
        defaultUserInfoShouldNotBeFound("login.equals=" + UPDATED_LOGIN);
    }

    @Test
    @Transactional
    public void getAllUserInfosByLoginIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where login not equals to DEFAULT_LOGIN
        defaultUserInfoShouldNotBeFound("login.notEquals=" + DEFAULT_LOGIN);

        // Get all the userInfoList where login not equals to UPDATED_LOGIN
        defaultUserInfoShouldBeFound("login.notEquals=" + UPDATED_LOGIN);
    }

    @Test
    @Transactional
    public void getAllUserInfosByLoginIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where login in DEFAULT_LOGIN or UPDATED_LOGIN
        defaultUserInfoShouldBeFound("login.in=" + DEFAULT_LOGIN + "," + UPDATED_LOGIN);

        // Get all the userInfoList where login equals to UPDATED_LOGIN
        defaultUserInfoShouldNotBeFound("login.in=" + UPDATED_LOGIN);
    }

    @Test
    @Transactional
    public void getAllUserInfosByLoginIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where login is not null
        defaultUserInfoShouldBeFound("login.specified=true");

        // Get all the userInfoList where login is null
        defaultUserInfoShouldNotBeFound("login.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserInfosByLoginContainsSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where login contains DEFAULT_LOGIN
        defaultUserInfoShouldBeFound("login.contains=" + DEFAULT_LOGIN);

        // Get all the userInfoList where login contains UPDATED_LOGIN
        defaultUserInfoShouldNotBeFound("login.contains=" + UPDATED_LOGIN);
    }

    @Test
    @Transactional
    public void getAllUserInfosByLoginNotContainsSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where login does not contain DEFAULT_LOGIN
        defaultUserInfoShouldNotBeFound("login.doesNotContain=" + DEFAULT_LOGIN);

        // Get all the userInfoList where login does not contain UPDATED_LOGIN
        defaultUserInfoShouldBeFound("login.doesNotContain=" + UPDATED_LOGIN);
    }


    @Test
    @Transactional
    public void getAllUserInfosByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where name equals to DEFAULT_NAME
        defaultUserInfoShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the userInfoList where name equals to UPDATED_NAME
        defaultUserInfoShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUserInfosByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where name not equals to DEFAULT_NAME
        defaultUserInfoShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the userInfoList where name not equals to UPDATED_NAME
        defaultUserInfoShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUserInfosByNameIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where name in DEFAULT_NAME or UPDATED_NAME
        defaultUserInfoShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the userInfoList where name equals to UPDATED_NAME
        defaultUserInfoShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUserInfosByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where name is not null
        defaultUserInfoShouldBeFound("name.specified=true");

        // Get all the userInfoList where name is null
        defaultUserInfoShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserInfosByNameContainsSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where name contains DEFAULT_NAME
        defaultUserInfoShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the userInfoList where name contains UPDATED_NAME
        defaultUserInfoShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUserInfosByNameNotContainsSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where name does not contain DEFAULT_NAME
        defaultUserInfoShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the userInfoList where name does not contain UPDATED_NAME
        defaultUserInfoShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllUserInfosByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where gender equals to DEFAULT_GENDER
        defaultUserInfoShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the userInfoList where gender equals to UPDATED_GENDER
        defaultUserInfoShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllUserInfosByGenderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where gender not equals to DEFAULT_GENDER
        defaultUserInfoShouldNotBeFound("gender.notEquals=" + DEFAULT_GENDER);

        // Get all the userInfoList where gender not equals to UPDATED_GENDER
        defaultUserInfoShouldBeFound("gender.notEquals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllUserInfosByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultUserInfoShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the userInfoList where gender equals to UPDATED_GENDER
        defaultUserInfoShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllUserInfosByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where gender is not null
        defaultUserInfoShouldBeFound("gender.specified=true");

        // Get all the userInfoList where gender is null
        defaultUserInfoShouldNotBeFound("gender.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserInfosByGenderIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where gender is greater than or equal to DEFAULT_GENDER
        defaultUserInfoShouldBeFound("gender.greaterThanOrEqual=" + DEFAULT_GENDER);

        // Get all the userInfoList where gender is greater than or equal to UPDATED_GENDER
        defaultUserInfoShouldNotBeFound("gender.greaterThanOrEqual=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllUserInfosByGenderIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where gender is less than or equal to DEFAULT_GENDER
        defaultUserInfoShouldBeFound("gender.lessThanOrEqual=" + DEFAULT_GENDER);

        // Get all the userInfoList where gender is less than or equal to SMALLER_GENDER
        defaultUserInfoShouldNotBeFound("gender.lessThanOrEqual=" + SMALLER_GENDER);
    }

    @Test
    @Transactional
    public void getAllUserInfosByGenderIsLessThanSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where gender is less than DEFAULT_GENDER
        defaultUserInfoShouldNotBeFound("gender.lessThan=" + DEFAULT_GENDER);

        // Get all the userInfoList where gender is less than UPDATED_GENDER
        defaultUserInfoShouldBeFound("gender.lessThan=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllUserInfosByGenderIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where gender is greater than DEFAULT_GENDER
        defaultUserInfoShouldNotBeFound("gender.greaterThan=" + DEFAULT_GENDER);

        // Get all the userInfoList where gender is greater than SMALLER_GENDER
        defaultUserInfoShouldBeFound("gender.greaterThan=" + SMALLER_GENDER);
    }


    @Test
    @Transactional
    public void getAllUserInfosByBirthdayIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where birthday equals to DEFAULT_BIRTHDAY
        defaultUserInfoShouldBeFound("birthday.equals=" + DEFAULT_BIRTHDAY);

        // Get all the userInfoList where birthday equals to UPDATED_BIRTHDAY
        defaultUserInfoShouldNotBeFound("birthday.equals=" + UPDATED_BIRTHDAY);
    }

    @Test
    @Transactional
    public void getAllUserInfosByBirthdayIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where birthday not equals to DEFAULT_BIRTHDAY
        defaultUserInfoShouldNotBeFound("birthday.notEquals=" + DEFAULT_BIRTHDAY);

        // Get all the userInfoList where birthday not equals to UPDATED_BIRTHDAY
        defaultUserInfoShouldBeFound("birthday.notEquals=" + UPDATED_BIRTHDAY);
    }

    @Test
    @Transactional
    public void getAllUserInfosByBirthdayIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where birthday in DEFAULT_BIRTHDAY or UPDATED_BIRTHDAY
        defaultUserInfoShouldBeFound("birthday.in=" + DEFAULT_BIRTHDAY + "," + UPDATED_BIRTHDAY);

        // Get all the userInfoList where birthday equals to UPDATED_BIRTHDAY
        defaultUserInfoShouldNotBeFound("birthday.in=" + UPDATED_BIRTHDAY);
    }

    @Test
    @Transactional
    public void getAllUserInfosByBirthdayIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where birthday is not null
        defaultUserInfoShouldBeFound("birthday.specified=true");

        // Get all the userInfoList where birthday is null
        defaultUserInfoShouldNotBeFound("birthday.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserInfosByMobileIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where mobile equals to DEFAULT_MOBILE
        defaultUserInfoShouldBeFound("mobile.equals=" + DEFAULT_MOBILE);

        // Get all the userInfoList where mobile equals to UPDATED_MOBILE
        defaultUserInfoShouldNotBeFound("mobile.equals=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllUserInfosByMobileIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where mobile not equals to DEFAULT_MOBILE
        defaultUserInfoShouldNotBeFound("mobile.notEquals=" + DEFAULT_MOBILE);

        // Get all the userInfoList where mobile not equals to UPDATED_MOBILE
        defaultUserInfoShouldBeFound("mobile.notEquals=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllUserInfosByMobileIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where mobile in DEFAULT_MOBILE or UPDATED_MOBILE
        defaultUserInfoShouldBeFound("mobile.in=" + DEFAULT_MOBILE + "," + UPDATED_MOBILE);

        // Get all the userInfoList where mobile equals to UPDATED_MOBILE
        defaultUserInfoShouldNotBeFound("mobile.in=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllUserInfosByMobileIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where mobile is not null
        defaultUserInfoShouldBeFound("mobile.specified=true");

        // Get all the userInfoList where mobile is null
        defaultUserInfoShouldNotBeFound("mobile.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserInfosByMobileContainsSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where mobile contains DEFAULT_MOBILE
        defaultUserInfoShouldBeFound("mobile.contains=" + DEFAULT_MOBILE);

        // Get all the userInfoList where mobile contains UPDATED_MOBILE
        defaultUserInfoShouldNotBeFound("mobile.contains=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllUserInfosByMobileNotContainsSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where mobile does not contain DEFAULT_MOBILE
        defaultUserInfoShouldNotBeFound("mobile.doesNotContain=" + DEFAULT_MOBILE);

        // Get all the userInfoList where mobile does not contain UPDATED_MOBILE
        defaultUserInfoShouldBeFound("mobile.doesNotContain=" + UPDATED_MOBILE);
    }


    @Test
    @Transactional
    public void getAllUserInfosByMailIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where mail equals to DEFAULT_MAIL
        defaultUserInfoShouldBeFound("mail.equals=" + DEFAULT_MAIL);

        // Get all the userInfoList where mail equals to UPDATED_MAIL
        defaultUserInfoShouldNotBeFound("mail.equals=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    public void getAllUserInfosByMailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where mail not equals to DEFAULT_MAIL
        defaultUserInfoShouldNotBeFound("mail.notEquals=" + DEFAULT_MAIL);

        // Get all the userInfoList where mail not equals to UPDATED_MAIL
        defaultUserInfoShouldBeFound("mail.notEquals=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    public void getAllUserInfosByMailIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where mail in DEFAULT_MAIL or UPDATED_MAIL
        defaultUserInfoShouldBeFound("mail.in=" + DEFAULT_MAIL + "," + UPDATED_MAIL);

        // Get all the userInfoList where mail equals to UPDATED_MAIL
        defaultUserInfoShouldNotBeFound("mail.in=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    public void getAllUserInfosByMailIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where mail is not null
        defaultUserInfoShouldBeFound("mail.specified=true");

        // Get all the userInfoList where mail is null
        defaultUserInfoShouldNotBeFound("mail.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserInfosByMailContainsSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where mail contains DEFAULT_MAIL
        defaultUserInfoShouldBeFound("mail.contains=" + DEFAULT_MAIL);

        // Get all the userInfoList where mail contains UPDATED_MAIL
        defaultUserInfoShouldNotBeFound("mail.contains=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    public void getAllUserInfosByMailNotContainsSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where mail does not contain DEFAULT_MAIL
        defaultUserInfoShouldNotBeFound("mail.doesNotContain=" + DEFAULT_MAIL);

        // Get all the userInfoList where mail does not contain UPDATED_MAIL
        defaultUserInfoShouldBeFound("mail.doesNotContain=" + UPDATED_MAIL);
    }


    @Test
    @Transactional
    public void getAllUserInfosByIdnoIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where idno equals to DEFAULT_IDNO
        defaultUserInfoShouldBeFound("idno.equals=" + DEFAULT_IDNO);

        // Get all the userInfoList where idno equals to UPDATED_IDNO
        defaultUserInfoShouldNotBeFound("idno.equals=" + UPDATED_IDNO);
    }

    @Test
    @Transactional
    public void getAllUserInfosByIdnoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where idno not equals to DEFAULT_IDNO
        defaultUserInfoShouldNotBeFound("idno.notEquals=" + DEFAULT_IDNO);

        // Get all the userInfoList where idno not equals to UPDATED_IDNO
        defaultUserInfoShouldBeFound("idno.notEquals=" + UPDATED_IDNO);
    }

    @Test
    @Transactional
    public void getAllUserInfosByIdnoIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where idno in DEFAULT_IDNO or UPDATED_IDNO
        defaultUserInfoShouldBeFound("idno.in=" + DEFAULT_IDNO + "," + UPDATED_IDNO);

        // Get all the userInfoList where idno equals to UPDATED_IDNO
        defaultUserInfoShouldNotBeFound("idno.in=" + UPDATED_IDNO);
    }

    @Test
    @Transactional
    public void getAllUserInfosByIdnoIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where idno is not null
        defaultUserInfoShouldBeFound("idno.specified=true");

        // Get all the userInfoList where idno is null
        defaultUserInfoShouldNotBeFound("idno.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserInfosByIdnoContainsSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where idno contains DEFAULT_IDNO
        defaultUserInfoShouldBeFound("idno.contains=" + DEFAULT_IDNO);

        // Get all the userInfoList where idno contains UPDATED_IDNO
        defaultUserInfoShouldNotBeFound("idno.contains=" + UPDATED_IDNO);
    }

    @Test
    @Transactional
    public void getAllUserInfosByIdnoNotContainsSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where idno does not contain DEFAULT_IDNO
        defaultUserInfoShouldNotBeFound("idno.doesNotContain=" + DEFAULT_IDNO);

        // Get all the userInfoList where idno does not contain UPDATED_IDNO
        defaultUserInfoShouldBeFound("idno.doesNotContain=" + UPDATED_IDNO);
    }


    @Test
    @Transactional
    public void getAllUserInfosBySchoolIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where school equals to DEFAULT_SCHOOL
        defaultUserInfoShouldBeFound("school.equals=" + DEFAULT_SCHOOL);

        // Get all the userInfoList where school equals to UPDATED_SCHOOL
        defaultUserInfoShouldNotBeFound("school.equals=" + UPDATED_SCHOOL);
    }

    @Test
    @Transactional
    public void getAllUserInfosBySchoolIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where school not equals to DEFAULT_SCHOOL
        defaultUserInfoShouldNotBeFound("school.notEquals=" + DEFAULT_SCHOOL);

        // Get all the userInfoList where school not equals to UPDATED_SCHOOL
        defaultUserInfoShouldBeFound("school.notEquals=" + UPDATED_SCHOOL);
    }

    @Test
    @Transactional
    public void getAllUserInfosBySchoolIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where school in DEFAULT_SCHOOL or UPDATED_SCHOOL
        defaultUserInfoShouldBeFound("school.in=" + DEFAULT_SCHOOL + "," + UPDATED_SCHOOL);

        // Get all the userInfoList where school equals to UPDATED_SCHOOL
        defaultUserInfoShouldNotBeFound("school.in=" + UPDATED_SCHOOL);
    }

    @Test
    @Transactional
    public void getAllUserInfosBySchoolIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where school is not null
        defaultUserInfoShouldBeFound("school.specified=true");

        // Get all the userInfoList where school is null
        defaultUserInfoShouldNotBeFound("school.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserInfosBySchoolContainsSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where school contains DEFAULT_SCHOOL
        defaultUserInfoShouldBeFound("school.contains=" + DEFAULT_SCHOOL);

        // Get all the userInfoList where school contains UPDATED_SCHOOL
        defaultUserInfoShouldNotBeFound("school.contains=" + UPDATED_SCHOOL);
    }

    @Test
    @Transactional
    public void getAllUserInfosBySchoolNotContainsSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where school does not contain DEFAULT_SCHOOL
        defaultUserInfoShouldNotBeFound("school.doesNotContain=" + DEFAULT_SCHOOL);

        // Get all the userInfoList where school does not contain UPDATED_SCHOOL
        defaultUserInfoShouldBeFound("school.doesNotContain=" + UPDATED_SCHOOL);
    }


    @Test
    @Transactional
    public void getAllUserInfosBySpecialtyIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where specialty equals to DEFAULT_SPECIALTY
        defaultUserInfoShouldBeFound("specialty.equals=" + DEFAULT_SPECIALTY);

        // Get all the userInfoList where specialty equals to UPDATED_SPECIALTY
        defaultUserInfoShouldNotBeFound("specialty.equals=" + UPDATED_SPECIALTY);
    }

    @Test
    @Transactional
    public void getAllUserInfosBySpecialtyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where specialty not equals to DEFAULT_SPECIALTY
        defaultUserInfoShouldNotBeFound("specialty.notEquals=" + DEFAULT_SPECIALTY);

        // Get all the userInfoList where specialty not equals to UPDATED_SPECIALTY
        defaultUserInfoShouldBeFound("specialty.notEquals=" + UPDATED_SPECIALTY);
    }

    @Test
    @Transactional
    public void getAllUserInfosBySpecialtyIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where specialty in DEFAULT_SPECIALTY or UPDATED_SPECIALTY
        defaultUserInfoShouldBeFound("specialty.in=" + DEFAULT_SPECIALTY + "," + UPDATED_SPECIALTY);

        // Get all the userInfoList where specialty equals to UPDATED_SPECIALTY
        defaultUserInfoShouldNotBeFound("specialty.in=" + UPDATED_SPECIALTY);
    }

    @Test
    @Transactional
    public void getAllUserInfosBySpecialtyIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where specialty is not null
        defaultUserInfoShouldBeFound("specialty.specified=true");

        // Get all the userInfoList where specialty is null
        defaultUserInfoShouldNotBeFound("specialty.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserInfosBySpecialtyContainsSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where specialty contains DEFAULT_SPECIALTY
        defaultUserInfoShouldBeFound("specialty.contains=" + DEFAULT_SPECIALTY);

        // Get all the userInfoList where specialty contains UPDATED_SPECIALTY
        defaultUserInfoShouldNotBeFound("specialty.contains=" + UPDATED_SPECIALTY);
    }

    @Test
    @Transactional
    public void getAllUserInfosBySpecialtyNotContainsSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where specialty does not contain DEFAULT_SPECIALTY
        defaultUserInfoShouldNotBeFound("specialty.doesNotContain=" + DEFAULT_SPECIALTY);

        // Get all the userInfoList where specialty does not contain UPDATED_SPECIALTY
        defaultUserInfoShouldBeFound("specialty.doesNotContain=" + UPDATED_SPECIALTY);
    }


    @Test
    @Transactional
    public void getAllUserInfosByEducationIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where education equals to DEFAULT_EDUCATION
        defaultUserInfoShouldBeFound("education.equals=" + DEFAULT_EDUCATION);

        // Get all the userInfoList where education equals to UPDATED_EDUCATION
        defaultUserInfoShouldNotBeFound("education.equals=" + UPDATED_EDUCATION);
    }

    @Test
    @Transactional
    public void getAllUserInfosByEducationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where education not equals to DEFAULT_EDUCATION
        defaultUserInfoShouldNotBeFound("education.notEquals=" + DEFAULT_EDUCATION);

        // Get all the userInfoList where education not equals to UPDATED_EDUCATION
        defaultUserInfoShouldBeFound("education.notEquals=" + UPDATED_EDUCATION);
    }

    @Test
    @Transactional
    public void getAllUserInfosByEducationIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where education in DEFAULT_EDUCATION or UPDATED_EDUCATION
        defaultUserInfoShouldBeFound("education.in=" + DEFAULT_EDUCATION + "," + UPDATED_EDUCATION);

        // Get all the userInfoList where education equals to UPDATED_EDUCATION
        defaultUserInfoShouldNotBeFound("education.in=" + UPDATED_EDUCATION);
    }

    @Test
    @Transactional
    public void getAllUserInfosByEducationIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where education is not null
        defaultUserInfoShouldBeFound("education.specified=true");

        // Get all the userInfoList where education is null
        defaultUserInfoShouldNotBeFound("education.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserInfosByEducationContainsSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where education contains DEFAULT_EDUCATION
        defaultUserInfoShouldBeFound("education.contains=" + DEFAULT_EDUCATION);

        // Get all the userInfoList where education contains UPDATED_EDUCATION
        defaultUserInfoShouldNotBeFound("education.contains=" + UPDATED_EDUCATION);
    }

    @Test
    @Transactional
    public void getAllUserInfosByEducationNotContainsSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where education does not contain DEFAULT_EDUCATION
        defaultUserInfoShouldNotBeFound("education.doesNotContain=" + DEFAULT_EDUCATION);

        // Get all the userInfoList where education does not contain UPDATED_EDUCATION
        defaultUserInfoShouldBeFound("education.doesNotContain=" + UPDATED_EDUCATION);
    }


    @Test
    @Transactional
    public void getAllUserInfosByGraduationIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where graduation equals to DEFAULT_GRADUATION
        defaultUserInfoShouldBeFound("graduation.equals=" + DEFAULT_GRADUATION);

        // Get all the userInfoList where graduation equals to UPDATED_GRADUATION
        defaultUserInfoShouldNotBeFound("graduation.equals=" + UPDATED_GRADUATION);
    }

    @Test
    @Transactional
    public void getAllUserInfosByGraduationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where graduation not equals to DEFAULT_GRADUATION
        defaultUserInfoShouldNotBeFound("graduation.notEquals=" + DEFAULT_GRADUATION);

        // Get all the userInfoList where graduation not equals to UPDATED_GRADUATION
        defaultUserInfoShouldBeFound("graduation.notEquals=" + UPDATED_GRADUATION);
    }

    @Test
    @Transactional
    public void getAllUserInfosByGraduationIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where graduation in DEFAULT_GRADUATION or UPDATED_GRADUATION
        defaultUserInfoShouldBeFound("graduation.in=" + DEFAULT_GRADUATION + "," + UPDATED_GRADUATION);

        // Get all the userInfoList where graduation equals to UPDATED_GRADUATION
        defaultUserInfoShouldNotBeFound("graduation.in=" + UPDATED_GRADUATION);
    }

    @Test
    @Transactional
    public void getAllUserInfosByGraduationIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where graduation is not null
        defaultUserInfoShouldBeFound("graduation.specified=true");

        // Get all the userInfoList where graduation is null
        defaultUserInfoShouldNotBeFound("graduation.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserInfosByGraduationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where graduation is greater than or equal to DEFAULT_GRADUATION
        defaultUserInfoShouldBeFound("graduation.greaterThanOrEqual=" + DEFAULT_GRADUATION);

        // Get all the userInfoList where graduation is greater than or equal to UPDATED_GRADUATION
        defaultUserInfoShouldNotBeFound("graduation.greaterThanOrEqual=" + UPDATED_GRADUATION);
    }

    @Test
    @Transactional
    public void getAllUserInfosByGraduationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where graduation is less than or equal to DEFAULT_GRADUATION
        defaultUserInfoShouldBeFound("graduation.lessThanOrEqual=" + DEFAULT_GRADUATION);

        // Get all the userInfoList where graduation is less than or equal to SMALLER_GRADUATION
        defaultUserInfoShouldNotBeFound("graduation.lessThanOrEqual=" + SMALLER_GRADUATION);
    }

    @Test
    @Transactional
    public void getAllUserInfosByGraduationIsLessThanSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where graduation is less than DEFAULT_GRADUATION
        defaultUserInfoShouldNotBeFound("graduation.lessThan=" + DEFAULT_GRADUATION);

        // Get all the userInfoList where graduation is less than UPDATED_GRADUATION
        defaultUserInfoShouldBeFound("graduation.lessThan=" + UPDATED_GRADUATION);
    }

    @Test
    @Transactional
    public void getAllUserInfosByGraduationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where graduation is greater than DEFAULT_GRADUATION
        defaultUserInfoShouldNotBeFound("graduation.greaterThan=" + DEFAULT_GRADUATION);

        // Get all the userInfoList where graduation is greater than SMALLER_GRADUATION
        defaultUserInfoShouldBeFound("graduation.greaterThan=" + SMALLER_GRADUATION);
    }


    @Test
    @Transactional
    public void getAllUserInfosByCompanyIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where company equals to DEFAULT_COMPANY
        defaultUserInfoShouldBeFound("company.equals=" + DEFAULT_COMPANY);

        // Get all the userInfoList where company equals to UPDATED_COMPANY
        defaultUserInfoShouldNotBeFound("company.equals=" + UPDATED_COMPANY);
    }

    @Test
    @Transactional
    public void getAllUserInfosByCompanyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where company not equals to DEFAULT_COMPANY
        defaultUserInfoShouldNotBeFound("company.notEquals=" + DEFAULT_COMPANY);

        // Get all the userInfoList where company not equals to UPDATED_COMPANY
        defaultUserInfoShouldBeFound("company.notEquals=" + UPDATED_COMPANY);
    }

    @Test
    @Transactional
    public void getAllUserInfosByCompanyIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where company in DEFAULT_COMPANY or UPDATED_COMPANY
        defaultUserInfoShouldBeFound("company.in=" + DEFAULT_COMPANY + "," + UPDATED_COMPANY);

        // Get all the userInfoList where company equals to UPDATED_COMPANY
        defaultUserInfoShouldNotBeFound("company.in=" + UPDATED_COMPANY);
    }

    @Test
    @Transactional
    public void getAllUserInfosByCompanyIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where company is not null
        defaultUserInfoShouldBeFound("company.specified=true");

        // Get all the userInfoList where company is null
        defaultUserInfoShouldNotBeFound("company.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserInfosByCompanyContainsSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where company contains DEFAULT_COMPANY
        defaultUserInfoShouldBeFound("company.contains=" + DEFAULT_COMPANY);

        // Get all the userInfoList where company contains UPDATED_COMPANY
        defaultUserInfoShouldNotBeFound("company.contains=" + UPDATED_COMPANY);
    }

    @Test
    @Transactional
    public void getAllUserInfosByCompanyNotContainsSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where company does not contain DEFAULT_COMPANY
        defaultUserInfoShouldNotBeFound("company.doesNotContain=" + DEFAULT_COMPANY);

        // Get all the userInfoList where company does not contain UPDATED_COMPANY
        defaultUserInfoShouldBeFound("company.doesNotContain=" + UPDATED_COMPANY);
    }


    @Test
    @Transactional
    public void getAllUserInfosByCompanyMailIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where companyMail equals to DEFAULT_COMPANY_MAIL
        defaultUserInfoShouldBeFound("companyMail.equals=" + DEFAULT_COMPANY_MAIL);

        // Get all the userInfoList where companyMail equals to UPDATED_COMPANY_MAIL
        defaultUserInfoShouldNotBeFound("companyMail.equals=" + UPDATED_COMPANY_MAIL);
    }

    @Test
    @Transactional
    public void getAllUserInfosByCompanyMailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where companyMail not equals to DEFAULT_COMPANY_MAIL
        defaultUserInfoShouldNotBeFound("companyMail.notEquals=" + DEFAULT_COMPANY_MAIL);

        // Get all the userInfoList where companyMail not equals to UPDATED_COMPANY_MAIL
        defaultUserInfoShouldBeFound("companyMail.notEquals=" + UPDATED_COMPANY_MAIL);
    }

    @Test
    @Transactional
    public void getAllUserInfosByCompanyMailIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where companyMail in DEFAULT_COMPANY_MAIL or UPDATED_COMPANY_MAIL
        defaultUserInfoShouldBeFound("companyMail.in=" + DEFAULT_COMPANY_MAIL + "," + UPDATED_COMPANY_MAIL);

        // Get all the userInfoList where companyMail equals to UPDATED_COMPANY_MAIL
        defaultUserInfoShouldNotBeFound("companyMail.in=" + UPDATED_COMPANY_MAIL);
    }

    @Test
    @Transactional
    public void getAllUserInfosByCompanyMailIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where companyMail is not null
        defaultUserInfoShouldBeFound("companyMail.specified=true");

        // Get all the userInfoList where companyMail is null
        defaultUserInfoShouldNotBeFound("companyMail.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserInfosByCompanyMailContainsSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where companyMail contains DEFAULT_COMPANY_MAIL
        defaultUserInfoShouldBeFound("companyMail.contains=" + DEFAULT_COMPANY_MAIL);

        // Get all the userInfoList where companyMail contains UPDATED_COMPANY_MAIL
        defaultUserInfoShouldNotBeFound("companyMail.contains=" + UPDATED_COMPANY_MAIL);
    }

    @Test
    @Transactional
    public void getAllUserInfosByCompanyMailNotContainsSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where companyMail does not contain DEFAULT_COMPANY_MAIL
        defaultUserInfoShouldNotBeFound("companyMail.doesNotContain=" + DEFAULT_COMPANY_MAIL);

        // Get all the userInfoList where companyMail does not contain UPDATED_COMPANY_MAIL
        defaultUserInfoShouldBeFound("companyMail.doesNotContain=" + UPDATED_COMPANY_MAIL);
    }


    @Test
    @Transactional
    public void getAllUserInfosByMailValidatedIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where mailValidated equals to DEFAULT_MAIL_VALIDATED
        defaultUserInfoShouldBeFound("mailValidated.equals=" + DEFAULT_MAIL_VALIDATED);

        // Get all the userInfoList where mailValidated equals to UPDATED_MAIL_VALIDATED
        defaultUserInfoShouldNotBeFound("mailValidated.equals=" + UPDATED_MAIL_VALIDATED);
    }

    @Test
    @Transactional
    public void getAllUserInfosByMailValidatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where mailValidated not equals to DEFAULT_MAIL_VALIDATED
        defaultUserInfoShouldNotBeFound("mailValidated.notEquals=" + DEFAULT_MAIL_VALIDATED);

        // Get all the userInfoList where mailValidated not equals to UPDATED_MAIL_VALIDATED
        defaultUserInfoShouldBeFound("mailValidated.notEquals=" + UPDATED_MAIL_VALIDATED);
    }

    @Test
    @Transactional
    public void getAllUserInfosByMailValidatedIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where mailValidated in DEFAULT_MAIL_VALIDATED or UPDATED_MAIL_VALIDATED
        defaultUserInfoShouldBeFound("mailValidated.in=" + DEFAULT_MAIL_VALIDATED + "," + UPDATED_MAIL_VALIDATED);

        // Get all the userInfoList where mailValidated equals to UPDATED_MAIL_VALIDATED
        defaultUserInfoShouldNotBeFound("mailValidated.in=" + UPDATED_MAIL_VALIDATED);
    }

    @Test
    @Transactional
    public void getAllUserInfosByMailValidatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where mailValidated is not null
        defaultUserInfoShouldBeFound("mailValidated.specified=true");

        // Get all the userInfoList where mailValidated is null
        defaultUserInfoShouldNotBeFound("mailValidated.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserInfosByCompanyMailValidatedIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where companyMailValidated equals to DEFAULT_COMPANY_MAIL_VALIDATED
        defaultUserInfoShouldBeFound("companyMailValidated.equals=" + DEFAULT_COMPANY_MAIL_VALIDATED);

        // Get all the userInfoList where companyMailValidated equals to UPDATED_COMPANY_MAIL_VALIDATED
        defaultUserInfoShouldNotBeFound("companyMailValidated.equals=" + UPDATED_COMPANY_MAIL_VALIDATED);
    }

    @Test
    @Transactional
    public void getAllUserInfosByCompanyMailValidatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where companyMailValidated not equals to DEFAULT_COMPANY_MAIL_VALIDATED
        defaultUserInfoShouldNotBeFound("companyMailValidated.notEquals=" + DEFAULT_COMPANY_MAIL_VALIDATED);

        // Get all the userInfoList where companyMailValidated not equals to UPDATED_COMPANY_MAIL_VALIDATED
        defaultUserInfoShouldBeFound("companyMailValidated.notEquals=" + UPDATED_COMPANY_MAIL_VALIDATED);
    }

    @Test
    @Transactional
    public void getAllUserInfosByCompanyMailValidatedIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where companyMailValidated in DEFAULT_COMPANY_MAIL_VALIDATED or UPDATED_COMPANY_MAIL_VALIDATED
        defaultUserInfoShouldBeFound("companyMailValidated.in=" + DEFAULT_COMPANY_MAIL_VALIDATED + "," + UPDATED_COMPANY_MAIL_VALIDATED);

        // Get all the userInfoList where companyMailValidated equals to UPDATED_COMPANY_MAIL_VALIDATED
        defaultUserInfoShouldNotBeFound("companyMailValidated.in=" + UPDATED_COMPANY_MAIL_VALIDATED);
    }

    @Test
    @Transactional
    public void getAllUserInfosByCompanyMailValidatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where companyMailValidated is not null
        defaultUserInfoShouldBeFound("companyMailValidated.specified=true");

        // Get all the userInfoList where companyMailValidated is null
        defaultUserInfoShouldNotBeFound("companyMailValidated.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUserInfoShouldBeFound(String filter) throws Exception {
        restUserInfoMockMvc.perform(get("/api/user-infos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL)))
            .andExpect(jsonPath("$.[*].idno").value(hasItem(DEFAULT_IDNO)))
            .andExpect(jsonPath("$.[*].school").value(hasItem(DEFAULT_SCHOOL)))
            .andExpect(jsonPath("$.[*].specialty").value(hasItem(DEFAULT_SPECIALTY)))
            .andExpect(jsonPath("$.[*].education").value(hasItem(DEFAULT_EDUCATION)))
            .andExpect(jsonPath("$.[*].graduation").value(hasItem(DEFAULT_GRADUATION)))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY)))
            .andExpect(jsonPath("$.[*].companyMail").value(hasItem(DEFAULT_COMPANY_MAIL)))
            .andExpect(jsonPath("$.[*].mailValidated").value(hasItem(DEFAULT_MAIL_VALIDATED.booleanValue())))
            .andExpect(jsonPath("$.[*].companyMailValidated").value(hasItem(DEFAULT_COMPANY_MAIL_VALIDATED.booleanValue())));

        // Check, that the count call also returns 1
        restUserInfoMockMvc.perform(get("/api/user-infos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUserInfoShouldNotBeFound(String filter) throws Exception {
        restUserInfoMockMvc.perform(get("/api/user-infos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUserInfoMockMvc.perform(get("/api/user-infos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingUserInfo() throws Exception {
        // Get the userInfo
        restUserInfoMockMvc.perform(get("/api/user-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserInfo() throws Exception {
        // Initialize the database
        userInfoService.save(userInfo);

        int databaseSizeBeforeUpdate = userInfoRepository.findAll().size();

        // Update the userInfo
        UserInfo updatedUserInfo = userInfoRepository.findById(userInfo.getId()).get();
        // Disconnect from session so that the updates on updatedUserInfo are not directly saved in db
        em.detach(updatedUserInfo);
        updatedUserInfo
            .login(UPDATED_LOGIN)
            .name(UPDATED_NAME)
            .gender(UPDATED_GENDER)
            .birthday(UPDATED_BIRTHDAY)
            .mobile(UPDATED_MOBILE)
            .mail(UPDATED_MAIL)
            .idno(UPDATED_IDNO)
            .school(UPDATED_SCHOOL)
            .specialty(UPDATED_SPECIALTY)
            .education(UPDATED_EDUCATION)
            .graduation(UPDATED_GRADUATION)
            .company(UPDATED_COMPANY)
            .companyMail(UPDATED_COMPANY_MAIL)
            .mailValidated(UPDATED_MAIL_VALIDATED)
            .companyMailValidated(UPDATED_COMPANY_MAIL_VALIDATED);

        restUserInfoMockMvc.perform(put("/api/user-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserInfo)))
            .andExpect(status().isOk());

        // Validate the UserInfo in the database
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeUpdate);
        UserInfo testUserInfo = userInfoList.get(userInfoList.size() - 1);
        assertThat(testUserInfo.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testUserInfo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUserInfo.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testUserInfo.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testUserInfo.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testUserInfo.getMail()).isEqualTo(UPDATED_MAIL);
        assertThat(testUserInfo.getIdno()).isEqualTo(UPDATED_IDNO);
        assertThat(testUserInfo.getSchool()).isEqualTo(UPDATED_SCHOOL);
        assertThat(testUserInfo.getSpecialty()).isEqualTo(UPDATED_SPECIALTY);
        assertThat(testUserInfo.getEducation()).isEqualTo(UPDATED_EDUCATION);
        assertThat(testUserInfo.getGraduation()).isEqualTo(UPDATED_GRADUATION);
        assertThat(testUserInfo.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testUserInfo.getCompanyMail()).isEqualTo(UPDATED_COMPANY_MAIL);
        assertThat(testUserInfo.isMailValidated()).isEqualTo(UPDATED_MAIL_VALIDATED);
        assertThat(testUserInfo.isCompanyMailValidated()).isEqualTo(UPDATED_COMPANY_MAIL_VALIDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingUserInfo() throws Exception {
        int databaseSizeBeforeUpdate = userInfoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserInfoMockMvc.perform(put("/api/user-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userInfo)))
            .andExpect(status().isBadRequest());

        // Validate the UserInfo in the database
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserInfo() throws Exception {
        // Initialize the database
        userInfoService.save(userInfo);

        int databaseSizeBeforeDelete = userInfoRepository.findAll().size();

        // Delete the userInfo
        restUserInfoMockMvc.perform(delete("/api/user-infos/{id}", userInfo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
