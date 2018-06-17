package com.akhalikov.adstats.ads;

import com.akhalikov.adstats.ads.click.Click;
import com.akhalikov.adstats.ads.click.ClickService;
import com.akhalikov.adstats.ads.delivery.Delivery;
import com.akhalikov.adstats.ads.delivery.DeliveryService;
import com.akhalikov.adstats.ads.install.Install;
import com.akhalikov.adstats.ads.install.InstallService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ads")
public class AdsController {

  private final DeliveryService deliveryService;
  private final ClickService clickService;
  private final InstallService installService;

  public AdsController(DeliveryService deliveryService, ClickService clickService, InstallService installService) {
    this.deliveryService = deliveryService;
    this.clickService = clickService;
    this.installService = installService;
  }

  @PostMapping(value = "/delivery", consumes = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public void saveDelivery(@RequestBody Delivery delivery) {
    deliveryService.saveDelivery(delivery);
  }

  @PostMapping(value = "/click", consumes = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public void saveClick(@RequestBody Click click) {
    clickService.saveClick(click);
  }

  @PostMapping(value = "/install", consumes = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public void saveInstall(@RequestBody Install install) {
    installService.saveInstall(install);
  }
}
