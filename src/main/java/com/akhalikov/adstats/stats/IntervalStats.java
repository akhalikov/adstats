package com.akhalikov.adstats.stats;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

public final class IntervalStats {
  @JsonProperty
  private Interval interval;

  @JsonProperty
  private Stats stats;

  public IntervalStats() {
  }

  IntervalStats(Date start, Date end, Stats stats) {
    this.interval = new Interval(start, end);
    this.stats = stats;
  }

  public Interval getInterval() {
    return interval;
  }

  public Stats getStats() {
    return stats;
  }
}
