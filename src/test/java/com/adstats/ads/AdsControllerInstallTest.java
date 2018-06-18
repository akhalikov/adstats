package com.adstats.ads;

import com.adstats.RestTestBase;
import com.adstats.ads.click.Click;
import com.adstats.ads.delivery.Delivery;
import com.adstats.ads.install.Install;
import com.datastax.driver.core.Row;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;
import static java.time.ZonedDateTime.now;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.springframework.http.HttpStatus;

public class AdsControllerInstallTest extends RestTestBase {

  @Test
  public void shouldReturn200ForValidRequest() {
    Delivery delivery = getTestDelivery(now().minusSeconds(100));
    postOk("/delivery", delivery, Delivery.class);

    Click click = getTestClick(delivery.getDeliveryId(), now());
    postOk("/click", click, Click.class);

    Install install = getTestInstall(click.getClickId(), now());
    postOk("/install", install, Install.class);

    Row result = cassandraSession.execute(select()
        .from("install")
        .where(eq("install_id", install.getInstallId())))
        .one();

    assertEquals(install.getInstallId(), result.getString("install_id"));
    assertEquals(install.getClickId(), result.getString("click_id"));
  }

  @Test
  public void shouldReturn404IfClickIsNotFound() {
    Install install = getTestInstall("missing-click-id", now());

    postAndExpect("/install", install, Click.class, HttpStatus.NOT_FOUND);
  }
}
