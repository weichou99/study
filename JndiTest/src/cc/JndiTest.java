package cc;

import java.sql.Connection;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class JndiTest {

  private static InitialContext createContext() throws NamingException {
    Properties env = new Properties();
    env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
    env.put(Context.PROVIDER_URL, "rmi://localhost:1099");
    InitialContext context = new InitialContext(env);
    return context;
  }

  public void test() {
    try {
      Context ctx = new InitialContext();
      Object lookup = ctx.lookup("jdbc/MyDatabase");
      System.out.println(lookup);
      DataSource ds = (DataSource) lookup;
      Connection connection = ds.getConnection();
      System.out.println(connection);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
