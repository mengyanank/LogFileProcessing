package log.process;

import java.io.File;
import java.util.ArrayList;

import log.process.writer.Writter;
import log.process.writer.WritterFactory;

public class LogProcess {

	private ArrayList<String> logFiles;
	private String sourceDir;
	private String distDir;
	private int threads;
	boolean parallel;
	
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
		if(threads==1)
			parallel=false;
		Writter writter=WritterFactory.getWritter(parallel);
		writter.write(sourceDir,distDir,logFiles,threads);
	}
	
	private void addLogFiles(ArrayList<String> logFiles,String source){
		final File folder=new File(source);
		for (final File fileEntry : folder.listFiles()) {
	        if (!fileEntry.isDirectory()) 
	        {
	        	System.out.println(fileEntry.getName());
	        	logFiles.add(fileEntry.getName());
	        }
	    }
	}
	
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
