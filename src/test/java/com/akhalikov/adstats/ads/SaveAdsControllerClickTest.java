package com.akhalikov.adstats.ads;

import com.akhalikov.adstats.RestTestBase;
import com.akhalikov.adstats.ads.click.Click;
import com.akhalikov.adstats.ads.delivery.Delivery;
import com.datastax.driver.core.Row;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;

import org.joda.time.DateTime;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.springframework.http.HttpStatus;

public class SaveAdsControllerClickTest extends RestTestBase {

  @Test
  public void shouldReturn200ForValidRequest() {
    Delivery delivery = createTestDelivery();
    Click click = getTestClick(delivery.getDeliveryId(), DateTime.now().toDate());

    postAndExpect("/ads/click", click, Click.class, HttpStatus.OK);

    Row result = cassandraSession.execute(select()
        .from("click")
        .where(eq("click_id", click.getClickId())))
        .one();

    assertEquals(click.getClickId(), result.getString("click_id"));
    assertEquals(delivery.getDeliveryId(), result.getString("delivery_id"));
  }

  @Test
  public void shouldReturn404IfDeliveryIsNotFound() {
    Click click = createTestClick("missing-delivery-id");

    postAndExpect("/ads/click", click, Click.class, HttpStatus.NOT_FOUND);
  }
}
