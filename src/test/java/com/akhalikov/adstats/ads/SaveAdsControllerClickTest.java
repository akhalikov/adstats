package com.akhalikov.adstats.ads;

import com.akhalikov.adstats.AdsTestBase;
import com.akhalikov.adstats.ads.delivery.DeliveryDao;
import com.akhalikov.adstats.ads.click.Click;
import com.akhalikov.adstats.ads.delivery.Delivery;
import com.datastax.driver.core.Row;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;
import java.util.List;
import javax.inject.Inject;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.springframework.http.HttpStatus;

public class SaveAdsControllerClickTest extends AdsTestBase {
  @Inject
  private DeliveryDao deliveryDao;

  @Test
  public void shouldReturn200ForValidRequest() {
    Delivery delivery = createTestDelivery();

    deliveryDao.save(delivery);

    Click click = createTestClick(delivery.getDeliveryId());

    postAndExpect("/ads/click", click, Click.class, HttpStatus.OK);

    List<Row> results = cassandraSession.execute(select()
        .from("click")
        .where(eq("click_id", click.getClickId()))
        .and(eq("delivery_id", delivery.getDeliveryId())))
        .all();

    assertEquals(1, results.size());
  }

  @Test
  public void shouldReturn404IfDeliveryIsNotFound() {
    Click click = createTestClick("missing-delivery-id");

    postAndExpect("/ads/click", click, Click.class, HttpStatus.NOT_FOUND);
  }
}
