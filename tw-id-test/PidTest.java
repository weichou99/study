import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import com.fubon.mplus.util.CheckUtils;

public class PidTest {
	
	List<String> pidList = new ArrayList<>();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		File file = ResourceUtils.getFile("src/test/resources/pid.txt");
		System.out.println(file.getAbsolutePath());
		List<String> readLines = FileUtils.readLines(file );
		if (readLines!=null&&readLines.size()>0) {
			pidList.addAll(readLines);
		}
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		List<String> invalidList = new ArrayList<>();
		for (String pid : pidList) {
			boolean validID = CheckUtils.isValidID(pid);
			if (!validID) {
				invalidList.add(String.format("%s is invalid ID.", pid));
			}
		}
		
		System.out.format("total:%s, invalid:%s%n",pidList.size(), invalidList.size());
		if (invalidList.size()>0) {
			
			for (String error : invalidList) {
				System.out.println(error);
			}
			
			fail("invalid");
		}
	}

}
