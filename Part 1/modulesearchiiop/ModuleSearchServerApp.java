package modulesearchiiop;
import java.util.Properties;
import javax.naming.InitialContext;

public class ModuleSearchServerApp {

  static final String FACTORY      = "java.naming.factory.initial";
  static final String FACTORY_NAME = "com.sun.jndi.cosnaming.CNCtxFactory";
  static final String PROVIDER     = "java.naming.provider.url";
  static final String PROVIDER_URL = "iiop://localhost:900";

  public static void main(String args[]) {
    try {
      ModuleSearchServerImpl moduleSearchServerImpl = new ModuleSearchServerImpl();

      Properties props = new Properties();
      props.put(ModuleSearchServerApp.FACTORY, ModuleSearchServerApp.FACTORY_NAME);
      props.put(ModuleSearchServerApp.PROVIDER, ModuleSearchServerApp.PROVIDER_URL);
      InitialContext ic = new InitialContext(props);

      // Bind the object to the IIOP registry
      ic.rebind("ModuleSearchServer", moduleSearchServerImpl);
      System.out.println("ModuleSearchServer Started \n");
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
