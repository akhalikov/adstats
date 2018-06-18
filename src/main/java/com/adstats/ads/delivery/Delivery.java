package com.adstats.ads.delivery;

public final class Delivery {

  private String deliveryId;
  private int advertisementId;
  private String time;
  private String browser;
  private String os;
  private String site;

  public Delivery() {
  }

  public Delivery(String deliveryId, int advertisementId, String time, String browser, String os, String site) {
    this.deliveryId = deliveryId;
    this.advertisementId = advertisementId;
    this.time = time;
    this.browser = browser;
    this.os = os;
    this.site = site;
  }

  public int getAdvertisementId() {
    return advertisementId;
  }

  public String getDeliveryId() {
    return deliveryId;
  }

  public String getTime() {
    return time;
  }

  public String getBrowser() {
    return browser;
  }

  public String getOs() {
    return os;
  }

  public String getSite() {
    return site;
  }
}
