/***************************************************
 * Copyright (c) 2017; MIT license
 * @author Regina Hebig; github id: rhebig
 * https://github.com/rhebig/UI-Tracer 
 ***************************************************/

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;

public class CloneRemoteRepository {

    static Git git;
	static List<String> commitIDs = new ArrayList<String>();
	
    public static void main(String[] args) throws IOException, GitAPIException {  

    	long startTime = System.currentTimeMillis();

        run();
    	
        long stopTime = System.currentTimeMillis();
        
        log("System under study: " + Configurations.getInstance().getClass(), Configurations.getInstance().basicpath + "TimeMeasures");
        log("start time: " + startTime, Configurations.getInstance().basicpath + "TimeMeasures");
        log("end time: " + stopTime, Configurations.getInstance().basicpath + "TimeMeasures");
        log("elapsed time: " + (stopTime - startTime), Configurations.getInstance().basicpath + "TimeMeasures");
        
    }
    
    public static void run() {
    	try {
	        try {
	        	CloneCommand cc = Git.cloneRepository()
		                .setURI(Configurations.getInstance().REMOTE_URL)
		                .setDirectory(new File(Configurations.getInstance().projectPath));
	        	git = cc.call();
	       
		     }catch(Exception e) {System.out.println(e);}
	        
        }catch(Exception e) {System.out.println(e);}

        commitIDs = iterateCommits(git);
         
        for(int i=417; i<420; i++)
        {
        	checkoutAndTriggerBuildAndAnalysis(commitIDs.get(i), i);
        }
         
        ComparingScreens.run();
        
    }
   
    public static void log(String message, String filename) {
		try {
			PrintWriter out = new PrintWriter(new FileWriter(filename, true), true);
			out.println(message);
			out.close();
		}catch(Exception e) {System.out.println(e);}
	}
    
    /**
	 * Method retrieves list of all commits
	 */
	public static List<String>  iterateCommits(Git git){
		List<String> commitIDs = new ArrayList<String>();
		try{
		        Iterable<RevCommit> commits = git.log().all().call();
	            int count = 0;
	            for (RevCommit commit : commits) {
	                commitIDs.add(commit.getName());
	                count++;
	            }
	    }catch(Exception e) {System.out.println(e);}
		return commitIDs;
	}
    
    /**
     * Method to checkout and trigger analysis (build, run and screenshot) a version at a specific commit
     * @param id commit id (name)
     * @param number counter of commit (note the current version is 0)
     **/
    public static void checkoutAndTriggerBuildAndAnalysis(String id, Integer number) {
    	try {	
    		git.checkout().setName( id).call();
    		BuildAndScreenshotCoordinator.buildAndAnalyze(id, number);
    	}catch(Exception e) {System.out.println(e);}
    }
    
}