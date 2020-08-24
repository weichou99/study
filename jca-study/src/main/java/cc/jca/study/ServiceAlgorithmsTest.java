package cc.jca.study;

import java.security.Security;
import java.util.Set;

/**
 * Service有那些演算法可以用。
 *
 */
public class ServiceAlgorithmsTest {
  public static void main(String[] args) {
    try {
      String[] serviceNames = { "Signature", "MessageDigest", "Cipher", "Mac", "KeyStore" };
      for (String serviceName : serviceNames) {
        Set<String> algorithms = Security.getAlgorithms(serviceName);
        System.out.format("Service Name:%s%n", serviceName);
        System.out.format("Algorithms:%s%n", algorithms);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
