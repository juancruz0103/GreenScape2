package DLL;
 import java.util.LinkedList;

import BLL.Usuario;

public class ListUsuario {

	private static 	LinkedList<Usuario> usuarios;

	public static LinkedList<Usuario> getInstance(){
		if (usuarios==null) {
			usuarios = new LinkedList<Usuario>();
			ListaGreenTeam();		
		} else {	
			System.out.println("La lista ya existe");
		}	
		return usuarios;
	}
	public static void guardar(Usuario usuario) {
		usuarios.add(usuario);
	}
	
	// Lista de usuarios
	
	public static void ListaGreenTeam() {
		usuarios.add(new  Usuario("Andres",0 ,"Andres@gmail.com","Andrespass1", "jardinero"));
		usuarios.add(new  Usuario("Juan",1 ,"Juan@gmail.com","Juanpass1", "almacenero"));
		usuarios.add(new  Usuario("Lucas",2 ,"Lucas@gmail.com","Lucasspass1", "jardinero"));
		usuarios.add(new  Usuario("Pepe",3 ,"Pepe@gmail.com","Pepespass1", "usuario"));
		

	}
}
