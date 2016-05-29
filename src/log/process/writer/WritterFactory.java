package log.process.writer;

public class WritterFactory {

	public static Writter getWritter(boolean parallel,int threads){
		if(parallel)
			return new ParallelWritter(threads);
		else
			return new SequentialWritter();
		
	}
}
