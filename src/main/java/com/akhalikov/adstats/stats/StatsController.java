package com.akhalikov.adstats.stats;

import static com.akhalikov.adstats.util.DateTimeUtils.parseShort;
import java.util.Date;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ads")
public class StatsController {

  private final StatsDao statsDao;

  public StatsController(StatsDao statsDao) {
    this.statsDao = statsDao;
  }

  @GetMapping("/statistics")
  public StatsForInterval getStatisticsForInterval(@RequestParam(value = "start") String start,
                                                   @RequestParam(value = "end") String end) {

    Date startTime = Date.from(parseShort(start, true));
    Date endTime = Date.from(parseShort(end, true));

    Stats stats = statsDao.fetchStats(startTime, endTime);
    return new StatsForInterval(startTime, endTime, stats);
  }
}
