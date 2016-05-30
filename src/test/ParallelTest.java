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

	/**
     * This function does the bulk of the testing and it does it
     * the way one would expect it to. It runs the function that
     * does what the program is suppose to do. After it finishes
     * running, I go through the read directory and the write
     * directory and make sure that contents in the files in write directory
     * match the contents in the files in the read files. That,
     * for each line in a file in the read directory, can I
     * perform operations that will produce the corresponding
     * line the written file?
     * 
     * If not, I will break the test and return the information of the first
     * incorrect output file found in the test.
     * 
     * If this checks out for all lines and all files, then the
     * test passes.
     *
     * The way the return value works is as follows. If null is
     * returned, then that means everything checked out and the
     * files match. Anything else means there was a mistake. The
     * array passed back will have two elements. One for the first file
     * where the error occurred which is found in the test process and 
     * the second element is the line number in which it occurred.
     *
     * @param sourceDir: String, read directory
     * @param distDir: String, write directory
     * @param logFiles, list of log files
     * @return String[] do files match? return null if yes; anything else for no
     *
     * @throws IOException
     */
	private static String[] doFilesMatch(String sourceDir, String distDir, ArrayList<String> logFiles)
            throws IOException {
        String[] retVal = new String[2];

        long count=1;
        for (final String file : logFiles) {
            
            FileReader fileReader1 = null;
			fileReader1 = new FileReader(sourceDir+"\\"+file);

            BufferedReader bufferedReader1 = new BufferedReader(fileReader1);
            
            FileReader fileReader2 = null;
			fileReader2 = new FileReader(distDir+"\\"+file);
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
