package com.adstats.stats;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public final class GroupStatsItem {

  @JsonProperty
  private Map<String, String> fields;

  @JsonProperty
  private Stats stats;

  public GroupStatsItem() {
  }

  public GroupStatsItem(Map<String, String> fields, Stats stats) {
    this.fields = fields;
    this.stats = stats;
  }

  public Map<String, String> getFields() {
    return fields;
  }

  public Stats getStats() {
    return stats;
  }
}
