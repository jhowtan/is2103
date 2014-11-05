package modulesearchrmi;

import modulesearchrmi.ModuleSearchServer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ModuleSearchServerImpl extends UnicastRemoteObject
  implements ModuleSearchServer {
    private static ArrayList<String> lecturerList;
    private static ArrayList<String> moduleList;
    private static ArrayList<String> titleList;

    public ModuleSearchServerImpl() throws RemoteException, IOException{
      lecturerList = new ArrayList<String>();
      moduleList = new ArrayList<String>();
      titleList = new ArrayList<String>();

      File lecturerInfo = new File("modulesearchrmi/lecturer.txt");
      File moduleInfo = new File("modulesearchrmi/module.txt");

      BufferedReader lectReader = new BufferedReader(new FileReader(lecturerInfo));
      while (lectReader.ready()) {
        String line = lectReader.readLine();
        String[] value = line.split(":", 2);
        titleList.add(value[0]);
        lecturerList.add(value[1]);
      }
      lectReader.close();

      BufferedReader modReader = new BufferedReader(new FileReader(moduleInfo));
      while (modReader.ready()) {
        String line = modReader.readLine();
        String[] value = line.split(":", 2);
        moduleList.add(value[0]);
      }
      modReader.close();
    }

    //input : module code (String)
    //output: title of this module (String)
    public String getTitle(String moduleCode) throws RemoteException {
      int modIndex = moduleList.indexOf(moduleCode);
      String moduleTitle = titleList.get(modIndex);
      System.out.println("getTitle(): " + moduleCode + " ==> " + "Title is " + moduleTitle);
      return moduleTitle;
    }

    //input : module title (String)
    //output: lecturer’s name of this module (String)
    public String getLecturer(String moduleTitle) throws RemoteException {
      int modIndex = titleList.indexOf(moduleTitle);
      String moduleLecturer = lecturerList.get(modIndex);
      System.out.println("getLecturer(): " + moduleTitle + " ==> " + "Lecturer is " + moduleLecturer);
      return moduleLecturer;
    }

    //input : module level (int)
    //output: list of module codes in this level (ArrayList<String>)
    public ArrayList<String> getModuleList(int moduleLevel) throws RemoteException {
      System.out.println();
      ArrayList<String> levelList = new ArrayList<String>();
      int flag = 0;
      int[] checkset = {1000, 2000, 3000, 4000, 5000, 6000};
      for (int c : checkset)
        if (moduleLevel == c)
          flag = 1;
      if (flag != 1)
        System.out.println("getModuleList(): " + moduleLevel + " ==> " + "Module level is not found");
      else {
        System.out.print("getModuleList(): " + moduleLevel + " ==> " + "Modules in this level:");
        for (int i = 0; i<moduleList.size(); i++) {
          if (Character.getNumericValue(moduleList.get(i).charAt(2)) == (moduleLevel/1000)) {
            levelList.add(moduleList.get(i));
          }
        }
        for (int i = 0; i<levelList.size(); i++)
          System.out.print(" " + levelList.get(i));
      }
      System.out.println();
      return levelList;
    }

  }
