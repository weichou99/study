package cc.study;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		try {
			String line = "java -version";
			CommandLine cmdLine = CommandLine.parse(line);
			DefaultExecutor executor = new DefaultExecutor();
			executor.setExitValue(0);
			ExecuteWatchdog watchdog = new ExecuteWatchdog(90);
			executor.setWatchdog(watchdog);
			int exitValue = executor.execute(cmdLine);
			System.out.println(exitValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
