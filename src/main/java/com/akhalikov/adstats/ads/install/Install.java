package com.akhalikov.adstats.ads.install;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class Install {

  @JsonProperty
  private String installId;

  @JsonProperty
  private String clickId;

  @JsonProperty
  private String time;

  public Install() {
  }

  public Install(String installId, String clickId, String time) {
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

  public String getTime() {
    return time;
  }
}
