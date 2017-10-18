/**
 * @author Anton Rose Gregory; github id: antongregory 
 */

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.BuildLogger;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;


public class Builder {

	
	public void runAntScripts(String filename) throws BuildException{
		BuildLogger logger = getConsoleLogger();
	    Project metricsProject  = new Project();
	    metricsProject.addBuildListener(logger);
	    
	    ProjectHelper helper = ProjectHelper.getProjectHelper();	
	    
	    metricsProject.addReference("ant.projectHelper", helper);
	    
	    File buildFile=new File(filename);
	    	

	    File folder=new File(buildFile.getParent());
	       
	    	buildFile = new File(filename);
	        
	        if(buildFile.isFile()){
	            helper.parse(metricsProject, buildFile);
	            
	            metricsProject.setProperty("ant.file", buildFile.getAbsolutePath());      

	            metricsProject.init();
	            metricsProject.setBaseDir(folder);
	            
	            metricsProject.executeTarget(metricsProject.getDefaultTarget());
	            
	            metricsProject.fireBuildFinished(null);
	            
	        }
	    
	}
	
    private static BuildLogger getConsoleLogger() {
    	BuildLogger consoleLogger = new DefaultLogger();
        consoleLogger.setErrorPrintStream(System.err);
        consoleLogger.setOutputPrintStream(System.out);
        consoleLogger.setMessageOutputLevel(Project.MSG_INFO);
        
        return consoleLogger;
    }
}
