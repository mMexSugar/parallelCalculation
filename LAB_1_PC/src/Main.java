import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Введіть кількість чисел");
        int n = scanner.nextInt();
        System.out.println("Введіть кількість знаків після коми");
        int precision = scanner.nextInt();

        HarmonicSummator HS = new HarmonicSummator(n, precision);
        HS.calculateSum();
        HS.calculateSumParallel();
        //HS.calculateSumParallel1();

    }
}