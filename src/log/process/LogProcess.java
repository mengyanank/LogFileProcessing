package log.process;

import java.io.File;
import java.util.ArrayList;

public class LogProcess {

	private ArrayList<String> logFiles;
	private String sourceDir;
	private String distDir;
	private int threads;
	
	public LogProcess(String []args){
		logFiles=new ArrayList<String>();
		sourceDir=args[0];
		distDir=args[1];
		threads=Integer.parseInt(args[2]);	
	}
	
	public void excute(){
		validateArgs(sourceDir,distDir,threads);
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
