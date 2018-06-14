package com.akhalikov.adstats.ads;

import com.akhalikov.adstats.RestTestBase;
import com.akhalikov.adstats.ads.delivery.Delivery;
import com.akhalikov.adstats.ads.stats.StatsForInterval;
import static com.akhalikov.adstats.util.Constants.TIME_FORMATTER;
import org.joda.time.DateTime;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class StatsControllerTest extends RestTestBase  {

  @Test
  public void getStatisticsForInterval() {
    DateTime end = DateTime.now();
    DateTime start = end.minusSeconds(100);

    Delivery delivery = createTestDelivery(start.toDate());
    createTestClick(delivery.getDeliveryId(), start.plusSeconds(20).toDate());

    Map<String, String> queryParams = new HashMap<>();
    queryParams.put("start", TIME_FORMATTER.print(start));
    queryParams.put("end", TIME_FORMATTER.print(end));

    StatsForInterval result = testRestTemplate.getForObject(getHost()
        + "/ads/statistics?start={start}&end={end}", StatsForInterval.class, queryParams);

    //assertEquals(start result.getInterval().getStart()));
    //assertEquals(end.toDate(), result.getInterval().getEnd());

    assertEquals(1, result.getStats().getDeliveries());
    assertEquals(1, result.getStats().getClicks());
    assertEquals(0, result.getStats().getInstalls());
  }
}
