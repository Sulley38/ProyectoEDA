package gestionDeSentencias;
import java.io.*;
import java.util.*;
import estructurasDeDatos.*;

/**
 * Almac�n de sentencias:
 * Contiene todas las sentencias de un fichero y las operaciones definidas para trabajar sobre ellas
 * @author Daniel, Iv�n, Asier
 */
public class Almacen {

	/**
	 * Estructura de arista del grafo.
	 * Indica el v�rtice de destino, su peso (la propiedad) y el n�mero de veces que aparece repetida
	 */
	private class Arista {
		private int verticeObjetivo, arista, repeticiones;
		public Arista(int Objeto, int Propiedad) {
			verticeObjetivo = Objeto;
			arista = Propiedad;
			repeticiones = 0;
		}
	}
	
	/// ATRIBUTOS DE LA CLASE
	// Dos listas de adyacencia para representar el grafo.
	private ListaEnlazada<Arista> nodosEntrantes[], nodosSalientes[]; // nodosEntrantes[i][j] devuelve la j-�sima relaci�n del nodo i, cuyos valores son nodosEntrantes[i][j].nodoObjetivo, etc. 
	// N�mero de nodos (sujetos+objetos) y de aristas (propiedades); la suma de los tres es el n�mero de entidades
	private int sujetos, objetos, propiedades;
	// Relaciones entre entidad e �ndice correspondiente (Trie), y viceversa (array)
	private Trie arbolSujetosObjetos, arbolPropiedades;
	private String listaSujetosObjetos[], listaPropiedades[];
	
	
	/**
	 * CONSTRUCTORA
	 * Carga las sentencias en el almac�n desde el fichero especificado.
	 * @param nombreDeArchivo de texto desde el que leer las entidades
	 */
	public Almacen( String nombreDeArchivo ) {
		sujetos = objetos = propiedades = 0;
		arbolSujetosObjetos = new Trie();
		arbolPropiedades = new Trie();
		// Listas enlazadas temporales antes de conocer el tama�o de los arrays definitivos
		ListaEnlazada<String> tempSujetosObjetos = new ListaEnlazada<String>();
		ListaEnlazada<String> tempPropiedades = new ListaEnlazada<String>();
		ListaEnlazada< ListaEnlazada<Arista> > tempNodosEntrantes = new ListaEnlazada< ListaEnlazada<Arista> >();
		ListaEnlazada< ListaEnlazada<Arista> > tempNodosSalientes = new ListaEnlazada< ListaEnlazada<Arista> >();
		// Lee las sentencias desde el fichero y las a�ade al trie y a la lista de nodos del grafo
		try {
			Fichero.abrir(nombreDeArchivo, false);
			String sentencia, sujeto, propiedad, objeto;
			int idSujeto, idPropiedad, idObjeto;
			StringTokenizer tokenizador;
			while( (sentencia = Fichero.leerSentencia()) != null ) {
				tokenizador = new StringTokenizer(sentencia);
				sujeto = tokenizador.nextToken();
				propiedad = tokenizador.nextToken();
				objeto = tokenizador.nextToken();
				// MONTAR EL GRAFO: Necesita revisi�n
				if( arbolSujetosObjetos.insertar(sujeto, sujetos+objetos) ) {
					tempSujetosObjetos.insertLast(sujeto);
					// A�adir su hueco en la lista de nodos entrantes y salientes
					tempNodosEntrantes.insertLast( new ListaEnlazada<Arista>() );
					tempNodosSalientes.insertLast( new ListaEnlazada<Arista>() );
					// Incrementar contador
					idSujeto = sujetos+objetos;
					sujetos++;
				} else {
					idSujeto = arbolSujetosObjetos.obtenerValor(sujeto); // Esto se puede mejorar si haces que insertar() devuelva el valor de la ya insertada en lugar de true/false
				}
				if( arbolPropiedades.insertar(propiedad, propiedades) ) {
					tempPropiedades.insertLast(propiedad);
					// Incrementar contador
					idPropiedad = propiedades;
					propiedades++;
				} else {
					idPropiedad = arbolPropiedades.obtenerValor(propiedad);
				}
				if( arbolSujetosObjetos.insertar(objeto, sujetos+objetos) ) {
					tempSujetosObjetos.insertLast(objeto);
					// A�adir su hueco en la lista de nodos entrantes y salientes
					tempNodosEntrantes.insertLast( new ListaEnlazada<Arista>() );
					tempNodosSalientes.insertLast( new ListaEnlazada<Arista>() );
					// Incrementar contador
					idObjeto = sujetos+objetos;
					objetos++;
				} else {
					idObjeto = arbolSujetosObjetos.obtenerValor(objeto);
				}
				
				// Insertar la relaci�n en sus sitios
				// Ir al nodo en tempNodosEntrantes, buscar en la lista si existe una relaci�n con el otro nodo
				// Si existe, aumentar repeticiones. Si no existe, a�adirla al final de la lista: insertLast( new Relacion(foo,bar) );
				// Lo mismo con tempNodosSalientes
				// Salgo de trabajar a las 22.00, antes de irme a dormir puede que me de tiempo a echarle un vistazo por encima
				
			}
			Fichero.cerrar();
		} catch (IOException e) {
			System.err.println("Error: Imposible acceder al fichero especificado.");
			return;
		}
		
		// TODO: Pasar las listas enlazadas a arrays est�ticos
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
