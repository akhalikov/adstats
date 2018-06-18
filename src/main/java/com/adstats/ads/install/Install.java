package com.adstats.ads.install;

public final class Install {

  private String installId;
  private String clickId;
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
