package com.mp.demo.entity;


import com.alibaba.fastjson.annotation.JSONType;

/**
 * @author ycn
 */
@JSONType(orders={"id","notime","area","deviceclass","name","isno","cause","sort","time"})
public class Ycn {

  private String num;
  private String id;
  private String notime;
  private String area;
  private String deviceclass;
  private String name;
  private String isno;
  private String cause;
  private String sort;
  private String time;
  private String note;
  private String ip;
  private String port;
  private String reboot;


  public String getNum() {
    return num;
  }

  public void setNum(String num) {
    this.num = num;
  }


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  public String getNotime() {
    return notime;
  }

  public void setNotime(String notime) {
    this.notime = notime;
  }


  public String getArea() {
    return area;
  }

  public void setArea(String area) {
    this.area = area;
  }


  public String getDeviceclass() {
    return deviceclass;
  }

  public void setDeviceclass(String deviceclass) {
    this.deviceclass = deviceclass;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getIsno() {
    return isno;
  }

  public void setIsno(String isno) {
    this.isno = isno;
  }


  public String getCause() {
    return cause;
  }

  public void setCause(String cause) {
    this.cause = cause;
  }


  public String getSort() {
    return sort;
  }

  public void setSort(String sort) {
    this.sort = sort;
  }


  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }


  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }


  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }


  public String getPort() {
    return port;
  }

  public void setPort(String port) {
    this.port = port;
  }


  public String getReboot() {
    return reboot;
  }

  public void setReboot(String reboot) {
    this.reboot = reboot;
  }

}
