package gestionDeSentencias;

import java.io.*;
import java.util.*;
import estructurasDeDatos.*;

/**
 * Almacén de sentencias:
 * Contiene todas las sentencias de un fichero y las operaciones definidas para trabajar sobre ellas.
 * @author Daniel, Iván, Asier
 */
public class Almacen {

	/**
	 * Estructura de arista del grafo.
	 * Indica el vértice ligado, su peso (la propiedad) y el número de veces que aparece repetida.
	 */
	private class Arista implements Comparable<Arista> {
		// Atributos
		private final int verticeObjetivo, arista;
		private int repeticiones;
		// Constructora
		public Arista(final int Objetivo, final int Propiedad) {
			verticeObjetivo = Objetivo;
			arista = Propiedad;
			repeticiones = 1;
		}
		// Comparadora en orden lexicográfico según propiedades y objetos
		@Override
		public int compareTo(final Arista a) {
			if( this.arista == a.arista )
				return listaSujetosObjetos.getElementByPosition(this.verticeObjetivo).compareTo(listaSujetosObjetos.getElementByPosition(a.verticeObjetivo));
			else
				return listaPropiedades.getElementByPosition(this.arista).compareTo(listaPropiedades.getElementByPosition(a.arista));
		}
		// Comparadora de los valores de la arista
		@Override
		public boolean equals(final Object a) {
			return (verticeObjetivo == ((Arista)a).verticeObjetivo) && (arista == ((Arista)a).arista);
		}
	}
	
	/// ATRIBUTOS DE LA CLASE
	// Dos listas de adyacencia para representar el grafo.
	private ListaArray< ListaEnlazada<Arista> > nodosEntrantes, nodosSalientes;
	// Número de nodos (sujetos+objetos) y de aristas (propiedades); la suma de los tres es el número de entidades
	private int sujetos, objetos, propiedades;
	// Relaciones entre entidad e índice correspondiente (Trie), y viceversa (array)
	private Trie arbolSujetosObjetos, arbolPropiedades;
	private ListaArray<String> listaSujetosObjetos, listaPropiedades;
	
	
	/**
	 * Constructora
	 * Carga las sentencias en el almacén desde el fichero especificado.
	 * @param nombreDeArchivo de texto desde el que leer las entidades
	 */
	public Almacen( String nombreDeArchivo ) {
		// Inicializa los atributos de la clase
		nodosEntrantes = new ListaArray< ListaEnlazada<Arista> >(250000);
		nodosSalientes = new ListaArray< ListaEnlazada<Arista> >(250000);
		sujetos = objetos = propiedades = 0;
		arbolSujetosObjetos = new Trie();
		arbolPropiedades = new Trie();
		listaSujetosObjetos = new ListaArray<String>(250000);
		listaPropiedades = new ListaArray<String>(100);
		// Estructura temporal para insertar las sentencias
		Arista tempArista;
		// Lee las sentencias desde el fichero y las añade al trie y a la lista de nodos del grafo
		try {
			Fichero.abrir(nombreDeArchivo,false);
			String sentencia, sujeto, propiedad, objeto;
			int idSujeto, idPropiedad, idObjeto;
			StringTokenizer tokenizador;
			while( (sentencia = Fichero.leerSentencia()) != null ) {
				tokenizador = new StringTokenizer(sentencia);
				sujeto = tokenizador.nextToken();
				propiedad = tokenizador.nextToken();
				objeto = tokenizador.nextToken();
				
				// Insertar sujeto en el trie
				idSujeto = arbolSujetosObjetos.insertar(sujeto, sujetos+objetos);
				if( idSujeto == sujetos+objetos ) {
					// Agregar al array de int -> String
					listaSujetosObjetos.insertLast(sujeto);
					// Añadir su hueco en las listas de adyacencia
					nodosEntrantes.insertLast( new ListaEnlazada<Arista>() );
					nodosSalientes.insertLast( new ListaEnlazada<Arista>() );
					// Incrementar contador
					sujetos++;
				}
				// Insertar propiedad en el trie
				idPropiedad = arbolPropiedades.insertar(propiedad, propiedades);
				if( idPropiedad == propiedades ) {
					// Agregar al array de int -> String
					listaPropiedades.insertLast(propiedad);
					// Incrementar contador
					propiedades++;
				}
				// Insertar objeto en el trie
				idObjeto = arbolSujetosObjetos.insertar(objeto, sujetos+objetos);
				if( idObjeto == sujetos+objetos ) {
					// Agregar al array de int -> String
					listaSujetosObjetos.insertLast(objeto);
					// Añadir su hueco en las listas de adyacencia
					nodosEntrantes.insertLast( new ListaEnlazada<Arista>() );
					nodosSalientes.insertLast( new ListaEnlazada<Arista>() );
					// Incrementar contador
					objetos++;
				}
				
				// Inserta la arista en la primera lista de adyacencia, o añade una repetición
				tempArista = nodosSalientes.getElementByPosition(idSujeto).getElementByValue( new Arista(idObjeto,idPropiedad) );
				if( tempArista == null )
					nodosSalientes.getElementByPosition(idSujeto).insertOrdered( new Arista(idObjeto,idPropiedad) );
				else
					tempArista.repeticiones++;
				
				// Inserta la arista en la segunda lista de adyacencia, o añade una repetición
				tempArista = nodosEntrantes.getElementByPosition(idObjeto).getElementByValue( new Arista(idSujeto,idPropiedad) );
				if( tempArista == null )
					nodosEntrantes.getElementByPosition(idObjeto).insertOrdered( new Arista(idSujeto,idPropiedad) );
				else
					tempArista.repeticiones++;
			}
	
			Fichero.cerrar();
		} catch (IOException e) {
			System.err.println("Error: Imposible acceder al fichero especificado.");
			return;
		}

	}
	
