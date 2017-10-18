/***************************************************
 * Copyright (c) 2017; MIT license
 * @author Regina Hebig; github id: rhebig
 * https://github.com/rhebig/UI-Tracer 
 ***************************************************/

import java.awt.Rectangle;

public class Configurations {
	public String REMOTE_URL = "";
    String basicpath = "";
	String projectPath = basicpath + "";
	String buildName ="";
	String imageSavePath = basicpath + "";
	String imagesInputPath = basicpath +  "";
	String antLocation = projectPath + "" + ".xml";
	String jarLocation = projectPath + "" + ".jar";
	
	public static Configurations getInstance() {
			return new ConfigurationsAOI();
	}
	
	
	public void plantFilesForBuild(Integer buildCount) {}


	public void customizedSikuliScript(Rectangle screenRect, Integer commitNumber, String commitID) {}
}
