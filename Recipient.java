import java.util.*;

/**
 * Recipients will be receiving the messages you send via messagepub.<br/><br/>
 * A recipient object has the following data members:
 * <ul>
 * <li><strong>position</strong>: this helps you set the order in which the notifications must be sent</li>
 * <li><strong>channel</strong>: the channel where you want the message to be sent. One of: aim, email, twitter, phone, sms, gchat</li>
 * <li><strong>address</strong>: can be an email address, a phone number, a twitter or IM username, depending on the channel</li>
 * <li><strong>status</strong>: the status of the message sent to that recipient. Can be: new, sent, sending, cancelled. <em>This is set by messagepub servers, not the user of the library</em>.</li>
 * <li><strong>sendAt</strong>: a string representing the date at which the message will be sent to this recipient. <em>This is set by the messagepub servers, not the user of the library</em>.</li>
 * </ul>
 *
 * <p>For more info, see the messagepub documentation: <a href="http://messagepub.com/documentation">messagepub.com/documentation</a></p>
 *  
 * @author Luc Castera (ShareMeme Inc.)
 *
 *
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

