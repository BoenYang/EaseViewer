package team.androidreader.mainview;

import java.util.List;

public class FileListController {

	private FileListModel fileListModel;
	public static final int ADD = 1;
	public static final int DELETE = 2;

	public FileListController(FileListModel fileListModel) {
		this.fileListModel = fileListModel;
	}

	public void handleDirectoryChange(List<FileInfo> fileList, String dir) {
		fileListModel.setFileList(fileList);
		fileListModel.setCurrentDirectory(dir);
		boolean[] checked = new boolean[fileList.size()];
		fileListModel.setSeletct(checked);
		fileListModel.clearSelectFIles();
	}

	public void handSelectFile(FileInfo fileInfo, int model) {
		if (model == ADD) {
			fileListModel.addSelectFile(fileInfo);
		} else if (model == DELETE) {
			fileListModel.deleteSelectFile(fileInfo);
		}
	}

}
