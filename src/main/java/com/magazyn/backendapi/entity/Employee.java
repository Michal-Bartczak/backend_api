package com.magazyn.backendapi.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Setter
@Getter
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "employees")
public class Employee extends BaseUser{
    @Size(min=3, message = "{employee.name.size}")
    private String name;
    @Size(min=3, max = 15, message = "{employee.surname.size}")
    private String surname;
    private String role = "ROLE_EMPLOYEE";
}
