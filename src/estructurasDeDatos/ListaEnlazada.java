package estructurasDeDatos;

import gestionDeSentencias.Fichero;
import java.io.IOException;
import java.util.Iterator;

/**
 * Estructura de lista enlazada:
 * Contiene referencias al primer y al último elemento, permitiendo insertar en ambas posiciones en tiempo constante.
 * Puede insertar mantiendo un orden ascendente de sus elementos.
 * @param <T> - Parámetro genérico que implemente la interfaz Comparable<T>
 */
public class ListaEnlazada<T extends Comparable<T>> {

	/**
	 * Clase interna: nodo de la lista, con dato del tipo T y puntero al elemento siguiente.
	 */
	private static class NodoLista<T> {
		// Atributos
		private final T dato;
		private NodoLista<T> siguiente;
		// Constructora
		public NodoLista(final T elemento)
			{ dato = elemento; siguiente = null; }
	}

	/**
	 * Clase interna: iterador de la lista, se construye apuntando al primer elemento y avanza
	 * devolviendo el siguiente elemento.
	 */
	public static class Iterador<T extends Comparable<T>> implements Iterator<T> {
		// Atributos
		private NodoLista<T> actual;
		// Constructora
		public Iterador() {}
		// Métodos
		public void load( final ListaEnlazada<T> list )
			{ actual = list.first; }
		public boolean hasNext()
			{ return (actual != null); }
		public T next() {
			if( !hasNext() ) 
				return null;
			final T result = actual.dato;
			actual = actual.siguiente;
			return result;
		}
		public void remove() {}
	}

	// ATRIBUTOS DE LA CLASE
	private NodoLista<T> first;
	private NodoLista<T> last;
	private int numNodos;
	
	
	/**
	 * Constructora
	 */
	public ListaEnlazada() {
		first = null;                 
		last = null;
		numNodos = 0;
	}
	
	/**
	 * Comprueba si hay algún elemento en la lista.
	 * @return true si la lista es vacía
	 */
	public boolean isEmpty() {
		return (first == null);
	}
	
	/**
	 * Devuelve el tamaño de la lista.
	 * @return el número de nodos que componen la lista
	 */
	public int size() {
		return numNodos;
	}

	/**
	 * Devuelve el valor del primer elemento de la lista.
	 * @return primer valor de la lista
	 */
	public T first() {
		return first.dato;
	}
	
	/**
	 * Devuelve el valor del último elemento de la lista.
	 * @return último valor de la lista
	 */
	public T last() {
		return last.dato;
	}
	
	/**
	 * Devuelve el valor del elemento en la posición indicada.
	 * @param index - la posición del valor que se busca, empezando a contar desde cero
	 * @return el valor del elemento en la posición index, o null si no existe
	 */
	public T elementAt(final int index) {
		NodoLista<T> current = first;
		for( int i = 0; i < index; ++i )
			if( current != null ) current = current.siguiente;
		if( current == null ) return null;
		return current.dato;
	}

	/**
	 * Devuelve el valor del primer elemento igual a T.
	 * @param T - elemento a buscar en la lista
	 * @return elemento encontrado, o null si no se ha encontrado
	 */
	public T elementMatch(final T elemento) {
		NodoLista<T> current = first;
		while( current != null ) {
			if( elemento.equals(current.dato) ) return current.dato;
			current = current.siguiente;
		}
		return null;
	}
	
	/**
	 * Inserta un elemento en la lista en primera posición.
	 * @param elemento - Dato a insertar
	 */
	public void insertFirst(final T elemento) {
		NodoLista<T> newLink = new NodoLista<T>(elemento);
		if( isEmpty() )
			last = newLink;
		else
			newLink.siguiente = first;
		first = newLink;
		numNodos++;
	}
	
	/**
	 * Inserta un elemento en la lista en última posición.
	 * @param elemento - Dato a insertar
	 */
	public void insertLast(final T elemento) {
		NodoLista<T> newLink = new NodoLista<T>(elemento);
		if( isEmpty() )
			first = newLink;
		else
			last.siguiente = newLink;
		last = newLink;
		numNodos++;
	}
	
	/**
	 * Inserta un elemento en su posición siguiendo un orden creciente de elementos.
	 * @param elemento - Dato a insertar
	 */
	public void insertOrdered(final T elemento) {
		if( isEmpty() ) {
			insertFirst(elemento);
		} else {
			NodoLista<T> newLink = new NodoLista<T>(elemento);
			// Inserción en primera posición
			if( first.dato.compareTo(elemento) >= 0 ) {
				newLink.siguiente = first;
				first = newLink;
			} else {
				NodoLista<T> current = first;
				// Ir hasta la posición a insertar
				while( current.siguiente != null && current.siguiente.dato.compareTo(elemento) < 0 )
					current = current.siguiente;
				// Inserción en última posición
				if( current.siguiente == null )
					last = newLink;
				// Actualizar los punteros
				newLink.siguiente = current.siguiente;
				current.siguiente = newLink;
			}
			numNodos++;
		}
	}
	
	/**
	 * Elimina el primer elemento de la lista. Si la lista es vacía, no hace nada.
	 */
	public void removeFirst() {
		if( !isEmpty() ) {
			if(first.siguiente == null)
				last = null;
			first = first.siguiente;
			numNodos--;
		}
	}
	
	/**
	 * Elimina todos los elementos de la lista.
	 */
	public void removeAll() {
		first = null;                 
		last = null;
		numNodos = 0;
	}
	
	/**
	 * Devuelve un array a partir de la lista enlazada.
	 * @return un array cuyos elementos son los valores de la lista
	 */
	public Object[] toArray() {
		Object[] array = new Object[numNodos];
		NodoLista<T> current = first;
		for( int i = 0; i < numNodos; ++i ) {
			array[i] = current.dato;
			current = current.siguiente;
		}
		return array;
	}

	/**
	 * Escribe todos los elementos de la lista enlazada en un fichero de texto, uno por línea.
	 * @param filename - Archivo a escribir
	 */
	public void printToFile(final String filename) {
		try {
			Fichero.abrir(filename, true);
			NodoLista<T> current = first;
			for( int i = 0; i < numNodos; ++i ) {
				Fichero.escribirSentencia(current.dato.toString());
				current = current.siguiente;
			}
			Fichero.cerrar();
		} catch (IOException e) {
			System.out.println("Error: Imposible acceder al fichero especificado.");
		}
	}
	
}


