package com.adstats.ads.click;

public final class Click {

  private String clickId;
  private String deliveryId;
  private String time;

  public Click() {
  }

  public Click(String clickId, String deliveryId, String time) {
    this.clickId = clickId;
    this.deliveryId = deliveryId;
    this.time = time;
  }

  public String getClickId() {
    return clickId;
  }

  public String getDeliveryId() {
    return deliveryId;
  }

  public String getTime() {
    return time;
  }
}
