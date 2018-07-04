package com.adstats.service;

import com.adstats.exception.ClickNotFoundException;
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
import java.time.Instant;
import java.util.Optional;

public class InstallService extends AbstractService {

  private final DeliveryService deliveryService;
  private final ClickService clickService;

  private final PreparedStatement saveInstall;

  public InstallService(Session cassandraSession, StatsService statsService, DeliveryService deliveryService, ClickService clickService) {
    super(cassandraSession, statsService);

    this.deliveryService = deliveryService;
    this.clickService = clickService;

    saveInstall = cassandraSession.prepare(QueryBuilder
        .insertInto("install")
        .value("install_id", bindMarker())
        .value("click_id", bindMarker())
        .value("time", bindMarker()));
  }

  public ResultSetFuture saveInstall(String installId, String clickId, Instant time) {
    Optional<Click> click = clickService.getClick(clickId);
    if (!click.isPresent()) {
      throw new ClickNotFoundException(clickId);
    }

    Optional<Delivery> delivery = deliveryService.getDelivery(click.get().deliveryId);
    if (!delivery.isPresent()) {
      throw new DeliveryNotFoundException(click.get().deliveryId);
    }

    ResultSetFuture future = cassandraSession.executeAsync(saveInstall.bind(installId, clickId, time));

    statsService.updateMetric(Metric.INSTALL, StatsData.of(delivery.get()), time);

    return future;
  }
}
