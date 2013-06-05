package team.androidreader.mainview;

import java.util.List;

public class FileListController {
	
	private FileListModel fileListModel;
	
	public FileListController(FileListModel fileListModel){
		this.fileListModel = fileListModel;
	}
	
	public void handleDirectoryChange(List<FileInfo> fileList,String dir){
		fileListModel.setFileList(fileList);
		fileListModel.setCurrentDirectory(dir);
	}

}
