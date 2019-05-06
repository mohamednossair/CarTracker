package com.tracker.car.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Car.
 */
@Entity
@Table(name = "car")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @NotNull
    @Column(name = "car_type", nullable = false)
    private String carType;

    @Column(name = "category")
    private String category;

    @Column(name = "serial_num")
    private String serialNum;

    @Column(name = "hide")
    private Boolean hide;

    @Column(name = "is_new")
    private Boolean isNew;

    @OneToMany(mappedBy = "car")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EmployeeCars> employeeCars = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Car code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCarType() {
        return carType;
    }

    public Car carType(String carType) {
        this.carType = carType;
        return this;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCategory() {
        return category;
    }

    public Car category(String category) {
        this.category = category;
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public Car serialNum(String serialNum) {
        this.serialNum = serialNum;
        return this;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public Boolean isHide() {
        return hide;
    }

    public Car hide(Boolean hide) {
        this.hide = hide;
        return this;
    }

    public void setHide(Boolean hide) {
        this.hide = hide;
    }

    public Boolean isIsNew() {
        return isNew;
    }

    public Car isNew(Boolean isNew) {
        this.isNew = isNew;
        return this;
    }

    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
    }

    public Set<EmployeeCars> getEmployeeCars() {
        return employeeCars;
    }

    public Car employeeCars(Set<EmployeeCars> employeeCars) {
        this.employeeCars = employeeCars;
        return this;
    }

    public Car addEmployeeCars(EmployeeCars employeeCars) {
        this.employeeCars.add(employeeCars);
        employeeCars.setCar(this);
        return this;
    }

    public Car removeEmployeeCars(EmployeeCars employeeCars) {
        this.employeeCars.remove(employeeCars);
        employeeCars.setCar(null);
        return this;
    }

    public void setEmployeeCars(Set<EmployeeCars> employeeCars) {
        this.employeeCars = employeeCars;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Car)) {
            return false;
        }
        return id != null && id.equals(((Car) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Car{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", carType='" + getCarType() + "'" +
            ", category='" + getCategory() + "'" +
            ", serialNum='" + getSerialNum() + "'" +
            ", hide='" + isHide() + "'" +
            ", isNew='" + isIsNew() + "'" +
            "}";
    }
}