	/**
	 * 1) Colección de sentencias del almacén que tienen un sujeto determinado.
	 * @param sujeto - sujeto cuyas sentencias han de devolverse
	 * @return Una lista enlazada de sentencias que tienen Sujeto como sujeto
	 */
	public ListaEnlazada<String> sentenciasPorSujeto( String sujeto ) {	
		ListaEnlazada<String> coleccionSentencias = new ListaEnlazada<String>();
		int index = arbolSujetosObjetos.obtenerValor(sujeto);
		if( index != -1 ) {
			// Si el sujeto no existe, devuelve una lista vacía
			ListaEnlazada.Iterador<Arista> it = new ListaEnlazada.Iterador<Arista>();
			it.load( nodosSalientes.getElementByPosition(index) );
			Arista prov;
			while( it.hasNext() ) {
				prov = it.next();
				for( int i = 0; i < prov.repeticiones; i++ )
					coleccionSentencias.insertLast( listaSujetosObjetos.getElementByPosition(index) + " " +  listaPropiedades.getElementByPosition(prov.arista) + " " + listaSujetosObjetos.getElementByPosition(prov.verticeObjetivo) + " ." );				
			}
		}
		return coleccionSentencias;
	}
	
	/**
	 * 2) Colección de sentencias distintas del almacén que tienen un sujeto determinado.
	 * @param sujeto - sujeto cuyas sentencias han de devolverse
	 * @return Una lista enlazada de sentencias sin repeticiones que tienen Sujeto como sujeto
	 */
	public ListaEnlazada<String> sentenciasDistintasPorSujeto( String sujeto ) {
		ListaEnlazada<String> coleccionSentencias = new ListaEnlazada<String>();
		int index = arbolSujetosObjetos.obtenerValor(sujeto);
		if( index != -1 ) {
			// Si el sujeto no existe, devuelve una lista vacía
			ListaEnlazada.Iterador<Arista> it = new ListaEnlazada.Iterador<Arista>();
			it.load( nodosSalientes.getElementByPosition(index) );
			Arista prov;
			while( it.hasNext() ) {
				prov = it.next();
				coleccionSentencias.insertLast( listaSujetosObjetos.getElementByPosition(index) + " " +  listaPropiedades.getElementByPosition(prov.arista) + " " + listaSujetosObjetos.getElementByPosition(prov.verticeObjetivo) + " ." );
			}
		}
		return coleccionSentencias;
	}
	
	/**
	 * 3) Colección de propiedades distintas que aparecen en las sentencias del almacén.
	 * @return Un array de las propiedades sin repeticiones que aparecen en el almacén
	 */
	public ListaArray<String> propiedadesDistintas() {
		return listaPropiedades;
	}
	
	/**
	 * 4) Colección de entidades distintas que son sujeto de alguna sentencia y también
	 * 	  son objeto de alguna sentencia de ese almacén.
	 * @return Una lista enlazada de strings sin repeticiones que son sujeto y a la vez objeto en el almacén
	 */
	public ListaEnlazada<String> entidadesSujetoObjeto() {
		ListaEnlazada<String> coleccionEntidades = new ListaEnlazada<String>();
		for( int i = 0; i < nodosSalientes.size(); i++ )
			if( !nodosSalientes.getElementByPosition(i).isEmpty() && !nodosEntrantes.getElementByPosition(i).isEmpty() )
				coleccionEntidades.insertLast( listaSujetosObjetos.getElementByPosition(i) );
		
		return coleccionEntidades;
	}
	
}
