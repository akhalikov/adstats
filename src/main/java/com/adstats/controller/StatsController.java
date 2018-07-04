package com.adstats.controller;

import com.adstats.stats.StatsService;
import com.adstats.controller.json.BasicStats;
import com.adstats.controller.json.GroupStats;
import static com.adstats.util.DateTimeUtils.parseShort;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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

    Date startTime = Date.from(parseShort(start, true));
    Date endTime = Date.from(parseShort(end, true));

    if (Objects.isNull(groupBy) || groupBy.isEmpty()) {
      BasicStats basicStats = statsService.getStatistics(startTime, endTime);
      return ResponseEntity.ok(basicStats);
    }

    GroupStats groupStats = statsService.getStatisticsByGroups(startTime, endTime, groupBy);
    return ResponseEntity.ok(groupStats);
  }
}
