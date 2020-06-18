package com.job.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.job.web.rest.TestUtil;

public class ApplyInfoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplyInfo.class);
        ApplyInfo applyInfo1 = new ApplyInfo();
        applyInfo1.setId(1L);
        ApplyInfo applyInfo2 = new ApplyInfo();
        applyInfo2.setId(applyInfo1.getId());
        assertThat(applyInfo1).isEqualTo(applyInfo2);
        applyInfo2.setId(2L);
        assertThat(applyInfo1).isNotEqualTo(applyInfo2);
        applyInfo1.setId(null);
        assertThat(applyInfo1).isNotEqualTo(applyInfo2);
    }
}
