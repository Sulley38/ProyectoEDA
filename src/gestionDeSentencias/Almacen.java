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
		private final int verticeObjetivo, propiedad;
		private int repeticiones;
		// Constructora
		public Arista(final int Objetivo, final int Propiedad) {
			verticeObjetivo = Objetivo;
			propiedad = Propiedad;
			repeticiones = 1;
		}
		// Comparadora en orden lexicográfico según propiedades y objetos
		@Override
		public int compareTo(final Arista a) {
			if( this.propiedad == a.propiedad )
				return listaSujetosObjetos.get(this.verticeObjetivo).compareTo(listaSujetosObjetos.get(a.verticeObjetivo));
			else
				return listaPropiedades.get(this.propiedad).compareTo(listaPropiedades.get(a.propiedad));
		}
		// Comparadora de los valores de la arista
		@Override
		public boolean equals(final Object a) {
			return (verticeObjetivo == ((Arista)a).verticeObjetivo) && (propiedad == ((Arista)a).propiedad);
		}
	}
	
	/// CONSTANTES
	private static final String propiedadEs = "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>";
	private static final String propiedadSubClaseDe = "<http://www.w3.org/2000/01/rdf-schema#subClassOf>";
	
	/// ATRIBUTOS DE LA CLASE
	// Dos listas de adyacencia para representar el grafo.
	private ListaArray< ListaArray<Arista> > nodosEntrantes, nodosSalientes;
	// Para cada nodo, índices de las aristas siguiendo el orden lexicográfico del enunciado
	private ListaArray< ListaArray<Integer> > nodosSalientesOrdenados;
	// Número de nodos (sujetos+objetos), de aristas (propiedades) y de sentencias; la suma de los tres primeros es el número de entidades
	private int sujetos, objetos, propiedades, sentencias;
	// Relaciones entre entidad e índice correspondiente (Trie), y viceversa (array)
	private Trie arbolSujetosObjetos, arbolPropiedades;
	private ListaArray<String> listaSujetosObjetos, listaPropiedades;
	// Representación de clases y subclases
	// TODO: pensar esto un poco más
	//private ListaArray< ListaArray<Integer> > clases, entidadesMiembro;
	//private Arbol relacionEntreClases;
	

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
		// Variable temporal para insertar las sentencias
		int tempArista;
		// Lee las sentencias desde el fichero y las añade al trie y a la lista de nodos del grafo
		try {
			Fichero.abrir(nombreDeArchivo,false,false);
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
				tempArista = nodosSalientes.get(idSujeto).find( new Arista(idObjeto,idPropiedad) );
				if( tempArista == -1 )
					nodosSalientes.get(idSujeto).insertLast( new Arista(idObjeto,idPropiedad) );
				else
					nodosSalientes.get(idSujeto).get(tempArista).repeticiones++;
				
				// Inserta la arista en la segunda lista de adyacencia, o añade una repetición
				tempArista = nodosEntrantes.get(idObjeto).find( new Arista(idSujeto,idPropiedad) );
				if( tempArista == -1 )
					nodosEntrantes.get(idObjeto).insertLast( new Arista(idSujeto,idPropiedad) );
				else
					nodosEntrantes.get(idObjeto).get(tempArista).repeticiones++;
			}
			
			// Ordenar las aristas salientes de cada nodo
			nodosSalientesOrdenados = new ListaArray< ListaArray<Integer> >(nodosSalientes.size());
			for( int i = 0; i < nodosSalientes.size(); ++i )
				nodosSalientesOrdenados.set( i, nodosSalientes.get(i).sort() );
	
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
			for( int i = 0; i < nodosSalientes.get(index).size(); ++i ) {
				prov = nodosSalientes.get(index).get(i);
				for( int j = 0; j < prov.repeticiones; j++ )
					coleccionSentencias.insertLast( listaSujetosObjetos.get(index) + " " +  listaPropiedades.get(prov.propiedad) + " " + listaSujetosObjetos.get(prov.verticeObjetivo) + " ." );				
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
			for( int i = 0; i < nodosSalientes.get(index).size(); ++i ) {
				prov = nodosSalientes.get(index).get(i);
				coleccionSentencias.insertLast( listaSujetosObjetos.get(index) + " " +  listaPropiedades.get(prov.propiedad) + " " + listaSujetosObjetos.get(prov.verticeObjetivo) + " ." );
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
			if( !nodosSalientes.get(i).isEmpty() && !nodosEntrantes.get(i).isEmpty() )
				coleccionEntidades.insertLast( listaSujetosObjetos.get(i) );
		
		return coleccionEntidades;
	}
	
	/**
	 * 5) Colección de entidades que son sujeto en todos y cada uno de los almacenes.
	 * @param coleccionAlmacenes - almacenes a intersectar
	 * @return Una lista enlazada de entidades que son sujeto en todos y cada uno de los almacenes
	 */
	public static ListaEnlazada<String> entidadesSujetoEnTodos(Almacen[] coleccionAlmacenes) {
		ListaEnlazada<String> resultado = new ListaEnlazada<String>();
		int menor = 0, i, j, valor;
		boolean posibleSujetoComun;
		String sujeto;

		// Se busca el índice del almacén que tiene menor número de sujetos
		for (i = 0; i < coleccionAlmacenes.length; i++)
			if (coleccionAlmacenes[i].sujetos < coleccionAlmacenes[menor].sujetos)
				menor = i;

		for (i = 0; i < coleccionAlmacenes[menor].nodosSalientes.size(); i++) {
			// Recorrer todas las entidades del almacén
			if (!coleccionAlmacenes[menor].nodosSalientes.get(i).isEmpty()) {
				// En caso de ser un sujeto, se busca en el resto de los almacenes
				sujeto = coleccionAlmacenes[menor].listaSujetosObjetos.get(i);
				posibleSujetoComun = true;
				j = 0;
				while (j < coleccionAlmacenes.length && posibleSujetoComun) {
					valor = coleccionAlmacenes[j].arbolSujetosObjetos.obtenerValor(sujeto);
					if (valor == -1 || coleccionAlmacenes[j].nodosSalientes.get(valor).isEmpty()) {
						// No existe o no es sujeto en el almacén j -> se deja de buscar
						posibleSujetoComun = false;
					}
					j++;
				}

				if (posibleSujetoComun) {
					// Es sujeto en todos los almacenes
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
		int nodo;
		Arista arista;
		for( int i = 0; i < valores.size(); ++i ) {
			nodo = valores.get(i);
			for( int j = 0; j < nodosSalientesOrdenados.get(nodo).size(); ++j ) {
				arista = nodosSalientes.get(nodo).get( nodosSalientesOrdenados.get(nodo).get(j) );
				for( int k = 0; k < arista.repeticiones; ++k )
					sentenciasEnOrden.insertLast( listaSujetosObjetos.get(nodo) + " " + listaPropiedades.get(arista.propiedad) + " " + listaSujetosObjetos.get(arista.verticeObjetivo) + " ." );
			}
		}
		return sentenciasEnOrden;
	}
	
	/**
	 * 9a) Cargar sentencias en un almacén, tomadas de archivos de texto de nuestro directorio.
	 * @param nombreDeArchivo - fichero del que se toman las sentencias para el almacén
	 * @return un almacén de sentencias a partir del contenido del fichero
	 */
	public static Almacen cargar(String nombreDeArchivo) {
		return new Almacen(nombreDeArchivo);
	}
	
	/**
	 * 9b) Descargar las sentencias de un almacén en un archivo de texto de nuestro directorio.
	 * @param nombreDeArchivo - fichero en el que se van a escribir las sentencias del almacén
	 */
	public void descargar(String nombreDeArchivo) {
		try {
			Arista arista;
			Fichero.abrir(nombreDeArchivo, true, false);
			for (int i = 0; i < nodosSalientes.size(); ++i) {
				for (int j = 0; j < nodosSalientes.get(i).size(); ++j) {
					arista = nodosSalientes.get(i).get(j);
					for (int k = 0; k < arista.repeticiones; ++k)
						Fichero.escribirSentencia( listaSujetosObjetos.get(i) + " " + listaPropiedades.get(arista.propiedad) + " " + listaSujetosObjetos.get(arista.verticeObjetivo) + " ." );
				}
			}
			Fichero.cerrar();
		} catch (IOException e) {
			System.err.println("Error: Imposible acceder al fichero especificado.");
			return;
		}
	}
}
