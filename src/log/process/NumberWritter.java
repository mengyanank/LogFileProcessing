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
		String path=distDir;
		File fout = new File(path+"\\"+logFiles.get(fileIndex));
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(fout);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		try{
			long number=lineCounts[fileIndex-baseIndex];
			for (int i = 0; i < contents[fileIndex-baseIndex].size(); i++) {
				String str= number+". "+contents[fileIndex-baseIndex].get(i);
				bw.write(str);
				bw.newLine();
				++number;
			}
			bw.close();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}  
