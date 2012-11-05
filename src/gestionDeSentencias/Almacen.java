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
				return listaSujetosObjetos.elementAt(this.verticeObjetivo).compareTo(listaSujetosObjetos.elementAt(a.verticeObjetivo));
			else
				return listaPropiedades.elementAt(this.arista).compareTo(listaPropiedades.elementAt(a.arista));
		}
		// Comparadora de los valores de la arista
		@Override
		public boolean equals(final Object a) {
			return (verticeObjetivo == ((Arista)a).verticeObjetivo) && (arista == ((Arista)a).arista);
		}
	}
	
	/// ATRIBUTOS DE LA CLASE
	// Dos listas de adyacencia para representar el grafo.
	private ListaArray< ListaArray<Arista> > nodosEntrantes, nodosSalientes;
	// Número de nodos (sujetos+objetos), de aristas (propiedades) y de sentencias; la suma de los tres primeros es el número de entidades
	private int sujetos, objetos, propiedades, sentencias;
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
		nodosEntrantes = new ListaArray< ListaArray<Arista> >();
		nodosSalientes = new ListaArray< ListaArray<Arista> >();
		sujetos = objetos = propiedades = sentencias = 0;
		arbolSujetosObjetos = new Trie();
		arbolPropiedades = new Trie();
		listaSujetosObjetos = new ListaArray<String>();
		listaPropiedades = new ListaArray<String>();
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
				sentencias++;
				
				// Insertar sujeto en el trie
				idSujeto = arbolSujetosObjetos.insertar(sujeto, sujetos+objetos);
				if( idSujeto == sujetos+objetos ) {
					// Agregar al array de int -> String
					listaSujetosObjetos.insertLast(sujeto);
					// Añadir su hueco en las listas de adyacencia
					nodosEntrantes.insertLast( new ListaArray<Arista>() );
					nodosSalientes.insertLast( new ListaArray<Arista>() );
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
					nodosEntrantes.insertLast( new ListaArray<Arista>() );
					nodosSalientes.insertLast( new ListaArray<Arista>() );
					// Incrementar contador
					objetos++;
				}
				
				// Inserta la arista en la primera lista de adyacencia, o añade una repetición
				tempArista = nodosSalientes.elementAt(idSujeto).elementMatch( new Arista(idObjeto,idPropiedad) );
				if( tempArista == null )
					nodosSalientes.elementAt(idSujeto).insertOrdered( new Arista(idObjeto,idPropiedad) );
				else
					tempArista.repeticiones++;
				
				// Inserta la arista en la segunda lista de adyacencia, o añade una repetición
				tempArista = nodosEntrantes.elementAt(idObjeto).elementMatch( new Arista(idSujeto,idPropiedad) );
				if( tempArista == null )
					nodosEntrantes.elementAt(idObjeto).insertOrdered( new Arista(idSujeto,idPropiedad) );
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
			Arista prov;
			for( int i = 0; i < nodosSalientes.elementAt(index).size(); ++i ) {
				prov = nodosSalientes.elementAt(index).elementAt(i);
				for( int j = 0; j < prov.repeticiones; j++ )
					coleccionSentencias.insertLast( listaSujetosObjetos.elementAt(index) + " " +  listaPropiedades.elementAt(prov.arista) + " " + listaSujetosObjetos.elementAt(prov.verticeObjetivo) + " ." );				
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
			Arista prov;
			for( int i = 0; i < nodosSalientes.elementAt(index).size(); ++i ) {
				prov = nodosSalientes.elementAt(index).elementAt(i);
				coleccionSentencias.insertLast( listaSujetosObjetos.elementAt(index) + " " +  listaPropiedades.elementAt(prov.arista) + " " + listaSujetosObjetos.elementAt(prov.verticeObjetivo) + " ." );
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
			if( !nodosSalientes.elementAt(i).isEmpty() && !nodosEntrantes.elementAt(i).isEmpty() )
				coleccionEntidades.insertLast( listaSujetosObjetos.elementAt(i) );
		
		return coleccionEntidades;
	}
	
	/**
	 * 5) Colección de entidades que son sujeto en todos y cada uno de los almacenes.
	 * @param Almacenes - Almacenes a intersectar
	 * @return Una lista enlazada de entidades que son sujeto en todos y cada uno de los almacenes
	 */
	public ListaEnlazada<String> interseccion(Almacen[] almacenes){
		ListaEnlazada<String> resultado= new ListaEnlazada<String>();
		int minimo = this.objetos;
		Almacen menor = this,swap;
		boolean posible;
		String sujeto;
		int provisional;
		
		//Guardamos en minimo el almacen con menor numero de objetos, el resto los dejamos en almacenes. 
		
		for (int i=0;i<almacenes.length;i++){
			if (almacenes[i].objetos<minimo){
				swap = menor;
				minimo = almacenes[i].objetos;
				menor  = almacenes[i];
				almacenes[i] = swap;
			}
		}		
		
		for( int i = 0; i < menor.nodosSalientes.size(); i++ ){			
			if( !menor.nodosSalientes.elementAt(i).isEmpty()){		//Recorremos todos los sujetos
				sujeto=menor.listaSujetosObjetos.elementAt(i);		//Obtenemos el string correspondiente
				posible=true;
				for (int n=0;n<almacenes.length && posible;n++){
					provisional=almacenes[n].arbolSujetosObjetos.obtenerValor(sujeto);
					if(provisional==-1 || almacenes[n].listaSujetosObjetos.elementAt(provisional).isEmpty()){
						posible=false;
					}
				}
				if (posible){
					resultado.insertLast(sujeto);
				}
			}
		}		
				
		return resultado;
	}
	
	/**
	 * 6) Colección ordenada de todas las sentencias que aparecen en el almacén.
	 * @return Un array con las sentencias del almacén según el orden descrito en el enunciado
	 */
	public ListaArray<String> sentenciasOrdenadas() {
		ListaArray<Integer> valores = arbolSujetosObjetos.recorrerEnProfundidad();
		ListaArray<String> sentenciasEnOrden = new ListaArray<String>(sentencias);
		Arista a;
		for( int i = 0; i < valores.size(); ++i ) {
			if( !nodosSalientes.elementAt( valores.elementAt(i) ).isEmpty() ) {
				for( int j = 0; j < nodosSalientes.elementAt( valores.elementAt(i) ).size(); ++j ) {
					a = nodosSalientes.elementAt( valores.elementAt(i) ).elementAt(j);
					for( int k = 0; k < a.repeticiones; ++k )
						sentenciasEnOrden.insertLast( listaSujetosObjetos.elementAt( valores.elementAt(i) ) + " " + listaPropiedades.elementAt(a.arista) + " " + listaSujetosObjetos.elementAt(a.verticeObjetivo) + " ." );
				}
			}
		}
		return sentenciasEnOrden;
	}
	
}
