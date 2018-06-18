package com.adstats.util;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import static java.time.format.DateTimeFormatter.ofPattern;

public final class DateTimeUtils {
  public static final String TIME_FORMAT_SHORT = "yyyy-MM-dd'T'HH:mm:ss";

  private static final DateTimeFormatter TIME_FORMATTER_FULL = ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ");
  public static final DateTimeFormatter TIME_FORMATTER_SHORT = ofPattern(TIME_FORMAT_SHORT);
  public static final DateTimeFormatter TIME_FORMATTER_SHORT_WITH_ZONE = ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");

  public static Instant parseFull(String time) {
    return Instant.from(TIME_FORMATTER_FULL.parse(replaceSpace(time)));
  }

  public static Instant parseShort(String time, boolean timeZone) {
    return timeZone
        ? Instant.from(TIME_FORMATTER_SHORT_WITH_ZONE.parse(replaceSpace(time)))
        : Instant.from(TIME_FORMATTER_SHORT.parse(replaceSpace(time)));
  }

  public static String formatFull(ZonedDateTime time) {
    return TIME_FORMATTER_FULL.format(time);
  }

  public static String formatShortWithZone(ZonedDateTime time) {
    return TIME_FORMATTER_SHORT_WITH_ZONE.format(time);
  }

  private static String replaceSpace(String str) {
    return str.contains(" ") ? str.replace(" ", "+") : str;
  }

  private DateTimeUtils() {
  }
}
