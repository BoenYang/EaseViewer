package team.androidreader.mainview;

import java.util.ArrayList;
import java.util.List;

public class FileListModel {
	
	
	public interface FileListChangeListener {
		
		public void onFileListChange(FileListModel fileListModel);
		
	}

	
	private List<FileInfo> fileList = new ArrayList<FileInfo>();
	private String currentDirectory;
	private FileListChangeListener listener;
	

	public FileListModel(List<FileInfo> filelist,String currentDir){
		this.fileList.clear();
		this.fileList.addAll(filelist);
		this.currentDirectory = currentDir;
	}
	
	public void setFileList(List<FileInfo> fileList) {
		this.fileList.clear();
		this.fileList.addAll(fileList);
		listener.onFileListChange(this);
	}

	public List<FileInfo> getFileList() {
		return fileList;
	}

	public String getCurrentDirectory() {
		return currentDirectory;
	}
	
	
	public void setCurrentDirectory(String currentDirectory) {
		this.currentDirectory = currentDirectory;
	}
	
	public void addFileListenerChangeListener(FileListChangeListener listener){
		this.listener = listener;
	}
}
