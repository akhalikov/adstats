package com.akhalikov.adstats.stats;

import com.akhalikov.adstats.RestTestBase;
import com.akhalikov.adstats.ads.click.Click;
import com.akhalikov.adstats.ads.delivery.Delivery;
import com.akhalikov.adstats.ads.install.Install;
import static com.akhalikov.adstats.util.DateTimeUtils.formatShortWithZone;
import java.time.ZonedDateTime;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.springframework.http.HttpStatus;

public class StatsControllerTest extends RestTestBase  {

  @Test
  public void shouldReturn200ForValidRequest() {
    String start = "2018-01-07T14:30:00+0000";
    String end = "2018-01-07T18:20:00+0000";

    getAndExpect("/statistics?start={start}&end={end}",
        StatsForInterval.class, HttpStatus.OK, start, end);
  }

  @Test
  public void shouldReturnCorrectStatistics() {
    ZonedDateTime start = ZonedDateTime.now().minusSeconds(200);
    ZonedDateTime end = start.plusSeconds(100);

    Delivery delivery1 = getTestDelivery(start.plusSeconds(10));
    postOk("/delivery", delivery1, Delivery.class);

    Delivery delivery2 = getTestDelivery(start.plusSeconds(20));
    postOk("/delivery", delivery2, Delivery.class);

    Click click = getTestClick(delivery1.getDeliveryId(), start.plusSeconds(12));
    postOk("/click", click, Click.class);
    postOk("/click", getTestClick(delivery2.getDeliveryId(), start.plusSeconds(150)), Click.class);

    postOk("/install", getTestInstall(click.getClickId(), start.plusSeconds(12)), Install.class);
    postOk("/install", getTestInstall(click.getClickId(), start.minusHours(1)), Install.class);

    StatsForInterval statsForInterval = getOk("/statistics?start={start}&end={end}",
        StatsForInterval.class,
        formatShortWithZone(start), formatShortWithZone(end));

    Stats stats = statsForInterval.getStats();

    assertEquals(2, stats.getDeliveries());
    assertEquals(1, stats.getClicks());
    assertEquals(1, stats.getInstalls());
  }
}
