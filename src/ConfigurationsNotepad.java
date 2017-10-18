/***************************************************
 * Copyright (c) 2017; MIT license
 * @author Regina Hebig; github id: rhebig
 * https://github.com/rhebig/UI-Tracer 
 ***************************************************/

import java.awt.Rectangle;
import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.sikuli.script.Key;
import org.sikuli.script.Screen;

public class ConfigurationsNotepad extends Configurations {
	
	public ConfigurationsNotepad() {
		REMOTE_URL = "https://github.com/rhebig/Simple-Java-Text-Editor.git";//https://github.com/pH-7/Simple-Java-Text-Editor.git";
	    basicpath = ".";
		projectPath = basicpath + "Simple-Java-Text-Editor-master\\";
		buildName ="Simple";
		imageSavePath = basicpath + "Images\\";
		imagesInputPath = basicpath +  "Script_SimpleJavaText\\";
		antLocation = projectPath + "buildSimple" + ".xml";
		jarLocation = projectPath + buildName + ".jar";
		
	}
	
	
	
	public void plantFilesForBuild(Integer buildCount) {
		File srcDir;
		if(buildCount<80)
			srcDir = new File(basicpath + "FilesToPlant");
		else if(buildCount<81)
			srcDir = new File(basicpath + "FilesToPlant2");
		else 
			srcDir = new File(basicpath + "FilesToPlant3");
		File destDir = new File(projectPath);
		try {
			FileUtils.copyDirectory(srcDir, destDir);
		}catch(Exception e) {System.out.println(e);}
	}


	public void customizedSikuliScript(Rectangle screenRect, Integer commitNumber, String commitID) {
		Screen s = new Screen();
		try {
		s.click(imagesInputPath + "enlarge.png");
		TimeUnit.SECONDS.sleep(2);
		BuildAndScreenshotCoordinator.screenShot(screenRect, commitNumber, commitID, "");
		
		s.click(imagesInputPath+"File.png");
		BuildAndScreenshotCoordinator.screenShot(screenRect, commitNumber, commitID, "File\\");

		}catch(Exception e) {System.out.println(e);}
		try {
		s.hover(imagesInputPath+"Edit.png");
		BuildAndScreenshotCoordinator.screenShot(screenRect, commitNumber, commitID, "Edit\\");
		}catch(Exception e) {System.out.println(e);}
		try {
		
		s.hover(imagesInputPath+"About.png");
		TimeUnit.SECONDS.sleep(1);
		BuildAndScreenshotCoordinator.screenShot(screenRect, commitNumber, commitID, "About\\");
		}catch(Exception e) {System.out.println(e);}
		try {
		
		s.hover(imagesInputPath+"Search.png");
		TimeUnit.SECONDS.sleep(1);
		BuildAndScreenshotCoordinator.screenShot(screenRect, commitNumber, commitID, "Search\\");
		}catch(Exception e) {System.out.println(e);}
		try {
		
		s.click(imagesInputPath+"DropDown.png");
		TimeUnit.SECONDS.sleep(1);
		BuildAndScreenshotCoordinator.screenShot(screenRect, commitNumber, commitID, "DropDown\\");
		}catch(Exception e) {System.out.println(e);}
		try {
		
		s.click(imagesInputPath+"First.png");
		TimeUnit.SECONDS.sleep(1);
		BuildAndScreenshotCoordinator.screenShot(screenRect, commitNumber, commitID, "One\\");
		TimeUnit.SECONDS.sleep(1);
		s.type(Key.ESC);
		}catch(Exception e) {System.out.println(e);}
		try {
			
		s.click(imagesInputPath+"Second.png");
		TimeUnit.SECONDS.sleep(1);
		BuildAndScreenshotCoordinator.screenShot(screenRect, commitNumber, commitID, "Two\\");
		TimeUnit.SECONDS.sleep(1);
		s.type(Key.ESC);
		}catch(Exception e) {System.out.println(e);}
		try {
		
		s.click(imagesInputPath+"Third.png");
		TimeUnit.SECONDS.sleep(1);
		BuildAndScreenshotCoordinator.screenShot(screenRect, commitNumber, commitID, "Three\\");
		TimeUnit.SECONDS.sleep(1);
		s.type(Key.ESC);
		}catch(Exception e) {System.out.println(e);}
		try {
		
		s.click(imagesInputPath+"Fourth.png");
		TimeUnit.SECONDS.sleep(1);
		BuildAndScreenshotCoordinator.screenShot(screenRect, commitNumber, commitID, "Four\\");
		TimeUnit.SECONDS.sleep(1);
		s.type(Key.ESC);
		}catch(Exception e) {System.out.println(e);}
		try {
		
		s.click(imagesInputPath+"fith.png");
		TimeUnit.SECONDS.sleep(1);
		BuildAndScreenshotCoordinator.screenShot(screenRect, commitNumber, commitID, "Five\\");
		TimeUnit.SECONDS.sleep(1);
		s.click(imagesInputPath+"close.png");
		}catch(Exception e) {System.out.println(e);}
		try {
		
		s.click(imagesInputPath+"sixth.png");
		TimeUnit.SECONDS.sleep(1);
		BuildAndScreenshotCoordinator.screenShot(screenRect, commitNumber, commitID, "Six\\");
		TimeUnit.SECONDS.sleep(1);
		s.click(imagesInputPath+"close.png");
		}catch(Exception e) {System.out.println(e);}
		try {
		
		s.click(imagesInputPath+"seventh.png");
		TimeUnit.SECONDS.sleep(1);
		BuildAndScreenshotCoordinator.screenShot(screenRect, commitNumber, commitID, "Seven\\");
		TimeUnit.SECONDS.sleep(1);
		s.click(imagesInputPath+"close.png");
		
		}catch(Exception e) {System.out.println(e);}
		try {
		s.click(imagesInputPath+"eigth.png");
		TimeUnit.SECONDS.sleep(1);
		BuildAndScreenshotCoordinator.screenShot(screenRect, commitNumber, commitID, "Eigth\\");
		}catch(Exception e) {System.out.println(e);}
		try {
		
		
		}catch(Exception e) {System.out.println(e);}
		
		
	}	

}
