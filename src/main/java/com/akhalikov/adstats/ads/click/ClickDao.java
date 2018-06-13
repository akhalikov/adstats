package com.akhalikov.adstats.ads.click;

import com.akhalikov.adstats.core.dao.AbstractDao;
import static com.akhalikov.adstats.util.DateTimeUtils.parseInstant;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import static com.datastax.driver.core.querybuilder.QueryBuilder.bindMarker;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;

public class ClickDao extends AbstractDao {

  private final PreparedStatement savePreparedStatement;
  private final PreparedStatement fetchByClickIdPreparedStatement;

  public ClickDao(Session cassandraSession) {
    super(cassandraSession);

    savePreparedStatement = cassandraSession.prepare(QueryBuilder
        .insertInto("click")
        .value("click_id", bindMarker())
        .value("delivery_id", bindMarker())
        .value("time", bindMarker()));

    fetchByClickIdPreparedStatement = cassandraSession.prepare(QueryBuilder.
        select().all()
        .from("click")
        .where(eq("click_id", QueryBuilder.bindMarker())));
  }

  public void save(Click click) {
    getCassandraSession().execute(savePreparedStatement.bind(
        click.getClickId(),
        click.getDeliveryId(),
        parseInstant(click.getTime())
    ));
  }

  public int fetchCount(String clickId) {
    return getCassandraSession()
        .execute(fetchByClickIdPreparedStatement.bind(clickId))
        .getAvailableWithoutFetching();
  }
}
