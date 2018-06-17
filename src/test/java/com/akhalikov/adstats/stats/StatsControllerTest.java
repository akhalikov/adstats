package com.akhalikov.adstats.stats;

import com.akhalikov.adstats.RestTestBase;
import com.akhalikov.adstats.ads.click.Click;
import com.akhalikov.adstats.ads.delivery.Delivery;
import com.akhalikov.adstats.ads.install.Install;
import static com.akhalikov.adstats.util.DateTimeUtils.formatShortWithZone;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.springframework.http.HttpStatus;

public class StatsControllerTest extends RestTestBase  {

  @Test
  public void shouldReturn200ForValidRequest() {
    String start = "2018-01-07T14:30:00+0000";
    String end = "2018-01-07T18:20:00+0000";

    getAndExpect("/statistics?start={start}&end={end}",
        IntervalStats.class, HttpStatus.OK, start, end);
  }

  @Test
  public void shouldReturnCorrectStatistics() {
    ZonedDateTime startTime = ZonedDateTime.now().minusSeconds(200);
    ZonedDateTime endTime = startTime.plusSeconds(100);

    Delivery delivery1 = getTestDelivery(startTime.plusSeconds(10));
    postOk("/delivery", delivery1, Delivery.class);

    Delivery delivery2 = getTestDelivery(startTime.plusSeconds(20));
    postOk("/delivery", delivery2, Delivery.class);

    Click click = getTestClick(delivery1.getDeliveryId(), startTime.plusSeconds(12));
    postOk("/click", click, Click.class);
    postOk("/click", getTestClick(delivery2.getDeliveryId(), startTime.plusSeconds(150)), Click.class);

    postOk("/install", getTestInstall(click.getClickId(), startTime.plusSeconds(12)), Install.class);
    postOk("/install", getTestInstall(click.getClickId(), startTime.minusHours(1)), Install.class);

    IntervalStats intervalStats = getOk("/statistics?start={start}&end={end}",
        IntervalStats.class, formatShortWithZone(startTime),
        formatShortWithZone(endTime));

    Interval interval = intervalStats.getInterval();
    
    assertEquals(Date.from(startTime.truncatedTo(ChronoUnit.SECONDS).toInstant()), interval.getStart());
    assertEquals(Date.from(endTime.truncatedTo(ChronoUnit.SECONDS).toInstant()), interval.getEnd());

    Stats stats = intervalStats.getStats();

    assertEquals(2, stats.getDeliveries());
    assertEquals(1, stats.getClicks());
    assertEquals(1, stats.getInstalls());
  }
}
