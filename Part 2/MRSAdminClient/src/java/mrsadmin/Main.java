/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mrsadmin;

import ejb.MRSBeanRemote;
import java.util.Scanner;
import javax.ejb.EJB;

/**
 *
 * @author jon
 */
public class Main {

    @EJB
    private static MRSBeanRemote mrsb;
    public Scanner sc = new Scanner(System.in);

    public Main() {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Main client = new Main();
        client.getUserChoice();
    }

    private void getUserChoice() {
        String userInput = "";
        do {
            System.out.println();
            displayMenu();
            userInput = sc.nextLine();
            if (userInput.equals("1")) {
                addStudent();
            } else if (userInput.equals("2")) {
                deleteStudent();
            } else if (userInput.equals("3")) {
                addLecturer();
            } else if (userInput.equals("4")) {
                deleteLecturer();
            } else if (userInput.equals("5")) {
                addModule();
            } else if (userInput.equals("6")) {
                deleteModule();
            } else if (userInput.equals("7")) {
                addTutor();
            } else if (userInput.equals("8")) {
                deleteTutor();
            } else if (userInput.equals("9")) {
                addTutorial();
            } else if (userInput.equals("10")) {
                deleteTutorial();
            } else if (userInput.equals("11")) {
                viewModules();
            } else if (userInput.equals("12")) {
                viewTutorials();
            } else if (userInput.equals("13")) {
                assignStudentModule();
            } else if (userInput.equals("14")) {
                assignStudentTutorial();
            } else if (userInput.equals("15")) {
                processAppeal();
            } else if (userInput.equals("Q") || userInput.equals("q")) {
                System.out.println("Terminated. Exiting...");
            } else {
                System.out.println("Please Input a Number from 1 to 12.");
                System.out.println("To exit, input 'Q' or 'q'.");
            }
        } while (userInput == null || (!userInput.equals("Q") && !userInput.equals("q")));
    }

    private void displayMenu() {
        System.out.println("***********************************************");
        System.out.println("Welcome to the Module Registration System Admin");
        System.out.println("***********************************************");
        System.out.println();
        System.out.println("Select an option:");
        System.out.println("1.  Add student account(s)");
        System.out.println("2.  Delete student account(s)");
        System.out.println("3.  Add lecturer(s)");
        System.out.println("4.  Delete lecturer(s)");
        System.out.println("5.  Add module(s)");
        System.out.println("6.  Delete module(s)");
        System.out.println("7.  Add tutor(s)");
        System.out.println("8.  Delete tutor(s)");
        System.out.println("9.  Add tutorial(s)");
        System.out.println("10. Delete tutorial(s)");
        System.out.println("11. View modules");
        System.out.println("12. View tutorials");
        System.out.println("13. Assign students to modules");
        System.out.println("14. Assign students to tutorials");
        System.out.println("15. Process appeals");
        System.out.println();
        System.out.println("Enter 'q' or 'Q' to exit this admin console");
        System.out.println();
        System.out.print("Enter your option: ");
    }

    private void addStudent() {
        try {
            System.out.println("Add Student Account:");
            System.out.println();
            System.out.print("Enter matric number: ");
            String matricNum = sc.nextLine();
            boolean isNewStudent = mrsb.isNewStudent(matricNum);
            if (isNewStudent) {
                System.out.print("Enter name: ");
                String name = sc.nextLine();
                System.out.print("Enter default password: ");
                String password = sc.nextLine();
                System.out.print("Enter email: ");
                String email = sc.nextLine();
                mrsb.createStudent(matricNum, name, password, email);
                System.out.println("Student Account for " + name + " has been added.");
            } else {
                System.out.println("Sorry, this student account already exists!");
            }
        } catch (Exception ex) {
            System.err.println("Caught unexpected exception.");
            ex.printStackTrace();
        }
    }

    private void deleteStudent() {
        System.out.println("Delete Student Account:");
        System.out.print("Enter matric number of student: ");
        String matricNum = sc.nextLine();
        String result = mrsb.removeStudent(matricNum);
        System.out.println();
        System.out.println(result);
    }

    private void addLecturer() {
        try {
            System.out.println("Add Lecturer:");
            System.out.print("Enter staff number: ");
            String staffNum = sc.nextLine();
            boolean isNewLecturer = mrsb.isNewLecturer(staffNum);
            if (isNewLecturer) {
                System.out.print("Enter title: ");
                String title = sc.nextLine();
                System.out.print("Enter name: ");
                String name = sc.nextLine();
                System.out.print("Enter email: ");
                String email = sc.nextLine();
                System.out.print("Enter office location: ");
                String office = sc.nextLine();
                mrsb.createLecturer(staffNum, title, name, email, office);
                System.out.println("Lecturer " + name + " has been added!");
            } else {
                System.out.println("Sorry, this lecturer already exists!");
            }
        } catch (Exception ex) {
            System.err.println("Caught unexpected exception.");
            ex.printStackTrace();
        }
    }

    private void deleteLecturer() {
        System.out.println("Delete Lecturer:");
        System.out.print("Enter staff number of lecturer: ");
        String staffNum = sc.nextLine();
        String result = mrsb.removeLecturer(staffNum);
        System.out.println();
        System.out.println(result);
    }

