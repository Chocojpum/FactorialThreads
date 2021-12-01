/*
Autor: José Pedro Unda Montecinos
*/
import java.math.BigInteger;
import java.util.Scanner; 

public class Factorial {
	public static void main(String[] args) {
		int num;
		long t1,t2,tiempoIterativo,tiempoRecursivo,tiempoThread;
		BigInteger valorBig=BigInteger.ZERO;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Ingrese el número a calcular su factorial:");
		num = sc.nextInt();
        /*
		**Medición del método iterativo
		*/
        t1 = System.currentTimeMillis();
		valorBig=factIterativoBig(num);
		t2 = System.currentTimeMillis();
		tiempoIterativo=t2-t1;
		System.out.println("\nEl factorial iterativo de " + num + " es: " + valorBig); 
		 /*
		**Medición del método recursivo
		*/
		t1 = System.currentTimeMillis();
        try{
            valorBig=factRecursivoBig(BigInteger.valueOf(num));
			t2 = System.currentTimeMillis();
			tiempoRecursivo=t2-t1;
			System.out.println("El factorial recursivo de " + num + " es: " + valorBig);
			
        }catch(StackOverflowError e){
			//La JVM aloca una cantidad de memoria que puede sobrepasarse al hacer muchos llamados recursivos, por lo que se indica por pantalla cuando ocurra el límite
            tiempoRecursivo=-1;
			System.err.println("\nLa recursión se llama demasiadas veces, no es posible calcular el valor del factorial recursivamente debido a que sobrepasa la memoria alocada por la máquina virtual de java.\n");
		}
		/*
		**Medición del método concurrencia
		*/
		t1 = System.currentTimeMillis();
		valorBig=factThreads(num);
		t2 = System.currentTimeMillis();
		tiempoThread=t2-t1;
		System.out.println("\nEl factorial con concurrencia de " + num + " es: " + valorBig); 
		
		System.out.println("\nTiempo de ejecución método iterativo: " + tiempoIterativo + " milisegundos.\n");
		if(tiempoRecursivo==-1){
			System.out.println("\nEl método recursivo no se ejecutó para el valor entregado debido a que supera el espacio de memoria del stack del jvm.\n");
		}else{
			System.out.println("\nTiempo de ejecución método recursivo: " + tiempoRecursivo + " milisegundos.\n");
		}
		System.out.println("\nTiempo de ejecución método concurrencia: " + tiempoThread + " milisegundos.\n");
        
		sc.close();
	}
	/*
	**Método iterativo del cálculo de un factorial
	*/
	public static BigInteger factIterativoBig(int num) {
		//Si el número es negativo, retorna -1 debido a que no se puede calcular el factorial
		if(num<0) return BigInteger.valueOf(-1);

		BigInteger valor = BigInteger.ONE;
		//Va multiplicando de n a 1
		for(int i=num;i>0;i--) valor=valor.multiply(BigInteger.valueOf(i));

		return valor;
	} 
	/*
	**Método recursivo del cálculo de un factorial
	*/
	public static BigInteger factRecursivoBig(BigInteger n) {
		//Si el número es negativo, retorna -1 debido a que no se puede calcular el factorial
		if(n.compareTo(BigInteger.ZERO)<0) return BigInteger.valueOf(-1);

		//Si el número es 0 o 1, retorna 1 debido a que son los casos base del factorial
        if(n.equals(BigInteger.ONE) || n.equals(BigInteger.ZERO)) return BigInteger.ONE;

		//El valor de n lo multiplica por el factorial de su antecesor, llamándose a sí mismo
        return n.multiply(factRecursivoBig(n.subtract(BigInteger.ONE))); 
	} 
	/*
	**Método de concurrencia (uso de threads) del cálculo de un factorial
	*/
	public static BigInteger factThreads(int num) {
		try{
			//si el número es menor a 20, simplemente se calcula el factorial iterativo
			if(num<=20) return factIterativoBig(num);

			BigInteger partSol;
			//booleano que dice si el número es par o impar
			boolean par = num%2!=0;
			//se hace el llamado al thread que se encargará de calcular la mitad superior del factorial
			FactorialThread r = new FactorialThread(num,par); 
			//el thread principal (main) se encarga de calcular la mitad de abajo del factorial, variando la mitad si es par o impar
			if(par) partSol = factIterativoBig((num-1)/2);
			else partSol = factIterativoBig((num/2)-1);		
			r.t.join();
			//cuando los dos threads estén listos se retornará la multiplicación entre sus dos resultados, siendo el valor final del factorial
			return r.res.multiply(partSol);
		}catch(InterruptedException e){
			//un thread fue interrumpido por lo que no se pudo realizar el cálculo, se retorna -1
			return BigInteger.valueOf(-1);
		}			
	}
}
/*
**Clase correspondiente al runnable del método para calcular con threads
*/
class FactorialThread implements Runnable{
	int num,limit;
	boolean par;
	BigInteger res;
	Thread t;
	FactorialThread(int num,boolean par){
		this.num=num;
		this.par=par;
		t=new Thread(this);
		t.start();
	}
	public void run() {
		res = BigInteger.ONE;
		//se establecen los límites para el cálculo de la mitad superior del factorial
		if(par) limit=num/2;
		else limit=(num-1)/2; 
		//se calcula el factorial como el método iterativo, pero solo hasta el límite establecido
		for(int i=num;i>limit;i--) res=res.multiply(BigInteger.valueOf(i));
	} 
}