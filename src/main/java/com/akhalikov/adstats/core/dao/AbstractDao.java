package com.akhalikov.adstats.core.dao;

import com.datastax.driver.core.Session;

public class AbstractDao {
  private final Session cassandraSession;

  public AbstractDao(Session cassandraSession) {
    this.cassandraSession = cassandraSession;
  }

  protected Session getCassandraSession() {
    return cassandraSession;
  }
}
