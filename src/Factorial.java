import java.math.BigInteger;
import java.util.Scanner; 

public class Factorial {
	public static void main(String[] args) {
		int num;
		long t1,t2,valor;
		BigInteger valorBig=BigInteger.ZERO;
		Scanner sc = new Scanner(System.in);
		System.out.println("Ingrese el número a calcular su factorial:");
		num = sc.nextInt();
        
        t1 = System.currentTimeMillis();
		valorBig=factIterativoBig(num);
		t2 = System.currentTimeMillis();
		System.out.println("\nEl factorial iterativo BIG de " + num + " es: " + valorBig); 
		System.out.println("Tiempo de ejecución método iterativo: " + (t2-t1) + " milisegundos.\n");
        
		t1 = System.currentTimeMillis();
        try{
            valorBig=factRecursivoBig(BigInteger.valueOf(num));
			t2 = System.currentTimeMillis();
			System.out.println("El factorial recursivo BIG de " + num + " es: " + valorBig);
			System.out.println("Tiempo de ejecución método recursivo: " + (t2-t1) + " milisegundos.\n");
        }catch(StackOverflowError e){
            System.err.println("\nLa recursión se llama demasiadas veces, no es posible calcular el valor del factorial recursivamente.");
		}
		
		sc.close();
	}
	public static BigInteger factIterativoBig(int num) {
		if(num<0){
			return BigInteger.valueOf(-1);
		}
		BigInteger valor = BigInteger.ONE;
		for(int i=num;i>0;i--) {
			valor=valor.multiply(BigInteger.valueOf(i));
		}
		return valor;
	} 

	public static BigInteger factRecursivoBig(BigInteger n) {
		if(n.compareTo(BigInteger.ZERO)<0){
			return BigInteger.valueOf(-1);
		}
        if(n.equals(BigInteger.ONE)||n.equals(BigInteger.ZERO)) {
            return BigInteger.ONE;
        }
        return n.multiply(factRecursivoBig(n.subtract(BigInteger.ONE))); 
	} 

	public static long factThreads(int n) {
		Runnable r = new FactorialThread(n);
		Thread t = new Thread(r);
		t.start();
		
		return -1;			
	}
}
class FactorialThread implements Runnable{
	int num;
	FactorialThread(int num){
		this.num=num;
	}
	public void run() {
		//TODO: calcular factorial
	}
}
