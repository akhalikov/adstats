package com.akhalikov.adstats.ads;

import com.akhalikov.adstats.AdsTestBase;
import com.akhalikov.adstats.ads.model.Click;
import com.akhalikov.adstats.ads.model.Delivery;
import com.datastax.driver.core.Row;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;
import java.util.List;
import javax.inject.Inject;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.springframework.http.HttpStatus;

public class AdsControllerClickTest extends AdsTestBase {
  @Inject
  private DeliveryDao deliveryDao;

  @Test
  public void shouldReturn200ForValidRequest() {
    Delivery delivery = new Delivery(4483,
        "244cf0db-ba28-4c5f-8c9c-2bf11ee42988",
        "2018-01-07T18:32:23.602300+0000",
        "Chrome",
        "iOS",
        "http://super-dooper-news.com");

    deliveryDao.save(delivery);

    Click click = new Click(delivery.getDeliveryId(),
        "fff54b83-49ff-476f-8bfb-2ec22b252c32",
        "2018-01-07T18:32:34.201100+0000");

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
    Click click = new Click("244cf0db-ba28-4c5f-8c9c-2bf11ee42988",
        "fff54b83-49ff-476f-8bfb-2ec22b252c32",
        "2018-01-07T18:32:34.201100+0000");

    postAndExpect("/ads/click", click, Click.class, HttpStatus.NOT_FOUND);
  }
}
