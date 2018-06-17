package com.akhalikov.adstats.ads.delivery;

import com.akhalikov.adstats.stats.Metric;
import com.akhalikov.adstats.stats.StatsService;
import com.akhalikov.adstats.util.DateTimeUtils;
import java.time.Instant;
import java.util.Date;

public class DeliveryService {
  private final DeliveryDao deliveryDao;
  private final StatsService statsService;

  public DeliveryService(DeliveryDao deliveryDao, StatsService statsService) {
    this.deliveryDao = deliveryDao;
    this.statsService = statsService;
  }

  public void saveDelivery(Delivery delivery) {
    Instant time = DateTimeUtils.parseFull(delivery.getTime());

    deliveryDao.save(
        delivery.getDeliveryId(),
        delivery.getAdvertisementId(),
        Date.from(time),
        delivery.getBrowser(),
        delivery.getOs(),
        delivery.getSite());

    statsService.updateMetric(Metric.DELIVERY, delivery, time);
  }
}
