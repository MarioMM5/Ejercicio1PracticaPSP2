package hilos;

import datos.Almazara;

import java.util.concurrent.ThreadLocalRandom;

public class Transportista implements Runnable{

    private final Almazara almazara;
    private final String tipo;

    public Transportista(Almazara almazara, String tipo) {
        this.almazara = almazara;
        this.tipo = tipo;
    }


    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            int cantidad = ThreadLocalRandom.current().nextInt(800, 1201);
            almazara.cargarTransporte(tipo, cantidad);
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(150, 501));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}