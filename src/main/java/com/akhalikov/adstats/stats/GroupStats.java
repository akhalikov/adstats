package com.akhalikov.adstats.stats;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class GroupStats {
  @JsonProperty
  private Interval interval;

  @JsonProperty
  private List<GroupStats> data;

  public GroupStats() {
  }

  public GroupStats(Interval interval, List<GroupStats> data) {
    this.interval = interval;
    this.data = data;
  }
}
