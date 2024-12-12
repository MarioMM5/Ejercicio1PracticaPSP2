package util;

import datos.Almazara;
import hilos.Aceitunero;
import hilos.Transportista;

public class Main {
    public static void main(String[] args) {
        Almazara almazara = new Almazara();
        ThreadGroup aceituneros = new ThreadGroup("Aceituneros");
        ThreadGroup transportistas = new ThreadGroup("Transportistas");

        for (int i = 0; i < 200; i++) {
            Thread aceitunero = new Thread(aceituneros, new Aceitunero(almazara, aceituneros), "Aceitunero nº" + i);
            aceitunero.start();
        }
        for (int i = 0; i < 20; i++) {
            Thread transportistaAceite = new Thread(transportistas, new Transportista(transportistas, almazara, "aceite"), "Transportista de Aceite nº" + i);
            transportistaAceite.start();
        }
        for (int i = 0; i < 7; i++) {
            Thread transportistaOrujo = new Thread(transportistas, new Transportista(transportistas, almazara, "orujo"), "Transportista de Orujo nº" + i);
            transportistaOrujo.start();
        }

        do {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (transportistas.activeCount() == 0 || aceituneros.activeCount() == 0) {
                almazara.finalizar();
            }
        } while (transportistas.activeGroupCount() == 0 && aceituneros.activeCount() == 0);

        almazara.mostrarEstadoFinal();
        System.exit(0);
    }
}
