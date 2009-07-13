import java.util.*;

public class Notification {

  private int id;
  private int escalation;
  private String sendAt;
  private String body;
  private String subject;
  ArrayList<Recipient> recipients;
  
  // Constructors
  public Notification() {
    escalation = 10;
    body = "";
    subject = "";
    recipients = new ArrayList<Recipient>();  
  }
  
  public Notification(String b) {
    escalation = 10;
    body = b;
    subject = "";
    recipients = new ArrayList<Recipient>();  
  }
  
  public Notification(String b, int e) {
    escalation = e;
    body = b;
    subject = "";    
    recipients = new ArrayList<Recipient>();  
  }
  
  public Notification(String b, int e, int i) {
    id = i;
    escalation = e;
    body = b;
    subject = "";    
    recipients = new ArrayList<Recipient>();
  }

  public Notification(String b, int e, int i, String s) {
    id = i;
    escalation = e;
    body = b;
    subject = "";    
    sendAt = s;
    recipients = new ArrayList<Recipient>();
  }  
  
  public int getId() {
    return this.id;
  }
  
  public void setId(int id) {
    this.id = id;
  }
  
  public int getEscalation() {
    return this.escalation;
  }
  
  public void setEscalation(int escalation) {
    this.escalation = escalation;
  }
  public String getSendAt() {
    return this.sendAt;
  }
  
  public void setSendAt(String sendAt) {
    this.sendAt = sendAt;
  }
  
  public String getBody() {
    return this.body;
  }
  
  public void setBody(String body) {
    this.body = body;
  }

  public String getSubject() {
    return this.subject;
  }
  
  public void setSubject(String subject) {
    this.subject = subject;
  }
    
  public ArrayList<Recipient> getRecipients() {
    return recipients;
  }
  
  public void addRecipient(Recipient recipient) {
    this.recipients.add(recipient);
  }
  
  public int getNumberOfRecipients() {
    return recipients.size();
  }
  
  public String getXML() {
    String content = "<notification>";
    content += "<body>" + getBody() + "</body>";
    content += "<subject>" + getSubject() + "</subject>";
    content += "<escalation>" + getEscalation() + "</escalation>";
    
    if (getSendAt() != null) {
      content += "<send_at>" + getSendAt() + "</send_at>";
    }
    
    content += "<recipients>";
    int index = 0;
    for (Recipient r : getRecipients()) {
      content += "<recipient><position>" + index + "</position><channel>" + r.getChannel() + "</channel>" +
                 "<address>" + r.getAddress() + "</address></recipient>";
      ++index;
    }
    content += "</recipients></notification>";
    
    return content;    
  }

}
