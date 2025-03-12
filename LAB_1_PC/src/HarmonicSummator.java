import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;


public class HarmonicSummator {
    private final String inputFile = "data//input.txt";
    private final String outputFile = "data//output.txt";
    private int n;
    private int precision;
    public BigDecimal[] arr;

    HarmonicSummator(int n, int precision){
        this.n = n;
        this.precision = precision;
        this.arr = new BigDecimal[n + 1];

        for (int i = 1; i <= n; i++) {
            BigDecimal num = BigDecimal.ONE.divide(BigDecimal.valueOf(i), precision, RoundingMode.HALF_UP);
            writeToFile(String.valueOf(num), inputFile, true);
            arr[i] = num;
        }
    }

    public void calculateSum(){
        System.out.println("Один потік:");
        BigDecimal sum = BigDecimal.ZERO;
        long startTime = System.nanoTime();

        for (int i = 1; i <= n; i++) {
            sum = sum.add(arr[i]);
        }
        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        String content = "Сума: " + sum + "\nЧас виконання: " + duration + " нс";
        writeToFile(content, outputFile, false);
        System.out.println(content);
    }

    public void calculateSumParallel() {
        System.out.println("Багатопотокове обчислення:");
        int numThreads = Runtime.getRuntime().availableProcessors();
        Thread[] threads = new Thread[numThreads];
        BigDecimal[] partialSums = new BigDecimal[numThreads];

        int chunkSize = n / numThreads;
        long startTime = System.nanoTime();

        for (int i = 0; i < numThreads; i++) {
            final int index = i;
            final int start = i * chunkSize + 1;
            final int end = (i + 1) * chunkSize;

            threads[i] = new Thread(() ->{
                partialSums[index] = calculatePartialSum(start, end);
            });

            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                System.out.println("Помилка у багатопоточних обчисленнях: " + e.getMessage());
            }
        }

        BigDecimal totalSum = BigDecimal.ZERO;
        for (BigDecimal sum : partialSums) {
            totalSum = totalSum.add(sum);
        }

        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        String content = "Кількість потоків " + numThreads +", Сума (Parallel): " + totalSum + "\nЧас виконання: " + duration  + " нс";
        writeToFile(content, outputFile, true);
        System.out.println(content);
    }

    private BigDecimal calculatePartialSum(int start, int end) {
        BigDecimal sum = BigDecimal.ZERO;
        for (int i = start; i <= end; i++) {
            sum = sum.add(arr[i]);
        }
        return sum;
    }

    public void writeToFile(String content, String fileName, boolean append) {
        File file = new File(fileName);
        try (FileWriter writer = new FileWriter(file, append)) {
            writer.write(content + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Помилка запису: " + e.getMessage());
        }
    }

}
