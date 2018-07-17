package com.adstats.exception;

public class ClickNotFoundException extends RuntimeException {
  public ClickNotFoundException(String deliveryId) {
    super(String.format("Click with id=%s is not found", deliveryId));
  }
}
