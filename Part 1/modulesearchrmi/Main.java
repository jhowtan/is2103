package modulesearchrmi;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.*;

public class Main {

  public static void main(String args[]) {
        try {
          String serverURL = "rmi://" + args[0] + "/ModuleSearchServer";
            ModuleSearchServer server = (ModuleSearchServer) Naming.lookup(serverURL);
            getUserChoice(server);
        } catch (Exception e) {
            e.printStackTrace();
        }
  }

  public static void getUserChoice(ModuleSearchServer server) throws RemoteException {
      Scanner scanner = new Scanner(System.in);
      int userInput;
      String userStringInput = "";

      while (!userStringInput.equals("Q") || !userStringInput.equals("q")) {
        System.out.print("Enter module level <'Q' or 'q' to exit>: ");
        if (scanner.hasNext()) {
          userStringInput = scanner.next();
          if (userStringInput.equals("Q") || userStringInput.equals("q")) {
            break;
          }
          if (userStringInput.matches("\\d+")) {
            userInput = Integer.parseInt(userStringInput);
            // Indicate not found for level entries out of the data set
            int flag = 0;
            int[] checkset = {1000, 2000, 3000, 4000, 5000, 6000};
            for (int c : checkset)
              if (userInput == c)
                flag = 1;
            if (flag != 1) {
              System.out.println("Module level is not found.");
              System.out.println();
              System.out.println();
            }
            // If four digits, call server
            if (userInput / 1000 > 0 && userInput / 1000 < 10) {
              search(server, userInput);
              System.out.println();
              System.out.println();
            }
          }
          else {
            System.out.println("Module level is not found.");
            System.out.println();
            System.out.println();
          }
        }
      }
      System.out.println();
      System.out.println("Terminated. Exiting...");
  }

  public static void search(ModuleSearchServer server, int input) throws RemoteException {
    ArrayList<String> modList = new ArrayList<String>(server.getModuleList(input));
    String modTitle = null;
    String modLecturer = null;

    for(int i = 0; i < modList.size(); i++) {
      modTitle = server.getTitle(modList.get(i));
      modLecturer = server.getLecturer(modTitle);
      System.out.println(modList.get(i) + ": " + modTitle + " <" + modLecturer + ">");
    }
  }

}
