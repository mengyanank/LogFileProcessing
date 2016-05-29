package log.process.writer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import log.process.LineReader;
import log.process.MidData;
import log.process.NumberWritter;
import log.process.ReaderWritterThread;
import log.process.Termination;

public class ParallelWritter extends Writter{

	private Thread []threads;
	//private ReaderWritterThread []rwThreads;
	
	public ParallelWritter(int threadCount){
		threads=new Thread[threadCount];
		for(int i=0;i<threadCount;++i)
			threads[i]= new Thread();
		/*rwThreads=new ReaderWritterThread[threadCount];
		for(int i=0;i<threadCount;++i)
			rwThreads[i]= new ReaderWritterThread();*/
		//this.executor = Executors.newFixedThreadPool(threadCount);
	}
	@Override
	public void write(String sourceDir, String distDir, ArrayList<String> logFiles, int threadCount) {
		// TODO Auto-generated method stub
		long base=1;
		for(int i=0;i<logFiles.size();i+=threadCount)
		{
			int upper=Math.min(i+threadCount-1, logFiles.size()-1);
			int activeThreadCount=upper-i+1;
			
			long []lineCounts=new long[activeThreadCount];
			ArrayList<String> []contents=(ArrayList<String>[]) new ArrayList[activeThreadCount];
			for(int k=0;k<contents.length;++k)
				contents[k]=new ArrayList<String>();
			//MidData midData=new MidData(activeThreadCount);
			//readFiles(sourceDir,logFiles,lineCounts,contents,i,upper);
			
			/*for(int j=i; j<=upper; ++j)
			{
				rwThreads[j-i]=new ReaderWritterThread(midData,sourceDir,distDir,logFiles,i,j);
			}
			for(int j=0;j<activeThreadCount;++j)
			{
				rwThreads[j].setIndexes(i,i+j);
				rwThreads[j].start();
			}
			while(true)
			{
				if(midData.count==activeThreadCount)
				{
					break;
				}
			}
			base = midData.calPrefix(base);
			midData.wake();*/
			
			for(int j=i;j<=upper;++j){
				threads[j-i]=new Thread(new LineReader(sourceDir,logFiles,j,i,lineCounts,contents));
			}
			for(int j=0;j<activeThreadCount;++j)
			{
				threads[j].start();
			}
			for(int k=0;k<activeThreadCount;++k)
			{
				try {
					threads[k].join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					Termination.terminate(e.getMessage(), -1);
				}
			}
			
			base=calPrefix(base,lineCounts);
			
			for(int j=i;j<=upper;++j)
			{
				threads[j-i]=new Thread(new NumberWritter(distDir,logFiles,j,i,lineCounts,contents));
			}
			for(int j=0;j<activeThreadCount;++j)
			{
				threads[j].start();
			}
			for(int k=0;k<activeThreadCount;++k)
			{
				try {
					threads[k].join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					Termination.terminate(e.getMessage(), -1);
				}
			}
		}
		
	}
	private void readFiles(String sourceDir, ArrayList<String> logFiles,long []lineCounts, ArrayList<String> []contents,int start, int end){
		for(int i=start;i<=end;++i)
		{
			int count=0;
			
			try{
			String fileName = sourceDir+"\\"+logFiles.get(i);
			FileReader fileReader = null;
			fileReader = new FileReader(fileName);
	        BufferedReader bufferedReader = new BufferedReader(fileReader);
	        String line;
			while((line = bufferedReader.readLine()) != null) {
				    
					contents[i-start].add(line);
				    ++count;
				} 
			}
			 catch(FileNotFoundException ex) {
				 Termination.terminate("file not found", -1);
		     }
		     catch(IOException ex) {
		    	 Termination.terminate("Error reading file", -1);
		     }
			lineCounts[i-start]=count;
		}
	}
	
	 private long calPrefix(long base, long []lineCounts){
		 for(int j=0;j<lineCounts.length;++j)
			{
				long temp = lineCounts[j];
				lineCounts[j]=base;
				base+=temp;
			}
		 return base;
	 }
}
