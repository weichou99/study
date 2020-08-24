package cc.jca.study;

import java.security.Provider;
import java.security.Provider.Service;
import java.security.Security;
import java.util.Set;

/**
 * 印出JCA的Providers.
 * 印出JCA的Services.
 */
public class JcaProviders {
  public static void main(String[] args) {
    JcaProviders.printProviders();
    JcaProviders.printServices();
  }

  public static void printServices() {
    System.out.println("Services=======================================start");
    Provider[] providers = Security.getProviders();
    for (Provider provider : providers) {
      Set<Service> services = provider.getServices();
      for (Service service : services) {
        System.out.println(service);
      }
    }
    System.out.println("Services=======================================end");
  }

  public static void printProviders() {
    System.out.println("Providers=======================================start");
    Provider[] providers = Security.getProviders();
    for (Provider provider : providers) {
      System.out.println(provider.getName());
    }
    System.out.println("Providers=======================================end");
  }

}
