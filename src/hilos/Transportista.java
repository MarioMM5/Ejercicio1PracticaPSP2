package hilos;

import datos.Almazara;

import java.util.concurrent.ThreadLocalRandom;

public class Transportista implements Runnable {

    private final Almazara almazara;
    private final String tipo;
    private boolean ejecucion = true;

    public Transportista(Almazara almazara, String tipo) {
        this.almazara = almazara;
        this.tipo = tipo;
    }

    @Override
    public void run() {
        while (this.ejecucion) {
            try {
                for (int i = 0; i < 10; i++) {
                    int cantidad = ThreadLocalRandom.current().nextInt(100, 301);
                    Thread.sleep(ThreadLocalRandom.current().nextInt(150, 500));
                    almazara.cargarTransporte(tipo, cantidad);
                }
            } catch (InterruptedException e) {
                this.ejecucion = false;
            }
        }
    }
}