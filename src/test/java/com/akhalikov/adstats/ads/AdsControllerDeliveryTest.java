package com.akhalikov.adstats.ads;

import com.akhalikov.adstats.AdsTestBase;
import com.akhalikov.adstats.ads.model.Delivery;
import com.datastax.driver.core.Row;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.springframework.http.HttpStatus;

public class AdsControllerDeliveryTest extends AdsTestBase {

  @Test
  public void shouldReturn200ForValidRequest() {
    Delivery delivery = createTestDelivery();

    postAndExpect("/ads/delivery", delivery, Delivery.class, HttpStatus.OK);

    List<Row> results = cassandraSession.execute(select()
        .from("delivery")
        .where(eq("delivery_id", delivery.getDeliveryId()))
        .and(eq("advertisement_id", delivery.getAdvertisementId())))
        .all();

    assertEquals(1, results.size());
  }

  @Test
  public void shouldReturn500IfCouldNotParseTime() {
    Delivery delivery = createTestDelivery("some wrong formatted time");

    postAndExpect("/ads/delivery", delivery, Delivery.class, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
