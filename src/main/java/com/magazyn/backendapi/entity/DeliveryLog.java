package com.magazyn.backendapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "delivery_logs")
@Getter
@Setter
public class DeliveryLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    public DeliveryStatus deliveryStatus;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    private LocalDate deliveryDate = LocalDate.now();
}
