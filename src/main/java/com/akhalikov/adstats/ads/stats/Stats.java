package com.akhalikov.adstats.ads.stats;

public final class Stats {
  private final int deliveries;
  private final int clicks;
  private final int installs;

  public Stats(int deliveries, int clicks, int installs) {
    this.deliveries = deliveries;
    this.clicks = clicks;
    this.installs = installs;
  }

  public int getDeliveries() {
    return deliveries;
  }

  public int getClicks() {
    return clicks;
  }

  public int getInstalls() {
    return installs;
  }
}
