package com.magazyn.backendapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.magazyn.backendapi.generators.TrackingNumberGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private ShipmentDimensions dimensions;
    @Min(value = 10, message = "{order.weigh.notnull}")
    private String weigh;
    @NotNull(  message = "{order.price.min}")
    private BigDecimal price;
    private LocalDate creationDate = LocalDate.now();
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.MAGAZYN;

    private String provider = "BRAK";
    @Pattern(regexp = "^\\d{2}-\\d{3}$", message ="{order.zipCodeRecipient.notnull}")
    private String zipCodeRecipient;
    @Size(min =3, message = "{order.cityRecipient.notnull}")
    private String cityRecipient;
    @Size(min =3,message = "{order.streetRecipient.notnull}")
    private String streetRecipient;
    @Size(min =3,message = "{order.nameRecipient.notnull}")
    private String nameRecipient;

    private String trackingNumber = TrackingNumberGenerator.generateTrackingNumber();
    @JsonBackReference
    @ManyToOne
    private Customer customer;

}
