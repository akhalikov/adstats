package com.akhalikov.adstats.ads.stats;

public final class StatsForInterval {
  private final Interval interval;
  private final Stats stats;

  public StatsForInterval(Interval interval, Stats stats) {
    this.interval = interval;
    this.stats = stats;
  }

  public Interval getInterval() {
    return interval;
  }

  public Stats getStats() {
    return stats;
  }
}
