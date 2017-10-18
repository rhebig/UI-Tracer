/***************************************************
 * Copyright (c) 2017; MIT license
 * @author Regina Hebig; github id: rhebig
 * https://github.com/rhebig/UI-Tracer 
 ***************************************************/

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.util.io.DisabledOutputStream;

import com.sun.tools.javac.util.Pair;

public class CodeDiffTracer {

	static String commitID = "654367a304fb1c6e4964220e54258130b9814691";
	static List<String> AllCommitsSorted = new ArrayList<String>();
	static List<Map<String,String>> pairsDiffCommits; 
	static List<Pair<String,String>> listOfLists;
	
	
	/**
	 * Method to identify code diffs for a pair of commits
	 */
	public static List<Pair<String,String>> traceForCommitPairToCodeDiff(Git git, String olderCommit, String youngerCommit) {
		listOfLists = new ArrayList<Pair<String,String>>();
		AllCommitsSorted  = CloneRemoteRepository.iterateCommits(git);
		
		try {
		Boolean foundyoungest = false;
		Boolean foundoldest = false;
		List<String> relevantCommits = new ArrayList<String>();
		Iterator<String> it = AllCommitsSorted.iterator();
		while(it.hasNext()) {
			String id = it.next();
			if(id.equals(olderCommit)) {
				foundoldest = true;
				System.out.println("foundoldest " );
			}
			if(foundyoungest && !foundoldest) {
				relevantCommits.add(id);
			}
			if(id.equals(youngerCommit)) {
				foundyoungest= true;
				relevantCommits.add(id);
				System.out.println("foundyoungest " );
				}
			
		}
		System.out.println(relevantCommits);
		
		
		Iterator<String> comit = relevantCommits.iterator();
		while(comit.hasNext()) {
			String com = comit.next();
			listOfLists.addAll(getFilesModifiedInCommit(git, com));
		}
		
		System.out.println(listOfLists);
		

		removeDoubles() ;
		
		}catch(Exception e) {System.out.println(e);}
		return listOfLists;
	}
	
	
	public static void removeDoubles() {
		List<Pair<String,String>> newlists = new ArrayList<Pair<String,String>>();
		
		for(int i =0;i<listOfLists.size();i++) {
			Pair<String,String> h = listOfLists.get(i);
			boolean found = false;
			for(int j=0;j<newlists.size();j++) {
				Pair<String,String> h2 = newlists.get(j);
				if(h.snd.equals(h2.snd))
					found = true;
			}
			if(!found)
				newlists.add(h);
		}
		
		
		listOfLists = newlists;
	}
	
	
	/**
	 * Method collects files modified in a commit,
	 * returns Pair<String,String> where the first string defines the type of modification and the second links the file
	 */
	public static List<Pair<String,String>> getFilesModifiedInCommit(Git git, String commitID)
	{
		List<Pair<String,String>> result = new ArrayList<Pair<String,String>>(); // = new Pair<String,String>(commitID, commitID);
		try {
			Iterable<RevCommit> commits = git.log().all().call();
	        for (RevCommit commit : commits) {
	        	if(commit.getName().equals(commitID)) {
	        		System.out.println(commit);
	        		System.out.println(commit.getFullMessage());
	        		System.out.println(commit.getRawBuffer());
	        			
	        		RevWalk rw = new RevWalk(git.log().getRepository());
	        		RevCommit parent = rw.parseCommit(commit.getParent(0).getId());
	        		DiffFormatter df = new DiffFormatter(DisabledOutputStream.INSTANCE);
	        		df.setRepository(git.log().getRepository());
	        		df.setDiffComparator(RawTextComparator.DEFAULT);
	        		df.setDetectRenames(true);
	        		List<DiffEntry> diffs = df.scan(parent.getTree(), commit.getTree());
	        		for (DiffEntry diff : diffs) {
	        		    System.out.println(MessageFormat.format("({0} {1} {2}", diff.getChangeType().name(), diff.getNewMode().getBits(), diff.getNewPath()));
	        		    result.add(new Pair<String,String>(diff.getChangeType().name() + "  " + diff.getNewMode().getBits(), diff.getNewPath()));
	        		}
	        		
	        	}
	        }
		}catch(Exception e) {System.out.println("Exception"); System.out.println(e);}
       
		return result;
	}
	
		
	
}
