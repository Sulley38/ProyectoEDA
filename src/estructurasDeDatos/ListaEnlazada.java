package estructurasDeDatos;

import gestionDeSentencias.Fichero;
import java.io.IOException;
import java.util.Iterator;

/**
 * Estructura de lista enlazada:
 * Contiene referencias al primer y al �ltimo elemento, permitiendo insertar en ambas posiciones en tiempo constante.
 * Puede insertar mantiendo un orden ascendente de sus elementos.
 * @param <T> - Par�metro gen�rico que implemente la interfaz Comparable<T>
 */
public class ListaEnlazada<T extends Comparable<T>> implements Iterable<T> {

	/**
	 * Clase interna: nodo de la lista, con dato del tipo T y puntero al elemento siguiente.
	 */
	private class NodoLista {
		// Atributos
		private final T dato;
		private NodoLista siguiente;
		// Constructora
		public NodoLista(final T elemento)
			{ dato = elemento; siguiente = null; }
	}

	/**
	 * Clase interna: iterador de la lista, se construye apuntando al primer elemento y avanza
	 * devolviendo el siguiente elemento.
	 */
	private class Iterador implements Iterator<T> {
		// Atributos
		private NodoLista actual;
		// Constructora
		public Iterador( NodoLista inicio )
			{ actual = inicio; }
		// M�todos
		public boolean hasNext()
			{ return (actual != null); }
		public T next() {
			if( !hasNext() ) 
				return null;
			final T result = actual.dato;
			actual = actual.siguiente;
			return result;
		}
		public void remove()
			{ throw new UnsupportedOperationException(); }
	}

	// ATRIBUTOS DE LA CLASE
	private NodoLista first;
	private NodoLista last;
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
	 * Comprueba si hay alg�n elemento en la lista.
	 * @return true si la lista es vac�a
	 */
	public boolean isEmpty() {
		return (first == null);
	}
	
	/**
	 * Devuelve el tama�o de la lista.
	 * @return el n�mero de nodos que componen la lista
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
	 * Devuelve el valor del �ltimo elemento de la lista.
	 * @return �ltimo valor de la lista
	 */
	public T last() {
		return last.dato;
	}
	
	/**
	 * Devuelve el valor del elemento en la posici�n indicada.
	 * @param index - la posici�n del valor que se busca, empezando a contar desde cero
	 * @return el valor del elemento en la posici�n index, o null si no existe
	 */
	public T elementAt(final int index) {
		NodoLista current = first;
		for( int i = 0; i < index; ++i )
			if( current != null ) current = current.siguiente;
		if( current == null ) return null;
		return current.dato;
	}

	/**
	 * Devuelve el valor del primer elemento igual a T.
	 * @param elemento - elemento a buscar en la lista
	 * @return elemento encontrado, o null si no se ha encontrado
	 */
	public T elementMatch(final T elemento) {
		NodoLista current = first;
		while( current != null ) {
			if( elemento.equals(current.dato) ) return current.dato;
			current = current.siguiente;
		}
		return null;
	}
	
	/**
	 * Inserta un elemento en la lista en primera posici�n.
	 * @param elemento - Dato a insertar
	 */
	public void insertFirst(final T elemento) {
		NodoLista newLink = new NodoLista(elemento);
		if( isEmpty() )
			last = newLink;
		else
			newLink.siguiente = first;
		first = newLink;
		numNodos++;
	}
	
	/**
	 * Inserta un elemento en la lista en �ltima posici�n.
	 * @param elemento - Dato a insertar
	 */
	public void insertLast(final T elemento) {
		NodoLista newLink = new NodoLista(elemento);
		if( isEmpty() )
			first = newLink;
		else
			last.siguiente = newLink;
		last = newLink;
		numNodos++;
	}
	
	/**
	 * Inserta un elemento en su posici�n siguiendo un orden creciente de elementos.
	 * @param elemento - Dato a insertar
	 */
	public void insertOrdered(final T elemento) {
		if( isEmpty() ) {
			insertFirst(elemento);
		} else {
			NodoLista newLink = new NodoLista(elemento);
			// Inserci�n en primera posici�n
			if( first.dato.compareTo(elemento) >= 0 ) {
				newLink.siguiente = first;
				first = newLink;
			} else {
				NodoLista current = first;
				// Ir hasta la posici�n a insertar
				while( current.siguiente != null && current.siguiente.dato.compareTo(elemento) < 0 )
					current = current.siguiente;
				// Inserci�n en �ltima posici�n
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
	 * Elimina el primer elemento de la lista y lo devuelve. Si la lista es vac�a, devuelve null.
	 * @return elemento eliminado
	 */
	public T removeFirst() {
		if( isEmpty() )
			return null;
		T dato = first.dato;
		if(first.siguiente == null)
			last = null;
		first = first.siguiente;
		numNodos--;
		return dato;
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
		NodoLista current = first;
		for( int i = 0; i < numNodos; ++i ) {
			array[i] = current.dato;
			current = current.siguiente;
		}
		return array;
	}

	/**
	 * Devuelve un iterador de la lista.
	 * @return un iterador que apunta al primer elemento de la lista
	 */
	public Iterator<T> iterator() {
		return new Iterador(first);
	}
	
	/**
	 * Escribe todos los elementos de la lista enlazada en un fichero de texto, uno por l�nea.
	 * @param filename - Archivo a escribir
	 */
	public void printToFile(final String filename) {
		try {
			Fichero.abrir(filename, true, false);
			NodoLista current = first;
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


