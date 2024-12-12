package datos;

public class Almazara {

    private static final int CAPACIDAD_ACEITE = 3600;
    private static final int CAPACIDAD_ORUJO = 1600;


    private int depositoAceite = 0;
    private int tanqueOrujo = 0;

    private boolean finalizado = false;
    private int esperasAceituneros = 0;
    private int esperasTransportistasAceite = 0;
    private int esperasTransportistasOrujo = 0;

    public synchronized void entregarAceituna(int peso) {
        if (!finalizado) {
            int aceite = (int) (peso * 0.7);
            int orujo = peso - aceite;

            while (depositoAceite + aceite > CAPACIDAD_ACEITE && !finalizado || tanqueOrujo + orujo > CAPACIDAD_ORUJO && !finalizado) {
                esperasAceituneros++;
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            depositoAceite += aceite;
            tanqueOrujo += orujo;
            System.out.println("Aceitunero entregó " + peso + " kg (Aceite: " + aceite + ", Orujo: " + orujo + ", Deposito Aceituna:  " + depositoAceite + ", Tanque Orujo:  " + tanqueOrujo + "  ).");
            notifyAll();
        }
    }

    public synchronized void cargarTransporte(String tipo, int cantidad) {
        if (!finalizado) {
            if (tipo.equals("aceite")) {
                while (depositoAceite < cantidad && !finalizado) {
                    esperasTransportistasAceite++;
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (!finalizado) {
                    depositoAceite -= cantidad;
                    System.out.println("Transportista cargó " + cantidad + " kg de aceite.");
                }
            } else if (tipo.equals("orujo")) {
                while (tanqueOrujo < cantidad && !finalizado) {
                    esperasTransportistasOrujo++;
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (!finalizado) {
                    tanqueOrujo -= cantidad;
                    System.out.println("Transportista cargó " + cantidad + " kg de orujo.");
                }
            }
            notifyAll();
        }
    }

    public void mostrarEstadoFinal() {
        System.out.println("\nEstado final:");
        System.out.println("Depósito de aceite: " + depositoAceite + " kg.");
        System.out.println("Tanque de orujo: " + tanqueOrujo + " kg.");
        System.out.println("Esperas aceituneros: " + esperasAceituneros);
        System.out.println("Esperas transportistas de aceite: " + esperasTransportistasAceite);
        System.out.println("Esperas transportistas de orujo: " + esperasTransportistasOrujo);
    }

}


