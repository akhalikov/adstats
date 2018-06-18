package com.adstats.stats;

import com.adstats.RestTestBase;
import com.adstats.ads.click.Click;
import com.adstats.ads.delivery.Delivery;
import com.adstats.ads.install.Install;
import com.adstats.stats.json.BasicStats;
import com.adstats.stats.json.GroupStats;
import com.adstats.stats.json.GroupStatsItem;
import com.adstats.stats.json.Interval;
import com.adstats.stats.json.Stats;
import static com.adstats.util.DateTimeUtils.formatShortWithZone;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
        BasicStats.class, formatShortWithZone(startTime), formatShortWithZone(endTime));

    assertInterval(startTime, endTime, basicStats.getInterval());

    Stats stats = basicStats.getStats();
    assertStats(2, 1, 1, stats);
  }

  @Test
  public void shouldReturnStatisticsByBrowser() {
    ZonedDateTime startTime = ZonedDateTime.now().minusSeconds(200);
    ZonedDateTime endTime = startTime.plusSeconds(100);

    Delivery delivery = getTestDelivery(startTime.plusSeconds(10), "Chrome", "iOS");
    postOk("/delivery", delivery, Delivery.class);

    Click click = getTestClick(delivery.getDeliveryId(), startTime.plusSeconds(12));
    postOk("/click", click, Click.class);

    postOk("/install", getTestInstall(click.getClickId(), startTime.plusSeconds(16)), Install.class);

    GroupStats groupStats = getOk("/statistics?start={start}&end={end}&group_by=browser&group_by=os",
        GroupStats.class, formatShortWithZone(startTime), formatShortWithZone(endTime));

    assertInterval(startTime, endTime, groupStats.getInterval());

    List<GroupStatsItem> data = groupStats.getData();
    assertNotNull(data);

    GroupStatsItem statsItem = data.get(0);
    Map<String, String> fields = statsItem.getFields();
    assertEquals("Chrome", fields.get("browser"));
    assertEquals("iOS", fields.get("os"));

    assertStats(1, 1, 1, statsItem.getStats());
  }

  private static void assertInterval(ZonedDateTime expectedStart, ZonedDateTime expectedEnd, Interval interval) {
    assertEquals(Date.from(expectedStart.truncatedTo(ChronoUnit.SECONDS).toInstant()), interval.getStart());
    assertEquals(Date.from(expectedEnd.truncatedTo(ChronoUnit.SECONDS).toInstant()), interval.getEnd());
  }

  private static void assertStats(long expectedDeliveries, long expectedClicks, long expectedInstalls, Stats stats) {
    assertEquals(expectedDeliveries, stats.getDeliveries());
    assertEquals(expectedClicks, stats.getClicks());
    assertEquals(expectedInstalls, stats.getInstalls());
  }
}
