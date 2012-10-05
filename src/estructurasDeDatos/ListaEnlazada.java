package estructurasDeDatos;

import java.util.Iterator;

/**
 * Estructura de lista enlazada:
 * Contiene referencias al primer y al último elemento, permitiendo insertar en ambas posiciones en tiempo constante.
 */
public class ListaEnlazada<T> {

	/**
	 * Clase interna: nodo de la lista, con dato del tipo T y puntero al elemento siguiente.
	 */
	private class NodoLista {
		// Atributos
		private final T dato;
		private NodoLista siguiente;
		// Constructora
		public NodoLista(T elemento)
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
		public Iterador()
			{ actual = first; }
		// Métodos
		public boolean hasNext()
			{ return (actual != null); }
		public T next() {
			if( !hasNext() ) 
				return null;
			T result = actual.dato;
			actual = actual.siguiente;
			return result;
		}
		public void remove() {}
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
	 * Inserta un elemento en la lista en primera posición.
	 * @param elemento a insertar
	 */
	public void insertFirst(T elemento) {
		NodoLista newLink = new NodoLista(elemento);
		if( isEmpty() )
			last = newLink;
		else
			newLink.siguiente = first;
		first = newLink;
		numNodos++;
	}
	
	/**
	 * Inserta un elemento en la lista en última posición.
	 * @param elemento a insertar
	 */
	public void insertLast(T elemento) {
		NodoLista newLink = new NodoLista(elemento);
		if( isEmpty() )
			first = newLink;
		else
			last.siguiente = newLink;
		last = newLink;
		numNodos++;
	}
	
	/**
	 * Elimina el primer elemento de la lista. Si la lista es vacía, no hace nada.
	 */
	public void deleteFirst() {
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
	public void deleteAll() {
		first = null;                 
		last = null;
		numNodos = 0;
	}
	
	/**
	 * Devuelve el valor del primer elemento de la lista.
	 * @return primer valor de la lista
	 */
	public T getFirstElement() {
		return first.dato;
	}
	
	/**
	 * Devuelve el valor del último elemento de la lista.
	 * @return último valor de la lista
	 */
	public T getLastElement() {
		return last.dato;
	}
	
	/**
	 * Devuelve el valor del elemento en la posición indicada.
	 * @param index - la posición del valor que se busca, empezando a contar desde cero
	 * @return el valor del elemento en la posición index
	 */
	public T getElementByPosition(int index) {
		NodoLista current = first;
		for( int i = 0; i < index; ++i )
			if( current != null ) current = current.siguiente;
		if( current == null ) return null;
		return current.dato;
	}
	
	/**
	 * Devuelve un array a partir de la lista enlazada.
	 * @return un array cuyos elementos son los valores de la lista
	 */
	@SuppressWarnings("unchecked")
	public T[] toArray() {
		T[] array = (T[]) new Object[numNodos];
		NodoLista current = first;
		for( int i = 0; i < numNodos; ++i ) {
			array[i] = current.dato;
			current = current.siguiente;
		}
		return array;
	}
	
	/**
	 * Devuelve un iterador de la lista.
	 * @return un iterador del tipo Iterator<T>
	 */
	public Iterator<T> getIterator() {
        return new Iterador();
    }
	
	/**
	 * Vuelve el iterador a su posición inicial
	 * @param it - iterador de la lista enlazada a resetear
	 */
	public void resetIterator( Iterator<T> it ) {
		((Iterador)it).actual = first;
	}
	
}


