package com.akhalikov.adstats;

import com.akhalikov.adstats.ads.click.Click;
import com.akhalikov.adstats.ads.delivery.Delivery;
import com.akhalikov.adstats.ads.install.Install;
import static com.akhalikov.adstats.util.DateTimeUtils.formatFull;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import java.time.ZonedDateTime;
import static java.util.UUID.randomUUID;
import javax.inject.Inject;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public abstract class RestTestBase {
  private static final String BASE_URL = "http://localhost:%d/ads";
  private static final int TEST_ADVERTISEMENT_ID = 4242;
  private static final String TEST_BROWSER = "Chrome";
  private static final String TEST_OS = "iOS";

  @LocalServerPort
  private int port;

  @Inject
  private TestRestTemplate testRestTemplate;

  @Inject
  protected Session cassandraSession;

  @Before
  public void setUp() {
    cassandraSession.execute(QueryBuilder.truncate("delivery"));
    cassandraSession.execute(QueryBuilder.truncate("click"));
    cassandraSession.execute(QueryBuilder.truncate("install"));
    cassandraSession.execute(QueryBuilder.truncate("stats"));
  }

  protected void postOk(String url, Object data, Class<?> responseType) {
    postAndExpect(url, data, responseType, HttpStatus.OK);
  }

  protected void postAndExpect(String url, Object data, Class<?> responseType, HttpStatus expectedStatus) {
    ResponseEntity responseEntity = testRestTemplate.postForEntity(getHost() + url, data, responseType);
    then(responseEntity.getStatusCode()).isEqualTo(expectedStatus);
  }

  protected <T> T getOk(String url, Class<T> responseType, Object...queryParams) {
    return getAndExpect(url, responseType, HttpStatus.OK, queryParams);
  }

  protected <T> T getAndExpect(String url, Class<T> responseType, HttpStatus expectedStatus, Object ...queryParams) {
    ResponseEntity<T> responseEntity = testRestTemplate.getForEntity(getHost() + url, responseType, queryParams);
    assertEquals(expectedStatus, responseEntity.getStatusCode());
    return responseEntity.getBody();
  }

  private String getHost() {
    return String.format(BASE_URL, port);
  }

  protected static Delivery getTestDelivery(ZonedDateTime time) {
    return new Delivery(
        randomUUID().toString(),
        TEST_ADVERTISEMENT_ID,
        formatFull(time),
        TEST_BROWSER, TEST_OS, "http://super-dooper-news.com");
  }

  protected static Delivery getTestDelivery(ZonedDateTime time, String browser, String os) {
    return new Delivery(
        randomUUID().toString(),
        TEST_ADVERTISEMENT_ID,
        formatFull(time),
        browser, os, "http://super-dooper-news.com");
  }

  protected static Click getTestClick(String deliveryId, ZonedDateTime time) {
    return new Click(randomUUID().toString(), deliveryId, formatFull(time));
  }

  protected static Install getTestInstall(String clickId, ZonedDateTime time) {
    return new Install(randomUUID().toString(), clickId, formatFull(time));
  }
}
