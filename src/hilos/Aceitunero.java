package hilos;

import datos.Almazara;

import java.util.concurrent.ThreadLocalRandom;

public class Aceitunero implements Runnable {

    private final Almazara almazara;
    private boolean ejecucion = true;

    public Aceitunero(Almazara almazara) {
        this.almazara = almazara;
    }


    @Override
    public void run() {
        while (this.ejecucion) {
            try {
                for (int i = 0; i < 10; i++) {
                    int peso = ThreadLocalRandom.current().nextInt(100, 301);
                    Thread.sleep(ThreadLocalRandom.current().nextInt(150, 500));
                    almazara.entregarAceituna(peso);
                }
            } catch (InterruptedException e) {
                this.ejecucion = false;
            }
        }
    }
}
