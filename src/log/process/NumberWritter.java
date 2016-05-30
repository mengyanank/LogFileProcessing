package log.process;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/*----------------------------------------------------------------
 *  Author: Yan Meng
 *----------------------------------------------------------------*/
public class NumberWritter implements Runnable{

	// the file index in "logFiles"
	private int fileIndex;
	
	// the index of the first file in current loop
	private int baseIndex;
	
	// write directory
	private String distDir;
	
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
     * file. Therefore, the threads are working independently, and writing different files.
     *
     * @param distDir: String, write directory
     * @param logFiles: ArrayList<String>, list of log files
     * @param index: int, the file index in "logFiles"
     * @param baseIndex: int, the index of the first file in current loop
     * @param lineCounts: long [], array of line counts
     * @param contents: ArrayList<String> [], array of file contents
     */
	public NumberWritter(String distDir, ArrayList<String> logFiles, int index, int baseIndex, long []lineCounts, ArrayList<String> []contents){
		this.distDir=distDir;
		this.fileIndex=index;
		this.baseIndex=baseIndex;
		this.logFiles=logFiles;
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
		try{
			File fout = new File(distDir+"\\"+logFiles.get(fileIndex));
			FileOutputStream fos = new FileOutputStream(fout);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			long number=lineCounts[fileIndex-baseIndex];
			for (int i = 0; i < contents[fileIndex-baseIndex].size(); i++) {
				String str= number+". "+contents[fileIndex-baseIndex].get(i);
				bw.write(str);
				bw.newLine();
				++number;
			}
			bw.close();
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			Termination.terminate(e.getMessage(),-1);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			Termination.terminate(e.getMessage(),-1);
		}
		
	}
}  
