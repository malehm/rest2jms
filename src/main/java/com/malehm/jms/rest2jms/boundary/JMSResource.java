package com.malehm.jms.rest2jms.boundary;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("jms")
@Stateless
public class JMSResource {

  @Resource(lookup = "java:jboss/DefaultJMSConnectionFactory")
  private ConnectionFactory connectionFactory;

  @Resource(lookup = "java:/jms/queue/ExpiryQueue")
  private Queue queue;

  @GET
  public void write() {
    Connection connection = null;
    try {
      connection = this.connectionFactory.createConnection();
      final Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
      final MessageProducer producer = session.createProducer(this.queue);
      final Message message = session.createTextMessage("Hello JMS");
      message.setStringProperty("firstProperty", "filterValue1");
      message.setStringProperty("secondProperty", "filterValue2");
      message.setStringProperty("thirdProperty", "filterValue3");
      producer.send(message);
    } catch (final Exception ex) {
      throw new RuntimeException("not working", ex);
    } finally {
      if (null != connection) {
        try {
          connection.close();
        } catch (final JMSException ex) {
          throw new RuntimeException("not working", ex);
        }
      }
    }
    System.out.println("hello jms!");
  }

}
