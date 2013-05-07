package team.top.utils;

import java.util.Comparator;

import team.top.data.FileInfo;

public class StringCompare implements Comparator<FileInfo>{

	@Override
	public int compare(FileInfo lhs, FileInfo rhs) {
		return lhs.fileName.compareTo(rhs.fileName);
	}
}
