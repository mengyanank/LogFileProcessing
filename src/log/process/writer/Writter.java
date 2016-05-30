package log.process.writer;

import java.util.ArrayList;

public abstract class Writter {

	/**
     * This function represents the single function of the writer classes.
     * It is a way to abstract away how the writing actually occurs; that
     * is, whether the writing was done in parallel or not. Although the main
     * attraction will be the parallel solution, the serial will exist in order
     * to have a point of reference for the parallel solution.
     * 
     * The number of threads configured by user determines whether the 
     * program uses the parallel writer of sequential writer.
     *
     * There is a factory class responsible for creating the correct writer class
     * based on the input argument
     * 
     * @param sourceDir: String, directory to read log files from (already validated)
     * @param distDir: String, directory to write log files to (already validated)
     * @param threads: int, number of threads in play
     * @param logFiles: List<String>, list of log files
     */
	public abstract void write(String sourceDir, String distDir, ArrayList<String> logFiles, int threads);
}
