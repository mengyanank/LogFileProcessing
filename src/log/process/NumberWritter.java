package log.process;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class NumberWritter implements Runnable{

	private int fileIndex;
	private int baseIndex;
	private String distDir;
	private ArrayList<String> logFiles;
	long []lineCounts;
	ArrayList<String> []contents;
	
	public NumberWritter(String distDir, ArrayList<String> logFiles, int index, int baseIndex, long []lineCounts, ArrayList<String> []contents){
		this.distDir=distDir;
		this.fileIndex=index;
		this.baseIndex=baseIndex;
		this.logFiles=logFiles;
		this.lineCounts=lineCounts;
		this.contents=contents;
	}
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
