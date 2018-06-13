package com.akhalikov.adstats.ads;

import com.akhalikov.adstats.AdsTestBase;
import com.akhalikov.adstats.ads.model.Click;
import com.akhalikov.adstats.ads.model.Install;
import com.datastax.driver.core.Row;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;
import java.util.List;
import javax.inject.Inject;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.springframework.http.HttpStatus;

public class AdsControllerInstallTest extends AdsTestBase {
  @Inject
  private ClickDao clickDao;

  @Inject
  private InstallDao installDao;

  @Test
  public void shouldReturn200ForValidRequest() {
    Click click = createTestClick(TEST_DELIVERY_ID);

    clickDao.save(click);

    Install install = createTestInstall(click.getClickId());

    postAndExpect("/ads/install", install, Install.class, HttpStatus.OK);

    List<Row> results = cassandraSession.execute(select()
        .from("install")
        .where(eq("install_id", install.getInstallId()))
        .and(eq("click_id", install.getClickId())))
        .all();

    assertEquals(1, results.size());
  }

  @Test
  public void shouldReturn404IfClickIsNotFound() {
    Click click = createTestClick("missing-click-id");

    postAndExpect("/ads/click", click, Click.class, HttpStatus.NOT_FOUND);
  }
}
