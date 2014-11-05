/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author jon
 */
@MessageDriven(mappedName = "jms/Queue", activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
    //@ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/Queue"),
    //@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "jms/Queue"),
    @ActivationConfigProperty(propertyName = "clientId", propertyValue = "jms/Queue")
})
public class AppealMDB implements MessageListener {

    AppealEntity appeal;
    StudentEntity student;

    @Resource(mappedName = "jms/QueueConnectionFactory")
    private ConnectionFactory queueConnectionFactory;
    // private Random processingTime = new Random();
    @PersistenceContext()
    private EntityManager em;

    public AppealMDB() {
    }

    @Override
    public void onMessage(Message inMessage) {
        MapMessage msg = null;
        try {
            if (inMessage instanceof MapMessage) {
                msg = (MapMessage) inMessage;
                // Thread.sleep(processingTime.nextInt(5) * 1000);
                setUpEntities(msg);
            } else {
                System.out.println("AppealMDB.onMessage: "
                        + "Message of wrong type: " + inMessage.getClass().getName());
            }
        } catch (Throwable te) {
            System.out.println("AppealMDB.onMessage: Exception: " + te.toString());
        }
    }

    private void setUpEntities(MapMessage msg) {

        String matricNum = null;
        String time = null;
        String status = null;
        String content = null;
        String comment = null;
        Connection connection = null;

        try {
            matricNum = msg.getString("matricNum");
            content = msg.getString("content");
            status = "Unread";

            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            time = sdf.format(date);
            
            appeal = new AppealEntity();
            appeal.create(time, content, status, comment);
            student = findStudent(matricNum);
            appeal.setStudent(student);
            /*
            Collection<AppealEntity> appealList = student.getAppeals();
            appealList.add(appeal);
            student.setAppeals(appealList);
            */
            update();
            //em.merge(student);
            
        } catch (Exception e) {
            System.out.println("AppealMDB.setUpEntities: "
                    + "Could not create entities");
        }
        
        try { 
            connection = queueConnectionFactory.createConnection();
        } catch (Exception e) {
            System.out.println("AppealMDB.setUpEntities: " + e.toString());
        }
        finally {
            if (connection != null) {
                try {
                    connection.close();
                }
                catch (JMSException je) {
                    System.out.println("AppealMDB.JMSException caught: " + je.toString());
                }
            }
        }
    }

    private StudentEntity findStudent(String matricNum) {
        StudentEntity s = new StudentEntity();
        try {
            Query q = em.createQuery("SELECT s FROM Student AS s WHERE s.matricNumber = :matricNum");
            q.setParameter("matricNum", matricNum);
            s = (StudentEntity) q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public void update() {
        em.persist(appeal);
    }
}
