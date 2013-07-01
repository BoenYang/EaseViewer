package team.androidreader.mainview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import team.androidreader.mainview.FileListHelper.FileCategory;
import team.androidreader.mainview.FileSortHelper.SortMethod;

public class FileListModel {
	
	public interface FileListChangeListener {
		public void onFileListChange(FileListModel fileListModel);
	}
	
	public interface onSelectedListener {
		public void onSelected(int selectedCount);
	}

	public interface onSelectedAllListener{
		public void onSelectedAll(boolean flag);
	}
	
	public interface onOperationModelChangListener{
		public void onModelChange(int model);
	}
	
	private List<FileInfo> fileList = new ArrayList<FileInfo>();
	private static List<FileInfo> selectFiles = new ArrayList<FileInfo>();
	private String currentDirectory;
	
	private FileListChangeListener listener;
	private onSelectedListener selectedListener;
	private onSelectedAllListener onSelectedAllListener;
	private onOperationModelChangListener onOperationModelChangListener;
	private boolean seletct[];
	private int selectedNum;
	private int opeartion = FileListController.DEFAULT;
	private FileListHelper.FileCategory fileCategory = FileCategory.SDCARD;



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
	
	public void addFileOperationChangeListener(onOperationModelChangListener listener){
		this.onOperationModelChangListener = listener;
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
		Arrays.fill(seletct, false);
		setSelectedNum(0);
	}
	
	public boolean[] getSeletct() {
		return seletct;
	}

	public void setSeletct(boolean[] seletct) {
		this.seletct = seletct;
	}

	public void selectedAll(boolean flag){
		Arrays.fill(seletct, flag);
		if(flag){
			selectFiles.addAll(fileList);
			setSelectedNum(fileList.size());
		}else{
			clearSelectFIles();
		}
		onSelectedAllListener.onSelectedAll(flag);
	}
	
	public void setModel(int model){
		opeartion = model;
		this.onOperationModelChangListener.onModelChange(model);
	}
	
	public int getOpeartion() {
		return opeartion;
	}
	
	public FileListHelper.FileCategory getFileCategory() {
		return fileCategory;
	}

	public void setFileCategory(FileListHelper.FileCategory fileCategory) {
		this.fileCategory = fileCategory;
	}
}
