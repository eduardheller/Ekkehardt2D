package utils;

import java.io.*;

import javax.xml.parsers.*;

import org.w3c.dom.*;

public class XMLHandler {
	
	private Document doc;

	public XMLHandler(String filename){
		
		try{
			File toRead = new File("src/res/" + filename);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(toRead);
			doc.getDocumentElement().normalize();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public Document getDoc(){
	
		return this.doc;
		
	}
	
}
