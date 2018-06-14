package com.akhalikov.adstats.ads.stats;

import com.akhalikov.adstats.core.dao.AbstractDao;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import static com.datastax.driver.core.querybuilder.QueryBuilder.bindMarker;
import static com.datastax.driver.core.querybuilder.QueryBuilder.gte;
import static com.datastax.driver.core.querybuilder.QueryBuilder.lte;

import java.util.Date;

public class StatsDao extends AbstractDao {

  private final PreparedStatement statsDeliveryPreparedStatement;
  private final PreparedStatement statsClicksPreparedStatement;
  private final PreparedStatement statsInstallsPreparedStatement;

  public StatsDao(Session cassandraSession) {
    super(cassandraSession);
    this.statsDeliveryPreparedStatement = cassandraSession.prepare(QueryBuilder
        .select().countAll()
        .from("delivery")
        .allowFiltering()
        .where(gte("time", bindMarker()))
        .and(lte("time", bindMarker())));

    this.statsClicksPreparedStatement = cassandraSession.prepare(QueryBuilder
        .select().countAll()
        .from("click")
        .allowFiltering()
        .where(gte("time", bindMarker()))
        .and(lte("time", bindMarker())));

    this.statsInstallsPreparedStatement = cassandraSession.prepare(QueryBuilder
        .select().countAll()
        .from("install")
        .allowFiltering()
        .where(gte("time", bindMarker()))
        .and(lte("time", bindMarker())));
  }

  public Stats fetchStats(Date start, Date end) {
    long deliveries = getCassandraSession().execute(statsDeliveryPreparedStatement
        .bind(start, end))
        .one().getLong(0);

    long clicks = getCassandraSession().execute(statsClicksPreparedStatement
        .bind(start, end))
        .one().getLong(0);

    long installs = getCassandraSession().execute(statsInstallsPreparedStatement
        .bind(start, end))
        .one().getLong(0);

    return new Stats(deliveries, clicks, installs);
  }
}
