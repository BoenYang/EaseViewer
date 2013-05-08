package team.top.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.w3c.dom.Document;

/**
 * 
 * @author ybw
 *
 */
public class Word2Html {
	private String fileName;
	private HWPFDocument document;
	private List<Picture> pictures;
	private FileSystem fileUtil;
	private String pictureName;

	public Word2Html(String filepath) throws IOException{
		String temp[] = filepath.split("/");
		String file = temp[temp.length - 1];
		fileName = file.substring(0, file.lastIndexOf('.'));
		FileInputStream inputStream = new FileInputStream(filepath);
		document = new HWPFDocument(inputStream);
		inputStream.close();
		pictures = document.getPicturesTable().getAllPictures();
		fileUtil = new FileSystem();
	}


	public String convert2Html() throws TransformerException, IOException,
			ParserConfigurationException {
		WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
				DocumentBuilderFactory.newInstance().newDocumentBuilder()
						.newDocument());
		wordToHtmlConverter.setPicturesManager(new PicturesManager() {
			public String savePicture(byte[] content, PictureType pictureType,
					String suggestedName, float widthInches, float heightInches) {
				pictureName = suggestedName;
				return suggestedName;
			}
		});

		wordToHtmlConverter.processDocument(document);
		if (pictures != null) {
			for (int i = 0; i < pictures.size(); i++) {
				Picture pic = (Picture) pictures.get(i);
				try {
					pic.writeImageContent(new FileOutputStream(FileSystem.APP_DIR
							+ File.separator + pictureName));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}

		Document htmlDocument = wordToHtmlConverter.getDocument();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DOMSource domSource = new DOMSource(htmlDocument);
		StreamResult streamResult = new StreamResult(out);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer serializer = tf.newTransformer();
		serializer.setOutputProperty(OutputKeys.ENCODING, "GB2312");
		serializer.setOutputProperty(OutputKeys.INDENT, "yes");
		serializer.setOutputProperty(OutputKeys.METHOD, "html");
		serializer.transform(domSource, streamResult);
		out.close();
		fileUtil.WriteToAppDir(fileName + ".html", out.toByteArray());
		return FileSystem.APP_DIR + File.separator + fileName + ".html";
	}
}
