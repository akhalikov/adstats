package com.akhalikov.adstats.ads.delivery;

import com.akhalikov.adstats.core.dao.AbstractDao;
import static com.akhalikov.adstats.util.DateTimeUtils.parseInstant;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import static com.datastax.driver.core.querybuilder.QueryBuilder.bindMarker;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;

public class DeliveryDao extends AbstractDao {
  private final PreparedStatement savePreparedStatement;
  private final PreparedStatement fetchByDeliveryIdPreparedStatement;

  public DeliveryDao(Session cassandraSession) {
    super(cassandraSession);

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

  public void save(Delivery delivery) {
    getCassandraSession().execute(savePreparedStatement.bind(
        delivery.getDeliveryId(),
        delivery.getAdvertisementId(),
        parseInstant(delivery.getTime()),
        delivery.getBrowser(),
        delivery.getOs(),
        delivery.getSite()));
  }

  public int fetchCount(String deliveryId) {
    return getCassandraSession()
        .execute(fetchByDeliveryIdPreparedStatement.bind(deliveryId))
        .getAvailableWithoutFetching();
  }
}
