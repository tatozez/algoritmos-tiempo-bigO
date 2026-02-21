import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Main {

    static final int RUNS = 5;
    static final int WARMUP = 2;
    static final long SEED = 12345L;

    public static void main(String[] args) throws Exception {

        new File("data").mkdirs();

        medirFactorial();
        medirFibonacci();
        medirBusquedaLineal();
        medirBurbuja();

        System.out.println("Listo. CSV generados en /data");
    }

    // ============================================================
    // FACTORIAL
    // ============================================================

    static long factorialIter(int n) {
        long res = 1L;
        for (int i = 2; i <= n; i++)
            res *= i;
        return res;
    }

    static long factorialRec(int n) {
        if (n <= 1) return 1L;
        return n * factorialRec(n - 1);
    }

    static void medirFactorial() throws IOException {

        try (FileWriter fw = new FileWriter("data/factorial.csv")) {

            fw.write("n,iter_ns,rec_ns\n");

            for (int n = 1; n <= 20; n++) {

                final int size = n;

                for (int i = 0; i < WARMUP; i++) {
                    factorialIter(size);
                    factorialRec(size);
                }

                long iterAvg = promedioNano(() -> factorialIter(size));
                long recAvg = promedioNano(() -> factorialRec(size));

                fw.write(size + "," + iterAvg + "," + recAvg + "\n");
            }
        }
    }

    // ============================================================
    // FIBONACCI
    // ============================================================

    static long fibIter(int n) {
        if (n <= 1) return n;

        long a = 0, b = 1;

        for (int i = 2; i <= n; i++) {
            long c = a + b;
            a = b;
            b = c;
        }
        return b;
    }

    static long fibRec(int n) {
        if (n <= 1) return n;
        return fibRec(n - 1) + fibRec(n - 2);
    }

    static void medirFibonacci() throws IOException {

        try (FileWriter fw = new FileWriter("data/fibonacci.csv")) {

            fw.write("n,iter_ns,rec_ns\n");

            for (int n = 1; n <= 35; n++) {

                final int size = n;

                for (int i = 0; i < WARMUP; i++) {
                    fibIter(size);
                    fibRec(size);
                }

                long iterAvg = promedioNano(() -> fibIter(size));
                long recAvg = promedioNano(() -> fibRec(size));

                fw.write(size + "," + iterAvg + "," + recAvg + "\n");
            }
        }
    }

    // ============================================================
    // BÚSQUEDA LINEAL
    // ============================================================

    static int busquedaIter(int[] arr, int objetivo) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == objetivo) return i;
        }
        return -1;
    }

    static int busquedaRec(int[] arr, int objetivo, int index) {
        if (index >= arr.length) return -1;
        if (arr[index] == objetivo) return index;
        return busquedaRec(arr, objetivo, index + 1);
    }

    static void medirBusquedaLineal() throws IOException {

        try (FileWriter fw = new FileWriter("data/busqueda_lineal.csv")) {

            fw.write("n,iter_ns,rec_ns\n");

            Random rnd = new Random(SEED);

            for (int n = 1000; n <= 20000; n += 1000) {

                final int size = n;

                int[] arr = generarArray(size, rnd);
                int objetivo = arr[size - 1]; // peor caso

                for (int i = 0; i < WARMUP; i++) {
                    busquedaIter(arr, objetivo);
                    busquedaRec(arr, objetivo, 0);
                }

                long iterAvg = promedioNano(() -> busquedaIter(arr, objetivo));
                long recAvg = promedioNano(() -> busquedaRec(arr, objetivo, 0));

                fw.write(size + "," + iterAvg + "," + recAvg + "\n");
            }
        }
    }

    // ============================================================
    // BURBUJA
    // ============================================================

    static void burbujaIter(int[] arr) {

        for (int i = 0; i < arr.length - 1; i++) {

            boolean swapped = false;

            for (int j = 0; j < arr.length - 1 - i; j++) {

                if (arr[j] > arr[j + 1]) {

                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;

                    swapped = true;
                }
            }

            if (!swapped) break;
        }
    }

    static void burbujaRec(int[] arr, int n) {

        if (n <= 1) return;

        boolean swapped = false;

        for (int i = 0; i < n - 1; i++) {

            if (arr[i] > arr[i + 1]) {

                int tmp = arr[i];
                arr[i] = arr[i + 1];
                arr[i + 1] = tmp;

                swapped = true;
            }
        }

        if (!swapped) return;

        burbujaRec(arr, n - 1);
    }

    static void medirBurbuja() throws IOException {

        try (FileWriter fw = new FileWriter("data/burbuja.csv")) {

            fw.write("n,iter_ns,rec_ns\n");

            Random rnd = new Random(SEED);

            for (int n = 200; n <= 2000; n += 200) {

                final int size = n;

                int[] base = generarArray(size, rnd);

                for (int i = 0; i < WARMUP; i++) {
                    burbujaIter(base.clone());
                    burbujaRec(base.clone(), size);
                }

                long iterAvg = promedioNano(() -> {
                    int[] copy = base.clone();
                    burbujaIter(copy);
                    return 0;
                });

                long recAvg = promedioNano(() -> {
                    int[] copy = base.clone();
                    burbujaRec(copy, size);
                    return 0;
                });

                fw.write(size + "," + iterAvg + "," + recAvg + "\n");
            }
        }
    }

    // ============================================================
    // UTILIDADES
    // ============================================================

    interface Task {
        long run();
    }

    static long promedioNano(Task task) {

        long total = 0;

        for (int i = 0; i < RUNS; i++) {

            long start = System.nanoTime();
            task.run();
            long end = System.nanoTime();

            total += (end - start);
        }

        return total / RUNS;
    }

    static int[] generarArray(int n, Random rnd) {

        int[] arr = new int[n];

        for (int i = 0; i < n; i++)
            arr[i] = rnd.nextInt();

        return arr;
    }
}