package com.adstats.ads.click;

import com.adstats.ads.delivery.Delivery;
import com.adstats.ads.delivery.DeliveryDao;
import com.adstats.stats.Metric;
import com.adstats.stats.StatsService;
import com.adstats.util.DateTimeUtils;

import java.time.Instant;
import java.util.Date;

public class ClickService {

  private final ClickDao clickDao;
  private final DeliveryDao deliveryDao;
  private final StatsService statsService;

  public ClickService(ClickDao clickDao, DeliveryDao deliveryDao, StatsService statsService) {
    this.clickDao = clickDao;
    this.deliveryDao = deliveryDao;
    this.statsService = statsService;
  }

  public void saveClick(Click click) {
    Delivery delivery = deliveryDao.getDeliveryOrThrow(click.getDeliveryId());
    Instant time = DateTimeUtils.parseFull(click.getTime());
    clickDao.save(click.getClickId(), click.getDeliveryId(), Date.from(time));
    statsService.updateMetric(Metric.CLICK, delivery, time);
  }
}
