package com.akhalikov.adstats.ads;

import com.akhalikov.adstats.ads.model.Delivery;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import static com.datastax.driver.core.querybuilder.QueryBuilder.bindMarker;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import static java.time.format.DateTimeFormatter.ofPattern;

public class DeliveryService {
  private static final DateTimeFormatter TIME_FORMATTER = ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ");

  private final Session cassandraSession;
  private final PreparedStatement saveDeliveryPreparedStatement;

  public DeliveryService(Session cassandraSession) {
    this.cassandraSession = cassandraSession;

    saveDeliveryPreparedStatement = cassandraSession.prepare(QueryBuilder
        .insertInto("adstats", "delivery")
        .value("advertisement_id", bindMarker())
        .value("delivery_id", bindMarker())
        .value("time", bindMarker())
        .value("browser", bindMarker())
        .value("os", bindMarker())
        .value("site", bindMarker()));
  }

  void saveDelivery(Delivery delivery) {
    Instant time = Instant.from(TIME_FORMATTER.parse(delivery.getTime()));

    cassandraSession.execute(saveDeliveryPreparedStatement.bind(
        delivery.getAdvertisementId(),
        delivery.getDeliveryId(),
        time,
        delivery.getBrowser(),
        delivery.getOs(),
        delivery.getSite()));
  }
}
