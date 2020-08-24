package cc.jca.study;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * MessageDigest的使用。
 *
 */
public class MessageDigestTest {
  public static void main(String[] args) {
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");

      String input = "The quick brown fox jumps over the lazy dog";
      byte[] bytes = input.getBytes(Charset.forName("UTF-8"));

      byte[] digest = md.digest(bytes);
      for (byte b : digest) {
        System.out.print(Integer.toHexString(Byte.toUnsignedInt(b)));
      }
      System.out.println();
      System.out.println(new String(digest));

      String encodeToString = Base64.getEncoder().encodeToString(digest);
      System.out.println(encodeToString);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
