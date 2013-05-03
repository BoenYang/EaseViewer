package team.top.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import team.top.activity.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class FileListFragment extends Fragment {

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	private static ListView listView;
	private static List<Map<String, String>> fileList;
	private static SimpleAdapter adapter;
	public static String currDirectory = "/";
	private static View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_filelist, null);
		listView = (ListView) view.findViewById(R.id.filelistview);
		fileList = GetFiles("/");
		adapter = new SimpleAdapter(view.getContext(), fileList,
				R.layout.file_item, new String[] { "filename" ,"fileinfo"},
				new int[] { R.id.filetext,R.id.fileinfo });
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new ItemOnClickListener());
		return view;
	}

	public void backToParentPath() {
		fileList.clear();
		File file = new File(currDirectory);
		File parent = file.getParentFile();
		fileList.addAll(GetFiles(parent.getAbsolutePath()));
		adapter.notifyDataSetChanged();
	}

	private List<Map<String, String>> GetFiles(String path) {
		List<Map<String, String>> fileNames = new ArrayList<Map<String, String>>();
		File file = new File(path);
		currDirectory = path;
		if (file != null && file.isDirectory()) {
			File[] files = file.listFiles();

			if (files != null) {
				for (File temp : files) {
					Map<String, String> item = new HashMap<String, String>();
					item.put("absolutepath", temp.getAbsolutePath());
					item.put("filename", temp.getName());
					if (temp.isDirectory()) {
						item.put("isDirectory", "true");
					} else {
						item.put("isDirectory", "false");
						item.put("fileinfo", temp.length() + "");
					}
					fileNames.add(item);
				}
			}
		}
		return fileNames;
	}

	public boolean onKeyDown(int keycode, KeyEvent keyEvent) {
		if (keycode == KeyEvent.KEYCODE_BACK) {
			if(!currDirectory.equals("/"))
				backToParentPath();
			else {
				System.exit(0);
			}
		}
		return true;
	}

	class ItemOnClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Map<String, String> map = fileList.get(position);
			String isDirector = map.get("isDirectory");
			if (isDirector.equals("true")) {
				fileList.clear();
				String absolutePath = map.get("absolutepath");
				fileList.addAll(GetFiles(absolutePath));
				adapter.notifyDataSetChanged();
			}
		}
	}
	
	public static void setData(List<Map<String,String>> fileList){
		FileListFragment.fileList = fileList;
		adapter = new SimpleAdapter(view.getContext(), fileList,
				R.layout.file_item, new String[] { "filename" },
				new int[] { R.id.filetext });
		listView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

}
