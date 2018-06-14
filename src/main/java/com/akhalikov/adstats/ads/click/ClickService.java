package com.akhalikov.adstats.ads.click;

import com.akhalikov.adstats.ads.delivery.DeliveryDao;
import com.akhalikov.adstats.exception.DeliveryNotFoundException;
import com.akhalikov.adstats.stats.Metric;
import com.akhalikov.adstats.stats.StatsDao;
import com.akhalikov.adstats.util.DateTimeUtils;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class ClickService {

  private final ClickDao clickDao;
  private final DeliveryDao deliveryDao;
  private final StatsDao statsDao;

  public ClickService(ClickDao clickDao, DeliveryDao deliveryDao, StatsDao statsDao) {
    this.clickDao = clickDao;
    this.deliveryDao = deliveryDao;
    this.statsDao = statsDao;
  }

  public void saveClick(Click click) {
    if (!deliveryDao.hasDelivery(click.getDeliveryId())) {
      throw new DeliveryNotFoundException(click.getDeliveryId());
    }

    Instant time = DateTimeUtils.parseFull(click.getTime());
    clickDao.save(click.getClickId(), click.getDeliveryId(), Date.from(time));
    statsDao.increaseMetric(Metric.CLICK, time.truncatedTo(ChronoUnit.SECONDS));
  }
}
