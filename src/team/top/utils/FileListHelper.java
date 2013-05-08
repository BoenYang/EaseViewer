package team.top.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import team.top.data.FileInfo;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore.Audio;
import android.provider.MediaStore.Files;
import android.provider.MediaStore.Files.FileColumns;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Video;

@SuppressLint("InlinedApi")
public class FileListHelper {

	private Context context;
	private final static String VOLUME = "external";
	private final static String[] SEARCH_COLUMNS = { FileColumns._ID,
			FileColumns.DATA, FileColumns.SIZE, FileColumns.DATE_MODIFIED };
	public static String sZipFileMimeType = "application/zip";

	private static final int COLUMN_ID = 0;
	private static final int COLUMN_PATH = 1;
	private static final int COLUMN_SIZE = 2;
	// private static final int COLUMN_DATE = 3;

	public static HashSet<String> sDocMimeTypesSet = new HashSet<String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			add("text/plain");
			add("text/plain");
			add("application/pdf");
			add("application/msword");
			add("application/vnd.ms-excel");
			add("application/vnd.ms-excel");
		}
	};

	public enum FileCategory {
		ALL, MUSIC, VIDEO, PICTURE, THEME, DOC, ZIP, APK, OTHER
	}

	public FileListHelper(Context context) {
		this.context = context;
	}

	public List<FileInfo> GetAllFiles(String path, boolean showHidden) {
		List<FileInfo> fileNames = new ArrayList<FileInfo>();
		List<FileInfo> dirList = new ArrayList<FileInfo>();
		List<FileInfo> fList = new ArrayList<FileInfo>();
		File file = new File(path);
		if (file != null && file.isDirectory()) {
			File[] files = file.listFiles();
			if (files != null) {
				for (File temp : files) {
					FileInfo fileInfo = new FileInfo();
					fileInfo.fileName = temp.getName();
					fileInfo.absolutePath = temp.getAbsolutePath();
					if (temp.isDirectory()) {
						fileInfo.isDirectory = true;
						if (!isHidden(fileInfo.fileName))
							fList.add(fileInfo);
					} else {
						fileInfo.isDirectory = false;
						fileInfo.fileSize = temp.length();
						if (!isHidden(fileInfo.fileName))
							fList.add(fileInfo);
					}
				}
			}
		}
		StringCompare stringCompare = new StringCompare();
		Collections.sort(dirList, stringCompare);
		fileNames.addAll(dirList);
		Collections.sort(fList, stringCompare);
		fileNames.addAll(fList);
		return fileNames;
	}

	public List<FileInfo> GetAllFiles(FileCategory c, boolean showHidden) {
		List<FileInfo> fileList = new ArrayList<FileInfo>();
		Cursor cursor = getCursor(c);
		while (cursor.moveToNext()) {
			FileInfo fileInfo = new FileInfo();
			fileInfo.fileId = cursor.getLong(COLUMN_ID);
			fileInfo.absolutePath = cursor.getString(COLUMN_PATH);
			if (!fileInfo.absolutePath.equals("")) {
				System.out.println("-----" + fileInfo.absolutePath);
				fileInfo.fileName = GetNameByPath(fileInfo.absolutePath);
				System.out.println("---------------->>" + fileInfo.fileName);
				fileInfo.isHidden = isHidden(fileInfo.fileName);
				fileInfo.isDirectory = isDirectory(fileInfo.absolutePath);
				fileInfo.fileSize = cursor.getLong(COLUMN_SIZE);

				if (!showHidden) {
					if (fileInfo.isHidden)
						continue;
				}
				fileList.add(fileInfo);
			}
		}
		return fileList;
	}

	private Uri GetUri(FileCategory c) {
		Uri uri = null;
		switch (c) {
		case DOC:
		case THEME:
		case ZIP:
		case APK:
			uri = Files.getContentUri(VOLUME);
			break;
		case MUSIC:
			uri = Audio.Media.getContentUri(VOLUME);
			break;
		case VIDEO:
			uri = Video.Media.getContentUri(VOLUME);
			break;
		case PICTURE:
			uri = Images.Media.getContentUri(VOLUME);
			break;
		default:
			uri = null;
			break;
		}
		return uri;
	}

	private Cursor getCursor(FileCategory c) {
		Uri uri = GetUri(c);
		String selection = GetDocSelection(c);
		Cursor cursor = context.getContentResolver().query(uri, SEARCH_COLUMNS,
				selection, null, null);
		return cursor;
	}

	private String GetDocSelection(FileCategory c) {
		String selection = null;
		switch (c) {
		case THEME:
			selection = FileColumns.DATA + " LIKE '%.mtz'";
			break;
		case DOC:
			selection = GetOfficeSelection();
			break;
		case ZIP:
			selection = "(" + FileColumns.MIME_TYPE + " == '"
					+ sZipFileMimeType + "')";
			break;
		case APK:
			selection = FileColumns.DATA + " LIKE '%.apk'";
			break;
		default:
			selection = null;
		}
		return selection;
	}

	private String GetOfficeSelection() {
		StringBuilder selection = new StringBuilder();
		Iterator<String> iter = sDocMimeTypesSet.iterator();
		while (iter.hasNext()) {
			selection.append("(" + FileColumns.MIME_TYPE + "=='" + iter.next()
					+ "') OR ");
		}
		return selection.substring(0, selection.lastIndexOf(")") + 1);
	}

	private boolean isHidden(String fileName) {
		if (fileName.charAt(0) == '.') {
			return true;
		}
		return false;
	}

	private boolean isDirectory(String path) {
		File file = new File(path);
		return file.isDirectory();
	}

	private String GetNameByPath(String path) {
		File file = new File(path);
		return file.getName();
	}

}
