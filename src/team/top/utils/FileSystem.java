package team.top.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import team.top.constant.Constant;
import android.os.Environment;

/**This is a class use for write file in sdcard , application directory or file in another path
 * 
 * @author ybw
 *
 */
public class FileSystem {

	public static String SDCARD_PATH;
	public static String APP_DIR;
	
	static{
		SDCARD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
		APP_DIR = SDCARD_PATH + File.separator + Constant.APP_NAME;
	}

	/**
	 * 
	 * @throws SdCardNotFoud	thrown if sdcard not mounted
	 */
	public FileSystem(){
	}

	/**write files to application directory
	 * 
	 * @param filename		the file write to application directory
	 * @param data			the file data 
	 * @return				the result true--success  false -- failed
	 */
	public boolean WriteToAppDir(String filename,byte[] data) {
		makeAppDir();
		return Write(APP_DIR + File.separator + filename, data);
	}

	/**write files to scard root directory
	 * 
	 * @param filename		the file write to sdcard root directory
	 * @param data			the file data 
	 * @return				the result true--success  false -- failed
	 */
	public boolean WriteToScard(String filepath,byte[] data) {
		return Write(SDCARD_PATH + File.separator + filepath, data);
	}
	
	/**write files to any path
	 * 
	 * @param filename		the file write to any path
	 * @param data			the file data 
	 * @return				the result true--success  false -- failed
	 */
	public boolean Write(String filepath, byte[] data) {
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
	
	private void makeAppDir() {
		File file = new File(FileSystem.APP_DIR);
		if (!file.exists())
			file.mkdir();
	}
}
