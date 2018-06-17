package com.akhalikov.adstats.ads.install;

import com.akhalikov.adstats.core.dao.AbstractDao;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import static com.datastax.driver.core.querybuilder.QueryBuilder.bindMarker;
import java.util.Date;

public class InstallDao extends AbstractDao {

  private final PreparedStatement saveQuery;

  public InstallDao(Session cassandraSession) {
    super(cassandraSession);

    saveQuery = cassandraSession.prepare(QueryBuilder
        .insertInto("install")
        .value("install_id", bindMarker())
        .value("click_id", bindMarker())
        .value("time", bindMarker()));
  }

  void save(String installId, String clickId, Date time) {
    getCassandraSession().execute(saveQuery.bind(installId, clickId, time));
  }
}
