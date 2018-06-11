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

public class AdsControllerTest extends AdsTestBase {

  @Test
  public void deliveryShouldReturn200ForValidRequest() {
    Delivery delivery = new Delivery(4483,
        "244cf0db-ba28-4c5f-8c9c-2bf11ee42988",
        "2018-01-07T18:32:23.602300+0000",
        "Chrome",
        "iOS",
        "http://super-dooper-news.com");

    postAndExpect("/ads/delivery", delivery, Delivery.class, HttpStatus.OK);

    List<Row> results = cassandraSession.execute(select()
        .from("delivery")
        .where(eq("advertisement_id", 4483))
        .and(eq("delivery_id", "244cf0db-ba28-4c5f-8c9c-2bf11ee42988")))
        .all();

    assertEquals(1, results.size());
  }

  @Test
  public void deliveryShouldReturn500IfCouldNotParseTime() {
    Delivery delivery = new Delivery(4483,
        "244cf0db-ba28-4c5f-8c9c-2bf11ee42988",
        "2018 08 09 some wrong time",
        "Chrome",
        "iOS",
        "http://test.com");

    postAndExpect("/ads/delivery", delivery, Delivery.class, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
