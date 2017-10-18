/***************************************************
 * Copyright (c) 2017; MIT license
 * @author Regina Hebig; github id: rhebig
 * https://github.com/rhebig/UI-Tracer 
 ***************************************************/

import java.util.ArrayList;
import java.util.List;

import com.sun.tools.javac.util.Pair;

public class DocumentationMatch {

	public String screenshotNewer;
	public String screenshotOlder;
	public String mappingFile;
	
	public String newer_commitid;
	public String older_commitid;
	
	public List<Pair<String,String>> listOfChangedFiles = new ArrayList<Pair<String, String>>();
	
	public DocumentationMatch(String pathImageNewer, String pathImageOlder, String commitidNewer, String commitidOlder, List<Pair<String,String>> changesList, String mappingFilePath) {
		
		screenshotNewer = pathImageNewer;
		screenshotOlder = pathImageOlder;
		mappingFile = mappingFilePath;
		
		newer_commitid = commitidNewer;
		older_commitid = commitidOlder;
		listOfChangedFiles = changesList;
		
		
	}
	
	
	public String toString() {
		String result = "";
		result += "Younger Commit: " + newer_commitid + " " + screenshotNewer  + " ";
		result += "Older Commit: " + older_commitid + " " + screenshotOlder  + " ";
		result += "Diff: " + mappingFile + " ";
		result += "Files Changed: " + listOfChangedFiles;
		return result;
	}
}
