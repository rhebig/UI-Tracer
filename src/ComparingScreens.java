/***************************************************
 * Copyright (c) 2017; MIT license
 * @author Regina Hebig; github id: rhebig
 * https://github.com/rhebig/UI-Tracer 
 ***************************************************/

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.eclipse.jgit.api.Git;
import org.sikuli.script.Finder;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;
import org.sikuli.script.ScreenImage;

import com.sun.tools.javac.util.Pair;

import net.avh4.util.imagecomparison.ImageComparison;
import net.avh4.util.imagecomparison.ImageComparisonResult;



public class ComparingScreens {

	static String imageSavePath = Configurations.getInstance().imageSavePath;
	static List<File> Images = new ArrayList<File>(); 
	static List<File> Folders = new ArrayList<File>(); 
	static List<DocumentationMatch> matches = new ArrayList<DocumentationMatch>();
	static Git git;
	
	public static void getGit() {
		try {
		git = Git.open(new File(Configurations.getInstance().projectPath));
		}catch(Exception e){System.out.println(e);}
	}
	
	
	public static void run() {
		renameImagesInPreparation();
		getListOfImages();					
		iteratePairs();
		
		
		for(int i = 0; i<Folders.size();i++) {
			Images = new ArrayList<File>();
			imageSavePath = Configurations.getInstance().imageSavePath + Folders.get(i).getName() + "\\";
			
			renameImagesInPreparation();
			getListOfImages();					
			iteratePairs();
		}
	}
	
	public static String extractCommitIDFromFilename(String filename) {
		String result = "";
		
		String[] parts = filename.split("_");
		result = parts[2].split(".png")[0];
		
		
		return result;
	}
	
	public static String extractCommitNumberFromFilename(String filename) {
		String result = "";
		
		String[] parts = filename.split("_");
		result = parts[1];
		
		
		return result;
	}
	
	public static void iteratePairs() {
		getGit();
		for(int i = 0; i<Images.size()-1; i++){
			File newer = Images.get(i);
			File older = Images.get(i+1);
			String commitID_newer = extractCommitIDFromFilename(newer.getName());
			String commitID_older = extractCommitIDFromFilename(older.getName());
			String  sub = imageSavePath.replace(Configurations.getInstance().basicpath, "");
			String mappingFileName = Configurations.getInstance().basicpath + "Mapping\\" + sub + "_" +commitID_newer + "_" + commitID_older + ".png";
			File f = new File(Configurations.getInstance().basicpath + "Mapping\\" + sub);
			if(!f.exists())
				f.mkdirs();
			System.out.println("comparing: " + newer.getName() + " & " + older.getName());
			
			if(!isEqual(newer.getAbsolutePath(),older.getAbsolutePath(), mappingFileName))
			{
				//store names, extract commitIDs!!
				System.out.println(commitID_newer);
				System.out.println(commitID_older);
				System.out.println(git);
				
				List<Pair<String,String>> codeDiff = CodeDiffTracer.traceForCommitPairToCodeDiff(git ,commitID_older, commitID_newer);
				System.out.println("codeDiff");System.out.println(codeDiff);
				
				DocumentationMatch dm =new DocumentationMatch(newer.getAbsolutePath(),older.getAbsolutePath(),commitID_newer, commitID_older, codeDiff, mappingFileName); 
				matches.add(dm);
				log(dm.toString(),Configurations.getInstance().basicpath + "Mapping\\mapping.txt");
			}
			
		}
	}
	
	
	public static void log(String message, String filename) {
		try {
			PrintWriter out = new PrintWriter(new FileWriter(filename, true), true);
			out.println(message);
			out.close();
		}catch(Exception e) {System.out.println(e);}
	}
	
	/**
	 * Helper method gets a list of all relevant files to analyze
	 */
	public static void getListOfImages() {
		File folder = new File(imageSavePath);
		File[] listOfFiles = folder.listFiles();

		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		          Images.add(listOfFiles[i]);
		    	  System.out.println("File " + listOfFiles[i].getName());
		        
		      } else if (listOfFiles[i].isDirectory()) {
		    	  Folders.add(listOfFiles[i]);
		        System.out.println("Directory " + listOfFiles[i].getName());
		      }
		    }
	
	}
	
	public static void renameImagesInPreparation() {
		File folder = new File(imageSavePath);
		File[] listOfFiles = folder.listFiles();

		    for (int i = 0; i < listOfFiles.length; i++) {
		    	 if (listOfFiles[i].isFile()) {
			          try{
			        	  File file = listOfFiles[i];
			                
			        	  String name = file.getName();
			        	  String path = file.getAbsolutePath().replace(name, "");
			        	  String[] parts = name.split("_");
			        	  if(parts[1].length()<2)
			        		  parts[1] = "000" + parts[1];
			        	  else if(parts[1].length()<3)
			        		  parts[1] = "00" + parts[1];
			        	  else if(parts[1].length()<4)
			        		  parts[1] = "0" + parts[1];
			        	  String internewname = parts[0] + "_" + parts[1] + "_" + parts[2];
			        	  String newname = internewname.split(".png")[0];
			        	  
			        	  
				          File file2 = new File(path + newname + ".png");
	
				          if (file2.exists())
				             throw new java.io.IOException("file exists");
	
				          boolean success = file.renameTo(file2);
			          }catch(Exception e) {System.out.println(e);}
			      } 
		    }
	}
	
 	public static Boolean isEqual(String filepath1, String filepath2, String mappingFilePath) 
	{
		try {
			BufferedImage img1 = ImageIO.read(new File(filepath1));
			BufferedImage img2 = ImageIO.read(new File(filepath2));
			
			ImageComparisonResult r =  ImageComparison.compare(img1, img2);
			if(r.isEqual())
			{
				System.out.println("equal");
				return true;
			}
			else {
				System.out.println("NOT equal");
			}
			
							
			}catch(Exception e) {System.out.println(e);}
		return false;
	}

	
	
	
}
