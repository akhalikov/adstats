package com.akhalikov.adstats.ads;

import com.akhalikov.adstats.ads.model.Delivery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ads")
public final class AdsController {

  @PostMapping("/delivery")
  public ResponseEntity saveDelivery(@RequestBody Delivery delivery) {
    return new ResponseEntity(HttpStatus.OK);
  }
}
