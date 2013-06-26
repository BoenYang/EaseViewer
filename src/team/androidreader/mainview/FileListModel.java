package team.androidreader.mainview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileListModel {
	
	public interface FileListChangeListener {
		public void onFileListChange(FileListModel fileListModel);
	}
	
	public interface onSelectedListener {
		public void onSelected(int selectedCount);
	}

	public interface onSelectedAllListener{
		public void onSelectedAll();
	}
	
	private List<FileInfo> fileList = new ArrayList<FileInfo>();
	private List<FileInfo> selectFiles = new ArrayList<FileInfo>();
	private String currentDirectory;
	
	private FileListChangeListener listener;
	private onSelectedListener selectedListener;
	private onSelectedAllListener onSelectedAllListener;
	private boolean seletct[];
	private int selectedNum;
	
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
	
	public void addSelectedListener(onSelectedListener listener) {
		this.selectedListener = listener;
	}

	public void addSelectedAllListener(onSelectedAllListener listener){
		onSelectedAllListener = listener;
	}
	
	public int getSelectedNum() {
		return selectedNum;
	}

	public void setSelectedNum(int selectedNum) {
		this.selectedNum = selectedNum;
		selectedListener.onSelected(selectedNum);
	}
	
	public List<FileInfo> getSelectFiles() {
		return selectFiles;
	}

	public void addSelectFile(FileInfo fileInfo){
		selectFiles.add(fileInfo);
		setSelectedNum(selectFiles.size());
	}
	
	public void deleteSelectFile(FileInfo fileInfo){
		selectFiles.remove(fileInfo);
		setSelectedNum(selectFiles.size());
	}
	
	public void clearSelectFIles(){
		selectFiles.removeAll(selectFiles);
	}
	
	public boolean[] getSeletct() {
		return seletct;
	}

	public void setSeletct(boolean[] seletct) {
		this.seletct = seletct;
	}

	public void selectedAll(boolean flag){
		Arrays.fill(seletct, flag);
		onSelectedAllListener.onSelectedAll();
	}
	
	
}
