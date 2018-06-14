package com.akhalikov.adstats.ads.install;

import com.akhalikov.adstats.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public final class Install {

  @JsonProperty
  private String installId;

  @JsonProperty
  private String clickId;

  @JsonProperty
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.TIME_FORMAT_STRICT)
  private Date time;

  public Install() {
  }

  public Install(String installId, String clickId, Date time) {
    this.installId = installId;
    this.clickId = clickId;
    this.time = time;
  }

  public String getInstallId() {
    return installId;
  }

  public String getClickId() {
    return clickId;
  }

  public Date getTime() {
    return time;
  }
}
