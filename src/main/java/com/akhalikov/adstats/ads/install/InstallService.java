package com.akhalikov.adstats.ads.install;

import com.akhalikov.adstats.ads.click.Click;
import com.akhalikov.adstats.ads.click.ClickDao;
import com.akhalikov.adstats.ads.delivery.Delivery;
import com.akhalikov.adstats.ads.delivery.DeliveryDao;
import com.akhalikov.adstats.stats.Metric;
import com.akhalikov.adstats.stats.StatsService;
import com.akhalikov.adstats.util.DateTimeUtils;
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
