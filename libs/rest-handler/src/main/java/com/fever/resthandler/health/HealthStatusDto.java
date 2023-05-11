package com.fever.resthandler.health;

public class HealthStatusDto {

  private String status;

  public HealthStatusDto() {
    this.status = "ok";
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
