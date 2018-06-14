package com.akhalikov.adstats.ads.install;

import com.akhalikov.adstats.ads.click.ClickDao;
import com.akhalikov.adstats.exception.ClickNotFoundException;
import com.akhalikov.adstats.stats.Metric;
import com.akhalikov.adstats.stats.StatsDao;
import com.akhalikov.adstats.util.DateTimeUtils;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class InstallService {

  private final ClickDao clickDao;
  private final InstallDao installDao;
  private final StatsDao statsDao;

  public InstallService(ClickDao clickDao, InstallDao installDao, StatsDao statsDao) {
    this.clickDao = clickDao;
    this.installDao = installDao;
    this.statsDao = statsDao;
  }

  public void saveInstall(Install install) {
    if (!clickDao.hasClick(install.getClickId())) {
      throw new ClickNotFoundException(install.getClickId());
    }
    Instant time = DateTimeUtils.parseFull(install.getTime());
    installDao.save(install.getInstallId(), install.getClickId(), Date.from(time));
    statsDao.increaseMetric(Metric.INSTALL, time.truncatedTo(ChronoUnit.SECONDS));
  }
}
