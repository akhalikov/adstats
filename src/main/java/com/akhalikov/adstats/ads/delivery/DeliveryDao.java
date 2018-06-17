package com.akhalikov.adstats.ads.delivery;

import com.akhalikov.adstats.core.dao.AbstractDao;
import com.akhalikov.adstats.exception.DeliveryNotFoundException;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import static com.datastax.driver.core.querybuilder.QueryBuilder.bindMarker;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import java.util.Date;

public class DeliveryDao extends AbstractDao {
  private final PreparedStatement saveQuery;
  private final PreparedStatement getByIdQuery;

  public DeliveryDao(Session session) {
    super(session);

    saveQuery = session.prepare(QueryBuilder
        .insertInto("delivery")
        .value("delivery_id", bindMarker())
        .value("advertisement_id", bindMarker())
        .value("time", bindMarker())
        .value("browser", bindMarker())
        .value("os", bindMarker())
        .value("site", bindMarker()));

    getByIdQuery = session.prepare(QueryBuilder.
        select("delivery_id", "advertisement_id", "time", "browser", "os", "site")
        .from("delivery")
        .where(eq("delivery_id", bindMarker())));
  }

  void save(String deliveryId, int advertisementId, Date time, String browser, String os, String site) {
    getCassandraSession().execute(saveQuery.bind(deliveryId, advertisementId, time, browser, os, site));
  }

  public Delivery getDeliveryOrThrow(String deliveryId) {
    ResultSet resultSet = getCassandraSession().execute(getByIdQuery.bind(deliveryId));
    Row row = resultSet.one();
    if (row == null) {
      throw new DeliveryNotFoundException(deliveryId);
    }
    return new Delivery(
        row.getString("delivery_id"),
        row.getInt("advertisement_id"),
        row.getTimestamp("time").toString(),
        row.getString("browser"),
        row.getString("os"),
        row.getString("site"));
  }
}