    private void addModule() {
        try {
            System.out.println("Add Module:");
            System.out.print("Enter module code:");
            String modCode = sc.nextLine();
            boolean isNewModule = mrsb.isNewModule(modCode);
            if (isNewModule) {
                System.out.print("Enter title: ");
                String title = sc.nextLine();
                System.out.print("Enter time: ");
                String time = sc.nextLine();
                System.out.print("Enter venue: ");
                String venue = sc.nextLine();
                mrsb.createModule(modCode, title, time, venue);
                System.out.print("Enter module synopsis: ");
                String content = sc.nextLine();
                mrsb.createSynopsis(content);
                System.out.print("Enter module lecturer's staff number: ");
                String staffNum = sc.nextLine(); //
                if (!mrsb.isNewLecturer(staffNum)) {
                    mrsb.linkSynopsis();
                    mrsb.linkLecturer(staffNum);
                    System.out.println();
                    System.out.println("Module " + modCode + " has been added!");
                } else {
                    System.out.println("Lecturer for Module does not exist!");
                }
            } else {
                System.out.println("Sorry, this module already exists!");
            }
        } catch (Exception ex) {
            System.err.println("Caught an unexpected exception");
            ex.printStackTrace();
        }
    }

    private void deleteModule() {
        System.out.println("Delete Module:");
        System.out.print("Enter Module Code: ");
        String modCode = sc.nextLine();
        String result = mrsb.removeModule(modCode);
        System.out.println();
        System.out.println(result);
    }

    private void addTutor() {
        try {
            System.out.println("Add Tutor:");
            System.out.print("Enter staff number: ");
            String staffNum = sc.nextLine();
            boolean isNewTutor = mrsb.isNewTutor(staffNum);
            if (isNewTutor) {
                System.out.print("Enter name: ");
                String name = sc.nextLine();
                System.out.print("Enter email: ");
                String email = sc.nextLine();
                mrsb.createTutor(staffNum, name, email);
                System.out.println();
                System.out.println("Tutor " + name + " has been successfully added!");
            } else {
                System.out.println("Sorry, this tutor already exists!");
            }
        } catch (Exception ex) {
            System.err.println("Caught an unexpected exception");
            ex.printStackTrace();
        }
    }

    private void deleteTutor() {
        System.out.println("Enter tutor to remove.");
        System.out.print("Staff Number: ");
        String staffNum = sc.nextLine();
        String result = mrsb.removeTutor(staffNum);
        System.out.println();
        System.out.println(result);
    }

    private void addTutorial() {
        System.out.print("Enter module code for tutorial to be added to:");
        String modCode = sc.nextLine();
        if (!mrsb.isNewModule(modCode)) {
            System.out.println("Add Tutorial: ");
            System.out.print("Enter group number: ");
            String groupNum = sc.nextLine();
            boolean isNewTutorial = mrsb.isNewTutorial(modCode, groupNum);
            if (isNewTutorial) {
                System.out.print("Enter time: ");
                String time = sc.nextLine();
                System.out.print("Enter venue: ");
                String venue = sc.nextLine();
                mrsb.createTutorial(groupNum, time, venue);
                System.out.print("Enter tutor's staff number: ");
                String staffNum = sc.nextLine();
                mrsb.linkTutor(staffNum);
                mrsb.linkModule(modCode);
                System.out.println();
                System.out.print("Tutorial for " + modCode + " has been successfully added!");

            } else {
                System.out.println("Sorry, this tutorial already exists!");
            }
        } else {
            System.out.println("Module does not exist!");
        }
    }

    private void deleteTutorial() {
        System.out.println("Enter tutorial's module code");
        System.out.print("Module code: ");
        String modCode = sc.nextLine();
        System.out.print("Tutorial group number: ");
        String tutorialNum = sc.nextLine();
        String result = mrsb.removeTutorial(modCode, tutorialNum);
        System.out.println();
        System.out.println(result);
    }

    private void viewModules() {
        System.out.println("View Module");
        System.out.print("Enter module code: ");
        String modCode = sc.nextLine();
        System.out.println();
        String result = mrsb.viewModule(modCode);
        System.out.println(result);
    }

    private void viewTutorials() {
        System.out.println("View Tutorials");
        System.out.print("Enter module code: ");
        String modCode = sc.nextLine();
        System.out.println();
        String result = mrsb.viewTutorial(modCode);
        System.out.println(result);
    }

    private void assignStudentModule() {
        System.out.println("Assign Student to Module");
        System.out.print("Enter module code: ");
        String modCode = sc.nextLine();
        System.out.print("Enter matric number: ");
        String matricNum = sc.nextLine();
        String result = mrsb.assignModuleToStudent(modCode, matricNum);
        System.out.println(result);
    }

    private void assignStudentTutorial() {
        System.out.println("Assign Student to Tutorial");
        System.out.print("Enter module code: ");
        String modCode = sc.nextLine();
        System.out.print("Enter tutorial group: ");
        String groupNum = sc.nextLine();
        System.out.print("Enter matric number: ");
        String matricNum = sc.nextLine();
        String result = mrsb.assignTutorialToStudent(modCode, groupNum, matricNum);
        System.out.println(result);
    }

    private void processAppeal() {
        System.out.println("Process Appeals");
        String appealList = mrsb.processAppeals();
        if (appealList.equals("No appeals to show!\n")) {
            System.out.println(appealList);
            return;
        }
        System.out.print("Enter appeal id: ");
        String appealNum = sc.nextLine();
        System.out.print("Change status to: ");
        String statusChange = sc.nextLine();
        System.out.print("Comments: ");
        String commentsSet = sc.nextLine();
        mrsb.reviewAppeal(appealNum, statusChange, commentsSet);
    }
}
