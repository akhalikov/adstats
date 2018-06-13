package com.akhalikov.adstats.ads.stats;

import java.time.ZonedDateTime;

public class Interval {
  private final ZonedDateTime start;
  private final ZonedDateTime end;

  public Interval(ZonedDateTime start, ZonedDateTime end) {
    this.start = start;
    this.end = end;
  }

  public ZonedDateTime getStart() {
    return start;
  }

  public ZonedDateTime getEnd() {
    return end;
  }
}
