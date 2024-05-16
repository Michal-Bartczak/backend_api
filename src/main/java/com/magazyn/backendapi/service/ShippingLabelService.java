package com.magazyn.backendapi.service;

public interface ShippingLabelService {
    byte[] createPdf(String trackingNumber);
}
