package com.adstats.stats;

import static com.adstats.util.DateTimeUtils.parseShort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/ads")
public class StatsController {

  private final StatsService statsService;

  public StatsController(StatsService statsService) {
    this.statsService = statsService;
  }

  @GetMapping("/hello")
  @ResponseStatus(HttpStatus.OK)
  public void hello() {
    System.out.println("hello!");
  }

  @GetMapping("/statistics")
  public ResponseEntity getStatistics(@RequestParam(value = "start") String start,
                                      @RequestParam(value = "end") String end,
                                      @RequestParam(value = "group_by", required = false) List<String> groupBy) {

    Date startTime = Date.from(parseShort(start, true));
    Date endTime = Date.from(parseShort(end, true));

    if (groupBy == null || groupBy.isEmpty()) {
      BasicStats basicStats = statsService.getStatistics(startTime, endTime);
      return new ResponseEntity<>(basicStats, HttpStatus.OK);
    }

    GroupStats groupStats = statsService.getStatisticsByGroups(startTime, endTime, groupBy);
    return new ResponseEntity<>(groupStats, HttpStatus.OK);
  }
}
