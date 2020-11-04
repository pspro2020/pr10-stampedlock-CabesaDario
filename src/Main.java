import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException{
        List<Integer> lista = new ArrayList<>(Arrays.asList(1, 2, 1, 1, 2, 1, 3));
        Almacen almacen = new Almacen(lista);
        Thread[] consultores= new Thread[3];
        for (int i = 1; i < 4; i++) {
            consultores[i-1] = new Thread(new Consultor(i,almacen), "Consultor " + i);
            consultores[i-1].start();
        }

        Thread encargado = new Thread(new Encargado(almacen), "Encargao");

        encargado.start();

        TimeUnit.SECONDS.sleep(10);

        consultores[0].interrupt();
        consultores[1].interrupt();
        consultores[2].interrupt();

        encargado.interrupt();


    }
}
