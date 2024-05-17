package com.magazyn.backendapi.dto;

import com.magazyn.backendapi.entity.ShipmentDimensions;
import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Data
@Component
@Getter
public class FilterOrderDTO {
    private String filterText;
    private LocalDate filterData;
    private String filterStatus;
    private ShipmentDimensions filterKindEur;
    private ShipmentDimensions filterKindHp;



}
