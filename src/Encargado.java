import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class Encargado implements Runnable{
    private Almacen almacen;
    Random random = new Random();
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    int id;

    public Encargado(Almacen almacen) {
        this.almacen = almacen;
    }


    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                id = random.nextInt(3)+1;
                almacen.poner(id);
                System.out.printf("%s -> %s ha añadío una unidad del producto %d al almacén\n", LocalTime.now().format(dateTimeFormatter), Thread.currentThread().getName(), id);
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                System.out.println("I've been interrupted while adding some product the stock");
                return;
            }
        }
    }
}
