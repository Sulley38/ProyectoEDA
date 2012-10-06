package gestionDeSentencias;

import java.io.*;

/**
 * Gestión de ficheros:
 * Proporciona métodos estáticos para leer y escribir sentencias usando ficheros de texto.
 * @author Daniel, Iván, Asier
 */
public class Fichero {

	// Atributo indicando si se abre el fichero para lectura (f) o para escritura (t)
	private static boolean modoEscritura;
	// Atributos para lectura y escritura
	private static BufferedReader lectura;
	private static BufferedWriter escritura;
	
	/**
	 * Constructora
	 */
	private Fichero() {}
	
	
	/**
	 * Abre el fichero indicado para su posterior lectura/escritura.
	 * @param Ruta del fichero a acceder
	 * @param Escribir true si se quiere abrir en modo escritura
	 * @throws IOException En caso de producirse un error de entrada/salida
	 */
	public static void abrir( String Ruta, boolean Escribir ) throws IOException {
		modoEscritura = Escribir;
		if( Escribir )
			escritura = new BufferedWriter( new FileWriter(Ruta) );
		else
			lectura = new BufferedReader( new FileReader(Ruta) );
	}
	
	/**
	 * Devuelve una sentencia del fichero que se está leyendo.
	 * Si el fichero fue abierto en modo escritura, devuelve un puntero nulo.
	 * @return Una línea del fichero, null si se ha alcanzado el final
	 * @throws IOException En caso de producirse un error de entrada/salida
	 */
	public static String leerSentencia() throws IOException {
		if( !modoEscritura )
			return lectura.readLine();
		else
			return null;
	}
	
	/**
	 * Escribe una sentencia dada como parámetro en el fichero abierto.
	 * Si el fichero fue abierto en modo lectura, no hace nada.
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
	 * @throws IOException En caso de producirse un error de entrada/salida
	 */
	public static void cerrar() throws IOException {
		if( modoEscritura )
			escritura.close();
		else
			lectura.close();
	}
	
}
