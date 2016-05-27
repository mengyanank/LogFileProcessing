package log.process.writer;

public class WritterFactory {

	public static Writter getWritter(boolean parallel){
		if(parallel)
			return new ParallelWritter();
		else
			return new SequentialWritter();
		
	}
}
