package com.akhalikov.adstats.util;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public final class Constants {
  public static final String TIME_FORMAT_STRICT = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ";
  public static final String TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

  public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormat.forPattern(TIME_FORMAT);

  private Constants() {
  }
}
