package com.mp.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("T_GIS_JUNCTION") //@TableName中的值对应着表名
public class TGisJunction {

  private static final long serialVersionUID=1L;

  private String lkbh;
  private String qyh;
  private String zqh;
  private String lkh;
  private String lkmc;
  private String lxdm;
  private String kzjx;
  private String lklx;
  private java.sql.Date gxsj;
  private String gxbm;
  private String glbm;
  private String jd;
  private String wd;
  private String sfsg;
  private String jkbh;
  private String jkmc;
  private String bzxw;
  private String bz;
  private String kdh;
  private String lkbj;


  public String getLkbh() {
    return lkbh;
  }

  public void setLkbh(String lkbh) {
    this.lkbh = lkbh;
  }


  public String getQyh() {
    return qyh;
  }

  public void setQyh(String qyh) {
    this.qyh = qyh;
  }


  public String getZqh() {
    return zqh;
  }

  public void setZqh(String zqh) {
    this.zqh = zqh;
  }


  public String getLkh() {
    return lkh;
  }

  public void setLkh(String lkh) {
    this.lkh = lkh;
  }


  public String getLkmc() {
    return lkmc;
  }

  public void setLkmc(String lkmc) {
    this.lkmc = lkmc;
  }


  public String getLxdm() {
    return lxdm;
  }

  public void setLxdm(String lxdm) {
    this.lxdm = lxdm;
  }


  public String getKzjx() {
    return kzjx;
  }

  public void setKzjx(String kzjx) {
    this.kzjx = kzjx;
  }


  public String getLklx() {
    return lklx;
  }

  public void setLklx(String lklx) {
    this.lklx = lklx;
  }


  public java.sql.Date getGxsj() {
    return gxsj;
  }

  public void setGxsj(java.sql.Date gxsj) {
    this.gxsj = gxsj;
  }


  public String getGxbm() {
    return gxbm;
  }

  public void setGxbm(String gxbm) {
    this.gxbm = gxbm;
  }


  public String getGlbm() {
    return glbm;
  }

  public void setGlbm(String glbm) {
    this.glbm = glbm;
  }


  public String getJd() {
    return jd;
  }

  public void setJd(String jd) {
    this.jd = jd;
  }


  public String getWd() {
    return wd;
  }

  public void setWd(String wd) {
    this.wd = wd;
  }


  public String getSfsg() {
    return sfsg;
  }

  public void setSfsg(String sfsg) {
    this.sfsg = sfsg;
  }


  public String getJkbh() {
    return jkbh;
  }

  public void setJkbh(String jkbh) {
    this.jkbh = jkbh;
  }


  public String getJkmc() {
    return jkmc;
  }

  public void setJkmc(String jkmc) {
    this.jkmc = jkmc;
  }


  public String getBzxw() {
    return bzxw;
  }

  public void setBzxw(String bzxw) {
    this.bzxw = bzxw;
  }


  public String getBz() {
    return bz;
  }

  public void setBz(String bz) {
    this.bz = bz;
  }


  public String getKdh() {
    return kdh;
  }

  public void setKdh(String kdh) {
    this.kdh = kdh;
  }


  public String getLkbj() {
    return lkbj;
  }

  public void setLkbj(String lkbj) {
    this.lkbj = lkbj;
  }

}
