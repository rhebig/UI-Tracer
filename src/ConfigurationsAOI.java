/***************************************************
 * Copyright (c) 2017; MIT license
 * @author Regina Hebig; github id: rhebig
 * https://github.com/rhebig/UI-Tracer 
 ***************************************************/

import java.awt.Rectangle;
import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.sikuli.script.Screen;

public class ConfigurationsAOI extends Configurations {
	
	 public ConfigurationsAOI() {
		 REMOTE_URL = "https://github.com/rhebig/ArtOfIllusion.git";
	      basicpath = ".";
		  projectPath = basicpath + "AOI\\";
		  buildName ="ArtOfIllusion";
		  imageSavePath = basicpath + "Images\\";
		  imagesInputPath = basicpath +  "ScriptInputs\\";
		  antLocation = projectPath + "ArtOfIllusion" + ".xml";
		  jarLocation = projectPath + buildName + ".jar";
		
		 
	 }
	 
	public void plantFilesForBuild(Integer buildCount) {
		File srcDir;
		File f = new File(Configurations.getInstance().antLocation);
		if(!f.exists()) {
		if(buildCount<418)
			srcDir = new File(basicpath + "FilesToPlant");
		else
			srcDir = new File(basicpath + "FilesToPlant_before417");
		File destDir = new File(projectPath);
		try {
			FileUtils.copyDirectory(srcDir, destDir);
		}catch(Exception e) {System.out.println(e);}}
	}


	public void customizedSikuliScript(Rectangle screenRect, Integer commitNumber, String commitID) {
		Screen s = new Screen();
		try {
		s.click(imagesInputPath + "FullScreenButton.png");
		TimeUnit.SECONDS.sleep(2);
		BuildAndScreenshotCoordinator.screenShot(screenRect, commitNumber, commitID, "");
		
		s.click(imagesInputPath+"File.png");
		BuildAndScreenshotCoordinator.screenShot(screenRect, commitNumber, commitID, "File\\");
	
		s.hover(imagesInputPath+"Edit.png");
		BuildAndScreenshotCoordinator.screenShot(screenRect, commitNumber, commitID, "Edit\\");
		
		s.hover(imagesInputPath+"Object.png");
		BuildAndScreenshotCoordinator.screenShot(screenRect, commitNumber, commitID, "Object\\");
	
		s.hover(imagesInputPath+"Tools.png");
		BuildAndScreenshotCoordinator.screenShot(screenRect, commitNumber, commitID, "Tools\\");
		
		s.hover(imagesInputPath+"Scene.png");
		BuildAndScreenshotCoordinator.screenShot(screenRect, commitNumber, commitID, "Scene\\");
		
		s.hover(imagesInputPath+"Animation.png");
		BuildAndScreenshotCoordinator.screenShot(screenRect, commitNumber, commitID, "Animation\\");
			
		s.hover(imagesInputPath+"View.png");
		BuildAndScreenshotCoordinator.screenShot(screenRect, commitNumber, commitID, "View\\");
		}catch(Exception e) {System.out.println(e);}
		
		
	}	

}
