package log.process.writer;

import java.util.ArrayList;

public class ParallelWritter extends Writter{

	@Override
	public void write(String sourceDir, String distDir, ArrayList<String> logFiles, int threadCount) {
		// TODO Auto-generated method stub
		for(int i=0;i<logFiles.size();i+=threadCount)
		{
			int upper=Math.min(i+threadCount-1, logFiles.size()-1);
			ArrayList<Thread> threads=new ArrayList<Thread>();
			for(int j=i;j<upper;++j)
			{
				
			}
		}
	}


}
