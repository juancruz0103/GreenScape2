package GUI;
import javax.swing.*;

import BLL.Curso;
import BLL.Producto;
import BLL.Proyecto;
import BLL.Usuario;
import DLL.AdministradorAlmacen;
import DLL.AdministradorJardinero;
import DLL.ListUsuario;
import DLL.Listproyectos;
import BLL.Usuario;

import java.util.LinkedList;

public class Main {
	

    public static void main(String[] args) {
       
        LinkedList<Usuario> usuarios = ListUsuario.getInstance();

        
        MenuPrincipal(usuarios);
    }

    
    public static void MenuPrincipal(LinkedList<Usuario> usuarios) {
        String[] MenuUsuario = {"Iniciar sesión", "Registrar usuario", "Salir"};
        int IdMenu;

        do {
            IdMenu = JOptionPane.showOptionDialog(null, "Bienvenido a la plataforma de GreenScape. Elija una opción", "GreenScape", 0, JOptionPane.QUESTION_MESSAGE, null, MenuUsuario, MenuUsuario[0]);

            switch (IdMenu) {
                case 0: 
                    iniciarSesion(usuarios);
                    break;

                case 1: 
                    registrarUsuario();
                    break;

                case 2: 
                    JOptionPane.showMessageDialog(null, "Gracias por participar en la demo de GreenScape.");
                    break;

                default:
                    break;
            }
        } while (IdMenu != 2);  
    }

    
    
    public static void iniciarSesion(LinkedList<Usuario> usuario) {
        String email = JOptionPane.showInputDialog("Ingrese su email:");
        String contrasena = JOptionPane.showInputDialog("Ingrese su contraseña:");

        Usuario usuarioLogueado = Usuario.login(email, contrasena);

        if (usuarioLogueado != null) {
            JOptionPane.showMessageDialog(null, "Login exitoso. Bienvenido " + usuarioLogueado.getNombre());
            mostrarMenuUsuario(usuarioLogueado);
        } else {
            JOptionPane.showMessageDialog(null, "Credenciales incorrectas. Intente de nuevo.");
        }
    }
    
    public static boolean validarContraseña( String contrasena){
    	boolean repetir = true;
    	boolean valCaracter = false;
    	boolean validarMayus = false; 
    	boolean validarMinus = false; 
    	boolean validarNum = false; 
    	
    	for (int j = 0; j < contrasena.length(); j++) {
        	
        	
        	// Contraseña 8 caracteres, 1 num, 1 mayus, 1 minus.   	
        	for (int i = 0; i < contrasena.length(); i++) {
				
    			if (Character.isDigit(contrasena.charAt(i))) {
    				validarNum = true;
				}
    			if (Character.isUpperCase(contrasena.charAt(i))) {
    				validarMayus = true;
				}
    			if (Character.isLowerCase(contrasena.charAt(i))) {	
    				validarMinus = true;
    			}
    		}
        }   	    	    	
    	
    	  if (contrasena.length() < 8 ) {
    		JOptionPane.showMessageDialog(null, "Tiene que tener 8 caracteres como minimo.");
    	  }else{
    		valCaracter = true;
    	  }
    	
    	  if(validarMayus == false){
          	JOptionPane.showMessageDialog(null, "Una mayuscula como minimo.");
          }
        
          if(validarMinus == false){
          	JOptionPane.showMessageDialog(null, "Una minuscula como minimo.");
          }
          
          if(validarNum == false){
          	JOptionPane.showMessageDialog(null, "tiene que contener numeros.");
          }
          
          if(valCaracter && validarMayus && validarMinus && validarNum == true){
        	  repetir = false;
          }
          
          return repetir;
    	}
    
    public static boolean validarNegativo(int digito){	   
    	boolean validarNeg = false;
    	
    	if (digito < 0) {
			JOptionPane.showMessageDialog(null, "No debe haber números negativos");
    	} else {
    		validarNeg = true;
		}
 
 	   return validarNeg;
    }

    public static void registrarUsuario() {
        String nombre = JOptionPane.showInputDialog("Ingrese nombre");

        int idUsuario;
        boolean numeroNegativo;
        try {
            do {
                idUsuario = Integer.parseInt(JOptionPane.showInputDialog("Ingrese un id numerico de usuario para identificarlo"));
                numeroNegativo = validarNegativo(idUsuario);
            } while (numeroNegativo == false);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID de usuario no válido. Debe ser un número.");
            return;
        }

        String email = JOptionPane.showInputDialog("Ingrese email");
        String contrasena;
        boolean repetir;
        do {
            contrasena = JOptionPane.showInputDialog("Ingrese contraseña");
            repetir = validarContraseña(contrasena);
        } while (repetir == true);

        String rol = JOptionPane.showInputDialog("Ingrese rol (usuario, estudiante)");

        // Llama al método save para insertar el usuario en la base de datos
        Usuario.save(nombre, idUsuario, email, contrasena, rol);

        JOptionPane.showMessageDialog(null, "Usuario registrado exitosamente.");
    }


