package com.akhalikov.adstats;

import com.akhalikov.adstats.ads.click.Click;
import com.akhalikov.adstats.ads.delivery.Delivery;
import com.akhalikov.adstats.ads.install.Install;
import static com.akhalikov.adstats.util.DateTimeUtils.TIME_FORMATTER;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import java.time.ZonedDateTime;
import javax.inject.Inject;
import static org.assertj.core.api.BDDAssertions.then;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AdsTestBase {
  private static final String BASE_URL = "http://localhost";
  protected static final String TEST_DELIVERY_ID = "244cf0db-ba28-4c5f-8c9c-2bf11ee42988";
  private static final String TEST_CLICK_ID = "fff54b83-49ff-476f-8bfb-2ec22b252c32";
  private static final String TEST_INSTALL_ID = "144cf0db-ba28-4c5f-8c9c-2bf11ee42988";

  @LocalServerPort
  int port;

  @Inject
  private TestRestTemplate testRestTemplate;

  @Inject
  protected Session cassandraSession;

  @Before
  public void setUp() {
    cassandraSession.execute(QueryBuilder.truncate("delivery"));
    cassandraSession.execute(QueryBuilder.truncate("click"));
    cassandraSession.execute(QueryBuilder.truncate("install"));
  }

  protected void postAndExpect(String url, Object data, Class<?> type, HttpStatus expectedStatus) {
    ResponseEntity responseEntity = testRestTemplate.postForEntity(BASE_URL + ":" + port + url, data, type);

    then(responseEntity.getStatusCode()).isEqualTo(expectedStatus);
  }

  protected static Delivery createTestDelivery() {
    return createTestDelivery(getCurrentTime());
  }

  protected static Delivery createTestDelivery(String time) {
    return new Delivery(4483, TEST_DELIVERY_ID, time, "Chrome", "iOS", "http://super-dooper-news.com");
  }

  protected static Click createTestClick(String deliveryId) {
    return createTestClick(deliveryId, getCurrentTime());
  }

  protected static Click createTestClick(String deliveryId, String time) {
    return new Click(deliveryId, TEST_CLICK_ID, time);
  }

  protected static Install createTestInstall(String clickId) {
    return createTestInstall(clickId, getCurrentTime());
  }

  protected static Install createTestInstall(String clickId, String time) {
    return new Install(TEST_INSTALL_ID, clickId, time);
  }

  private static String getCurrentTime() {
    return TIME_FORMATTER.format(ZonedDateTime.now());
  }
}
