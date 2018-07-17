package com.adstats.controller;

import com.adstats.service.model.Click;
import com.adstats.service.model.Delivery;
import com.adstats.service.model.Install;
import com.adstats.service.ClickService;
import com.adstats.service.DeliveryService;
import com.adstats.service.InstallService;
import static com.adstats.util.DateTimeUtils.parseFull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/ads", consumes = "application/json")
@ResponseStatus(HttpStatus.ACCEPTED)
public class AdsController {

  private final DeliveryService deliveryService;
  private final ClickService clickService;
  private final InstallService installService;

  public AdsController(DeliveryService deliveryService, ClickService clickService, InstallService installService) {
    this.deliveryService = deliveryService;
    this.clickService = clickService;
    this.installService = installService;
  }

  @PostMapping(value = "/delivery")
  public void saveDelivery(@RequestBody Delivery delivery) {
    deliveryService.saveDelivery(
        delivery.deliveryId,
        delivery.advertisementId,
        parseFull(delivery.time),
        delivery.browser,
        delivery.os,
        delivery.site);
  }

  @PostMapping(value = "/click")
  public void saveClick(@RequestBody Click click) {
    clickService.saveClick(click.clickId, click.deliveryId, parseFull(click.time));
  }

  @PostMapping(value = "/install")
  public void saveInstall(@RequestBody Install install) {
    installService.saveInstall(install.installId, install.clickId, parseFull(install.time));
  }
}
