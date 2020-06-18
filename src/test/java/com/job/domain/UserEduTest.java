package com.job.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.job.web.rest.TestUtil;

public class UserEduTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserEdu.class);
        UserEdu userEdu1 = new UserEdu();
        userEdu1.setId(1L);
        UserEdu userEdu2 = new UserEdu();
        userEdu2.setId(userEdu1.getId());
        assertThat(userEdu1).isEqualTo(userEdu2);
        userEdu2.setId(2L);
        assertThat(userEdu1).isNotEqualTo(userEdu2);
        userEdu1.setId(null);
        assertThat(userEdu1).isNotEqualTo(userEdu2);
    }
}
