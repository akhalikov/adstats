package com.akhalikov.adstats.ads.model;

import java.time.ZonedDateTime;

public final class Delivery {
  private int advertisementId;
  private String deliveryId;
  private ZonedDateTime time;
  private String browser;
  private String os;
  private String site;

  public Delivery() {
  }

  public Delivery(int advertisementId,
                  String deliveryId,
                  ZonedDateTime time,
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

  public ZonedDateTime getTime() {
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
