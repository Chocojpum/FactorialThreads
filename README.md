# FactorialThreads
## Calcular un factorial grande y comparar tiempos entre las soluciones iterativa, recursiva y con concurrencia.

Para el método con concurrencia (utilización de threads) se implementan 2 hilos y dividen el cálculo del factorial en 2.

Se solicitará el número a calcular su factorial por teclado y para el testeo se utiliza el valor 1.000.000 para comparar como valor grande.

En caso de utilizar un valor muy grande (por ejemplo 1.000.000) se recomienda comentar las líneas de código que imprimen el valor para visualizar mejor la comparación de tiempos.

> Los specs del ordenador utilizado en el testeo son: Intel(R) Core(TM) i7-6700HQ CPU @ 2.60GHz, 1999 Mhz, 4 Cores, 8 Logical Processors.

Los resultados para el valor 1.000.000 son:

```
Tiempo de ejecución método iterativo: 400890 milisegundos.

La recursión se llama demasiadas veces, no es posible calcular el valor del factorial recursivamente debido a que sobrepasa la memoria alocada por la máquina virtual de java.

Tiempo de ejecución método concurrencia: 117065 milisegundos.
```


En este proyecto se responden las siguientes preguntas:

- ¿Si calculamos el factorial de 1.000.000 iterativamente es más eficiente que hacerlo con 2 hilos?

R: No, se puede observar que el método con hilos es más eficiente debido a que utiliza más CPU para dividirse la tarea iterativa y realizarla más rápido.
Cabe comentar que en valores pequeños el método con hilos es más ineficiente que el iterativo.

- ¿Si calculamos el factorial de 1.000.000 recursivamente es más eficiente que hacerlo con 2 hilos?

R: No es posible calcular el factorial de 1.000.000 con el método recursivo debido a que supera la cantidad de memoria alocada por la JVM y lanza el error StackOverflow.
Con valores pequeños es más eficiente el método recursivo.


**Autor: José Pedro Unda Montecinos** 