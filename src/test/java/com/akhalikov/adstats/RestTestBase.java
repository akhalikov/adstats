package com.akhalikov.adstats;

import com.akhalikov.adstats.ads.click.Click;
import com.akhalikov.adstats.ads.click.ClickDao;
import com.akhalikov.adstats.ads.delivery.Delivery;
import com.akhalikov.adstats.ads.delivery.DeliveryDao;
import com.akhalikov.adstats.ads.install.Install;
import com.akhalikov.adstats.ads.install.InstallDao;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import static org.assertj.core.api.BDDAssertions.then;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.util.Date;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class RestTestBase {
  private static final String BASE_URL = "http://localhost";
  private static final int TEST_ADVERTISEMENT_ID = 4242;

  @LocalServerPort
  private int port;

  @Inject
  protected TestRestTemplate testRestTemplate;

  @Inject
  protected Session cassandraSession;

  @Inject
  private DeliveryDao deliveryDao;

  @Inject
  private ClickDao clickDao;

  @Inject
  private InstallDao installDao;

  @Before
  public void setUp() {
    cassandraSession.execute(QueryBuilder.truncate("delivery"));
    cassandraSession.execute(QueryBuilder.truncate("click"));
    cassandraSession.execute(QueryBuilder.truncate("install"));
  }

  protected void postAndExpect(String url, Object data, Class<?> type, HttpStatus expectedStatus) {
    ResponseEntity responseEntity = testRestTemplate.postForEntity(getHost() + url, data, type);

    then(responseEntity.getStatusCode()).isEqualTo(expectedStatus);
  }

  protected Delivery createTestDelivery() {
    return createTestDelivery(DateTime.now().toDate());
  }

  protected Delivery createTestDelivery(Date date) {
    Delivery delivery = new Delivery(TEST_ADVERTISEMENT_ID, UUID.randomUUID().toString(), date,
        "Chrome", "iOS", "http://super-dooper-news.com");
    deliveryDao.save(delivery);
    return delivery;
  }

  protected Click createTestClick(String deliveryId) {
    return createTestClick(deliveryId, DateTime.now().toDate());
  }

  protected Click createTestClick(String deliveryId, Date date) {
    Click click = getTestClick(deliveryId, date);
    clickDao.save(click);
    return click;
  }

  protected static Click getTestClick(String deliveryId, Date date) {
    return new Click(UUID.randomUUID().toString(), deliveryId, date);
  }

  protected Install createTestInstall(String clickId, Date date) {
    Install install = getTestInstall(clickId, date);
    installDao.save(install);
    return install;
  }

  protected static Install getTestInstall(String clickId, Date date) {
    return new Install(UUID.randomUUID().toString(), clickId, date);
  }

  protected String getHost() {
    return BASE_URL + ":" + port;
  }
}
