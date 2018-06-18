package com.adstats.stats;

import com.adstats.RestTestBase;
import com.adstats.ads.click.Click;
import com.adstats.ads.delivery.Delivery;
import com.adstats.ads.install.Install;
import static com.adstats.util.DateTimeUtils.formatShortWithZone;
import static com.adstats.util.DateTimeUtils.parseShort;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import static com.datastax.driver.core.querybuilder.QueryBuilder.bindMarker;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.gte;
import static com.datastax.driver.core.querybuilder.QueryBuilder.lte;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingLong;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class StatsControllerTest extends RestTestBase {

  @Test
  public void shouldReturn200ForValidRequests() {
    String start = "2018-01-07T14:30:00+0000";
    String end = "2018-01-07T18:20:00+0000";

    getAndExpect("/statistics?start={start}&end={end}",
        BasicStats.class, HttpStatus.OK, start, end);

    getAndExpect("/statistics?start={start}&end={end}&group_by=browser",
        BasicStats.class, HttpStatus.OK, start, end);

    getAndExpect("/statistics?start={start}&end={end}&group_by=browser&group_by=os",
        BasicStats.class, HttpStatus.OK, start, end);
  }

  @Test
  public void shouldReturnBasicStatistics() {
    ZonedDateTime startTime = ZonedDateTime.now().minusSeconds(200);
    ZonedDateTime endTime = startTime.plusSeconds(100);

    Delivery delivery1 = getTestDelivery(startTime.plusSeconds(10), "Chrome", "iOS");
    postOk("/delivery", delivery1, Delivery.class);

    Delivery delivery2 = getTestDelivery(startTime.plusSeconds(20), "Safari", "iOS");
    postOk("/delivery", delivery2, Delivery.class);

    Click click = getTestClick(delivery1.getDeliveryId(), startTime.plusSeconds(12));
    postOk("/click", click, Click.class);
    postOk("/click", getTestClick(delivery2.getDeliveryId(), startTime.plusSeconds(150)), Click.class);

    postOk("/install", getTestInstall(click.getClickId(), startTime.plusSeconds(12)), Install.class);
    postOk("/install", getTestInstall(click.getClickId(), startTime.minusHours(1)), Install.class);

    BasicStats basicStats = getOk("/statistics?start={start}&end={end}",
        BasicStats.class,
        formatShortWithZone(startTime),
        formatShortWithZone(endTime));

    Interval interval = basicStats.getInterval();

    assertEquals(Date.from(startTime.truncatedTo(ChronoUnit.SECONDS).toInstant()), interval.getStart());
    assertEquals(Date.from(endTime.truncatedTo(ChronoUnit.SECONDS).toInstant()), interval.getEnd());

    Stats stats = basicStats.getStats();

    assertEquals(2, stats.getDeliveries());
    assertEquals(1, stats.getClicks());
    assertEquals(1, stats.getInstalls());
  }

  @Test
  public void shouldReturnStatisticsByGroups() {
    ZonedDateTime startTime = ZonedDateTime.now().minusSeconds(200);
    ZonedDateTime endTime = startTime.plusSeconds(100);

    Delivery delivery1 = getTestDelivery(startTime.plusSeconds(10), "Chrome", "iOS");
    postOk("/delivery", delivery1, Delivery.class);

    Delivery delivery2 = getTestDelivery(startTime.plusSeconds(20), "Safari", "iOS");
    postOk("/delivery", delivery2, Delivery.class);
    postOk("/delivery", getTestDelivery(startTime.plusSeconds(15), "Chrome", "Android"), Delivery.class);
    postOk("/delivery", getTestDelivery(startTime.plusSeconds(16), "Chrome", "Android"), Delivery.class);
  }

  private void fetchMetricByGroups(Metric metric, String start, String end) {
    PreparedStatement getMetricQuery = cassandraSession.prepare(QueryBuilder
        .select("counter_value", "browser", "os")
        .from("stats")
        .where(eq("metric_name", bindMarker()))
        .and(gte("event_time", bindMarker()))
        .and(lte("event_time", bindMarker())));

    Date startTime = Date.from(parseShort(start, true));
    Date endTime = Date.from(parseShort(end, true));

    List<Row> rows = cassandraSession
        .execute(getMetricQuery.bind(metric.toString().toLowerCase(), startTime, endTime))
        .all();

    Map<String, Map<String, Long>> results = rows.stream()
        .map(row -> new GroupMetric(
            row.getLong("counter_value"),
            row.getString("browser"),
            row.getString("os")
        ))
        .collect(
            groupingBy(GroupMetric::getBrowser,
            groupingBy(GroupMetric::getOs, summingLong(GroupMetric::getValue)))
        );

    for (String key: results.keySet()) {
      System.out.println(key + "=" + results.get(key));
    }
  }

  static class GroupMetric {
    long value;
    String browser;
    String os;

    public GroupMetric(long value, String browser, String os) {
      this.value = value;
      this.browser = browser;
      this.os = os;
    }

    public long getValue() {
      return value;
    }

    public String getBrowser() {
      return browser;
    }

    public String getOs() {
      return os;
    }

    @Override
    public String toString() {
      return "GroupMetric{" +
          "value=" + value +
          ", browser='" + browser + '\'' +
          ", os='" + os + '\'' +
          '}';
    }
  }
}
