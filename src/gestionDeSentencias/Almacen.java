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
	private class Arista {
		// Atributos
		private int verticeObjetivo, arista, repeticiones;
		// Constructora
		public Arista(int Objetivo, int Propiedad) {
			verticeObjetivo = Objetivo;
			arista = Propiedad;
			repeticiones = 1;
		}
	}
	
	/// ATRIBUTOS DE LA CLASE
	// Dos listas de adyacencia para representar el grafo.
	private ListaArray<ListaEnlazada<Arista>> nodosEntrantes, nodosSalientes;
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
		sujetos = objetos = propiedades = 0;
		arbolSujetosObjetos = new Trie();
		arbolPropiedades = new Trie();
		//Listas enlazadas temporales antes de conocer el tamaño de los arrays definitivos
		ListaArray<String> tempSujetosObjetos = new ListaArray<String>(250000);
		ListaArray<String> tempPropiedades = new ListaArray<String>(100);
		ListaArray< ListaEnlazada<Arista> > tempNodosEntrantes = new ListaArray< ListaEnlazada<Arista> >(250000);
		ListaArray< ListaEnlazada<Arista> > tempNodosSalientes = new ListaArray< ListaEnlazada<Arista> >(250000);
		ListaEnlazada.Iterador<Arista> iterador = new ListaEnlazada.Iterador<Arista>();
		ListaEnlazada<Arista> tempLista;
		Arista tempArista;
		boolean encontrado;
		// Lee las sentencias desde el fichero y las añade al trie y a la lista de nodos del grafo
		try {
			Fichero f = new Fichero(nombreDeArchivo,false);
			String sentencia, sujeto, propiedad, objeto;
			int idSujeto, idPropiedad, idObjeto;
			StringTokenizer tokenizador;
			while( (sentencia = f.leerSentencia()) != null ) {
				tokenizador = new StringTokenizer(sentencia);
				sujeto = tokenizador.nextToken();
				propiedad = tokenizador.nextToken();
				objeto = tokenizador.nextToken();
				
				// Insertar sujeto
				idSujeto = arbolSujetosObjetos.insertar(sujeto, sujetos+objetos);
				if( idSujeto == sujetos+objetos ) {
					// Agregar al array de int -> String
					tempSujetosObjetos.insertLast(sujeto);					
					// Añadir su hueco en la lista de nodos entrantes y salientes
					tempNodosEntrantes.insertLast( new ListaEnlazada<Arista>() );
					tempNodosSalientes.insertLast( new ListaEnlazada<Arista>() );
					// Incrementar contador
					sujetos++;
				}
				// Insertar propiedad
				idPropiedad = arbolPropiedades.insertar(propiedad, propiedades);
				if( idPropiedad == propiedades ) {
					// Agregar al array de int -> String
					tempPropiedades.insertLast(propiedad);
					// Incrementar contador
					propiedades++;
				}
				// Insertar objeto
				idObjeto = arbolSujetosObjetos.insertar(objeto, sujetos+objetos);
				if( idObjeto == sujetos+objetos ) {
					// Agregar al array de int -> String
					tempSujetosObjetos.insertLast(objeto);
					// Añadir su hueco en la lista de nodos entrantes y salientes
					tempNodosEntrantes.insertLast( new ListaEnlazada<Arista>() );
					tempNodosSalientes.insertLast( new ListaEnlazada<Arista>() );
					// Incrementar contador
					objetos++;
				}
				
				// Añadir la relación a la lista de adyacencia
				encontrado = false;
				tempLista = tempNodosSalientes.getElementByPosition(idSujeto);
				iterador.load(tempLista);
				while( iterador.hasNext() ) {
					tempArista = iterador.next();
					if( tempArista.verticeObjetivo == idObjeto && tempArista.arista == idPropiedad ) {
						tempArista.repeticiones++;
						encontrado = true;
						break;
					}
				}
				//si no lo ha encontrado crea la arista
				if( !encontrado )
					tempLista.insertLast(new Arista(idObjeto,idPropiedad));
				
				// Lo mismo con la otra lista de adyacencia
				encontrado = false;
				tempLista = tempNodosEntrantes.getElementByPosition(idObjeto);
				iterador.load(tempLista);
				while( iterador.hasNext() ) {
					tempArista = iterador.next();
					if( tempArista.verticeObjetivo == idSujeto && tempArista.arista == idPropiedad ) {
						tempArista.repeticiones++;
						encontrado = true;
						break;
					}
				}
				//si no lo ha encontrado crea la arista
				if( !encontrado )
					tempLista.insertLast(new Arista(idSujeto,idPropiedad));
			}
	
			f.cerrar();
		} catch (IOException e) {
			System.err.println("Error: Imposible acceder al fichero especificado.");
			return;
		}
		
		nodosEntrantes = tempNodosEntrantes;
		nodosSalientes = tempNodosSalientes;
		listaSujetosObjetos = tempSujetosObjetos;
		listaPropiedades = tempPropiedades;
	}
	
	/**
	 * 1) Colección de sentencias del almacén que tienen un sujeto determinado.
	 * @param Sujeto - sujeto cuyas sentencias han de devolverse
	 * @return Una lista enlazada de sentencias que tienen Sujeto como sujeto
	 */
	public ListaEnlazada<String> sentenciasPorSujeto( String Sujeto ) {	
		ListaEnlazada<String> coleccionSentencias = new ListaEnlazada<String>();
		int index = arbolSujetosObjetos.obtenerValor(Sujeto);
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
	 * @param Sujeto - sujeto cuyas sentencias han de devolverse
	 * @return Una lista enlazada de sentencias sin repeticiones que tienen Sujeto como sujeto
	 */
	public ListaEnlazada<String> sentenciasDistintasPorSujeto( String Sujeto ) {
		ListaEnlazada<String> coleccionSentencias = new ListaEnlazada<String>();
		int index = arbolSujetosObjetos.obtenerValor(Sujeto);
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
