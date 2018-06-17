package com.akhalikov.adstats.ads.click;

import com.akhalikov.adstats.core.dao.AbstractDao;
import com.akhalikov.adstats.exception.ClickNotFoundException;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import static com.datastax.driver.core.querybuilder.QueryBuilder.bindMarker;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import java.util.Date;

public class ClickDao extends AbstractDao {

  private final PreparedStatement saveQuery;
  private final PreparedStatement getClickByIdQuery;

  public ClickDao(Session cassandraSession) {
    super(cassandraSession);

    saveQuery = cassandraSession.prepare(QueryBuilder
        .insertInto("click")
        .value("click_id", bindMarker())
        .value("delivery_id", bindMarker())
        .value("time", bindMarker()));

    getClickByIdQuery = cassandraSession.prepare(QueryBuilder.
        select("click_id", "delivery_id", "time")
        .from("click")
        .where(eq("click_id", QueryBuilder.bindMarker())));
  }

  void save(String clickId, String deliveryId, Date time) {
    getCassandraSession().execute(saveQuery.bind(clickId, deliveryId, time));
  }

  public Click getClickOrThrow(String clickId) {
    ResultSet resultSet = getCassandraSession().execute(getClickByIdQuery.bind(clickId));
    Row row = resultSet.one();
    if (row == null) {
      throw new ClickNotFoundException(clickId);
    }
    return new Click(
        row.getString("click_id"),
        row.getString("delivery_id"),
        row.getTimestamp("time").toString());
  }
}
