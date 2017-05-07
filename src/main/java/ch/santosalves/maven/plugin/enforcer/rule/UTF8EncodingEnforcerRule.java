package ch.santosalves.maven.plugin.enforcer.rule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.apache.maven.enforcer.rule.api.EnforcerRule;
import org.apache.maven.enforcer.rule.api.EnforcerRuleException;
import org.apache.maven.enforcer.rule.api.EnforcerRuleHelper;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluationException;

/**
 * This class, will checks that all files in a given folder are UTF-8 
 * @author salves
 *
 */
public class UTF8EncodingEnforcerRule implements EnforcerRule {
	
	/**
	 * The directory to scan, in a maven project src/main/java/
	 */
	private String dirToScan;

	/**
	 * Lists recursively the files in a root folder. Only files are returned
	 * @param path The root path
	 * @return a List of paths
	 */
	protected List<Path> getFiles(Path path) {
		//formatter:off
		try {
			return Files
					.list(path)
					.parallel()
					.flatMap(x -> x.toFile().isFile() ? Arrays.asList(x).stream() : getFiles(x).stream())
					.collect(Collectors.toList());
		} catch (IOException e) {
			return new ArrayList<>();
		}
		//formatter:on
	}

	@Override
	public void execute(EnforcerRuleHelper arg0) throws EnforcerRuleException {
		try {
			ExecutorService pool = Executors.newCachedThreadPool();
			String encoding = "UTF-8";
			
			if(Files.notExists(Paths.get(dirToScan))) {
				try {
					Files.createDirectories(Paths.get(dirToScan));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			List<Future<FileInformation>> futures = pool.invokeAll(getFiles(Paths.get(dirToScan)).parallelStream().map(x -> new FileEncodingExtractor(x.toFile())).collect(Collectors.toList()));
			List<FileInformation> list = futures.parallelStream().filter(x -> {
				try {
					return x.get().getEncoding() == null || x.get().getEncoding() != null && ! x.get().getEncoding().equals(encoding);
				} catch (InterruptedException | ExecutionException e) {
					return false;
				}
			}).map(m-> {
				try {
					return m.get();
				} catch (InterruptedException | ExecutionException e) {
					return null;
				}
			}).peek(x-> System.out.println("Detected bad encoding ("+x.getEncoding()+") in file "+ x.getFullPath())).collect(Collectors.toList());

			if(list.size() > 0) {
				throw new EnforcerRuleException("Some files ("+list.size()+") have a wrong encoding.");
			}
		} catch (InterruptedException e) {
			throw new EnforcerRuleException("The rule failed because of an internal exception", e);
		}
	}

	@Override
	public String getCacheId() {
		return this.dirToScan;
	}

	@Override
	public boolean isCacheable() {
		return false;
	}

	@Override
	public boolean isResultValid(EnforcerRule arg0) {
		return false;
	}

}
