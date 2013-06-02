package team.androidreader.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import team.androidreader.constant.Constant;
import team.androidreader.mainview.FileInfo;
import android.os.Environment;

/**
 * This is a class use for write file in sdcard , application directory or file
 * in another path
 * 
 * @author ybw
 * 
 */
public class FileSystem {

	public static String SDCARD_PATH;
	public static String APP_DIR;
	public static String WORD_CACHE;
	public static String EXCEL_CACHE;
	public static String PDF_CACHE;
	public static String CAMERA_CACHE;
	public static String PRTSCR_DIR;

	static {
		SDCARD_PATH = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		APP_DIR = SDCARD_PATH + File.separator + Constant.APP_NAME;
		WORD_CACHE = APP_DIR + File.separator + Constant.WORD_CACHE_DIR;
		EXCEL_CACHE = APP_DIR + File.separator + Constant.EXCEL_CACHE_DIR;
		PDF_CACHE = APP_DIR + File.separator + Constant.PDF_CACHE_DIR;
		CAMERA_CACHE = APP_DIR + File.separator + Constant.CAMERA_CHCHE_DIR;
		PRTSCR_DIR = APP_DIR + File.separator + Constant.PTRSCR_DIR;
	}

	/**
	 * write files to application directory
	 * 
	 * @param filename
	 *            the file write to application directory
	 * @param data
	 *            the file data
	 * @return the result true--success false -- failed
	 */
	public static boolean WriteToAppDir(String filename, byte[] data) {
		return Write(APP_DIR + File.separator + filename, data);
	}

	/**
	 * write files to scard root directory
	 * 
	 * @param filename
	 *            the file write to sdcard root directory
	 * @param data
	 *            the file data
	 * @return the result true--success false -- failed
	 */
	public static boolean WriteToScard(String filepath, byte[] data) {
		return Write(SDCARD_PATH + File.separator + filepath, data);
	}

	/**
	 * write files to any path
	 * 
	 * @param filename
	 *            the file write to any path
	 * @param data
	 *            the file data
	 * @return the result true--success false -- failed
	 */
	public static boolean Write(String filepath, byte[] data) {
		File file = new File(filepath);
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(file);
			fileOutputStream.write(data);
			return true;
		} catch (FileNotFoundException e) {
			if (fileOutputStream != null)
				try {
					fileOutputStream.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			e.printStackTrace();
			return false;
		} catch (IOException e) {

			try {
				if (fileOutputStream != null) {
					fileOutputStream.flush();
					fileOutputStream.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 */
	public static void makeAppDirTree() {
		File appDir = new File(FileSystem.APP_DIR);
		if (!appDir.exists())
			appDir.mkdir();
		File wordDir = new File(FileSystem.WORD_CACHE);
		if (!wordDir.exists())
			wordDir.mkdir();
		File excelDir = new File(FileSystem.EXCEL_CACHE);
		if (!excelDir.exists())
			excelDir.mkdir();
		File pdfDir = new File(FileSystem.PDF_CACHE);
		if (!pdfDir.exists())
			pdfDir.mkdir();
		File cameraDir = new File(CAMERA_CACHE);
		if (!cameraDir.exists())
			cameraDir.mkdir();
		File prtScr = new File(PRTSCR_DIR);
		if (!prtScr.exists()) {
			prtScr.mkdir();
		}
	}

	/**
	 * 
	 * @param path
	 */
	public static void makeDir(String path) {
		File file = new File(path);
		if (!file.exists())
			file.exists();
	}

	/**
	 * 
	 * @param path
	 * @return
	 */
	public static String GetFileNameNoExtension(FileInfo fileInfo) {
		String fileName = fileInfo.absolutePath.substring(fileInfo.absolutePath
				.lastIndexOf('/') + 1);
		return fileName.substring(0, fileName.lastIndexOf('.'));
	}

	public static String GetFileNameNoExtension(String path) {
		String fileName = path.substring(path.lastIndexOf('/') + 1);
		return fileName.substring(0, fileName.lastIndexOf('.'));
	}

	public static String GetFileNameHasExtension(FileInfo fileInfo) {
		return fileInfo.absolutePath.substring(fileInfo.absolutePath
				.lastIndexOf('/') + 1);
	}

	public static String GetFileNameHasExtension(String path) {
		return path.substring(path.lastIndexOf('/') + 1);
	}

	public static boolean isHidden(FileInfo fileInfo) {
		if (fileInfo.fileName.charAt(0) == '.') {
			return true;
		}
		return false;
	}

	public static boolean isDirectory(FileInfo fileInfo) {
		File file = new File(fileInfo.absolutePath);
		return file.isDirectory();
	}

	/**
	 * 
	 * @return a file name with time information
	 */
	public static String GetTimeFileName() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int mounth = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int munites = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		String name = "" + year + mounth + day + hour + munites + second;
		return name;
	}

	/**
	 * 根据文件后缀名获得对应的MIME类型。
	 * 
	 * @param file
	 */
	public static String getMIMEType(FileInfo fileinfo) {
		String type = "*/*";
		String fName = fileinfo.fileName;
		// 获取后缀名前的分隔符"."在fName中的位置。
		int dotIndex = fName.lastIndexOf(".");
		if (dotIndex < 0) {
			return type;
		}
		/* 获取文件的后缀名 */
		String end = fName.substring(dotIndex, fName.length()).toLowerCase();
		if (end == "")
			return type;
		// 在MIME和文件类型的匹配表中找到对应的MIME类型。
		for (int i = 0; i < Constant.MIME_MapTable.length; i++) {
			if (end.equals(Constant.MIME_MapTable[i][0]))
				type = Constant.MIME_MapTable[i][1];
		}
		return type;
	}

}
