import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    private static final String filename = "C:/Users/jakub/Desktop/equations.txt";
    public static final Lock lock = new ReentrantLock();

    public static void main(String[] args) throws FileNotFoundException{
        List<MyCallable> callList = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(3);

        File file = new File(filename);
        Scanner scan = new Scanner(file);

        // Na początku oddzielamy nasze wyniku od równanń dla czytelności
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write(System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (scan.hasNextLine()) {
                String equation = scan.nextLine();
                MyCallable call = new MyCallable(equation);
                executor.submit(() -> {
                    lock.lock();
                    try {
                        MyFutureTask futureTask = new MyFutureTask(call);
                        futureTask.run();
                    } finally {
                        lock.unlock();
                    }
                });
        }

        scan.close();
        executor.shutdown();
    }
}