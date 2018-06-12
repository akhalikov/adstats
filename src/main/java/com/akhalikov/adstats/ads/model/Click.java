package com.akhalikov.adstats.ads.model;

public final class Click {

  private String deliveryId;
  private String clickId;
  private String time;

  public Click() {
  }

  public Click(String deliveryId, String clickId, String time) {
    this.deliveryId = deliveryId;
    this.clickId = clickId;
    this.time = time;
  }

  public String getDeliveryId() {
    return deliveryId;
  }

  public String getClickId() {
    return clickId;
  }

  public String getTime() {
    return time;
  }
}
