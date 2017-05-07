package ch.santosalves.maven.plugin.enforcer.rule;

/**
 * File information pojo. To be used in a futur version to allow caching an avoiding file detection of files that didn't 
 * change from last analyse.
 * 
 * @author salves
 *
 */
public class FileInformation {
	private String fullPath;
	private long size;
	private long lastModified;
	private String encoding;
	public String getFullPath() {
		return fullPath;
	}
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}
	
	public long getSize() {
		return size;
	}
	
	public void setSize(long size) {
		this.size = size;
	}
	
	public long getLastModified() {
		return lastModified;
	}
	
	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}
	
	public String getEncoding() {
		return encoding;
	}
	
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
}