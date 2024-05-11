package com.magazyn.backendapi.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
@Setter
@Getter
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "admins")
public class Admin extends BaseUser {

    private String role = "ROLE_ADMIN";
}
