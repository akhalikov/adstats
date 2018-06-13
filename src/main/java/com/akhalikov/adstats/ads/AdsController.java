package com.akhalikov.adstats.ads;

import com.akhalikov.adstats.ads.model.Click;
import com.akhalikov.adstats.ads.model.Delivery;
import com.akhalikov.adstats.ads.model.Install;
import org.springframework.http.ResponseEntity;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ads")
public final class AdsController {

  private final DeliveryDao deliveryDao;
  private final ClickDao clickDao;
  private final InstallDao installDao;

  public AdsController(DeliveryDao deliveryDao, ClickDao clickDao, InstallDao installDao) {
    this.deliveryDao = deliveryDao;
    this.clickDao = clickDao;
    this.installDao = installDao;
  }

  @PostMapping("/delivery")
  public ResponseEntity saveDelivery(@RequestBody Delivery delivery) {
    deliveryDao.save(delivery);
    return ok().build();
  }

  @PostMapping("/click")
  public ResponseEntity click(@RequestBody Click click) {
    if (deliveryDao.fetchCount(click.getDeliveryId()) == 0) {
      return notFound().build();
    }
    clickDao.save(click);
    return ok().build();
  }

  @PostMapping("/install")
  public ResponseEntity install(@RequestBody Install install) {
    if (clickDao.fetchCount(install.getClickId()) == 0) {
      return notFound().build();
    }
    installDao.save(install);
    return ok().build();
  }
}
