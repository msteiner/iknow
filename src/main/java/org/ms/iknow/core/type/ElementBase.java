package org.ms.iknow.core.type;

import java.util.Date;
import java.util.UUID;

abstract class ElementBase {
  
  protected String id;
  protected String createUser;
  protected String changeUser;
  protected Date createDate;
  protected Date changeDate;
  
  public ElementBase() {
    this.id = UUID.randomUUID().toString();
    this.createDate = new Date();
  }
  
  public String getId() {
    return id;
  }
  
  public String getCreateUser() {
    return createUser;
  }
  
  public void setCreateUser(String createUser) {
    this.createUser = createUser;
  }
  
  public String getChangeUser() {
    return changeUser;
  }
  
  public void setChangeUser(String changeUser) {
    this.changeUser = changeUser;
    setChangeDate();
  }
  
  public Date getCreateDate() {
    return createDate;
  }
  
  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }
  
  public Date getChangeDate() {
    return changeDate;
  }
  
  public void setChangeDate(Date changeDate) {
    this.changeDate = changeDate;
  }
  
  public String toString() {
    StringBuilder builder = new StringBuilder();
    
    builder.append("  id         = " + id + "\n");
    builder.append("  createUser = " + createUser + "\n");
    builder.append("  createDate = " + createDate + "\n");
    builder.append("  changeUser = " + changeUser + "\n");
    builder.append("  changeDate = " + changeDate + "\n");
    
    return builder.toString();
  }
  
  protected void setChangeDate() {
    setChangeDate(new Date());
  }
}
