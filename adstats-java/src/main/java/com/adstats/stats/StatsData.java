package com.adstats.stats;

import com.adstats.service.model.Delivery;

public class StatsData {
  private final String browser;
  private final String os;

  public StatsData(String browser, String os) {
    this.browser = browser;
    this.os = os;
  }

  public static StatsData of(Delivery delivery) {
    return new StatsData(delivery.browser, delivery.os);
  }

  public String getBrowser() {
    return browser;
  }

  public String getOs() {
    return os;
  }
}
