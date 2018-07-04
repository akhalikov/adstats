package com.adstats.service;

import com.adstats.service.model.Delivery;
import com.adstats.stats.Metric;
import com.adstats.stats.StatsData;
import com.adstats.stats.StatsService;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSetFuture;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import static com.datastax.driver.core.querybuilder.QueryBuilder.bindMarker;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import java.time.Instant;
import java.util.Optional;
import static java.util.Optional.ofNullable;

public class DeliveryService extends AbstractService {
  private final PreparedStatement saveDelivery;
  private final PreparedStatement getDeliveryById;
  private final Mapper<Delivery> deliveryMapper;

  public DeliveryService(Session cassandraSession, StatsService statsService, MappingManager mappingManager) {
    super(cassandraSession, statsService);

    deliveryMapper = mappingManager.mapper(Delivery.class);

    saveDelivery = cassandraSession.prepare(QueryBuilder
        .insertInto("delivery")
        .value("delivery_id", bindMarker())
        .value("advertisement_id", bindMarker())
        .value("time", bindMarker())
        .value("browser", bindMarker())
        .value("os", bindMarker())
        .value("site", bindMarker()));

    getDeliveryById = cassandraSession.prepare(QueryBuilder.
        select("delivery_id", "advertisement_id", "time", "browser", "os", "site")
        .from("delivery")
        .where(eq("delivery_id", bindMarker())));
  }

  public ResultSetFuture saveDelivery(String deliveryId, int advertisementId, Instant time, String browser, String os, String site) {
    ResultSetFuture future = cassandraSession.executeAsync(
        saveDelivery.bind(deliveryId, advertisementId, time, browser, os, site));

    statsService.updateMetric(Metric.DELIVERY, new StatsData(browser, os), time);

    return future;
  }

  Optional<Delivery> getDelivery(String deliveryId) {
    return ofNullable(deliveryMapper
        .map(cassandraSession.execute(getDeliveryById.bind(deliveryId)))
        .one());
  }
}
