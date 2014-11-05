/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

/**
 *
 * @author jon
 */
@Stateless
public class AppealSessionBean implements AppealSessionBeanRemote {
    @Resource(mappedName= "jms/QueueConnectionFactory")
    private ConnectionFactory queueConnectionFactory;
    @Resource(mappedName= "jms/Queue")
    private Queue queue;
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public void sendInfo(String matricNum, String content) {
        Connection queueConnection = null;
        Session session = null;
        MapMessage message = null;
        MessageProducer producer = null;
        
        try {
            queueConnection = queueConnectionFactory.createConnection();
            session = queueConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            queueConnection.start();
            
            producer = session.createProducer(queue);
            message = session.createMapMessage();
            message.setString("matricNum", matricNum);
            message.setString("content", content);
            
            producer.send(message);
        } catch (Exception e) {
            System.err.println("AMSClient: Exception caught: " + e.toString());
        } finally {
            if (queueConnection != null) {
                try {
                    queueConnection.close();
                } catch (Exception e) {
                    System.out.println("AMSClient: Excpetion caught: " + e.toString());
                }
            }
        }
    }

    
}
