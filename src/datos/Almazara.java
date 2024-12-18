package datos;

public class Almazara {

    private static final int CAPACIDAD_ACEITE = 3600;
    private static final int CAPACIDAD_ORUJO = 1600;


    private int depositoAceite = 0;
    private int tanqueOrujo = 0;

    private int esperasAceituneros = 0;
    private int esperasTransportistasAceite = 0;
    private int esperasTransportistasOrujo = 0;

    public synchronized void entregarAceituna(int peso) {

        int aceite = (int) (peso * 0.7);
        int orujo = peso - aceite;

        while (depositoAceite + aceite > CAPACIDAD_ACEITE || tanqueOrujo + orujo > CAPACIDAD_ORUJO) {
            esperasAceituneros++;
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        depositoAceite += aceite;
        tanqueOrujo += orujo;
        System.out.println("Aceitunero entregó " + peso + " kg (Aceite: " + aceite + ", Orujo: " + orujo + ", Deposito Aceituna:  " + depositoAceite + ", Tanque Orujo:  " + tanqueOrujo + "  ).");
        notifyAll();
    }


    public synchronized void cargarTransporte(String tipo, int cantidad) {

            if (tipo.equals("aceite")) {
                while (depositoAceite < cantidad) {
                    esperasTransportistasAceite++;
                    try {
                        wait();
                    } catch (InterruptedException var5) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                depositoAceite -= cantidad;
                System.out.println("Transportista cargó " + cantidad + " kg de aceite.");
            } else if (tipo.equals("orujo")) {
                while (tanqueOrujo < cantidad) {
                    esperasTransportistasOrujo++;
                    System.out.println("esperando orujo");
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                tanqueOrujo -= cantidad;
                System.out.println("Transportista cargó " + cantidad + " kg de orujo.");
            }

        notifyAll();
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


