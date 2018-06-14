package com.akhalikov.adstats.stats;

import com.akhalikov.adstats.core.dao.AbstractDao;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import static com.datastax.driver.core.querybuilder.QueryBuilder.bindMarker;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.gte;
import static com.datastax.driver.core.querybuilder.QueryBuilder.incr;
import static com.datastax.driver.core.querybuilder.QueryBuilder.lte;
import java.time.Instant;
import java.util.Date;

public class StatsDao extends AbstractDao {

  private final PreparedStatement increaseMetricQuery;
  private final PreparedStatement getMetricQuery;

  public StatsDao(Session session) {
    super(session);

    increaseMetricQuery = session.prepare(QueryBuilder
        .update("stats")
        .with(incr("counter_value"))
        .where(eq("metric_name", bindMarker()))
        .and(eq("event_time", bindMarker())));

    getMetricQuery = session.prepare(QueryBuilder
        .select("counter_value")
        .from("stats")
        .where(eq("metric_name", bindMarker()))
        .and(gte("event_time", bindMarker()))
        .and(lte("event_time", bindMarker())));
  }

  public void increaseMetric(Metric metric, Instant time) {
    getSession().execute(increaseMetricQuery.bind(metric.toString().toLowerCase(), time));
  }

  Stats fetchStats(Date start, Date end) {
    long deliveries = fetchMetric(Metric.DELIVERY, start, end);
    long clicks = fetchMetric(Metric.CLICK, start, end);
    long installs = fetchMetric(Metric.INSTALL, start, end);
    return new Stats(deliveries, clicks, installs);
  }

  private long fetchMetric(Metric metric, Date start, Date end) {
    String metricName = metric.toString().toLowerCase();
    return getSession().execute(getMetricQuery.bind(metricName, start, end))
        .all().stream()
        .mapToLong(row -> row.getLong("counter_value"))
        .sum();
  }
}
