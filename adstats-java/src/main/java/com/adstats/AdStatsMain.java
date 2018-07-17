package com.adstats;

import com.adstats.config.ProdConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
    ProdConfig.class
})
public class AdStatsMain {

  public static void main(String[] args) {
    SpringApplication.run(AdStatsMain.class, args);
  }
}
