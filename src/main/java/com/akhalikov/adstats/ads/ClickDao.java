package com.akhalikov.adstats.ads;

import com.akhalikov.adstats.ads.model.Click;
import static com.akhalikov.adstats.util.DateTimeUtils.parseInstant;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import static com.datastax.driver.core.querybuilder.QueryBuilder.bindMarker;

public class ClickDao {

  private final Session cassandraSession;
  private final PreparedStatement savePreparedStatement;

  public ClickDao(Session cassandraSession) {
    this.cassandraSession = cassandraSession;

    savePreparedStatement = cassandraSession.prepare(QueryBuilder
        .insertInto("click")
        .value("click_id", bindMarker())
        .value("delivery_id", bindMarker())
        .value("time", bindMarker()));
  }

  void saveClick(Click click) {
    cassandraSession.execute(savePreparedStatement.bind(
        click.getClickId(),
        click.getDeliveryId(),
        parseInstant(click.getTime())
    ));
  }
}
