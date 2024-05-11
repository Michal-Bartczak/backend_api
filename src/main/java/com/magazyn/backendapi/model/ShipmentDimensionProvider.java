package com.magazyn.backendapi.model;

import com.magazyn.backendapi.entity.ShipmentDimensions;

public interface ShipmentDimensionProvider {
    ShipmentDimensions[] getValue();
}
