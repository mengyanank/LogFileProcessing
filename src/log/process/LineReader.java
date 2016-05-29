package log.process;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LineReader implements Runnable{

	private int fileIndex;
	private int baseIndex;
	private String sourceDir;
	private ArrayList<String> logFiles;
	long []lineCounts;
	ArrayList<String> []contents;
	
	public LineReader(String sourceDir, ArrayList<String> logFiles, int index, int baseIndex, long []lineCounts, ArrayList<String> []contents)
	{
		this.sourceDir=sourceDir;
		this.logFiles=logFiles;
		this.fileIndex=index;
		this.baseIndex=baseIndex;
		this.lineCounts=lineCounts;
		this.contents=contents;
	}
	
	public void run(){
		
		int count=0;	
		try{
			String fileName = sourceDir+"\\"+logFiles.get(fileIndex);
			FileReader fileReader = null;
			fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while((line = bufferedReader.readLine()) != null) {		    
				contents[fileIndex-baseIndex].add(line);
			    ++count;
			} 
		}
		 catch(FileNotFoundException ex) {
			 Termination.terminate(ex.getMessage(),-1);
	     }
	     catch(IOException ex) {
	    	 Termination.terminate(ex.getMessage(),-1);
	     }
		lineCounts[fileIndex-baseIndex]=count;
	}
	
}
