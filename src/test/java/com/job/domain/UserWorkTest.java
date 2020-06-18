package com.job.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.job.web.rest.TestUtil;

public class UserWorkTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserWork.class);
        UserWork userWork1 = new UserWork();
        userWork1.setId(1L);
        UserWork userWork2 = new UserWork();
        userWork2.setId(userWork1.getId());
        assertThat(userWork1).isEqualTo(userWork2);
        userWork2.setId(2L);
        assertThat(userWork1).isNotEqualTo(userWork2);
        userWork1.setId(null);
        assertThat(userWork1).isNotEqualTo(userWork2);
    }
}
