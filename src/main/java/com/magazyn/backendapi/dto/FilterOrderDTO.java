package com.magazyn.backendapi.dto;

import com.magazyn.backendapi.entity.ShipmentDimensions;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Data
@Component
public class FilterOrderDTO {
    private String filterText;
    private LocalDate filterData;
    private String status;
    private ShipmentDimensions kindEur;
    private ShipmentDimensions kindHp;
    private int page = 0;
    private int size = 10;


}
