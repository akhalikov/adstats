package com.akhalikov.adstats.ads;

import com.akhalikov.adstats.RestTestBase;
import com.akhalikov.adstats.ads.click.Click;
import com.akhalikov.adstats.ads.delivery.Delivery;
import com.akhalikov.adstats.ads.install.Install;
import com.datastax.driver.core.Row;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;
import org.joda.time.DateTime;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.springframework.http.HttpStatus;

public class SaveAdsControllerInstallTest extends RestTestBase {

  @Test
  public void shouldReturn200ForValidRequest() {
    Delivery delivery = createTestDelivery();
    Click click = createTestClick(delivery.getDeliveryId());
    Install install = getTestInstall(click.getClickId(), DateTime.now().toDate());

    postAndExpect("/ads/install", install, Install.class, HttpStatus.OK);

    Row result = cassandraSession.execute(select()
        .from("install")
        .where(eq("install_id", install.getInstallId())))
        .one();

    assertEquals(install.getInstallId(), result.getString("install_id"));
    assertEquals(install.getClickId(), result.getString("click_id"));
  }

  @Test
  public void shouldReturn404IfClickIsNotFound() {
    Click click = createTestClick("missing-click-id");

    postAndExpect("/ads/click", click, Click.class, HttpStatus.NOT_FOUND);
  }
}
