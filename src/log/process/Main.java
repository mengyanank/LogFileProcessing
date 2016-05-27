package log.process;

public class Main {

	public static void main(String []args){
		System.out.println(args.length);
		if(args.length!=3)
			Termination.terminate("args != 3", 1);
		LogProcess lp=new LogProcess(args);
		lp.excute();
	}
}
