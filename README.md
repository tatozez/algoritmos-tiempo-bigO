# Implementación y análisis de algoritmos iterativos y recursivos en Java

## Descripción
Este proyecto implementa 4 algoritmos en versión iterativa y recursiva:

- Factorial
- Fibonacci (sin memoización)
- Búsqueda lineal
- Ordenamiento burbuja

Se mide el tiempo de ejecución utilizando `System.nanoTime()` y se promedian 5 ejecuciones por cada tamaño de entrada.

## Estructura del proyecto

- `src/` → Código fuente en Java
- `data/` → Archivos CSV con resultados de tiempos
- `docs/` → Documentación en PDF

## Metodología

- Se utilizaron 5 ejecuciones por cada tamaño.
- Se aplicó calentamiento previo.
- No se incluyó la inicialización dentro de la medición.
- Se analizaron las complejidades temporales (Big-O).

## Complejidad esperada

| Algoritmo | Iterativo | Recursivo |
|-----------|-----------|-----------|
| Factorial | O(n) | O(n) |
| Fibonacci | O(n) | O(2^n) |
| Búsqueda lineal | O(n) | O(n) |
| Burbuja | O(n²) | O(n²) |

## Conclusiones
Los resultados obtenidos confirman el comportamiento teórico de cada algoritmo según su complejidad temporal. 
Se observa un crecimiento lineal en búsqueda lineal y factorial iterativo, crecimiento exponencial en Fibonacci recursivo sin memoización, y crecimiento cuadrático en el algoritmo de ordenamiento burbuja.

Las gráficas permiten visualizar claramente la diferencia entre implementaciones iterativas y recursivas, validando el análisis Big-O realizado.


## Cómo ejecutar
En macOS / Linux:
```bash
javac src/Main.java
java -cp src Main
## Video demostración



## Autor
Roberto Estuardo Chavarría Zelada
