package log.process.writer;

import java.util.ArrayList;

public abstract class Writter {

	public abstract void write(String sourceDir, String distDir, ArrayList<String> logFiles, int threads);
}
