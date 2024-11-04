package DLL;
import java.util.LinkedList; 
import javax.swing.*;

import BLL.Producto;

public class ListProducto {
	private static LinkedList<Producto> productos;

	
	public ListProducto() {
		
	}
	
	public static LinkedList<Producto> getInstance(String depositoParametro){
		if (productos==null) {
			productos = new LinkedList<Producto>();
			rellenarLista();
		} else {
			JOptionPane.showMessageDialog(null, "Ya existe");
		}
		return productos;
	}
	
	public static void guardar(Producto Producto) {
		productos.add(Producto);
	}
	
	
	public static void rellenarLista(){
		productos.add(new Producto("Rosa Roja", "Flor roja ornamental.", 30,310.00));
	}
}
 