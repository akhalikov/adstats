package com.akhalikov.adstats.util;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import static java.time.format.DateTimeFormatter.ofPattern;

public final class DateTimeUtils {
  public static final DateTimeFormatter TIME_FORMATTER = ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ");

  public static Instant parseInstant(String time) {
    return Instant.from(TIME_FORMATTER.parse(time));
  }

  private DateTimeUtils() {
  }
}
