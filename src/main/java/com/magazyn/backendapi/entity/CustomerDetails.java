package com.magazyn.backendapi.entity;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Getter
@Setter
public class CustomerDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 5, max = 30, message = "{customer.nameCompanySender.size}")
    private String nameCompanySender;
    @Pattern(regexp = "\\d{2}-\\d{3}", message = "{customer.zipCodeSender.pattern}")
    private String zipCodeSender;
    @Size(min = 3, message = "{customer.citySender.size}")
    private String citySender;
    @Size(min = 3, message = "{customer.streetSender.size}")
    private String streetSender;
    @Pattern(regexp = "\\d{9}", message = "{customer.contactSender.pattern}")
    private String contactSender;
    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
