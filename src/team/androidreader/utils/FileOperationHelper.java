package team.androidreader.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

	private static void DeleteFile(String path) {
		File file = new File(path);
		if (file.exists()) {
			boolean directory = file.isDirectory();
			if (directory) {
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
	public static boolean fileRename(String path, String newName) {
		File file = new File(path);
		String newPath = path.substring(0, path.lastIndexOf("/") + 1) + newName;
		return file.renameTo(new File(newPath));
	}

	/**
	 * 
	 * @param oldPath
	 * @param newPath
	 */
	public static void moveTo(String src, String dst) {

	}

	public static String copyFile(String src, String dst) {
		File srcFile = new File(src);
		String fileName = src.substring(src.lastIndexOf('/') + 1);
		String dstFilename = dst + File.separator + fileName;
		File newFile = new File(dst);
		String copy_mark = "copy_of_";
		while (newFile.exists()) {
			dstFilename = dst + File.separator + copy_mark + fileName;
			copy_mark += copy_mark;
			newFile = new File(dstFilename);
		}

		FileOutputStream ofStream = null;
		FileInputStream ifStream = null;

		try {
			if (!newFile.createNewFile()) {
				return null;
			}
			ofStream = new FileOutputStream(newFile);
			ifStream = new FileInputStream(srcFile);
			int count = 102400;
			byte[] buffer = new byte[count];
			int read = 0;
			while ((read = ifStream.read(buffer, 0, count)) != -1) {
				ofStream.write(buffer, 0, read);
			}
			return dstFilename;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ofStream != null)
					ofStream.close();
				if (ifStream != null)
					ifStream.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}
}
