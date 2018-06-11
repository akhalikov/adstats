package com.akhalikov.adstats.ads.model;

public final class Delivery {
  private int advertisementId;
  private String deliveryId;
  private String time;
  private String browser;
  private String os;
  private String site;

  public Delivery() {
  }

  public Delivery(int advertisementId,
                  String deliveryId,
                  String time,
                  String browser,
                  String os,
                  String site) {
    this.advertisementId = advertisementId;
    this.deliveryId = deliveryId;
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
