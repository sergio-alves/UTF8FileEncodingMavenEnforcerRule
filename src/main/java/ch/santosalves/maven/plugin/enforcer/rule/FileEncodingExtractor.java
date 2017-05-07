package ch.santosalves.maven.plugin.enforcer.rule;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.mozilla.universalchardet.UniversalDetector;

/**
 * This class is a callable object that will detect the file encoding. The 
 * <a href="https://mvnrepository.com/artifact/com.googlecode.juniversalchardet/juniversalchardet">juniversalchardet</a>
 * library is used to detect encoding. 
 * @author salves
 */
public class FileEncodingExtractor implements Callable<FileInformation> {
	private File file;

	public FileEncodingExtractor(File file) {
		this.file = file;
	}

	@Override
	public FileInformation call() throws Exception {
		byte[] buf = new byte[4096];

		try (FileInputStream fis = new java.io.FileInputStream(this.file)) {
			// (1)
			UniversalDetector detector = new UniversalDetector(null);

			// (2)
			int nread;
			while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
				detector.handleData(buf, 0, nread);
			}

			// (3)
			detector.dataEnd();

			// (4)
			FileInformation fileInformation= new FileInformation();
			String encoding = detector.getDetectedCharset();
			fileInformation.setEncoding(encoding);
			
			System.out.println("Detected " + encoding + " encoding for file " + file.getAbsolutePath());
			
			fileInformation.setFullPath(file.getAbsolutePath());
			fileInformation.setLastModified(this.file.lastModified());
			fileInformation.setSize(this.file.length());
			
			// (5)
			detector.reset();

			return fileInformation;
		}
	}
}