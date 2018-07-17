package com.adstats.service;

import com.adstats.stats.StatsService;
import com.datastax.driver.core.Session;

public abstract class AbstractService {
  protected final Session cassandraSession;
  protected final StatsService statsService;

  public AbstractService(Session cassandraSession, StatsService statsService) {
    this.cassandraSession = cassandraSession;
    this.statsService = statsService;
  }
}
