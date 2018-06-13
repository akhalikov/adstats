package com.akhalikov.adstats.ads;

import com.akhalikov.adstats.ads.stats.StatsForInterval;
import java.time.ZonedDateTime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ads")
public class StatsController {

  @GetMapping("/statistics")
  public StatsForInterval getStatisticsForInterval(@RequestParam("start") ZonedDateTime start,
                                                   @RequestParam("end") ZonedDateTime end) {
    return new StatsForInterval(null, null);
  }
}
