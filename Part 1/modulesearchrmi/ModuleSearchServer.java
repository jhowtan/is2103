package modulesearchrmi;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ModuleSearchServer extends Remote {
  //input : module code (String)
  //output: title of this module (String)
  public String getTitle(String moduleCode) throws RemoteException;

  //input : module title (String)
  //output: lecturer’s name of this module (String)
  public String getLecturer(String moduleTitle) throws RemoteException;

  //input : module level (int)
  //output: list of module codes in this level (ArrayList<String>)
  public ArrayList<String> getModuleList(int moduleLevel) throws RemoteException;
}
