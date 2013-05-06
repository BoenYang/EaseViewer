package team.top.data;

/**
 * 
 * @author ybw
 *
 */
public class FileInfo {

	public String fileName;
	public String absolutePath;
	public boolean isDirectory;
	public boolean isHidden;
	public long fileSize;
	@Override
	public String toString() {
		return "FileInfo [fileName=" + fileName + ", absolutePath="
				+ absolutePath + ", isDirectory=" + isDirectory + ", isHidden="
				+ isHidden + ", fileSize=" + fileSize + "]";
	}
}
