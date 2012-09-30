/**
 * Almac�n de sentencias:
 * Contiene todas las sentencias de un fichero y las operaciones definidas para trabajar sobre ellas
 * @author Daniel, Iv�n, Asier
 */
import java.io.*;
import java.util.*;

public class Almacen {

	// Matriz de adyacencia
	// Puede que haga falta cambiar el tipo de dato contenido, de int a 'Lista de enteros'
	// i -> Sujeto, j -> Objeto, dato -> Propiedad
	private int[][] relaciones;
	// N�mero de nodos (sujetos+objetos) y de aristas (propiedades)
	private int sujetos, objetos, propiedades;
	// Relaciones biyectivas entre �ndices y entidades correspondientes
	/***
	 * TODO: Implementar una estructura de datos que permita encontrar
	 *	de forma eficiente el �ndice dado un string (tabla hash?)
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
				// TODO: A�adir las entidades a la estructura que las relaciona con un �ndice
			}
			Fichero.cerrar();
		} catch (IOException e) {
			System.err.println("Error: Imposible acceder al fichero especificado.");
		}
	}
	
	
	/**
	 * 1) Colecci�n de sentencias del almac�n que tienen un sujeto determinado
	 * @param Sujeto
	 * @return
	 */
	public String[] sentenciasPorSujeto( String Sujeto ) {
		// TODO: Implementar
		return null;
	}
	
	/**
	 * 2) Colecci�n de sentencias distintas del almac�n que tienen un sujeto determinado
	 * @param Sujeto
	 * @return
	 */
	public String[] sentenciasDistintasPorSujeto( String Sujeto ) {
		// TODO: Implementar
		return null;
	}
	
	/**
	 * 3) Colecci�n de propiedades distintas que aparecen en las sentencias del almac�n
	 * @return
	 */
	public String[] propiedadesDistintas() {
		// TODO: Implementar
		return null;
	}
	
	/**
	 * 4) Colecci�n de entidades distintas que son sujeto de alguna sentencia y tambi�n
	 * son objeto de alguna sentencia de ese almac�n
	 * @return
	 */
	public String[] entidadesSujetoObjeto() {
		// TODO: Implementar
		return null;
	}
	
}
