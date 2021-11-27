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
		valor=factIterativo(num);
		t2 = System.currentTimeMillis();
		System.out.println("El factorial iterativo de " + num + " es: " + valor);
		System.out.println("Tiempo de ejecución: " + (t2-t1) + " milisegundos.");
		/*
		t1 = System.currentTimeMillis();
		valor=factRecursivo(num);
		t2 = System.currentTimeMillis();
		System.out.println("El factorial recursivo de " + num + " es: " + valor);
		System.out.println("Tiempo de ejecución: " + (t2-t1) + " milisegundos.");
		*/
		
        /*
        t1 = System.currentTimeMillis();
		valorBig=factIterativoBig(num);
		t2 = System.currentTimeMillis();
		System.out.println("El factorial iterativo BIG de " + num + " es: ");
		System.out.println(valorBig.toString());
		System.out.println("Tiempo de ejecución: " + (t2-t1) + " milisegundos.");
		*/

        
		t1 = System.currentTimeMillis();
        try{
            valorBig=factRecursivoBig(BigInteger.valueOf(num));
        }catch(Error e){
            System.err.println("caca");
        }
		
		t2 = System.currentTimeMillis();
		System.out.println("El factorial recursivo BIG de " + num + " es: " + valorBig);
		System.out.println("Tiempo de ejecución: " + (t2-t1) + " milisegundos.");
		
		
		sc.close();
	}
	public static BigInteger factIterativoBig(int num) {
		BigInteger valor = BigInteger.ONE;
		for(int i=num;i>0;i--) {
			valor=valor.multiply(BigInteger.valueOf(i));
		}
		return valor;
	}
	public static long factIterativo(int n) {
		long valor;
		if(n>1) {
			valor=n;
			for(int i=(int)n-1;i>0;i--)
					valor*=i;
			return valor;
		}else if(n>=0) {
			return 1;
		}
		return -1;
	}
	public static BigInteger factRecursivoBig(BigInteger n) {
        if(n.equals(BigInteger.ONE)||n.equals(BigInteger.ZERO)) {
            return BigInteger.ONE;
        }
        return n.multiply(factRecursivoBig(n.subtract(BigInteger.ONE))); 
	}
	public static long factRecursivo(int n) {
		if(n<0) {
			return -1;
		}else if(n==1||n==0) {
			return 1;
		}
		return n*factRecursivo(n-1);
		
	}
	public static long factThreads(int n) {
		Runnable r = new Comportamiento(n);
		Thread t = new Thread(r);
		t.start();
		
		return -1;			
	}
}
class Comportamiento implements Runnable{
	int num;
	Comportamiento(int num){
		this.num=num;
	}
	public void run() {
		//TODO: calcular factorial
	}
}
