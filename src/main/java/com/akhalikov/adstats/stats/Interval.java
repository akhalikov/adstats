package com.akhalikov.adstats.stats;

import com.akhalikov.adstats.util.DateTimeUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public final class Interval {
  @JsonProperty
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeUtils.TIME_FORMAT_SHORT)
  private Date start;

  @JsonProperty
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeUtils.TIME_FORMAT_SHORT)
  private Date end;

  public Interval() {
  }

  Interval(Date start, Date end) {
    this.start = start;
    this.end = end;
  }

  public Date getStart() {
    return start;
  }

  public Date getEnd() {
    return end;
  }
}
