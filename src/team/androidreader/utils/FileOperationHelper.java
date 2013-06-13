package team.androidreader.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import team.androidreader.mainview.FileInfo;

public class FileOperationHelper {

	public static void Delete(List<FileInfo> fileList) {
		for (FileInfo fileInfo : fileList) {
			DeleteFile(fileInfo.absolutePath);
		}
	}

	public static void Copy(List<FileInfo> fileList, String dst) {
		for (FileInfo fileInfo : fileList) {
			copyFile(fileInfo.absolutePath, dst);
		}
	}

	public static void Rename(List<FileInfo> fileList, List<String> names) {
		int size = fileList.size();
		for (int i = 0; i < size; i++) {
			RenameFile(fileList.get(i), names.get(i));
		}
	}

	public static void Move(List<FileInfo> fileList, String dst) {
		for (FileInfo fileInfo : fileList) {
			MoveFile(fileInfo.absolutePath, dst);
		}
	}

	public static void DeleteFile(String path) {
		File file = new File(path);
		if (file.exists()) {
			if (file.isDirectory()) {
				for (File child : file.listFiles()) {
					DeleteFile(child.getAbsolutePath());
				}
			}
			file.delete();
		}

	}

	/**
	 * 
	 * @param path
	 * @param newName
	 */
	public static boolean RenameFile(FileInfo fileInfo, String newName) {
		File file = new File(fileInfo.absolutePath);
		String newPath = fileInfo.absolutePath.substring(0,
				fileInfo.absolutePath.lastIndexOf("/") + 1) + newName;
		return file.renameTo(new File(newPath));
	}

	/**
	 * 
	 * @param oldPath
	 * @param newPath
	 */
	public static void MoveFile(String src, String dst) {
		copyFile(src, dst);
		DeleteFile(src);
	}

	public static boolean copyFile(String src, String dst) {
		if(src.equals(dst)){
			return false;
		}
		File srcFile = new File(src);
		String srcFilename = srcFile.getName();
		String dstFilename = srcFilename;
		String dstPath = dst + File.separator + dstFilename;
		File dstFile = new File(dstPath);
		FileOutputStream ofStream = null;
		FileInputStream ifStream = null;
		while (dstFile.exists()) {
			dstFilename = "copy_of_" + dstFilename;
			dstFile = new File(dst + File.separator + dstFilename);
		}

		if (srcFile.isDirectory()) {
			
			System.out.println("create file " + dstFile.mkdirs());
			File[] chlids = srcFile.listFiles();
			for (File file : chlids) {
				System.out.println("copy " + file.getAbsolutePath() + " to " + dst + File.separator
						+ dstFilename);
				copyFile(file.getAbsolutePath(), dstFile.getAbsolutePath() + File.separator
						+ dstFilename);
			}
			return true;
		} else {
			try {
				dstFile.createNewFile();
				ifStream = new FileInputStream(srcFile);
				ofStream = new FileOutputStream(dstFile);
				int count = 102400;
	            byte[] buffer = new byte[count];
	            int read = 0;
	            while ((read = ifStream.read(buffer, 0, count)) != -1) {
	                ofStream.write(buffer, 0, read);
	            }
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(ifStream != null){
					try {
						ifStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(ofStream != null){
					try {
						ofStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return false;
	}
}
