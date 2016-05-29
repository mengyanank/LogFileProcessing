package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import log.process.LogProcess;

public class ParallelTest {

	private static String[] doFilesMatch(String sourceDir, String distDir, ArrayList<String> logFiles)
            throws IOException {
        String[] retVal = new String[2];

        long count=1;
        for (final String file : logFiles) {
            
            FileReader fileReader1 = null;
			fileReader1 = new FileReader(sourceDir+"\\"+file);

            BufferedReader bufferedReader1 = new BufferedReader(fileReader1);
            
            FileReader fileReader2 = null;
			fileReader2 = new FileReader(sourceDir+"\\"+file);
            BufferedReader bufferedReader2 = new BufferedReader(fileReader2);
            
    		String line1,line2;
    		
			while(((line1 = bufferedReader1.readLine()) != null)&&((line2 = bufferedReader2.readLine()) != null)) {
				String str=count+". "+line1;
				if(!str.equals(line2))
				{
					retVal[0]=file;
					retVal[1]=""+count;
					return retVal;
				}
				++count;
			}
        }

        return retVal;
    }

    @Test
    public void testExecute() throws Exception {
        final String[] args = {"D:\\logs\\emr","D:\\log_output","3"};
        LogProcess lp=new LogProcess(args);
		lp.excute();
		ArrayList <String> logFiles=lp.getLogFiles();
		String s=lp.getSourceDir();
		String d=lp.getDistDir();
		String []ret=doFilesMatch(s,d,logFiles);
        Assert.assertTrue("Difference found in file " + ret[0] + " at line " + ret[1] + ".", ret[0] == null);
    }
}
