package com.akhalikov.adstats.config;

import com.akhalikov.adstats.ads.AdsController;
import com.akhalikov.adstats.ads.ClickDao;
import com.akhalikov.adstats.ads.DeliveryDao;
import com.akhalikov.adstats.util.cassandra.EmbeddedCassandraConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
    EmbeddedCassandraConfig.class,
    DeliveryDao.class,
    ClickDao.class,
    AdsController.class
})
public class CommonConfig {
  // For beans that are shared between prod and test environments
}
