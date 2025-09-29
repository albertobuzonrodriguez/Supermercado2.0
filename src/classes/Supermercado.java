package classes;

class Cajera implements Runnable {
	
	public final Cliente cliente;
	
	public Cajera (Cliente cliente) {
		this.cliente = cliente;
	}
	
	
	@Override
	public void run() {
		int tiempoTotal = 0;
		int [] tiempoClientes = new int [2];
		
		for (int i = cliente.productos; i >= 0; i--) {
			try {
				Thread.sleep(1000*cliente.tiempo);
				tiempoTotal = tiempoTotal + cliente.tiempo;
				System.out.printf("%s producto escaneado.\n", cliente.nombre );
				
				for (int j = 0; j < tiempoClientes.length; j++) {
					tiempoClientes[j]= tiempoTotal;
				}
				
			} catch (InterruptedException e) {
				System.out.printf("Hilo %s interrumpido.\n");
			}
		}
		
		System.out.println("\n"+this.cliente.nombre+" tiene "+cliente.productos+" y ha tardado un promedio de "+
				(tiempoClientes[0]/cliente.productos)+" segundos por producto.\n");
	}
	
}
public class Supermercado {

	public static void main(String[] args) {
		
		Cliente cliente1 = new Cliente ("Cliente 1", ((int)(Math.random() * 2) + 2), ((int)(Math.random() * 5) + 5));
		Cliente cliente2 = new Cliente ("Cliente 2", ((int)(Math.random() * 2) + 2), ((int)(Math.random() * 5) + 5));
		
		Thread h1 = new Thread(new Cajera (cliente1));
		Thread h2 = new Thread(new Cajera (cliente2));
		
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


