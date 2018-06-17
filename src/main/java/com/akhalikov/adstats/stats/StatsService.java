package com.akhalikov.adstats.stats;

import com.akhalikov.adstats.ads.delivery.Delivery;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class StatsService {

  private final StatsDao statsDao;

  public StatsService(StatsDao statsDao) {
    this.statsDao = statsDao;
  }

  BasicStats getStatistics(String start, String end) {
    Interval interval = Interval.from(start, end);
    Stats stats = statsDao.fetchStats(interval);
    return new BasicStats(interval, stats);
  }

  public void updateMetric(Metric metric, Delivery delivery, Instant time) {
    statsDao.updateMetric(metric, time.truncatedTo(ChronoUnit.SECONDS), delivery.getBrowser(), delivery.getOs());
  }
}
