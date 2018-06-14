package com.akhalikov.adstats.ads.delivery;

import com.akhalikov.adstats.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public final class Delivery {

  @JsonProperty
  private int advertisementId;

  @JsonProperty
  private String deliveryId;

  @JsonProperty
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.TIME_FORMAT_STRICT)
  private Date time;

  @JsonProperty
  private String browser;

  @JsonProperty
  private String os;

  @JsonProperty
  private String site;

  public Delivery() {
  }

  public Delivery(int advertisementId, String deliveryId, Date time, String browser, String os, String site) {
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

  public Date getTime() {
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
