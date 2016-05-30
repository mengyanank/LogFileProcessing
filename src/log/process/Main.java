package log.process;

/*----------------------------------------------------------------
 *  Author: Yan Meng
 *----------------------------------------------------------------*/

public class Main {

	/*----------------------------------------------------------------
	 * This is the entry of the program.
	 * There should be three program arguments.
	 * The first argument is the input folder name.
	 * The second argument is the output folder name.
	 * The third argument is the number of threads.
	 *----------------------------------------------------------------*/
	public static void main(String []args){

		if(args.length!=3)
			Termination.terminate("number of arguments is not equal to 3", 1);
		LogProcess lp=new LogProcess(args);
		lp.excute();
	}
}
