package com.magazyn.backendapi.entity;

import com.magazyn.backendapi.model.ShipmentDimensionProvider;

public enum ShipmentDimensions implements ShipmentDimensionProvider {
    HP,
    EUR;



    @Override
    public ShipmentDimensions[] getValue() {
        return ShipmentDimensions.values();
    }

}
