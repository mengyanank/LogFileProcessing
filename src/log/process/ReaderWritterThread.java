package log.process;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class ReaderWritterThread extends Thread{
	
	private int fileIndex;
	private int baseIndex;
	private String sourceDir;
	private String distDir;
	private ArrayList<String> logFiles;
	private long []lineCounts;
	private ArrayList<String> []contents;
	private boolean isRead;
	private MidData midData;
	
	public ReaderWritterThread(MidData midData, String sourceDir, String distDir, ArrayList<String> logFiles, int base, int index){
		this.midData = midData;
		this.sourceDir = sourceDir;
		this.distDir = distDir;
		this.logFiles = logFiles;
		this.baseIndex=base;
		this.fileIndex=index;
	}
	public ReaderWritterThread(){
		isRead=true;
	}
	
	public void setRead(boolean f){
		this.isRead=f;
	}
	public void setFileIndex(int x){
		this.fileIndex=x;
	}
	public void setBaseIndex(int x){
		this.baseIndex=x;
	}
	public void setSource(String str){
		this.sourceDir=str;
	}
	public void setDistination(String str){
		this.distDir=str;
	}
	public void setLogFiles(ArrayList<String> logFiles){
		this.logFiles=logFiles;
	}
	public void setLineCounts(long []lineCounts){
		this.lineCounts=lineCounts;
	}
	public void setContents(ArrayList<String> []contents){
		this.contents=contents;
	}
	
	public void setTask(long []lineCounts, ArrayList<String> []contents){
		this.contents=contents;
		this.lineCounts=lineCounts;
	}
	
	public void setFilePath(String s, String d,ArrayList<String> logFiles)
	{
		this.sourceDir=s;
		this.distDir=d;
		this.logFiles=logFiles;
	}
	
	public void setIndexes(int baseIndex, int fileIndex){
		this.baseIndex=baseIndex;
		this.fileIndex=fileIndex;
	}
	
	public void run(){
		readLine(midData);
		
		//System.out.println(midData.count);
		midData.rest();
		writeFile(midData);
	}
	
	private void readLine(MidData m){
		int count=0;
		try{
		String fileName = sourceDir+"\\"+logFiles.get(fileIndex);
		FileReader fileReader = null;
		fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
		while((line = bufferedReader.readLine()) != null) {
			    
				m.contents[fileIndex-baseIndex].add(line);
			    ++count;
			} 
		}
		 catch(FileNotFoundException ex) {
			 Termination.terminate("file not found", -1);
	     }
	     catch(IOException ex) {
	    	 Termination.terminate("Error reading file", -1);
	     }
		m.lineCounts[fileIndex-baseIndex]=count;
	}
	private void readLine(){
		int count=0;
		//System.out.println(fileIndex+": "+baseIndex);
		try{
		String fileName = sourceDir+"\\"+logFiles.get(fileIndex);
		FileReader fileReader = null;
		fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
		while((line = bufferedReader.readLine()) != null) {
			    
				contents[fileIndex-baseIndex].add(line);
			    ++count;
			} 
		}
		 catch(FileNotFoundException ex) {
			 Termination.terminate("file not found", -1);
	     }
	     catch(IOException ex) {
	    	 Termination.terminate("Error reading file", -1);
	     }
		lineCounts[fileIndex-baseIndex]=count;
	}
	
	private void writeFile(MidData m){
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
			long number=m.lineCounts[fileIndex-baseIndex];
			for (int i = 0; i < m.contents[fileIndex-baseIndex].size(); i++) {
				String str= number+". "+m.contents[fileIndex-baseIndex].get(i);
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
	private void writeFile(){
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
