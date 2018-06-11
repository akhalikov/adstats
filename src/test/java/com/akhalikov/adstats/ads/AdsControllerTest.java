package com.akhalikov.adstats.ads;

import com.akhalikov.adstats.AdsTestBase;
import com.akhalikov.adstats.ads.model.Delivery;
import java.time.ZonedDateTime;
import org.junit.Test;
import org.springframework.http.HttpStatus;

public class AdsControllerTest extends AdsTestBase {

  @Test
  public void deliveryShouldReturn200ForValidRequest() {
    Delivery delivery = new Delivery(4483,
        "244cf0db-ba28-4c5f-8c9c-2bf11ee42988",
        ZonedDateTime.now().minusSeconds(10),
        "Chrome",
        "iOS",
        "http://super-dooper-news.com");

    postAndExpect("/ads/delivery", delivery, Delivery.class, HttpStatus.OK);
  }
}
