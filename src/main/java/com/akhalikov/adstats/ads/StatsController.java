package com.akhalikov.adstats.ads;

import com.akhalikov.adstats.ads.stats.Stats;
import com.akhalikov.adstats.ads.stats.StatsDao;
import com.akhalikov.adstats.ads.stats.StatsForInterval;
import com.akhalikov.adstats.util.Constants;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/ads")
public class StatsController {

  private final StatsDao statsDao;

  public StatsController(StatsDao statsDao) {
    this.statsDao = statsDao;
  }

  @GetMapping("/statistics")
  public StatsForInterval getStatisticsForInterval(
      @RequestParam("start") @DateTimeFormat(pattern = Constants.TIME_FORMAT) Date start,
      @RequestParam("end") @DateTimeFormat(pattern = Constants.TIME_FORMAT) Date end) {

    Stats stats = statsDao.fetchStats(start, end);
    return new StatsForInterval(start, end, stats);
  }
}
