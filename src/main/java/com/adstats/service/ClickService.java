package com.adstats.service;

import com.adstats.exception.DeliveryNotFoundException;
import com.adstats.service.model.Click;
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

public class ClickService extends AbstractService {

  private final DeliveryService deliveryService;

  private final PreparedStatement saveClick;
  private final PreparedStatement getClickById;
  private final Mapper<Click> clickMapper;

  public ClickService(Session cassandraSession,
                      StatsService statsService,
                      DeliveryService deliveryService,
                      MappingManager mappingManager) {
    super(cassandraSession, statsService);

    this.deliveryService = deliveryService;

    clickMapper = mappingManager.mapper(Click.class);

    saveClick = cassandraSession.prepare(QueryBuilder
        .insertInto("click")
        .value("click_id", bindMarker())
        .value("delivery_id", bindMarker())
        .value("time", bindMarker()));

    getClickById = cassandraSession.prepare(QueryBuilder.
        select("click_id", "delivery_id", "time")
        .from("click")
        .where(eq("click_id", QueryBuilder.bindMarker())));
  }

  public ResultSetFuture saveClick(String clickId, String deliveryId, Instant time) {
    Optional<Delivery> delivery = deliveryService.getDelivery(deliveryId);
    if (!delivery.isPresent()) {
      throw new DeliveryNotFoundException(deliveryId);
    }

    ResultSetFuture future = cassandraSession.executeAsync(saveClick.bind(clickId, deliveryId, time));

    statsService.updateMetric(Metric.CLICK, StatsData.of(delivery.get()), time);

    return future;
  }

  Optional<Click> getClick(String clickId) {
    return ofNullable(clickMapper
        .map(cassandraSession.execute(getClickById.bind(clickId)))
        .one());
  }
}
