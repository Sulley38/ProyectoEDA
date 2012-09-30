/**
 * Almacén de sentencias:
 * Contiene todas las sentencias de un fichero y las operaciones definidas para trabajar sobre ellas
 * @author Daniel, Iván, Asier
 */
import java.io.*;
import java.util.*;

public class Almacen {

	// Matriz de adyacencia
	// Puede que haga falta cambiar el tipo de dato contenido, de int a 'Lista de enteros'
	// i -> Sujeto, j -> Objeto, dato -> Propiedad
	private int[][] relaciones;
	// Número de nodos (sujetos+objetos) y de aristas (propiedades)
	private int sujetos, objetos, propiedades;
	// Relaciones biyectivas entre índices y entidades correspondientes
	/***
	 * TODO: Implementar una estructura de datos que permita encontrar
	 *	de forma eficiente el índice dado un string (tabla hash?)
	 **/
	
	
	/**
	 * CONSTRUCTORA
	 * @param Fichero de texto desde el que leer las entidades
	 */
	public Almacen( String Ruta ) {
		sujetos = objetos = propiedades = 0;
		// Lee las sentencias desde el fichero
		try {
			Fichero.abrir(Ruta, false);
			String sentencia;
			StringTokenizer tokenizador;
			while( (sentencia = Fichero.leerSentencia()) != null ) {
				tokenizador = new StringTokenizer(sentencia);
				// TODO: Añadir las entidades a la estructura que las relaciona con un índice
			}
			Fichero.cerrar();
		} catch (IOException e) {
			System.err.println("Error: Imposible acceder al fichero especificado.");
		}
	}
	
	
	/**
	 * 1) Colección de sentencias del almacén que tienen un sujeto determinado
	 * @param Sujeto
	 * @return
	 */
	public String[] sentenciasPorSujeto( String Sujeto ) {
		// TODO: Implementar
		return null;
	}
	
	/**
	 * 2) Colección de sentencias distintas del almacén que tienen un sujeto determinado
	 * @param Sujeto
	 * @return
	 */
	public String[] sentenciasDistintasPorSujeto( String Sujeto ) {
		// TODO: Implementar
		return null;
	}
	
	/**
	 * 3) Colección de propiedades distintas que aparecen en las sentencias del almacén
	 * @return
	 */
	public String[] propiedadesDistintas() {
		// TODO: Implementar
		return null;
	}
	
	/**
	 * 4) Colección de entidades distintas que son sujeto de alguna sentencia y también
	 * son objeto de alguna sentencia de ese almacén
	 * @return
	 */
	public String[] entidadesSujetoObjeto() {
		// TODO: Implementar
		return null;
	}
	
}
