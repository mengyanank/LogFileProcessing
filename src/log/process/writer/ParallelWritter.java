package log.process.writer;

import java.util.ArrayList;

import log.process.LineReader;
import log.process.NumberWritter;
import log.process.Termination;

public class ParallelWritter extends Writter{

	@Override
	public void write(String sourceDir, String distDir, ArrayList<String> logFiles, int threadCount) {
		// TODO Auto-generated method stub
		int base=1;
		for(int i=0;i<logFiles.size();i+=threadCount)
		{
			int upper=Math.min(i+threadCount-1, logFiles.size()-1);
			ArrayList<Thread> threads=new ArrayList<Thread>();
			long []lineCounts=new long[upper-i+1];
			ArrayList<String> []contents=(ArrayList<String>[]) new ArrayList[upper-i+1];
			for(int k=0;k<contents.length;++k)
				contents[k]=new ArrayList<String>();
			for(int j=i;j<=upper;++j)
			{
				threads.add(new Thread(new LineReader(sourceDir,logFiles,j,i,lineCounts,contents)));
			}
			for(int j=0;j<threads.size();++j)
			{
				threads.get(j).start();
			}
			for(int k=0;k<threads.size();++k)
			{
				try {
					threads.get(k).join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					Termination.terminate(e.getMessage(), -1);
				}
			}
			//long accu=base;
			/*for(long a:lineCounts)
				System.out.println(a);*/
			for(int j=0;j<lineCounts.length;++j)
			{
				long temp = lineCounts[j];
				lineCounts[j]=base;
				base+=temp;
			}
			/*for(long a:lineCounts)
				System.out.println(a);*/
			
			threads.clear();
			for(int j=i;j<=upper;++j)
			{
				threads.add(new Thread(new NumberWritter(distDir,logFiles,j,i,lineCounts,contents)));
			}
			for(int j=0;j<threads.size();++j)
			{
				threads.get(j).start();
			}
			for(int k=0;k<threads.size();++k)
			{
				try {
					threads.get(k).join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					Termination.terminate(e.getMessage(), -1);
				}
			}
		}
	}


}
