package com.adstats.config;

import com.adstats.service.ClickService;
import com.adstats.service.DeliveryService;
import com.adstats.service.InstallService;
import com.adstats.stats.StatsDao;
import com.adstats.stats.StatsService;
import com.adstats.util.cassandra.EmbeddedCassandraConfig;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.MappingManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
    EmbeddedCassandraConfig.class,
    DeliveryService.class,
    ClickService.class,
    InstallService.class,
    ClickService.class,
    InstallService.class,
    StatsDao.class,
    StatsService.class
})
public class CommonConfig {

  // This context is intended for beans that are shared between
  // production and testing environments

  @Bean
  MappingManager mappingManager(Session cassandraSession) {
    return new MappingManager(cassandraSession);
  }
}
