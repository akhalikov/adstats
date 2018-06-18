package com.adstats.config;

import com.adstats.ads.click.ClickDao;
import com.adstats.ads.click.ClickService;
import com.adstats.ads.delivery.DeliveryDao;
import com.adstats.ads.delivery.DeliveryService;
import com.adstats.ads.install.InstallDao;
import com.adstats.ads.install.InstallService;
import com.adstats.stats.StatsDao;
import com.adstats.stats.StatsService;
import com.adstats.util.cassandra.EmbeddedCassandraConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
    EmbeddedCassandraConfig.class,
    DeliveryDao.class,
    ClickDao.class,
    InstallDao.class,
    DeliveryService.class,
    ClickService.class,
    InstallService.class,
    StatsDao.class,
    StatsService.class
})
public class CommonConfig {
  // This context is intended for beans that are shared between
  // production and testing environments
}
