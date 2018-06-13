package com.akhalikov.adstats.ads.install;

import com.akhalikov.adstats.core.dao.AbstractDao;
import static com.akhalikov.adstats.util.DateTimeUtils.parseInstant;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import static com.datastax.driver.core.querybuilder.QueryBuilder.bindMarker;

public class InstallDao extends AbstractDao {

  private final PreparedStatement savePreparedStatement;

  public InstallDao(Session cassandraSession) {
    super(cassandraSession);

    savePreparedStatement = cassandraSession.prepare(QueryBuilder
        .insertInto("install")
        .value("install_id", bindMarker())
        .value("click_id", bindMarker())
        .value("time", bindMarker()));
  }

  public void save(Install install) {
    getCassandraSession().execute(savePreparedStatement.bind(
        install.getInstallId(),
        install.getClickId(),
        parseInstant(install.getTime())
    ));
  }
}
