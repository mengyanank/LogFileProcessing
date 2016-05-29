package log.process;

import java.util.ArrayList;

public class MidData {

	public long []lineCounts;
	public ArrayList<String> []contents;
	public volatile int count;
	
	public MidData(int len){
		this.count=0;
		lineCounts=new long[len];
		contents=(ArrayList<String>[]) new ArrayList[len];
		for(int k=0;k<contents.length;++k)
			contents[k]=new ArrayList<String>();
	}
	
	 public synchronized void rest(){
		 try {
			this.count++;
			//System.out.println("read finished "+count);
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Termination.terminate("cannot release lock", 1);
		}
	 }
	 
	 public synchronized void wake(){
		 //count=0;
		 //System.out.println("wake");
		 notifyAll();
	 }
	 
	 public synchronized int calPrefix(int base){
		 for(int j=0;j<lineCounts.length;++j)
			{
				long temp = lineCounts[j];
				lineCounts[j]=base;
				base+=temp;
			}
		 return base;
	 }
}
