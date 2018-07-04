package com.adstats.service.model;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Table;
import com.datastax.driver.mapping.annotations.Transient;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.Date;

@Table(name = "delivery")
public final class Delivery {

  @Column(name = "delivery_id")
  public String deliveryId;

  @Column(name = "advertisement_id")
  public int advertisementId;

  @Column(name = "time")
  @JsonIgnore
  public Date timestamp;

  @Column(name = "browser")
  public String browser;

  @Column(name = "os")
  public String os;

  @Column(name = "site")
  public String site;

  @Transient
  public String time;
}
