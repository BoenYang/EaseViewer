package team.androidreader.utils;


import android.content.Context;
import android.content.SharedPreferences;

public class AppSharedPreferences {
	
	private static SharedPreferences sharedPreferences;
	
	public static void GetSharePreferences(Context context){
		AppSharedPreferences.sharedPreferences = context.getSharedPreferences("appdata", Context.MODE_WORLD_WRITEABLE);
	}
	
	public static void SetTheme(){
		
	}
	
	
}
