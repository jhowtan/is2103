package modulesearchrmi;
import java.rmi.Naming;

public class ModuleSearchServerApp {

  public static void main(String args[]) {
    try {
      ModuleSearchServerImpl moduleSearchServerImpl;
      moduleSearchServerImpl = new ModuleSearchServerImpl();
      Naming.rebind("ModuleSearchServer", moduleSearchServerImpl);
      System.out.println("ModuleSearchServer Started\n");
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
