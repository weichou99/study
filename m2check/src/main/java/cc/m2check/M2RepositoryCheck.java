package cc.m2check;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

public class M2RepositoryCheck {
	public static void main(String[] args) {
		List<File> invalidList = new ArrayList<>();

//有一些sha1計算到一半的，其實檔案也是錯了，可能會如下，有aether開頭的檔案。
//		
//		C:\Users\carter\.m2\repository\net\bytebuddy\byte-buddy-agent\1.10.2\_remote.repositories
//		C:\Users\carter\.m2\repository\net\bytebuddy\byte-buddy-agent\1.10.2\aether-3a9a034c-1271-40b5-a3c7-6c24d943c9e4-byte-buddy-agent-1.10.2.jar.sha1-in-progress
//		C:\Users\carter\.m2\repository\net\bytebuddy\byte-buddy-agent\1.10.2\aether-a1953376-8da4-48d1-9755-33ad2bb0eb75-byte-buddy-agent-1.10.2.jar.sha1-in-progress
//		C:\Users\carter\.m2\repository\net\bytebuddy\byte-buddy-agent\1.10.2\byte-buddy-agent-1.10.2.jar
//		C:\Users\carter\.m2\repository\net\bytebuddy\byte-buddy-agent\1.10.2\byte-buddy-agent-1.10.2.pom
//		C:\Users\carter\.m2\repository\net\bytebuddy\byte-buddy-agent\1.10.2\byte-buddy-agent-1.10.2.pom.sha1

//		rd /Q /S C:\Users\carter\.m2\repository\org\apache\commons\commons-collections4\4.3
//		rd /Q /S C:\Users\carter\.m2\repository\org\springframework\boot\spring-boot\2.1.5.RELEASE
//		rd /Q /S C:\Users\carter\.m2\repository\org\springframework\boot\spring-boot-autoconfigure\2.1.5.RELEASE
//		rd /Q /S C:\Users\carter\.m2\repository\org\springframework\boot\spring-boot-test\2.1.5.RELEASE
//		rd /Q /S C:\Users\carter\.m2\repository\org\springframework\spring-aop\5.1.7.RELEASE
//		rd /Q /S C:\Users\carter\.m2\repository\org\springframework\spring-context\5.1.7.RELEASE
			
		System.out.format("SystemUtils.USER_HOME:%s%n",SystemUtils.USER_HOME);
		//C:\Users\carter\.m2\repository\
		//C:\Users\carter\.m2\repository\org\apache\commons\
		File m2Repository = new File(SystemUtils.USER_HOME,".m2/repository/");
		File[] files = m2Repository.listFiles();
		IOFileFilter fileFilter = new WildcardFileFilter("*.jar") ;
		IOFileFilter dirFilter = new WildcardFileFilter("*") ;
		Collection<File> listFilesAndDirs = FileUtils.listFiles(m2Repository, fileFilter, dirFilter);
		//System.out.println(listFilesAndDirs);
		for (File jarFile : listFilesAndDirs) {
			String name = jarFile.getName();
			//System.out.format("name:%s%n",name);
			try (FileInputStream openInputStream = FileUtils.openInputStream(jarFile);) {
				String sha1Hex = DigestUtils.sha1Hex(openInputStream);
				//System.out.format("sha1Hex:%s%n",sha1Hex);
				File sha1File = new File(jarFile.getParent(),name+".sha1");
				if (!sha1File.exists()) {
					System.out.format("sha1 file not exsit:%s%n",sha1File);
					continue;
				}
				String sha1 = FileUtils.readFileToString(sha1File).trim();
				//System.out.format("sha1:%s%n",sha1);
				
				if (!StringUtils.startsWith(sha1, sha1Hex)) {
					System.out.format("name:%s%n",name);
					System.out.format("sha1:%s%n",sha1);
					System.out.format("sha1Hex:%s%n",sha1Hex);
					invalidList.add(jarFile);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		
		for (File invalidFile : invalidList) {
			System.out.format("invalidFile:%s%n",invalidFile);
		}
	}
}
