package com.akhalikov.adstats.ads;

import com.akhalikov.adstats.RestTestBase;
import com.akhalikov.adstats.ads.delivery.Delivery;
import com.datastax.driver.core.Row;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.springframework.http.HttpStatus;

public class SaveAdsControllerDeliveryTest extends RestTestBase {

  @Test
  public void shouldReturn200ForValidRequest() {
    Delivery delivery = createTestDelivery();

    postAndExpect("/ads/delivery", delivery, Delivery.class, HttpStatus.OK);

    Row result = cassandraSession.execute(select()
        .from("delivery")
        .where(eq("delivery_id", delivery.getDeliveryId())))
        .one();

    assertEquals(delivery.getDeliveryId(), result.getString("delivery_id"));
    assertEquals(delivery.getAdvertisementId(), result.getInt("advertisement_id"));
  }
}
