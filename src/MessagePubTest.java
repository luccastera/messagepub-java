import java.util.*;

public class MessagePubTest {

  public static void main(String[] args) {
  
    // Testing Recipient.java and Notification.java 
    System.out.println("-----------Testing Notification.java and Recipient.java-------------");    
    Notification note = new Notification("This is a test", 10);
    note.setSubject("This is the subject for emails only");
    note.addRecipient(new Recipient(1, 1, "twitter", "sharememeinc")); //TODO: Replace with your twitter username
    note.addRecipient(new Recipient(2, 2, "email", "joe@example.com")); //TODO: Replace with valid email
    
    System.out.println("Notification has " + note.getNumberOfRecipients() + " recipients.");
    for (Recipient r : note.getRecipients()) {
      System.out.println("recipient with id=" + r.getId() +
                         ", position=" + r.getPosition() + 
                         ", channel=" + r.getChannel() +
                         ", address=" + r.getAddress() );
    }
    System.out.println("_________________________________");
    System.out.println(note.getXML());
    System.out.println("_________________________________");
    
    System.out.println("\n");
    
    // Testing MessagePub.java
    MessagePub mpb = new MessagePub("your api key"); //TODO: REPLACE WITH YOUR API KEY 
    
    // Testing Create new notification
    System.out.println("-------------Testing MessagePub#create()-----------");
    String responseBody = mpb.create(note);    
    System.out.println("HTTP Response: ");
    System.out.println(responseBody);
    
    System.out.println("* * * Waiting before fetching all notifications... * * * \n");
    try { 
      Thread.sleep(3000);
    } catch (Exception e) {
      //do nothing
    }
    
    // Testing List
    System.out.println("------------Testing MessagePub#list():----------");
    ArrayList<Notification> myNotifications = mpb.list();
    int lastNotification = 1;
    System.out.println("Number of notifications found: " + myNotifications.size());
    for (Notification n : myNotifications) {
      if (lastNotification == 1) {
        lastNotification = n.getId();
      }
      System.out.println("notification with id=" + n.getId() +
                         ", escalation=" + n.getEscalation() +
                         ", body=" + n.getBody() +
                         ", subject=" + n.getSubject() +
                         ", send_at=" + n.getSendAt());
      for (Recipient r : n.getRecipients()) {
        System.out.println("- " + r.getChannel() + "<" + r.getAddress() + 
                           ">    (" + r.getStatus() + ")" + "---" + r.getSendAt());
      }
      System.out.println("");
    }
    
    // Testing cancel notification
    System.out.println("Cancelling notification with id=" + lastNotification + "...");
    boolean cancelled = mpb.cancel(lastNotification);
    if (cancelled) {
      System.out.println("Notification was cancelled.");
    }
    
    System.out.println("\n* * * Waiting before fetching the notification we just created... * * * \n");
    try { 
      Thread.sleep(5000);
    } catch (Exception e) {
      //do nothing
    }    
    
    
    // Testing get()
    Notification notification = mpb.get(lastNotification);
    System.out.println("-----------Testing MessagePub#get()-------------");
    System.out.println("ID=" + notification.getId());
    System.out.println("BODY=" + notification.getBody());
    System.out.println("SUBJECT=" + notification.getSubject());
    System.out.println("ESCALATION=" + notification.getEscalation());
    System.out.println("SEND AT=" + notification.getSendAt());
    System.out.println("RECIPIENTS:");
    ArrayList<Recipient> recipients = notification.getRecipients();
    for (Recipient r : notification.getRecipients()) {
      System.out.println("- " + r.getChannel() + "<" + r.getAddress() + 
                         ">    (" + r.getStatus() + ")" + "---" + r.getSendAt());
    }
    
    System.out.println("\n");           
    
  }


}
