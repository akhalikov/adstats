package com.akhalikov.adstats.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
    CommonConfig.class
})
public class ProdConfig {
  // This context is for production beans only
  // for example: services that should be mocked in the unit tests
}
