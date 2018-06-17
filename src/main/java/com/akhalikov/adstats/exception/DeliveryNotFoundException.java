package com.akhalikov.adstats.exception;

public class DeliveryNotFoundException extends RuntimeException {
  public DeliveryNotFoundException(String deliveryId) {
    super(String.format("Delivery with id=%s is not found", deliveryId));
  }
}
