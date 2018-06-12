package com.akhalikov.adstats;

import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
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
public class AdsTestBase {

  private static final String BASE_URL = "http://localhost";

  @LocalServerPort
  int port;

  @Inject
  private TestRestTemplate testRestTemplate;

  @Inject
  protected Session cassandraSession;

  @Before
  public void setUp() throws Exception {
    cassandraSession.execute(QueryBuilder.truncate("delivery"));
  }

  protected void postAndExpect(String url, Object data, Class<?> type, HttpStatus expectedStatus) {
    ResponseEntity responseEntity = testRestTemplate.postForEntity(BASE_URL + ":" + port + url, data, type);

    then(responseEntity.getStatusCode()).isEqualTo(expectedStatus);
  }
}
