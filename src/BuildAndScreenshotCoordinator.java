/***************************************************
 * Copyright (c) 2017; MIT license
 * @author Regina Hebig; github id: rhebig
 * https://github.com/rhebig/UI-Tracer 
 ***************************************************/

import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.util.concurrent.TimeUnit;

import org.sikuli.script.Region;
import org.sikuli.script.Screen;
import org.sikuli.script.ScreenImage;

public class BuildAndScreenshotCoordinator {

	static ProcessBuilder pb;
	static Process p ;
		
	public static void buildAndAnalyze(String CommitID, Integer commitNumberFromLast) {
		try {
			Configurations.getInstance().plantFilesForBuild(commitNumberFromLast);
			
			Builder b = new Builder();
			b.runAntScripts(Configurations.getInstance().antLocation);
					
			TimeUnit.SECONDS.sleep(5);
			openBuiltSystem(Configurations.getInstance().jarLocation);
			
			TimeUnit.SECONDS.sleep(5);
			
			moveMouseALot();
			TimeUnit.SECONDS.sleep(2);
			
			Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			Configurations.getInstance().customizedSikuliScript(screenRect, commitNumberFromLast, CommitID);
		
			closeRunningBuiltSystem();
		}catch(Exception e) {System.out.println(e);}
		
	}
	
	public static void screenShot(Rectangle screenRect, Integer commitNumber, String commitID, String scriptStep) {
		try {
		File f = new File(Configurations.getInstance().imageSavePath + scriptStep);
		if(!f.exists())
			f.mkdir();
		
		Screen s = new Screen();
		Region	r = new Region(1,0,1919,1017);
		
		ScreenImage image = s.capture(r);
		image.save(Configurations.getInstance().imageSavePath + scriptStep, "version_" + commitNumber +"_" + commitID + ".png");
				
		
		}catch(Exception e) {System.out.println(e);}
	}
	
	/**
	 * Helper Method to move the mouse, so that random UI effects disappear
	 **/
	public static void moveMouseALot() {
		Region r = new Region(100,100, 100,100);
		r.hover();

		r = new Region(400,400, 100,100);
		r.hover();
		
	}
	
	/**
	 * clean-up to prevent overloading the system
	 */
	public static void closeRunningBuiltSystem() {
		if(p!=null) {
			p.destroy();
		}
	}
	
	/**
	 * Methods that starts a system programmatically
	 * @param url link to the executable jar file
	 */
	public static void openBuiltSystem(String url){
		
		pb = new ProcessBuilder("java", "-jar", Configurations.getInstance().buildName + ".jar");
		
		pb.directory(new File(Configurations.getInstance().projectPath));
		try {
			p = pb.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}



}
