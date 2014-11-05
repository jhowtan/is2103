/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amsclient;

import ejb.AppealSessionBeanRemote;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import javax.ejb.EJB;

/**
 *
 * @author jon
 */
public class Main {

    @EJB
    private static AppealSessionBeanRemote appealSessionBean;

    public Main() {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Main client = new Main();
        client.doAddRequest(args);
    }

    public void doAddRequest(String[] args) {
        try {
            String choice = "";

            System.out.println("Main: Client Started...");
            System.out.println("*****************************************");
            System.out.println("Welcome to the Appeal Management System ");
            System.out.println("*****************************************");
            while (!choice.equalsIgnoreCase("Q")) {
                System.out.println("Enter 'Q' to quit or 'Y' to proceed");

                System.out.print("Enter choice: ");
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                choice = br.readLine();

                if (choice.equalsIgnoreCase("Y")) {

                    System.out.print("Enter Matric Number: ");
                    String matricNumber = br.readLine();

                    System.out.print("Enter Appeal: ");
                    String content = br.readLine();

                    appealSessionBean.sendInfo(matricNumber, content);

                    System.out.println(matricNumber + ": " + content);
                    System.out.println("Appeal Sent");

                }
            }
        } catch (Exception ex) {
            System.err.println("Caught an unexpected exception!");
            ex.printStackTrace();
        }
    }
}
