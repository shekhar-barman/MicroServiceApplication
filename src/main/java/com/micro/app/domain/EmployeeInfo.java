package com.micro.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A EmployeeInfo.
 */
@Entity
@Table(name = "employee_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmployeeInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "emp_name")
    private String empName;

    @Column(name = "designation")
    private String designation;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "remarks")
    private String remarks;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmpName() {
        return empName;
    }

    public EmployeeInfo empName(String empName) {
        this.empName = empName;
        return this;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getDesignation() {
        return designation;
    }

    public EmployeeInfo designation(String designation) {
        this.designation = designation;
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getMobile() {
        return mobile;
    }

    public EmployeeInfo mobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public LocalDate getDob() {
        return dob;
    }

    public EmployeeInfo dob(LocalDate dob) {
        this.dob = dob;
        return this;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getRemarks() {
        return remarks;
    }

    public EmployeeInfo remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeInfo)) {
            return false;
        }
        return id != null && id.equals(((EmployeeInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeInfo{" +
            "id=" + getId() +
            ", empName='" + getEmpName() + "'" +
            ", designation='" + getDesignation() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", dob='" + getDob() + "'" +
            ", remarks='" + getRemarks() + "'" +
            "}";
    }
}
