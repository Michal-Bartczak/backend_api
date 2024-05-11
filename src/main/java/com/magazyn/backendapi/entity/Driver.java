package com.magazyn.backendapi.entity;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.util.List;

@Setter
@Getter
@Entity

@Table(name = "drivers")
public class Driver extends BaseUser{
    @Size(min=3, message = "{driver.name.size}")
    private String name;
    @Size(min=3, max = 15, message = "{driver.surname.size}")
    private String surname;
    private String role = "ROLE_DRIVER";
    //   @JsonManagedReference
    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeliveryLog> logs;
    public void addLog(DeliveryLog log) {
        this.logs.add(log);
        log.setDriver(this);
    }

    public void removeLog(DeliveryLog log) {
        this.logs.remove(log);
        log.setDriver(null);
    }
}
