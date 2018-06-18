package com.adstats.stats;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ads")
public class StatsController {

  private final StatsService statsService;

  public StatsController(StatsService statsService) {
    this.statsService = statsService;
  }

  @GetMapping("/statistics")
  public ResponseEntity getStatistics(@RequestParam(value = "start") String start,
                                  @RequestParam(value = "end") String end,
                                  @RequestParam(value = "group_by", required = false) List<String> groupBy) {

    if (groupBy == null || groupBy.isEmpty()) {
      BasicStats basicStats = statsService.getStatistics(start, end);
      return new ResponseEntity<>(basicStats, HttpStatus.OK);
    }

    GroupStats groupStats = new GroupStats();
    return new ResponseEntity<>(groupStats, HttpStatus.OK);
  }
}
