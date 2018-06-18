package com.adstats.stats;

import com.adstats.core.dao.AbstractDao;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import static com.datastax.driver.core.querybuilder.QueryBuilder.bindMarker;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.gte;
import static com.datastax.driver.core.querybuilder.QueryBuilder.incr;
import static com.datastax.driver.core.querybuilder.QueryBuilder.lte;

import java.time.Instant;

public class StatsDao extends AbstractDao {

  private final PreparedStatement increaseMetricQuery;
  private final PreparedStatement getMetricQuery;

  public StatsDao(Session cassandraSession) {
    super(cassandraSession);

    increaseMetricQuery = cassandraSession.prepare(QueryBuilder
        .update("stats")
        .with(incr("counter_value"))
        .where(eq("metric_name", bindMarker()))
        .and(eq("event_time", bindMarker()))
        .and(eq("browser", bindMarker()))
        .and(eq("os", bindMarker()))
    );

    getMetricQuery = cassandraSession.prepare(QueryBuilder
        .select("counter_value", "browser", "os")
        .from("stats")
        .where(eq("metric_name", bindMarker()))
        .and(gte("event_time", bindMarker()))
        .and(lte("event_time", bindMarker())));
  }

  void updateMetric(Metric metric, Instant time, String browser, String os) {
    BoundStatement statement = increaseMetricQuery.bind(metric.toString().toLowerCase(), time, browser, os);
    getCassandraSession().execute(statement);
  }

  Stats fetchStats(Interval interval) {
    long deliveries = fetchMetric(Metric.DELIVERY, interval);
    long clicks = fetchMetric(Metric.CLICK, interval);
    long installs = fetchMetric(Metric.INSTALL, interval);
    return new Stats(deliveries, clicks, installs);
  }

  private long fetchMetric(Metric metric, Interval interval) {
    return getCassandraSession().execute(getMetricQuery
        .bind(getMetricName(metric), interval.getStart(), interval.getEnd()))
        .all().stream()
        .mapToLong(row -> row.getLong("counter_value"))
        .sum();
  }

  private static String getMetricName(Metric metric) {
    return metric.toString().toLowerCase();
  }
}
