package com.adstats.service.model;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Table;
import com.datastax.driver.mapping.annotations.Transient;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.Date;

@Table(name = "install")
public final class Install {

  @Column(name = "install_id")
  public String installId;

  @Column(name = "click_id")
  public String clickId;

  @Column(name = "time")
  @JsonIgnore
  public Date timestamp;

  @Transient
  public String time;
}
