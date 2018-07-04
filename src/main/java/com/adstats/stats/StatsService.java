package com.adstats.stats;

import com.adstats.controller.json.BasicStats;
import com.adstats.controller.json.GroupStats;
import com.adstats.controller.json.GroupStatsItem;
import com.adstats.controller.json.Interval;
import com.adstats.controller.json.Stats;
import java.time.Instant;
import static java.time.temporal.ChronoUnit.SECONDS;
import java.util.Date;
import java.util.List;

public class StatsService {

  private final StatsDao statsDao;

  public StatsService(StatsDao statsDao) {
    this.statsDao = statsDao;
  }

  public BasicStats getStatistics(Date start, Date end) {
    Interval interval = new Interval(start, end);
    Stats stats = statsDao.getStatistics(interval);
    return new BasicStats(interval, stats);
  }

  public GroupStats getStatisticsByGroups(Date start, Date end, List<String> groups) {
    Interval interval = new Interval(start, end);
    List<GroupStatsItem> groupStats = statsDao.getStatisticsByGroup(interval, groups);
    return new GroupStats(interval, groupStats);
  }

  public void updateMetric(Metric metric, StatsData statsData, Instant time) {
    statsDao.updateMetric(metric, time.truncatedTo(SECONDS), statsData);
  }
}
