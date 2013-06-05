package team.androidreader.mainview;

import java.util.Comparator;
import java.util.HashMap;

public class FileSortHelper {
	public enum SortMethod {
		name, size, date, type,nosort
	}

	private SortMethod mSort;

	private boolean mFileFirst;

	private HashMap<SortMethod, Comparator> mComparatorList = new HashMap<SortMethod, Comparator>();

	public FileSortHelper() {
		mSort = SortMethod.name;
		mComparatorList.put(SortMethod.name, cmpName);
		mComparatorList.put(SortMethod.size, cmpSize);
		mComparatorList.put(SortMethod.date, cmpDate);
		mComparatorList.put(SortMethod.type, cmpType);
	}

	public void setSortMethog(SortMethod s) {
		mSort = s;
	}

	public SortMethod getSortMethod() {
		return mSort;
	}

	public void setFileFirst(boolean f) {
		mFileFirst = f;
	}

	public Comparator getComparator() {
		if(mSort == SortMethod.nosort){
			return null;
		}else{
			return mComparatorList.get(mSort);
		}
	}

	private abstract class FileComparator implements Comparator<FileInfo> {

		@Override
		public int compare(FileInfo object1, FileInfo object2) {
			if (object1.isDirectory == object2.isDirectory) {
				return doCompare(object1, object2);
			}

			if (mFileFirst) {
				return (object1.isDirectory ? 1 : -1);
			} else {
				return object1.isDirectory ? -1 : 1;
			}
		}

		protected abstract int doCompare(FileInfo object1, FileInfo object2);
	}

	private Comparator cmpName = new FileComparator() {
		@Override
		public int doCompare(FileInfo object1, FileInfo object2) {
			return object1.fileName.compareToIgnoreCase(object2.fileName);
		}
	};

	private Comparator cmpSize = new FileComparator() {
		@Override
		public int doCompare(FileInfo object1, FileInfo object2) {
			return longToCompareInt(object1.size - object2.size);
		}
	};

	private Comparator cmpDate = new FileComparator() {
		@Override
		public int doCompare(FileInfo object1, FileInfo object2) {
			return longToCompareInt(object2.modifyTime - object1.modifyTime);
		}
	};

	private int longToCompareInt(long result) {
		return result > 0 ? 1 : (result < 0 ? -1 : 0);
	}

	private Comparator cmpType = new FileComparator() {
		@Override
		public int doCompare(FileInfo object1, FileInfo object2) {
			int result = object1.fileName.compareToIgnoreCase(object2.fileName);
			if (result != 0)
				return result;
			return object1.fileName.compareToIgnoreCase(object2.fileName);
		}
	};
}
