import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;

public class Almacen {
    private List<Integer> list;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final StampedLock stampedLock = new StampedLock();

    public Almacen(List<Integer> list) {
        this.list = list;

        for (Integer n : list) {
            if(n < 1){
                throw new IllegalArgumentException();
            }
        }


    }

    protected int consultar(int number) throws InterruptedException{
        int counter = 0;

        long stamp = stampedLock.tryOptimisticRead();

        for (Integer n : list) {
            if(n == number){
                counter++;
            }
        }

        if (!stampedLock.validate(stamp)) {
            counter=0;
            // Si no es válido debemos volver a leer los valores, pero antes
            // obtenemos el cerrojo de lectura de forma optimista.
            stamp = stampedLock.readLock();
            try {
                for (Integer n : list) {
                    if(n == number){
                        counter++;
                    }
                }
            } finally {
                // Se libera el cerrojo de lectura.
                stampedLock.unlockRead(stamp);
            }
        }

        return counter;

    }

    protected void poner(int number){
        long stamp = stampedLock.writeLock();
        try {
            list.add(number);

        } finally {
            stampedLock.unlockWrite(stamp);
        }

    }

}
