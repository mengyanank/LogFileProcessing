package log.process;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LineReader implements Runnable{

	// the file index in "logFiles"
	private int fileIndex;
	
	// the index of the first file in current loop
	private int baseIndex;
	
	// read directory
	private String sourceDir;
	
	// list of log files
	private ArrayList<String> logFiles;
	
	// array of line counts
	long []lineCounts;
	
	// array of file contents
	ArrayList<String> []contents;
	
	/**
     * Each thread will get an instance of this class. 
     * The log files, and the memory which stores number of lines and file contents are shared
     * by all threads. but each thread has its one index parameter which leads it to a corresponding
     * file. Therefore, the threads are working independently and reading different files.
     *
     * @param sourceDir: String, read directory
     * @param logFiles: ArrayList<String>, list of log files
     * @param index: int, the file index in "logFiles"
     * @param baseIndex: int, the index of the first file in current loop
     * @param lineCounts: long [], array of line counts
     * @param contents: ArrayList<String> [], array of file contents
     */
	public LineReader(String sourceDir, ArrayList<String> logFiles, int index, int baseIndex, long []lineCounts, ArrayList<String> []contents)
	{
		this.sourceDir=sourceDir;
		this.logFiles=logFiles;
		this.fileIndex=index;
		this.baseIndex=baseIndex;
		this.lineCounts=lineCounts;
		this.contents=contents;
	}
	
	/**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting that thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     */
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
