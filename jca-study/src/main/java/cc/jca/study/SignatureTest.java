package cc.jca.study;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.Security;
import java.security.Signature;
import java.util.Set;

/**
 * Signature的使用。
 *
 */
public class SignatureTest {
  
  public static void main(String[] args) {
    Set<String> algorithms1 = Security.getAlgorithms("KeyPairGenerator");
    System.out.println(algorithms1);
    Set<String> algorithms2 = Security.getAlgorithms("Signature");
    System.out.println(algorithms2);
    
    try {
      KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
      keyGen.initialize(2048);
      KeyPair keyPair = keyGen.generateKeyPair();
      PrivateKey privateKey = keyPair.getPrivate();
      
      
      Signature signature = Signature.getInstance("SHA256withDSA");
      System.out.println(signature);
      signature.initSign(privateKey);
      signature.update("hi".getBytes("UTF-8"));
      byte[] sign = signature.sign();
      for (byte b : sign) {
        String hexString = Integer.toHexString(Byte.toUnsignedInt(b));
        System.out.println(hexString);
        
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
