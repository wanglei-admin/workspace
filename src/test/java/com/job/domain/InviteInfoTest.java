package com.job.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.job.web.rest.TestUtil;

public class InviteInfoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InviteInfo.class);
        InviteInfo inviteInfo1 = new InviteInfo();
        inviteInfo1.setId(1L);
        InviteInfo inviteInfo2 = new InviteInfo();
        inviteInfo2.setId(inviteInfo1.getId());
        assertThat(inviteInfo1).isEqualTo(inviteInfo2);
        inviteInfo2.setId(2L);
        assertThat(inviteInfo1).isNotEqualTo(inviteInfo2);
        inviteInfo1.setId(null);
        assertThat(inviteInfo1).isNotEqualTo(inviteInfo2);
    }
}
