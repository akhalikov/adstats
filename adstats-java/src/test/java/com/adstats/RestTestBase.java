package com.adstats;

import com.adstats.service.model.Click;
import com.adstats.service.model.Delivery;
import com.adstats.service.model.Install;
import static com.adstats.util.DateTimeUtils.formatFull;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.time.ZonedDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class RestTestBase {
  private static final String BASE_URL = "http://localhost:%d/ads";
  private static final String TEST_SITE = "http://news.com";

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
    postAndExpect(url, data, responseType, HttpStatus.ACCEPTED);
  }

  protected void postAndExpect(String url, Object data, Class<?> responseType, HttpStatus expectedStatus) {
    ResponseEntity responseEntity = testRestTemplate.postForEntity(getHost() + url, data, responseType);
    then(responseEntity.getStatusCode()).isEqualTo(expectedStatus);
  }

  protected <T> T getOk(String url, Class<T> responseType, Object...queryParams) {
    return getAndExpect(url, responseType, HttpStatus.OK, queryParams);
  }

  private <T> T getAndExpect(String url, Class<T> responseType, HttpStatus expectedStatus, Object... queryParams) {
    ResponseEntity<T> responseEntity = testRestTemplate.getForEntity(getHost() + url, responseType, queryParams);
    assertEquals(expectedStatus, responseEntity.getStatusCode());
    return responseEntity.getBody();
  }

  private String getHost() {
    return String.format(BASE_URL, port);
  }

  protected static Delivery getTestDelivery(ZonedDateTime time) {
    return getTestDelivery(time, "Chrome", "iOS");
  }

  private static Delivery getTestDelivery(ZonedDateTime time, String browser, String os) {
    Delivery delivery = new Delivery();
    delivery.deliveryId = randomUUID().toString();
    delivery.advertisementId = 42;
    delivery.browser = browser;
    delivery.os = os;
    delivery.site = TEST_SITE;
    delivery.time = formatFull(time);
    return delivery;
  }

  protected static Click getTestClick(String deliveryId, ZonedDateTime time) {
    Click click = new Click();
    click.clickId = randomUUID().toString();
    click.deliveryId = deliveryId;
    click.time = formatFull(time);
    return click;
  }

  protected static Install getTestInstall(String clickId, ZonedDateTime time) {
    Install install = new Install();
    install.installId = randomUUID().toString();
    install.clickId = clickId;
    install.time = formatFull(time);
    return install;
  }
}
