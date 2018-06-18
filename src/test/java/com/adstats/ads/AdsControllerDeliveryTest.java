package com.adstats.ads;

import com.adstats.RestTestBase;
import com.adstats.stats.Metric;
import com.adstats.ads.delivery.Delivery;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.springframework.http.HttpStatus;

public class AdsControllerDeliveryTest extends RestTestBase {

  @Test
  public void shouldReturn200ForValidRequest() {
    Delivery delivery = getTestDelivery(ZonedDateTime.now().minusSeconds(100));
    postAndExpect("/delivery", delivery, Delivery.class, HttpStatus.OK);

    Row result = cassandraSession.execute(select()
        .from("delivery")
        .where(eq("delivery_id", delivery.getDeliveryId())))
        .one();

    assertEquals(delivery.getDeliveryId(), result.getString("delivery_id"));
    assertEquals(delivery.getAdvertisementId(), result.getInt("advertisement_id"));
  }

  @Test
  public void shouldAddMetric() {
    ZonedDateTime time = ZonedDateTime.now();

    Delivery delivery = getTestDelivery(time);
    postAndExpect("/delivery", delivery, Delivery.class, HttpStatus.OK);

    Row metric = cassandraSession.execute(select()
        .from("stats")
        .where(QueryBuilder.eq("metric_name", Metric.DELIVERY.toString().toLowerCase())))
        .all()
        .get(0);

    assertEquals(1, metric.getLong("counter_value"));

    Date expectedTime = Date.from(time.toInstant().truncatedTo(ChronoUnit.SECONDS));
    assertEquals(expectedTime, metric.getTimestamp("event_time"));
  }
}
