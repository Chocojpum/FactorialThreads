/*
Autor: José Pedro Unda Montecinos
*/
import java.math.BigInteger;
import java.util.Scanner; 

public class Factorial {
	public static void main(String[] args) {
		int num;
		long t1,t2;
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
		System.out.println("\nEl factorial iterativo de " + num + " es: " + valorBig); 
		System.out.println("Tiempo de ejecución método iterativo: " + (t2-t1) + " milisegundos.\n");
        /*
		**Medición del método recursivo
		*/
		t1 = System.currentTimeMillis();
        try{
            valorBig=factRecursivoBig(BigInteger.valueOf(num));
			t2 = System.currentTimeMillis();
			System.out.println("El factorial recursivo de " + num + " es: " + valorBig);
			System.out.println("Tiempo de ejecución método recursivo: " + (t2-t1) + " milisegundos.\n");
        }catch(StackOverflowError e){
            System.err.println("\nLa recursión se llama demasiadas veces, no es posible calcular el valor del factorial recursivamente debido a que sobrepasa la memoria alocada por la máquina virtual de java.\n");
		}
		/*
		**Medición del método concurrencia
		*/
		t1 = System.currentTimeMillis();
		valorBig=factThreads(num);
		t2 = System.currentTimeMillis();
		System.out.println("\nEl factorial con concurrencia de " + num + " es: " + valorBig); 
		System.out.println("Tiempo de ejecución método concurrencia: " + (t2-t1) + " milisegundos.\n");
        
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
			Thread t = new Thread(r);
			t.start();
			//el segundo thread se encarga de calcular la mitad de abajo del factorial, variando la mitad si es par o impar
			if(num%2!=0) partSol = factIterativoBig((num-1)/2);
			else partSol = factIterativoBig((num/2)-1);		
			t.join();
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
	FactorialThread(int num,boolean par){
		this.num=num;
		this.par=par;
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