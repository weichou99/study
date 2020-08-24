package cc.jca.study;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;

/**
 * SecureRandom的使用。
 *
 */
public class SecureRandomTest {

  public static void main(String[] args) {
    SecureRandomTest.initSeed();
    SecureRandomTest.getByAlgorithm();
    SecureRandomTest.getStrongAlgorithmsProperty();
    SecureRandomTest.getStrongSecureRandom();
  }

  public static void initSeed() {
    try {
      SecureRandom sr = SecureRandom.getInstanceStrong();
      byte[] seed = sr.generateSeed(8);
      int nextInt1 = sr.nextInt();
      System.out.println(nextInt1);
      sr.setSeed(seed);
      int nextInt2 = sr.nextInt();
      System.out.println(nextInt2);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public static void getByAlgorithm() {
    System.out.println("getByAlgorithm=======================================start");
    // SecureRandom.DRBG
    // SecureRandom.SHA1PRNG
    // SecureRandom.Windows-PRNG
    String[] algorithmAry = { "DRBG", "SHA1PRNG", "Windows-PRNG" };
    for (String algorithm : algorithmAry) {
      try {
        SecureRandom sr = SecureRandom.getInstance(algorithm);
        System.out.println("algorithm: ");
        System.out.println(algorithm);
        System.out.println("instance: ");
        System.out.println(sr);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    System.out.println("getByAlgorithm=======================================end");
  }

  public static void getStrongSecureRandom() {
    System.out.println("getStrongSecureRandom=======================================start");
    try {
      SecureRandom sr = SecureRandom.getInstanceStrong();
      System.out.println(sr);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    System.out.println("getStrongSecureRandom=======================================end");
  }

  public static void getStrongAlgorithmsProperty() {
    System.out.println("getStrongAlgorithmsProperty=======================================start");
    String property = Security.getProperty("securerandom.strongAlgorithms");
    System.out.println(property);
    System.out.println("getStrongAlgorithmsProperty=======================================end");
  }
}
