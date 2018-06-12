package com.akhalikov.adstats.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
    CommonConfig.class
})
public class ProdConfig {
  // Only production beans
}
