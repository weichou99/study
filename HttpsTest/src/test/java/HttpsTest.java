import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class HttpsTest {
  
  private String testUrl = "https://tb2c.518fb.com/b2cws/oltp.wsdl";
//  private String testUrl = "https://github.com/ESAPI/esapi-java-legacy";

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  }

  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void test() {
    int timeoutValue =10000;
    try {

//      SSLContext sslcontext = SSLContext.getInstance("SSL","SunJSSE");
//      sslcontext.init(null, new TrustManager[]{new MyX509TrustManager()}, new java.security.SecureRandom());
//
//      HostnameVerifier ignoreHostnameVerifier = new HostnameVerifier() {
//          public boolean verify(String s, SSLSession sslsession) {
//              System.out.println("WARNING: Hostname is not matched for cert.");
//              return true;
//          }
//      };
//      HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
//      HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext.getSocketFactory());

      URL url = new URL(testUrl);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setConnectTimeout(timeoutValue);
      connection.setReadTimeout(timeoutValue);
      connection.connect(); //connect
      int resCode = connection.getResponseCode();
      if (HttpURLConnection.HTTP_OK == resCode) {
        System.out.println("ok");
      }
      System.out.println(resCode);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
