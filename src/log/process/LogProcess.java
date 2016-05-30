package log.process;

import java.io.File;
import java.util.ArrayList;

import log.process.writer.Writter;
import log.process.writer.WritterFactory;

/*----------------------------------------------------------------
 *  Author: Yan Meng
 *----------------------------------------------------------------*/

public class LogProcess {

	/*-----------------------------------------
	*this class parses the arguments, 
	*create the object which process the logs,
	*and run the file processing procedure
	*--------------------------------------------------*/
	
	//a list which stores all the log file names
	private ArrayList<String> logFiles; 
	
	//the input folder which contains all the log files
	private String sourceDir;
	
	//the output folder where the output files are written
	private String distDir;
	
	// number of threads
	private int threads;
	
	// whether to use the parallel solution
	boolean parallel;
	
	public ArrayList<String> getLogFiles(){
		return this.logFiles;
	}
	
	public String getSourceDir(){
		return this.sourceDir;
	}
	
	public String getDistDir(){
		return this.distDir;
	}
	
	public LogProcess(String []args){
		logFiles=new ArrayList<String>();
		sourceDir=args[0];
		distDir=args[1];
		threads=Integer.parseInt(args[2]);
		parallel=true;
	}
	
	public void excute(){
		validateArgs(sourceDir,distDir,threads);
		addLogFiles(logFiles,sourceDir);
		
		if(logFiles.size()<1)
			Termination.terminate("no files to process",0);
		
		/* if the user configures one thread, it means
		 the sequential solution is used */
		if(threads==1)
			parallel=false;
		
		Writter writter=WritterFactory.getWritter(parallel,threads);
		
		writter.write(sourceDir,distDir,logFiles,threads);
	}
	
	//this function find all the files in the input folder, and store the file names in memory
	private void addLogFiles(ArrayList<String> logFiles,String source){
		final File folder=new File(source);
		for (final File fileEntry : folder.listFiles()) {
	        if (!fileEntry.isDirectory()) 
	        {
	        	//System.out.println(fileEntry.getName());
	        	logFiles.add(fileEntry.getName());
	        }
	    }
	}
	
	// this function validates the input folder, ouput folder and thread number
	private void validateArgs(String s,String d,int n){
		if(n<1){
			String error="number of threads is less than 1";
			Termination.terminate(error,1);
		}
		
		File f1=new File(s);
		if(!f1.isDirectory()||!f1.canRead()){
			String error="Input directory is invalid";
			Termination.terminate(error,-1);
		}
		
		File f2=new File(d);
		if(!f2.isDirectory()||!f2.canWrite()){
			String error="Output directory is invalid";
			Termination.terminate(error,-1);
		}
	}
}
