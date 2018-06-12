package com.akhalikov.adstats.ads;

import com.akhalikov.adstats.ads.model.Delivery;
import static com.akhalikov.adstats.util.DateTimeUtils.parseInstant;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import static com.datastax.driver.core.querybuilder.QueryBuilder.bindMarker;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;

public class DeliveryDao {
  private final Session cassandraSession;
  private final PreparedStatement savePreparedStatement;
  private final PreparedStatement fetchByDeliveryIdPreparedStatement;

  public DeliveryDao(Session cassandraSession) {
    this.cassandraSession = cassandraSession;

    savePreparedStatement = cassandraSession.prepare(QueryBuilder
        .insertInto("delivery")
        .value("delivery_id", bindMarker())
        .value("advertisement_id", bindMarker())
        .value("time", bindMarker())
        .value("browser", bindMarker())
        .value("os", bindMarker())
        .value("site", bindMarker()));

    fetchByDeliveryIdPreparedStatement = cassandraSession.prepare(QueryBuilder.
        select().all()
        .from("delivery")
        .where(eq("delivery_id", QueryBuilder.bindMarker())));
  }

  void save(Delivery delivery) {
    cassandraSession.execute(savePreparedStatement.bind(
        delivery.getDeliveryId(),
        delivery.getAdvertisementId(),
        parseInstant(delivery.getTime()),
        delivery.getBrowser(),
        delivery.getOs(),
        delivery.getSite()));
  }

  int fetchCount(String deliveryId) {
    return cassandraSession.execute(fetchByDeliveryIdPreparedStatement.bind(deliveryId))
        .getAvailableWithoutFetching();
  }
}
