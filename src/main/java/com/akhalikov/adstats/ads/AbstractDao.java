package com.akhalikov.adstats.ads;

import com.datastax.driver.core.Session;

public class AbstractDao<T> {
  private final Session cassandraSession;

  public AbstractDao(Session cassandraSession) {
    this.cassandraSession = cassandraSession;
  }

  Session getCassandraSession() {
    return cassandraSession;
  }
}
