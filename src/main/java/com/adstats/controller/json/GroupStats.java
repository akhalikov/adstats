package com.adstats.controller.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class GroupStats {
  @JsonProperty
  private Interval interval;

  @JsonProperty
  private List<GroupStatsItem> data;

  public GroupStats() {
  }

  public GroupStats(Interval interval, List<GroupStatsItem> data) {
    this.interval = interval;
    this.data = data;
  }

  public Interval getInterval() {
    return interval;
  }

  public List<GroupStatsItem> getData() {
    return data;
  }
}
