package com.adstats.stats;

import static com.adstats.util.DateTimeUtils.TIME_FORMAT_SHORT;
import static com.adstats.util.DateTimeUtils.parseShort;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public final class Interval {
  @JsonProperty
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_FORMAT_SHORT)
  private Date start;

  @JsonProperty
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_FORMAT_SHORT)
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

  public static Interval from(String start, String end) {
    Date startTime = Date.from(parseShort(start, true));
    Date endTime = Date.from(parseShort(end, true));
    return new Interval(startTime, endTime);
  }
}
