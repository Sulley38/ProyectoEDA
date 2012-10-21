package gestionDeSentencias;

import java.io.*;

/**
 * Gestión de ficheros:
 * Proporciona métodos estáticos para leer y escribir sentencias usando ficheros de texto.
 * @author Daniel, Iván, Asier
 */
public final class Fichero {

	// Indica si se ha abierto el fichero para lectura (f) o para escritura (t)
	private static boolean modoEscritura;
	// Atributos para lectura y escritura
	private static BufferedReader lectura;
	private static BufferedWriter escritura;
	
	/**
	 * Constructora privada: la clase no se instancia
	 */
	private Fichero() {}
	
	
	/**
	 * Abre el fichero indicado para su posterior lectura/escritura.
	 * Si hay un fichero abierto que aún no se ha cerrado, no hace nada.
	 * @param fichero - ruta del fichero a acceder
	 * @param escribir - true si se quiere abrir en modo escritura
	 * @throws IOException En caso de producirse un error de entrada/salida
	 */
	public static void abrir( String fichero, boolean escribir ) throws IOException {
		modoEscritura = escribir;
		if( escribir )
			escritura = new BufferedWriter( new FileWriter(fichero) );
		else
			lectura = new BufferedReader( new FileReader(fichero) );
	}
	
	/**
	 * Devuelve una sentencia del fichero que se está leyendo.
	 * Si el fichero fue abierto en modo escritura, o no se ha abierto, devuelve un puntero nulo.
	 * @return Una línea del fichero, null si se ha alcanzado el final
	 * @throws IOException En caso de producirse un error de entrada/salida
	 */
	public static String leerSentencia() throws IOException {
		if( modoEscritura )
			return null;
		else
			return lectura.readLine();
	}
	
	/**
	 * Escribe una sentencia dada como parámetro en el fichero abierto.
	 * Si el fichero fue abierto en modo lectura, o no se ha abierto, no hace nada.
	 * @param Sentencia a escribir en una línea
	 * @throws IOException En caso de producirse un error de entrada/salida
	 */
	public static void escribirSentencia( String Sentencia ) throws IOException {
		if( modoEscritura ) {
			escritura.write(Sentencia);
			escritura.newLine();
		}
	}
	
	/**
	 * Cierra el fichero que se está utilizando.
	 * Si no hay ningún fichero abierto, no hace nada.
	 * @throws IOException En caso de producirse un error de entrada/salida
	 */
	public static void cerrar() throws IOException {
		if( modoEscritura )
			escritura.close();
		else
			lectura.close();
	}
	
}
