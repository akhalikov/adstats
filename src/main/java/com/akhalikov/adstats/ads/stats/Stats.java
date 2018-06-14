package com.akhalikov.adstats.ads.stats;

public final class Stats {
  private final long deliveries;
  private final long clicks;
  private final long installs;

  public Stats() {
    this(0, 0, 0);
  }

  Stats(long deliveries, long clicks, long installs) {
    this.deliveries = deliveries;
    this.clicks = clicks;
    this.installs = installs;
  }

  public long getDeliveries() {
    return deliveries;
  }

  public long getClicks() {
    return clicks;
  }

  public long getInstalls() {
    return installs;
  }
}