    public static void mostrarMenuUsuario(Usuario usuarioLogueado) {
        String[] opcionesUsuario = {"Curso", "Ir a tienda", "Ver progreso", "Salir"};
        int opcionE;

        do {
            opcionE = JOptionPane.showOptionDialog(null, "(GreenScape) Menú general", "Menú", 0, 
                          JOptionPane.QUESTION_MESSAGE, null, opcionesUsuario, opcionesUsuario[0]);

            switch (opcionE) {
                case 0:
                    if (usuarioLogueado.getRol().equalsIgnoreCase("usuario")) {
                        LinkedList<Curso> cursos = AdministradorJardinero.mostrarCursos();
                        if (cursos.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "No hay cursos registrados.");
                        } else {
                            StringBuilder listaCursos = new StringBuilder();
                            listaCursos.append("Cursos disponibles:\n");
                            for (Curso curso : cursos) {
                                listaCursos.append("ID: ").append(curso.getIdCurso())
                                          .append(", Nombre: ").append(curso.getNombre())
                                          .append(", Información: ").append(curso.getinformacion())
                                          .append("\n");
                            }
                            JOptionPane.showMessageDialog(null, listaCursos.toString());
                        }
                    } else if(usuarioLogueado.getRol().equalsIgnoreCase("jardinero")){
                    	gestionarCurso(usuarioLogueado);
                    }else {
                        JOptionPane.showMessageDialog(null, "Solo los jardineros pueden administrar los cursos.");
                    }
                    break;

                case 1:
                    gestionarTienda(usuarioLogueado);
                    break;

                case 2:
                    mostrarProgreso();
                    break;

                case 3:  
                    JOptionPane.showMessageDialog(null, "Saliendo del menú del usuario.");
                    return;
            }
        } while (true);
    }



    public static void mostrarProyectos() {
        LinkedList<Proyecto> proyectos = Listproyectos.getInstance(); 

        if (proyectos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay proyectos registrados.");
            return;
        }

        StringBuilder listaProyectos = new StringBuilder();
        listaProyectos.append("Proyectos:\n");

        for (Proyecto proyecto : proyectos) {
            listaProyectos.append("ID: ").append(proyecto.getIdProyecto())
                          .append(", Nombre: ").append(proyecto.getNombre())
                          .append(", Fecha de Inicio: ").append(proyecto.getFechaInicio())
                          .append(", Estado: ").append(proyecto.getEstado())
                          .append("\n");
        }

        JOptionPane.showMessageDialog(null, listaProyectos.toString());
    }


    public static void mostrarProgreso() {
        JOptionPane.showMessageDialog(null, "Progreso del usuario.");
     
        // por agregar
    }

    
    // Funcion para el curso el jardinero quiera gestionar los cursos.
    public static void gestionarCurso
    (
    		
     // Le pasamos un objeto por parametro de nombre jardinero de tipo usuario.
     Usuario Jardinero

    )
    {
    	// Logica Acá.
    	
    	String[] opcionesCurso;
    	
    	opcionesCurso = new String[] {"Ver Cursos", "Crear Curso", "Actualizar Curso", "Eliminar Curso", "Salir"}; 
    	
    	int opcionCurso = JOptionPane.showOptionDialog(null, "Opciones", "Gestionamiento de cursos por el Jardinero "+Jardinero.getNombre(),
    			0, JOptionPane.OK_OPTION, null, opcionesCurso, opcionesCurso);

    	
    	switch (opcionCurso) {
		case 0:
			
			//Ver Cursos
			LinkedList<Curso> cursos = AdministradorJardinero.mostrarCursos();
			StringBuilder listaCursos = new StringBuilder();
			
			if (cursos.isEmpty()) {
				JOptionPane.showMessageDialog(null, "No hay cursos.");
				return;
			}
			
			for (Curso curso : cursos) {
				listaCursos.append("ID del Jardinero: ").append(curso.getIdCurso())
				.append(", Nombre: ").append(curso.getNombre())
				.append(", Descripción: ").append(curso.getinformacion())
				.append("\n");
			}
			
			JOptionPane.showMessageDialog(null, listaCursos.toString(), "Lista de los Cursos", JOptionPane.INFORMATION_MESSAGE);
			break;
		case 1:	
			//Crear Curso
			
			String nombreCurso = JOptionPane.showInputDialog("Ingrese nombre del curso");
			
			if (nombreCurso == null || nombreCurso.trim().isEmpty())
			{
				JOptionPane.showMessageDialog(null, "No debe jaber datos vacios");
				return;
			};
			 
			 String descripcion = JOptionPane.showInputDialog("Descripcion");
			
			 if (descripcion == null || descripcion.trim().isEmpty())
			 {
				 JOptionPane.showMessageDialog(null, "No debe haber datos vacios");
				 return;
			 };
			
			Curso curso = new Curso(nombreCurso,descripcion);	
			AdministradorJardinero.crearCurso(curso);
		
			break;
		case 2:
			// Actualizar Curso
			String idDeCursoSTR = JOptionPane.showInputDialog("Ingrese el ID del curso a actualizar");
			if (idDeCursoSTR == null || idDeCursoSTR.trim().isEmpty())
			 {
				 JOptionPane.showMessageDialog(null, "No debe haber datos vacios");
				 return;
			 };
			 int idDeCurso = Integer.parseInt(idDeCursoSTR);
			
			String nuevoNombre = JOptionPane.showInputDialog("Ingrese nuevo nombre del curso");
			if (nuevoNombre == null || nuevoNombre.trim().isEmpty())
			 {
				 JOptionPane.showMessageDialog(null, "No debe jaber datos vacios");
				 return;
			 };
			
			String nuevaDescripcion = JOptionPane.showInputDialog("Ingrese nueva descripcion");
			if (nuevaDescripcion == null || nuevaDescripcion.trim().isEmpty())
			 {
				 JOptionPane.showMessageDialog(null, "No debe haber datos vacios");
				 return;
			 };
			
			Curso cursoActualizado = new Curso(idDeCurso,nuevoNombre,nuevaDescripcion);	
			AdministradorJardinero.actualizarCurso(cursoActualizado);			
			
			break;
		case 3:
			// Eliminar Curso
			String idCursoSTR = JOptionPane.showInputDialog("Ingrese ID de curso a eliminar");
			if (idCursoSTR == null || idCursoSTR.trim().isEmpty())
			 {
				 JOptionPane.showMessageDialog(null, "No debe haber datos vacios");
				 return;
			 };
			 int idCurso = Integer.parseInt(idCursoSTR);
			
			AdministradorJardinero.eliminarCurso(idCurso);
			
			break;
		default:
			break;
		}
    	
    	
    }
    
    // Gestionar tienda usuario
    
    public static void gestionarTienda(Usuario usuarioLogueado) {
        String[] opcionesTienda;

        // Si es usuario, solo puede ver productos y realizar compras
        if (usuarioLogueado.getRol().equalsIgnoreCase("usuario")) {
            opcionesTienda = new String[]{"Ver productos", "Comprar productos", "Salir"};
        } 
        // Si es almacenero, puede ver productos y gestionar los productos
        else if (usuarioLogueado.getRol().equalsIgnoreCase("almacenero")) {
            opcionesTienda = new String[]{"Ver productos", "Agregar producto", "Actualizar producto", "Eliminar producto", "Salir"};
        } else {
            JOptionPane.showMessageDialog(null, "Rol no autorizado para gestionar la tienda.");
            return;
        }

        boolean continuar = true;
        while (continuar) {
            int opcionTienda = JOptionPane.showOptionDialog(null, "Opciones", "Gestionamiento de los productos por el Almacenero "+usuarioLogueado.getNombre(), 
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcionesTienda, opcionesTienda[0]);

            if (opcionTienda == -1 || opcionesTienda[opcionTienda].equals("Salir")) {
                continuar = false;
                continue;
            }

            switch (opcionTienda) {
                case 0: // Ver productos
                    LinkedList<Producto> productos = AdministradorAlmacen.mostrarProducto();
                    StringBuilder listaProductos = new StringBuilder();
                    listaProductos.append("Productos disponibles:\n");

                    for (Producto producto : productos) {
                        listaProductos.append("ID: ").append(producto.getIdProducto())
                                      .append(": ").append(producto.getNombre())
                                      .append(", Descripción: ").append(producto.getDescripcion())
                                      .append(", Stock actual: ").append(producto.getStock())
                                      .append(", Precio: $").append(producto.getPrecio())
                                      .append("\n");
                    }

                    JOptionPane.showMessageDialog(null, listaProductos.toString());
                    break;

                case 1:
                    if (usuarioLogueado.getRol().equalsIgnoreCase("usuario")) {
                        realizarCompra(usuarioLogueado);
                    } else if (usuarioLogueado.getRol().equalsIgnoreCase("almacenero")) {
                        String nombreProducto = JOptionPane.showInputDialog("Ingrese el nombre del producto:");
                        if (nombreProducto == null || nombreProducto.trim().isEmpty()) continue;

                        String descripcion = JOptionPane.showInputDialog("Ingrese una descripción del producto:");
                        if (descripcion == null || descripcion.trim().isEmpty()) continue;

                        String stockStr = JOptionPane.showInputDialog("Ingrese la cantidad en stock:");
                        if (stockStr == null || stockStr.trim().isEmpty()) continue;
                        int stock = Integer.parseInt(stockStr);

                        String precioStr = JOptionPane.showInputDialog("Ingrese el precio del producto:");
                        if (precioStr == null || precioStr.trim().isEmpty()) continue;
                        double precio = Double.parseDouble(precioStr);

                        Producto nuevoProducto = new Producto(nombreProducto, descripcion, stock, precio);
                        AdministradorAlmacen.crearProducto(nuevoProducto);
                    }
                    break;

                case 2:
                    if (usuarioLogueado.getRol().equalsIgnoreCase("almacenero")) {
                        String idProductoStr = JOptionPane.showInputDialog("Ingrese el ID del producto a actualizar:");
                        if (idProductoStr == null || idProductoStr.trim().isEmpty()) continue;
                        int idProducto = Integer.parseInt(idProductoStr);

                        String nuevoNombre = JOptionPane.showInputDialog("Nuevo nombre del producto:");
                        if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) continue;

                        String nuevaDescripcion = JOptionPane.showInputDialog("Nueva descripción del producto:");
                        if (nuevaDescripcion == null || nuevaDescripcion.trim().isEmpty()) continue;

                        String nuevoStockStr = JOptionPane.showInputDialog("Nuevo stock del producto:");
                        if (nuevoStockStr == null || nuevoStockStr.trim().isEmpty()) continue;
                        int nuevoStock = Integer.parseInt(nuevoStockStr);

                        String nuevoPrecioStr = JOptionPane.showInputDialog("Nuevo precio del producto:");
                        if (nuevoPrecioStr == null || nuevoPrecioStr.trim().isEmpty()) continue;
                        double nuevoPrecio = Double.parseDouble(nuevoPrecioStr);

                        Producto productoActualizado = new Producto(idProducto, nuevoNombre, nuevaDescripcion, nuevoStock, nuevoPrecio);
                        AdministradorAlmacen.actualizarProducto(productoActualizado);
                    }
                    break;

                case 3:
                    if (usuarioLogueado.getRol().equalsIgnoreCase("almacenero")) {
                        String idProductoStr = JOptionPane.showInputDialog("Ingrese el ID del producto a eliminar:");
                        if (idProductoStr == null || idProductoStr.trim().isEmpty()) continue;
                        int idProducto = Integer.parseInt(idProductoStr);
                        AdministradorAlmacen.eliminarProducto(idProducto);
                    }
                    break;

                default:
                    break;
            }
        }
    }

    public static void realizarCompra(Usuario usuarioLogueado) {
        LinkedList<Producto> productos = AdministradorAlmacen.mostrarProducto();
        StringBuilder listaProductos = new StringBuilder();
        listaProductos.append("Productos disponibles:\n");

        for (Producto producto : productos) {
            listaProductos.append("ID: ").append(producto.getIdProducto())
                          .append(", Nombre: ").append(producto.getNombre())
                          .append(", Precio: $").append(producto.getPrecio())
                          .append("\n");
        }

        JOptionPane.showMessageDialog(null, listaProductos.toString());

        // Simular compra
        String idProductoStr = JOptionPane.showInputDialog("Ingrese el ID del producto que desea comprar:");
        if (idProductoStr == null || idProductoStr.trim().isEmpty()) return;
        int idProducto = Integer.parseInt(idProductoStr);

        Producto productoSeleccionado = null;
        for (Producto producto : productos) {
            if (producto.getIdProducto() == idProducto) {
                productoSeleccionado = producto;
                break;
            }
        }

        if (productoSeleccionado != null) {
            String cantidadStr = JOptionPane.showInputDialog("Ingrese la cantidad que desea comprar:");
            if (cantidadStr == null || cantidadStr.trim().isEmpty()) return;
            int cantidad = Integer.parseInt(cantidadStr);

            double total = productoSeleccionado.getPrecio() * cantidad;
            JOptionPane.showMessageDialog(null, "Compra realizada exitosamente. Total: $" + total);
        } else {
            JOptionPane.showMessageDialog(null, "Producto no encontrado.");
        }
    }

}






