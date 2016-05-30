package log.process.writer;

import java.util.ArrayList;
import log.process.LineReader;
import log.process.NumberWritter;
import log.process.Termination;

/**
 * @author Yan Meng
 */

public class ParallelWritter extends Writter{

	private Thread []threads;
	
	public ParallelWritter(int threadCount){
		threads=new Thread[threadCount];
		for(int i=0;i<threadCount;++i)
			threads[i]= new Thread();
	}
	
	/**
     * This function implements the parallel processing on the log files.
     * Each thread is processing one file each time. So the whole process
     * is a number of loops. In each loop, the threads are assigned with files.
     * The loop does not terminate until all the log files are processed.
     *  
     * This procedure in each loop consists of three parts.
     * 
     * The first step: count the number of lines of each file while reading the
     * file contents. 
     * The file contents are stored in memory. Since there is only 
     * 100MB memory, one thread cannot process too many files, otherwise the file contents
     * stored in memory will be larger than 100MB. That's why I assign only one file
     * to each thread.
     * The number of lines of each file is stored in memory, too.
     * 
     * The second step: calculate the start line number of each file, based on
     * the number of lines of each file.
     * 
     * The third step: Write the line number and file contents into the output files.
     * Each thread is responsible for one file, like the first step
     * 
     * @param parallel: boolean, create a parallel writter subclass if true
     * @param threads: int, number of threads when parallel version is created
     */
	@Override
	public void write(String sourceDir, String distDir, ArrayList<String> logFiles, int threadCount) {
		// TODO Auto-generated method stub
		long base=1;
		for(int i=0;i<logFiles.size();i+=threadCount)
		{
			int upper=Math.min(i+threadCount-1, logFiles.size()-1);
			int activeThreadCount=upper-i+1;
			
			/**
		     * @array: stores the number of lines of each file
		     * length of array = number of threads which process files
		     */
			long []lineCounts=new long[activeThreadCount];
			
			/**
		     * @array: stores contents of each file
		     * length of array = number of threads which process files
		     * 
		     * array element: a list stores file contents of one file
		     * the length of this list is the number of lines of this file
		     */
			ArrayList<String> []contents=(ArrayList<String>[]) new ArrayList[activeThreadCount];
			for(int k=0;k<contents.length;++k)
				contents[k]=new ArrayList<String>();
			
			// first step: count line number, store file contents
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
			
			// second step: calculate start line number of each file
			base=calPrefix(base,lineCounts);
			
			// third step: write output files
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
	
	 private long calPrefix(long base, long []lineCounts){
		  /*
		   * the return value is the start line number of the file
		   * to be processed in the next loop
		  */
		 for(int j=0;j<lineCounts.length;++j)
			{
				long temp = lineCounts[j];
				lineCounts[j]=base;
				base+=temp;
			}
		 return base;
	 }
}
