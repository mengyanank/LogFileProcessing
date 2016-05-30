package log.process;

/*----------------------------------------------------------------
 *  Author: Yan Meng
 *----------------------------------------------------------------*/
public class Termination {

	public static void terminate(String error,int n){
		System.out.println(error);
		System.exit(n);
	}
}
