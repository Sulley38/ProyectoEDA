package gestionDeSentencias;

import java.io.*;
import java.util.*;
import estructurasDeDatos.*;

/**
 * Almac�n de sentencias:
 * Contiene todas las sentencias de un fichero y las operaciones definidas para trabajar sobre ellas.
 * @author Daniel, Iv�n, Asier
 */
public class Almacen {

	/**
	 * Estructura de arista del grafo.
	 * Indica el v�rtice ligado, su peso (la propiedad) y el n�mero de veces que aparece repetida.
	 */
	private class Arista {
		// Atributos
		private final int verticeObjetivo, arista;
		private int repeticiones;
		// Constructora
		public Arista(final int Objetivo, final int Propiedad) {
			verticeObjetivo = Objetivo;
			arista = Propiedad;
			repeticiones = 1;
		}
	}
	
	/// ATRIBUTOS DE LA CLASE
	// Dos listas de adyacencia para representar el grafo.
	private ListaArray< ListaEnlazada<Arista> > nodosEntrantes, nodosSalientes;
	// N�mero de nodos (sujetos+objetos) y de aristas (propiedades); la suma de los tres es el n�mero de entidades
	private int sujetos, objetos, propiedades;
	// Relaciones entre entidad e �ndice correspondiente (Trie), y viceversa (array)
	private Trie arbolSujetosObjetos, arbolPropiedades;
	private ListaArray<String> listaSujetosObjetos, listaPropiedades;
	
	
	/**
	 * Constructora
	 * Carga las sentencias en el almac�n desde el fichero especificado.
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
		// Estructuras temporales para insertar las sentencias
		ListaEnlazada.Iterador<Arista> iterador = new ListaEnlazada.Iterador<Arista>();
		ListaEnlazada<Arista> tempLista;
		Arista tempArista;
		boolean encontrado;
		// Lee las sentencias desde el fichero y las a�ade al trie y a la lista de nodos del grafo
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
					// A�adir su hueco en las listas de adyacencia
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
					// A�adir su hueco en las listas de adyacencia
					nodosEntrantes.insertLast( new ListaEnlazada<Arista>() );
					nodosSalientes.insertLast( new ListaEnlazada<Arista>() );
					// Incrementar contador
					objetos++;
				}
				
				// Buscar la arista en la primera lista de adyacencia
				encontrado = false;
				tempLista = nodosSalientes.getElementByPosition(idSujeto);
				iterador.load(tempLista);
				while( !encontrado && iterador.hasNext() ) {
					tempArista = iterador.next();
					if( tempArista.verticeObjetivo == idObjeto && tempArista.arista == idPropiedad ) {
						tempArista.repeticiones++;
						encontrado = true;
					}
				}
				// Si no se encuentra, a�adirla
				if( !encontrado )
					tempLista.insertLast(new Arista(idObjeto,idPropiedad));
				
				// Buscar la arista en la segunda lista de adyacencia
				encontrado = false;
				tempLista = nodosEntrantes.getElementByPosition(idObjeto);
				iterador.load(tempLista);
				while( !encontrado && iterador.hasNext() ) {
					tempArista = iterador.next();
					if( tempArista.verticeObjetivo == idSujeto && tempArista.arista == idPropiedad ) {
						tempArista.repeticiones++;
						encontrado = true;
					}
				}
				// Si no se encuentra, a�adirla
				if( !encontrado )
					tempLista.insertLast(new Arista(idSujeto,idPropiedad));
			}
	
			Fichero.cerrar();
		} catch (IOException e) {
			System.err.println("Error: Imposible acceder al fichero especificado.");
			return;
		}

	}
	
	/**
	 * 1) Colecci�n de sentencias del almac�n que tienen un sujeto determinado.
	 * @param sujeto - sujeto cuyas sentencias han de devolverse
	 * @return Una lista enlazada de sentencias que tienen Sujeto como sujeto
	 */
	public ListaEnlazada<String> sentenciasPorSujeto( String sujeto ) {	
		ListaEnlazada<String> coleccionSentencias = new ListaEnlazada<String>();
		int index = arbolSujetosObjetos.obtenerValor(sujeto);
		if( index != -1 ) {
			// Si el sujeto no existe, devuelve una lista vac�a
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
	 * 2) Colecci�n de sentencias distintas del almac�n que tienen un sujeto determinado.
	 * @param sujeto - sujeto cuyas sentencias han de devolverse
	 * @return Una lista enlazada de sentencias sin repeticiones que tienen Sujeto como sujeto
	 */
	public ListaEnlazada<String> sentenciasDistintasPorSujeto( String sujeto ) {
		ListaEnlazada<String> coleccionSentencias = new ListaEnlazada<String>();
		int index = arbolSujetosObjetos.obtenerValor(sujeto);
		if( index != -1 ) {
			// Si el sujeto no existe, devuelve una lista vac�a
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
	 * 3) Colecci�n de propiedades distintas que aparecen en las sentencias del almac�n.
	 * @return Un array de las propiedades sin repeticiones que aparecen en el almac�n
	 */
	public ListaArray<String> propiedadesDistintas() {
		return listaPropiedades;
	}
	
	/**
	 * 4) Colecci�n de entidades distintas que son sujeto de alguna sentencia y tambi�n
	 * 	  son objeto de alguna sentencia de ese almac�n.
	 * @return Una lista enlazada de strings sin repeticiones que son sujeto y a la vez objeto en el almac�n
	 */
	public ListaEnlazada<String> entidadesSujetoObjeto() {
		ListaEnlazada<String> coleccionEntidades = new ListaEnlazada<String>();
		for( int i = 0; i < nodosSalientes.size(); i++ )
			if( !nodosSalientes.getElementByPosition(i).isEmpty() && !nodosEntrantes.getElementByPosition(i).isEmpty() )
				coleccionEntidades.insertLast( listaSujetosObjetos.getElementByPosition(i) );
		
		return coleccionEntidades;
	}
	
}
