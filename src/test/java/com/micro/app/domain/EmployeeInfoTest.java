package com.micro.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.micro.app.web.rest.TestUtil;

public class EmployeeInfoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeInfo.class);
        EmployeeInfo employeeInfo1 = new EmployeeInfo();
        employeeInfo1.setId(1L);
        EmployeeInfo employeeInfo2 = new EmployeeInfo();
        employeeInfo2.setId(employeeInfo1.getId());
        assertThat(employeeInfo1).isEqualTo(employeeInfo2);
        employeeInfo2.setId(2L);
        assertThat(employeeInfo1).isNotEqualTo(employeeInfo2);
        employeeInfo1.setId(null);
        assertThat(employeeInfo1).isNotEqualTo(employeeInfo2);
    }
}
