package hilos;

import datos.Almazara;

import java.util.concurrent.ThreadLocalRandom;

public class Aceitunero implements Runnable{

    private final Almazara almazara;

    public Aceitunero(Almazara almazara, ThreadGroup threadGroup) {
        this.almazara = almazara;
    }


    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            int peso = ThreadLocalRandom.current().nextInt(100,301);
            almazara.entregarAceituna(peso);
            try{
                Thread.sleep(ThreadLocalRandom.current().nextInt(150,500));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
