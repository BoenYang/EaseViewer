package team.top.utils;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.usermodel.PictureData;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.apache.poi.hwpf.usermodel.Picture;


public class Ppt2Images {
	
	private String fileName;
	private SlideShow ppt;
	
	
	public Ppt2Images(String filepath) throws IOException{
		String temp[] = filepath.split("/");
		String file = temp[temp.length - 1];
		fileName = file.substring(0, file.lastIndexOf('.'));
		FileInputStream inputStream = new FileInputStream(filepath);
		ppt = new SlideShow(inputStream);
		inputStream.close();
	}
	
	public String convert2Images(){
		Slide[] slides = ppt.getSlides();
		PictureData[] piPictures = ppt.getPictureData();
		for (int i = 0; i < slides.length; i++) {
			slides[i].getBackground();
		}
		return null;
	}
}
