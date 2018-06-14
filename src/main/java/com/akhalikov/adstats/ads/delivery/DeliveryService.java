package com.akhalikov.adstats.ads.delivery;

import com.akhalikov.adstats.stats.Metric;
import com.akhalikov.adstats.stats.StatsDao;
import com.akhalikov.adstats.util.DateTimeUtils;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DeliveryService {
  private final DeliveryDao deliveryDao;
  private final StatsDao statsDao;

  public DeliveryService(DeliveryDao deliveryDao, StatsDao statsDao) {
    this.deliveryDao = deliveryDao;
    this.statsDao = statsDao;
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

    statsDao.increaseMetric(Metric.DELIVERY, time.truncatedTo(ChronoUnit.SECONDS));
  }
}
