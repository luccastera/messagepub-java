import java.io.*;
import java.net.*;
import java.util.*;
import sun.misc.BASE64Encoder;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

/**
 * The MessagePub class lets you create a messagepub client to interact with the messagepub API.
 *
 * To use the client, you need your messagepub API key.
 *
 * <p>For more info, see the messagepub documentation: <a href="http://messagepub.com/documentation">messagepub.com/documentation</a></p>
 * 
 * @author Luc Castera (ShareMeme Inc.)
 *
 *
**/

public class MessagePub {

  private String apiKey;
  private String baseUrl;
  private String  encodedAuthorization;
  URLConnection connection;
  
  // Constructor
  public MessagePub() {  
  }
  
  /**
   * Instantiates a MessagePub client object.
   *
   * @param yourApiKey		The API Key of your messagepub account 
  **/
  public MessagePub(String yourApiKey) {
    apiKey = yourApiKey;
    baseUrl = "http://messagepub.com";
    setAuthenticationEncoding();
  }
  
  /**
   * Returns the API Key for the MessagePub object.
  **/  
  public String getApiKey() {
    return this.apiKey;
  }

  /**
   * Allows you to set the API Key for the MessagePub object.
   *
   * @param yourApiKey		The API Key of your messagepub account   
  **/    
  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
    setAuthenticationEncoding();
  }
  
  
  /**
   * Contacts the messagepub service via its HTTP REST API to create and send a notification.
   *
   * @see Notification
   * @see <a href="http://messagepub.com/documentation/api#createNotification">REST API DOC</a>
   *
   * @param myNotification		The notification that you want to send via messagepub
   * @return a String containing the XML returned by messagepub
  **/   
  public String create(Notification myNotification) {
    try {
      String xml = myNotification.getXML();
      // Get content from URL
      String urlName = this.baseUrl + "/notifications.xml";
      URL url = new URL(urlName);
      HttpURLConnection connection = (HttpURLConnection)url.openConnection();
      connection.setRequestProperty("Authorization", "Basic "+ this.encodedAuthorization);
      connection.setRequestProperty("Content-Type","text/xml");
      connection.setRequestMethod("POST");
      connection.setDoOutput(true);
      connection.setDoInput(true);
      
      PrintWriter pw = new PrintWriter(connection.getOutputStream());
      // send xml to messagepub
      pw.write(xml);
      pw.close();
      connection.connect();
      
      //String returnValue = Integer.toString(connection.getResponseCode());
      String returnValue = convertStreamToString(connection.getInputStream());
      
      return returnValue;
    } catch (Exception ex) {
      ex.printStackTrace();
      return "0";
    }
  }
  
  /**
   * Contacts the messagepub service via its HTTP REST API and fetches a list of the last 50 notifications sent from your account.
   *
   * @see Notification
   * @see <a href="http://messagepub.com/documentation/api#viewNotifications">REST API DOC</a>
   *
   * @return an ArrayList of Notification objects.
  **/   
  public ArrayList<Notification> list() {
    try {
      // Get content from URL
      String urlName = this.baseUrl + "/notifications.xml";
      URL url = new URL(urlName);
      HttpURLConnection connection = (HttpURLConnection)url.openConnection();
      connection.setRequestProperty("Authorization", "Basic "+ this.encodedAuthorization);
      connection.setRequestProperty("Content-Type","text/xml");
      connection.setRequestMethod("GET");      
      connection.connect();
      
      //Pase XML Returned
		  DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		  DocumentBuilder builder = factory.newDocumentBuilder();
		  Document doc = builder.parse(connection.getInputStream());
		  
		  Element root = doc.getDocumentElement();
		  
		  ArrayList<Notification> notificationArray = new ArrayList<Notification>();

		  if ( root.getTagName().equals("notifications") ) {
		    NodeList notificationNodes  = root.getChildNodes();
		    		    
		    for (int j = 0; j < notificationNodes.getLength(); j++) { // for each notification
		      Node notificationNode = notificationNodes.item(j);
		      
		      if (notificationNode instanceof Element) {
		      
		        Notification note = new Notification();      
		    
						NodeList notificationParameters  = notificationNode.getChildNodes();
						for (int i = 0; i < notificationParameters.getLength(); i++) {
							Node noteParam = notificationParameters.item(i);
							if (noteParam instanceof Element) {
							  Element noteParamElement = (Element) noteParam;
							  Text noteParamTextNode = (Text) noteParamElement.getFirstChild();
							  if (noteParamTextNode instanceof Text) {
									String noteParamValue = noteParamTextNode.getData().trim();
									if (noteParamElement.getTagName().equals("body")) {
									  note.setBody(noteParamValue);
									} else if (noteParamElement.getTagName().equals("subject")) {
									  note.setSubject(noteParamValue);
									} else if (noteParamElement.getTagName().equals("escalation")) {
									  note.setEscalation(Integer.parseInt(noteParamValue));
									} else if (noteParamElement.getTagName().equals("id")) {
									  note.setId(Integer.parseInt(noteParamValue));
									} else if (noteParamElement.getTagName().equals("send_at")) {
									  note.setSendAt(noteParamValue);
									} else if (noteParamElement.getTagName().equals("recipients")) {
									  NodeList recipientNodes = noteParamElement.getChildNodes();		          
									  for (int k=0; k < recipientNodes.getLength(); k ++) { // for each recipient
									    Node rcpt = recipientNodes.item(k);
									    if (rcpt instanceof Element) {
									      Recipient tempRecipient = new Recipient();
									      
									      Element rcptElement = (Element) rcpt;		              		              
												NodeList recipientParameters  = rcptElement.getChildNodes();
												for (int l = 0; l < recipientParameters.getLength(); l++) {
													Node recipientParam = recipientParameters.item(l);
													if (recipientParam instanceof Element) {
														Element recipientParamElement = (Element) recipientParam;
														Text recipientParamTextNode = (Text) recipientParamElement.getFirstChild();
														String recipientParamValue = recipientParamTextNode.getData().trim();
														if (recipientParamElement.getTagName().equals("channel")) {
															tempRecipient.setChannel(recipientParamValue);
														} else if (recipientParamElement.getTagName().equals("address")) {
															tempRecipient.setAddress(recipientParamValue);
														} else if (recipientParamElement.getTagName().equals("status")) {
															tempRecipient.setStatus(recipientParamValue);
														} else if (recipientParamElement.getTagName().equals("send_at")) {
															tempRecipient.setSendAt(recipientParamValue);
														}
													}
												}
									      note.addRecipient(tempRecipient);
									    }
									  }
									} else { // we got an empty node (for example <subject></subject>) so just ignore
									  // do nothing
									}
							  
							  
							  }
							}
						}
				
				    notificationArray.add(note);
		      
		      }

				}
		  }
      return notificationArray;
    } catch (Exception ex) {
      ex.printStackTrace();
      return new ArrayList<Notification>();
    }
  }
  
  /**
   * Contacts the messagepub service via its HTTP REST API and cancels all scheduled messages for a notification.
   *
   * @see Notification
   * @see <a href="http://messagepub.com/documentation/api#destroyNotification">REST API DOC</a>
   *
   * @param notificationId 		The unique ID for the notification you want to cancel
   *
   * @return <strong>true</strong> if the operation was a success.
  **/     
  public boolean cancel(int notificationId) {
    try {
			String urlName = this.baseUrl + "/notifications/" + notificationId + ".xml";
			URL url = new URL(urlName);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestProperty("Authorization", "Basic "+ this.encodedAuthorization);
			connection.setRequestProperty("Content-Type","text/xml");
			connection.setRequestMethod("DELETE");
      connection.setDoOutput(true);
      connection.setDoInput(true);			
			connection.connect();
      			
			String returnValue = convertStreamToString(connection.getInputStream());
			return true;
		}
    catch (Exception ex) {
      ex.printStackTrace();
      return false;
    }
  }

  /**
   * Contacts the messagepub service via its HTTP REST API and fetches the information for the notification with the ID you specify.
   *
   * @see Notification
   * @see <a href="http://messagepub.com/documentation/api#getNotification">REST API DOC</a>   
   *
   * @param notificationId 		The unique ID for the notification you want to get
   *
   * @return a Notification object.
  **/  
  public Notification get(int notificationId) {
    try {
      // Get content from URL
      String urlName = this.baseUrl + "/notifications/" + notificationId + ".xml";
      URL url = new URL(urlName);
      HttpURLConnection connection = (HttpURLConnection)url.openConnection();
      connection.setRequestProperty("Authorization", "Basic "+ this.encodedAuthorization);
      connection.setRequestProperty("Content-Type","text/xml");
      connection.setRequestMethod("GET");
      connection.connect();
      
      // Parse XML returned
		  DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		  DocumentBuilder builder = factory.newDocumentBuilder();
		  Document doc = builder.parse(connection.getInputStream());
		  
		  Element root = doc.getDocumentElement();
		  
		  Notification note = new Notification();
		  
		  if ( root.getTagName().equals("notification") ) {
		    NodeList notificationParameters  = root.getChildNodes();
		    for (int i = 0; i < notificationParameters.getLength(); i++) {
		      Node noteParam = notificationParameters.item(i);
		      if (noteParam instanceof Element) {
		        Element noteParamElement = (Element) noteParam;
		        Text noteParamTextNode = (Text) noteParamElement.getFirstChild();
		        if (noteParamTextNode instanceof Text) {
				      String noteParamValue = noteParamTextNode.getData().trim();		        
				      if (noteParamElement.getTagName().equals("body")) {
				        note.setBody(noteParamValue);
				      } else if (noteParamElement.getTagName().equals("subject")) {
				        note.setSubject(noteParamValue);
				      } else if (noteParamElement.getTagName().equals("escalation")) {
				        note.setEscalation(Integer.parseInt(noteParamValue));
				      } else if (noteParamElement.getTagName().equals("id")) {
				        note.setId(Integer.parseInt(noteParamValue));
				      } else if (noteParamElement.getTagName().equals("send_at")) {
				        note.setSendAt(noteParamValue);
				      } else if (noteParamElement.getTagName().equals("recipients")) {
				        NodeList recipientNodes = noteParamElement.getChildNodes();		          
				        for (int k=0; k < recipientNodes.getLength(); k ++) { // for each recipient
				          Node rcpt = recipientNodes.item(k);
				          if (rcpt instanceof Element) {
				            Recipient tempRecipient = new Recipient();
				            
				            Element rcptElement = (Element) rcpt;		              		              
										NodeList recipientParameters  = rcptElement.getChildNodes();
										for (int l = 0; l < recipientParameters.getLength(); l++) {
											Node recipientParam = recipientParameters.item(l);
											if (recipientParam instanceof Element) {
												Element recipientParamElement = (Element) recipientParam;
												Text recipientParamTextNode = (Text) recipientParamElement.getFirstChild();
												String recipientParamValue = recipientParamTextNode.getData().trim();
												if (recipientParamElement.getTagName().equals("channel")) {
													tempRecipient.setChannel(recipientParamValue);
												} else if (recipientParamElement.getTagName().equals("address")) {
													tempRecipient.setAddress(recipientParamValue);
												} else if (recipientParamElement.getTagName().equals("status")) {
													tempRecipient.setStatus(recipientParamValue);
												} else if (recipientParamElement.getTagName().equals("send_at")) {
													tempRecipient.setSendAt(recipientParamValue);
												}
											}
										}
				            note.addRecipient(tempRecipient);
				          }
				        }
				      } else { // we got an empty node, for example (<subject></subject>) so just do nothing
				        // do nothing
				      }
		        
		        
		        }
		      }
		    }
		  }
      return note;
    }
    catch (Exception ex) {
      ex.printStackTrace();
      return new Notification();
    }
  }
  
  private void setAuthenticationEncoding() {
    BASE64Encoder enc = new sun.misc.BASE64Encoder();
    this.encodedAuthorization = enc.encode( this.apiKey.getBytes() );
  }
  


  public static String convertStreamToString(InputStream is) {
    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    StringBuilder sb = new StringBuilder();
    String line = null;

    try {
      while ((line = reader.readLine()) != null) {
        sb.append(line).append("\n");
      }
    } catch (IOException e) {
           e.printStackTrace();
    } finally {
        try {
          is.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
    }
    return sb.toString();
  }  

}

