import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.FutureTask;

public class MyFutureTask extends FutureTask<Double>{
    private final String filename = "C:/Users/jakub/Desktop/equations.txt";
    String rownanie;
    Double wynik;
    MyCallable callable;

    public MyFutureTask(MyCallable callable) {
        super(callable);
        this.callable = callable;
        rownanie = callable.getRownanie();
        wynik = callable.getWynik();
        System.out.println("Thread " + Thread.currentThread().getName() + ": Obliczam rownanie:  " + rownanie);
    }

    public void done() {
        rownanie = callable.getRownanie();
        wynik = callable.getWynik();
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write(rownanie + " = " + this.wynik + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Thread " + Thread.currentThread().getName() + ": done | " + rownanie + " = " + this.wynik);    }
}
