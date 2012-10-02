package gestionDeSentencias;
import java.io.*;
import java.util.*;

/**
 * Almac�n de sentencias:
 * Contiene todas las sentencias de un fichero y las operaciones definidas para trabajar sobre ellas
 * @author Daniel, Iv�n, Asier
 */
public class Almacen {

	// Matriz de adyacencia
	// Puede que haga falta cambiar el tipo de dato contenido, de int a 'Lista de enteros'
	// i -> Sujeto, j -> Objeto, dato -> Propiedad
	private int[][] relaciones;
	// N�mero de nodos (sujetos+objetos) y de aristas (propiedades)
	private int sujetos, objetos, propiedades;
	// Relaciones biyectivas entre entidad (string) e �ndice correspondiente
	private Trie entidades;
	
	/**
	 * CONSTRUCTORA
	 * @param Fichero de texto desde el que leer las entidades
	 */
	public Almacen( String Ruta ) {
		sujetos = objetos = propiedades = 0;
		entidades = new Trie();
		// Lee las sentencias desde el fichero y las a�ade al trie
		try {
			Fichero.abrir(Ruta, false);
			String sentencia;
			StringTokenizer tokenizador;
			while( (sentencia = Fichero.leerSentencia()) != null ) {
				tokenizador = new StringTokenizer(sentencia);
				sujetos += entidades.insertar( tokenizador.nextToken() );
				propiedades += entidades.insertar( tokenizador.nextToken() );
				objetos += entidades.insertar( tokenizador.nextToken() );
			}
			Fichero.cerrar();
		} catch (IOException e) {
			System.err.println("Error: Imposible acceder al fichero especificado.");
		}
		// Crea la matriz de adyacencia con el espacio necesario y la inicializa a -1
		relaciones = new int[sujetos+objetos][sujetos+objetos];
		for( int i = 0; i < sujetos+objetos; ++i )
			for( int j = 0; j < sujetos+objetos; ++j )
				relaciones[i][j] = -1;
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
