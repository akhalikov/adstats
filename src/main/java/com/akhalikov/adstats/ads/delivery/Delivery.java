package com.akhalikov.adstats.ads.delivery;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class Delivery {

  @JsonProperty
  private int advertisementId;

  @JsonProperty
  private String deliveryId;

  @JsonProperty
  private String time;

  @JsonProperty
  private String browser;

  @JsonProperty
  private String os;

  @JsonProperty
  private String site;

  public Delivery() {
  }

  public Delivery(int advertisementId, String deliveryId, String time, String browser, String os, String site) {
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
