import java.util.*;

/**

**/
public class Recipient implements java.io.Serializable {

  private int id;
  private int position;
  private String channel;
  private String address;
  private String status;
  private String sendAt;
  
  // Constructor
  public Recipient() {
  }
  public Recipient(int p, String c, String a) {
    position = p;
    channel = c;
    address = a;    
  }
  
  public Recipient(int i, int p, String c, String a) {
    id = i;
    position = p;
    channel = c;
    address = a;    
  }
  
  public int getId() {
    return this.id;
  } 
  
  public void setId(int id) {
    this.id = id;
  }
  
  public int getPosition() {
    return this.position;
  }
  
  public void setPosition(int position) {
    this.position = position;
  }
  
  public String getChannel() {
    return this.channel;
  }
  
  public void setChannel(String channel) {
    this.channel = channel;
  }    
  public String getAddress() {
    return this.address;
  }
  
  public void setAddress(String address) {
    this.address = address;
  }

  public String getStatus() {
    return this.status;
  }
  
  public void setStatus(String status) {
    this.status = status;
  }  
  
  public String getSendAt() {
    return this.sendAt;
  }
  
  public void setSendAt(String sendAt) {
    this.sendAt = sendAt;
  }  
}

