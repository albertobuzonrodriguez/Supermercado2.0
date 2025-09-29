package classes;

class Cajera implements Runnable {
    private final Cliente[] clientes;
    private final String nombreCajera;

    public Cajera(String nombreCajera, Cliente[] clientes) {
        this.nombreCajera = nombreCajera;
        this.clientes = clientes;
    }

    @Override
    public void run() {
        int tiempoTotal = 0;
        int totalProductos = 0;

        System.out.printf("%s empieza a trabajar con %d clientes\n", nombreCajera, clientes.length);

        for (int i = 0; i < clientes.length; i++) {
            Cliente cliente = clientes[i];
            int tiempoCliente = 0;

            

            for (int j = 0; j < cliente.productos; j++) {
                try {
                    Thread.sleep(1000 * cliente.tiempo);
                    tiempoCliente = tiempoCliente + cliente.tiempo;
                    tiempoTotal = tiempoTotal + cliente.tiempo;
                    System.out.printf("%s -> %s producto %d procesado\n", nombreCajera, cliente.nombre, j + 1);
                    
                } catch (InterruptedException e) {
                    System.out.printf("Hilo %s interrumpido.\n", cliente.nombre);
                }
            }

            totalProductos += cliente.productos;
            System.out.printf("%s termina con %s en el tiempo: %ds\n", nombreCajera, cliente.nombre, tiempoTotal);
        }

        System.out.println("=== RESUMEN " + nombreCajera + " ===");
        System.out.println("Total clientes atendidos: " + clientes.length);
        System.out.println("Total productos procesados: " + totalProductos);
        System.out.println("Tiempo total: " + tiempoTotal + " segundos");
        if (totalProductos > 0) {
            System.out.println("Promedio general: " + (tiempoTotal / totalProductos) + " segundos por producto");
        }
        System.out.println("========================\n");
    }
}

public class Supermercado {
    public static void main(String[] args) {
        Cliente[] clientesCajera1 = {
            new Cliente("cliente 1", ((int)(Math.random() * 2) + 2), ((int)(Math.random() * 5) + 5)),
            new Cliente("cliente 4", ((int)(Math.random() * 2) + 2), ((int)(Math.random() * 5) + 5))
        };
        
        Cliente[] clientesCajera2 = {
            new Cliente("cliente 2", ((int)(Math.random() * 2) + 2), ((int)(Math.random() * 5) + 5)),
            new Cliente("cliente 5", ((int)(Math.random() * 2) + 2), ((int)(Math.random() * 5) + 5))
        };

        Thread h1 = new Thread(new Cajera("Cajera 1", clientesCajera1));
        Thread h2 = new Thread(new Cajera("Cajera 2", clientesCajera2));
        
        h1.start();
        h2.start();

        try {
            h1.join();
            h2.join();
            
        } catch (InterruptedException ex) {
            System.out.println("Hilo principal interrumpido.");
        }

        System.out.println("Hilo principal terminado.");
    }
}

