package log.process.writer;

public class WritterFactory {

	/**
     * This function creates the correct writter subclass.
     * 
     * @param parallel: boolean, create a parallel writter subclass if true
     * @param threads: int, number of threads when parallel version is created
     */
	public static Writter getWritter(boolean parallel,int threads){
		if(parallel)
			return new ParallelWritter(threads);
		else
			return new SequentialWritter();
		
	}
}
