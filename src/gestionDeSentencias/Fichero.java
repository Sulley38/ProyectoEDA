package gestionDeSentencias;

import java.io.*;

/**
 * Gestión de ficheros:
 * Proporciona métodos para leer y escribir sentencias usando ficheros de texto.
 * @author Daniel, Iván, Asier
 */
public class Fichero {

	// Atributo indicando si se abre el fichero para lectura (f) o para escritura (t)
	private final boolean modoEscritura;
	// Atributos para lectura y escritura
	private final BufferedReader lectura;
	private final BufferedWriter escritura;
	
	/**
	 * Constructora:
	 * Abre el fichero indicado para su posterior lectura/escritura.
	 * @param Ruta del fichero a acceder
	 * @param Escribir true si se quiere abrir en modo escritura
	 * @throws IOException En caso de producirse un error de entrada/salida
	 */
	public Fichero( String fichero, boolean escribir ) throws IOException {
		modoEscritura = escribir;
		if( escribir ) {
			lectura = null;
			escritura = new BufferedWriter( new FileWriter(fichero) );
		} else {
			lectura = new BufferedReader( new FileReader(fichero) );
			escritura = null;
		}
	}
	
	/**
	 * Devuelve una sentencia del fichero que se está leyendo.
	 * Si el fichero fue abierto en modo escritura, devuelve un puntero nulo.
	 * @return Una línea del fichero, null si se ha alcanzado el final
	 * @throws IOException En caso de producirse un error de entrada/salida
	 */
	public String leerSentencia() throws IOException {
		if( modoEscritura )
			return null;
		else
			return lectura.readLine();
	}
	
	/**
	 * Escribe una sentencia dada como parámetro en el fichero abierto.
	 * Si el fichero fue abierto en modo lectura, no hace nada.
	 * @param Sentencia a escribir en una línea
	 * @throws IOException En caso de producirse un error de entrada/salida
	 */
	public void escribirSentencia( String Sentencia ) throws IOException {
		if( modoEscritura ) {
			escritura.write(Sentencia);
			escritura.newLine();
		}
	}
	
	/**
	 * Cierra el fichero que se está utilizando.
	 * @throws IOException En caso de producirse un error de entrada/salida
	 */
	public void cerrar() throws IOException {
		if( modoEscritura )
			escritura.close();
		else
			lectura.close();
	}
	
}
