package com.akhalikov.adstats.ads.click;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class Click {

  @JsonProperty
  private String clickId;

  @JsonProperty
  private String deliveryId;

  @JsonProperty
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
