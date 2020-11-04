import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class Consultor implements Runnable{

    private int id;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private Almacen almacen;

    public Consultor(int id, Almacen almacen) {
        this.id = id;
        this.almacen = almacen;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                System.out.printf("%s -> %s : hay %d stock del producto %d\n", LocalTime.now().format(dateTimeFormatter), Thread.currentThread().getName(),almacen.consultar(id), id);

                sleep(500);
            } catch (InterruptedException e) {
                System.out.println("I've been interrupted while consulting the stock");
                return;
            }
        }

    }
}
