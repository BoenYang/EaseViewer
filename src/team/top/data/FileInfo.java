package team.top.data;

public class FileInfo {
	
	private String fileName;
	private String fileAbsolutePath;
	private boolean isDirector;
	
	public FileInfo(String fileName, String fileAbsolutePath, boolean isDirector) {
		super();
		this.fileName = fileName;
		this.fileAbsolutePath = fileAbsolutePath;
		this.isDirector = isDirector;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileAbsolutePath() {
		return fileAbsolutePath;
	}

	public void setFileAbsolutePath(String fileAbsolutePath) {
		this.fileAbsolutePath = fileAbsolutePath;
	}

	public boolean isDirector() {
		return isDirector;
	}

	public void setDirector(boolean isDirector) {
		this.isDirector = isDirector;
	}

	
}
