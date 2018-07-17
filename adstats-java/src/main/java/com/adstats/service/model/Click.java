package com.adstats.service.model;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Table;
import com.datastax.driver.mapping.annotations.Transient;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.Date;

@Table(name = "click")
public final class Click {
  @Column(name = "click_id")
  public String clickId;

  @Column(name = "delivery_id")
  public String deliveryId;

  @Column(name = "time")
  @JsonIgnore
  public Date timestamp;

  @Transient
  public String time;
}
