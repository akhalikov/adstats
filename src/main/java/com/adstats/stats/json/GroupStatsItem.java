package com.adstats.stats.json;

import com.adstats.stats.GroupByField;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.LinkedHashMap;
import java.util.Map;

public final class GroupStatsItem {

  @JsonProperty
  private Map<String, String> fields = new LinkedHashMap<>();

  @JsonProperty
  private Stats stats;

  public GroupStatsItem() {
  }

  public void addField(GroupByField field, String value) {
    fields.put(field.getKey(), value);
  }

  public Map<String, String> getFields() {
    return fields;
  }

  public Stats getStats() {
    return stats;
  }

  public void setStats(Stats stats) {
    this.stats = stats;
  }

  @Override
  public String toString() {
    return "GroupStatsItem{" +
        "fields=" + fields +
        ", stats=" + stats +
        '}';
  }
}
