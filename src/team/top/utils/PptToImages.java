package team.top.utils;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.usermodel.SlideShow;


public class PptToImages {
	
	private String fileName;
	private SlideShow ppt;
	
	
	public PptToImages(String filepath) throws IOException{
		String temp[] = filepath.split("/");
		String file = temp[temp.length - 1];
		fileName = file.substring(0, file.lastIndexOf('.'));
		FileInputStream inputStream = new FileInputStream(filepath);
		ppt = new SlideShow(inputStream);
		inputStream.close();
	}
	
	public String convert2Images(){
		Slide[] slides = ppt.getSlides();
		for (int i = 0; i < slides.length; i++) {
			slides[i].getBackground();
		}
		return null;
	}
}
