package com.akhalikov.adstats.config;

import com.akhalikov.adstats.ads.AdsController;
import com.akhalikov.adstats.ads.DeliveryService;
import com.akhalikov.adstats.util.cassandra.EmbeddedCassandraConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
    EmbeddedCassandraConfig.class,
    DeliveryService.class,
    AdsController.class
})
public class ProdConfig {
}
