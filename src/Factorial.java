/*
Autor: José Pedro Unda Montecinos
*/
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
		System.out.println("\nEl factorial iterativo de " + num + " es: " + valorBig); 
		System.out.println("Tiempo de ejecución método iterativo: " + (t2-t1) + " milisegundos.\n");
        
		t1 = System.currentTimeMillis();
        try{
            valorBig=factRecursivoBig(BigInteger.valueOf(num));
			t2 = System.currentTimeMillis();
			System.out.println("El factorial recursivo de " + num + " es: " + valorBig);
			System.out.println("Tiempo de ejecución método recursivo: " + (t2-t1) + " milisegundos.\n");
        }catch(StackOverflowError e){
            System.err.println("\nLa recursión se llama demasiadas veces, no es posible calcular el valor del factorial recursivamente debido a que sobre pasa la memoria alocada por la máquina virtual de java.");
		}
		
		t1 = System.currentTimeMillis();
		valorBig=factThreads(num);
		t2 = System.currentTimeMillis();
		System.out.println("\nEl factorial con concurrencia de " + num + " es: " + valorBig); 
		System.out.println("Tiempo de ejecución método concurrencia: " + (t2-t1) + " milisegundos.\n");
        
		sc.close();
	}
	public static BigInteger factIterativoBig(int num) {
		if(num<0){
			return BigInteger.valueOf(-1);
		}
		BigInteger valor = BigInteger.ONE;
		for(int i=num;i>0;i--) {
			//System.out.println(valor + "*" + i);
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

	public static BigInteger factThreads(int num) {
		try{
			if(num<=20){
				return factIterativoBig(num);
			}
			if(num%2!=0){
				FactorialThread r = new FactorialThread(num,false);
				Thread t = new Thread(r);
				t.start();
				BigInteger partSol = factIterativoBig((num-1)/2);
				t.join();
				return r.res.multiply(partSol); 
			}else{
				FactorialThread r = new FactorialThread(num,true);
				Thread t = new Thread(r);
				t.start();
				BigInteger partSol = factIterativoBig(num/2);
				t.join();
				return partSol.multiply(r.res);
			}
		}catch(InterruptedException e){
			return BigInteger.valueOf(-1);
		}			
	}
}
class FactorialThread implements Runnable{
	int num,limit;
	boolean par;
	BigInteger res;
	FactorialThread(int num,boolean par){
		this.num=num;
		this.par=par;
	}
	public void run() {
		res = BigInteger.ONE;
		if(par){ limit=num/2;}
		else{limit=(num-1)/2;} 

		for(int i=num;i>limit;i--) {
			//System.out.println(res + "*" + i);
			res=res.multiply(BigInteger.valueOf(i));
		}
	} 
}
