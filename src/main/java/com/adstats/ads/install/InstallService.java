package com.adstats.ads.install;

import com.adstats.ads.click.Click;
import com.adstats.ads.click.ClickDao;
import com.adstats.ads.delivery.Delivery;
import com.adstats.ads.delivery.DeliveryDao;
import com.adstats.stats.Metric;
import com.adstats.stats.StatsService;
import com.adstats.util.DateTimeUtils;
import java.time.Instant;
import java.util.Date;

public class InstallService {

  private final ClickDao clickDao;
  private final DeliveryDao deliveryDao;
  private final InstallDao installDao;
  private final StatsService statsService;

  public InstallService(ClickDao clickDao, DeliveryDao deliveryDao, InstallDao installDao, StatsService statsService) {
    this.clickDao = clickDao;
    this.deliveryDao = deliveryDao;
    this.installDao = installDao;
    this.statsService = statsService;
  }

  public void saveInstall(Install install) {
    Click click = clickDao.getClickOrThrow(install.getClickId());
    Delivery delivery = deliveryDao.getDeliveryOrThrow(click.getDeliveryId());
    Instant time = DateTimeUtils.parseFull(install.getTime());

    installDao.save(install.getInstallId(), install.getClickId(), Date.from(time));
    statsService.updateMetric(Metric.INSTALL, delivery, time);
  }
}
