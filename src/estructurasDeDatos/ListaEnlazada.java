package estructurasDeDatos;
import java.util.Iterator;

/**
 * Estructura de lista enlazada.
 * Contiene referencias al primer y al último elemento.
 */
public class ListaEnlazada<T> {

	private class NodoLista {
		private T Dato;
		private NodoLista siguiente;
		public NodoLista(T dato) {
			Dato = dato;
		}

		public T getData() {
			return Dato;
		}
		public NodoLista getNext() {
			return siguiente;
		}
		public void setNext(NodoLista nodo) {
			siguiente = nodo;
		}
	}

	private class Iterador implements Iterator<T> {
		private NodoLista actual;
		public Iterador() {
			actual = first;
		}
		
		public boolean hasNext() {
			return (actual != null);
		}
		public T next() {
			if( !hasNext() ) 
				return null;
			T result = actual.getData();
			actual = actual.getNext();
			return result;
		}
		public void remove() {
		}
	}


	private NodoLista first;
	private NodoLista last;
	// -------------------------------------------------------------
	public ListaEnlazada() {
		first = null;                 
		last = null;
	}
	// -------------------------------------------------------------
	public boolean isEmpty() {
		return (first == null);
	}
	// -------------------------------------------------------------
	public void insertFirst(T elemento) {
		NodoLista newLink = new NodoLista(elemento);

		if( isEmpty() )
			last = newLink;
		newLink.setNext( first );
		first = newLink;
	}
	// -------------------------------------------------------------
	public void insertLast(T elemento) {
		NodoLista newLink = new NodoLista(elemento);
		if( isEmpty() )
			first = newLink;
		else
			last.setNext( newLink );
		last = newLink; 
	}
	// -------------------------------------------------------------
	public T deleteFirst() {                              // (assumes non-empty list)
		T temp = first.getData();
		if(first.getNext() == null)
			last = null;
		first = first.getNext();
		return temp;
	}
	// -------------------------------------------------------------
	public T getFirstElement() {
		return first.getData();
	}
	// -------------------------------------------------------------
	public T getLastElement() {
		return last.getData();
	}
	// -------------------------------------------------------------

}


