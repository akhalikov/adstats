package com.akhalikov.adstats.config;

import com.akhalikov.adstats.ads.SaveAdsController;
import com.akhalikov.adstats.ads.click.ClickDao;
import com.akhalikov.adstats.ads.delivery.DeliveryDao;
import com.akhalikov.adstats.ads.install.InstallDao;
import com.akhalikov.adstats.ads.stats.StatsDao;
import com.akhalikov.adstats.util.cassandra.EmbeddedCassandraConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
    EmbeddedCassandraConfig.class,
    DeliveryDao.class,
    ClickDao.class,
    InstallDao.class,
    StatsDao.class,
    SaveAdsController.class
})
public class CommonConfig {
  // This context is intended for beans that are shared between
  // production and testing environments
}
