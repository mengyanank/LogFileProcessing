package log.process.writer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.rmi.CORBA.Util;

public class SequentialWritter extends Writter{
	@Override
	public void write(String sourceDir, String distDir, ArrayList<String> logFiles, int threads) 
	{
		// TODO Auto-generated method stub
		//int lineCount = 1;
		try{
			int count=1;
	        for (final String file : logFiles) {
	            
	            FileReader fileReader = null;
				fileReader = new FileReader(sourceDir+"\\"+file);
	            BufferedReader bufferedReader = new BufferedReader(fileReader);
	            
	            File fout = new File(distDir+"\\"+file);
	    		FileOutputStream fos = null;
				fos = new FileOutputStream(fout);
	    	 
	    		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
	            
	    		String line;
	    		
				while((line = bufferedReader.readLine()) != null) {
						String str=count+". "+line;
						bw.write(str);
						bw.newLine();
						++count;
				}
				bw.close();
	        }
		}		
       catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
