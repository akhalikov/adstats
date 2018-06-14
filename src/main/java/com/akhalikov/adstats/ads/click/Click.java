package com.akhalikov.adstats.ads.click;

import com.akhalikov.adstats.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public final class Click {

  @JsonProperty
  private String clickId;

  @JsonProperty
  private String deliveryId;

  @JsonProperty
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.TIME_FORMAT_STRICT)
  private Date time;

  public Click() {
  }

  public Click(String clickId, String deliveryId, Date time) {
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

  public Date getTime() {
    return time;
  }
}
