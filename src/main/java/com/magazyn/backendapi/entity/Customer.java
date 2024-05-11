package com.magazyn.backendapi.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import jakarta.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter
@Table(name = "customers")

public class Customer extends BaseUser {
    private String role = "ROLE_CUSTOMER";

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private CustomerDetails customerDetails;
    @JsonManagedReference
    @OneToMany(mappedBy = "customer")
    private List<Order> orders;
}
