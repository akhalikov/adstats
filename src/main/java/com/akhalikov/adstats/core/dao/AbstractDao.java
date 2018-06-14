package com.akhalikov.adstats.core.dao;

import com.datastax.driver.core.Session;

public class AbstractDao {
  private final Session session;

  public AbstractDao(Session session) {
    this.session = session;
  }

  protected Session getSession() {
    return session;
  }
}
