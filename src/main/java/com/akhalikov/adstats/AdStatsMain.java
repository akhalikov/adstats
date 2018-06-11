package com.akhalikov.adstats;

import com.akhalikov.adstats.ads.AdsController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
    AdsController.class
})
public class AdStatsMain {

  public static void main(String[] args) {
    SpringApplication.run(AdStatsMain.class, args);
  }
}
