package com.adstats.stats;

import com.adstats.ads.delivery.Delivery;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

public class StatsService {

  private final StatsDao statsDao;

  public StatsService(StatsDao statsDao) {
    this.statsDao = statsDao;
  }

  BasicStats getStatistics(Date start, Date end) {
    Interval interval = new Interval(start, end);
    Stats stats = statsDao.getStatistics(interval);
    return new BasicStats(interval, stats);
  }

  GroupStats getStatisticsByGroups(Date start, Date end, List<String> groups) {
    Interval interval = new Interval(start, end);
    List<GroupStatsItem> groupStats = statsDao.getStatisticsByGroup(interval, groups);
    return new GroupStats(interval, groupStats);
  }

  public void updateMetric(Metric metric, Delivery delivery, Instant time) {
    statsDao.updateMetric(metric, time.truncatedTo(ChronoUnit.SECONDS), delivery.getBrowser(), delivery.getOs());
  }
}
