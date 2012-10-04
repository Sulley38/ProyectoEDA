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

	public class Iterador implements Iterator<T> {
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
	private int numNodos;
	// -------------------------------------------------------------
	public ListaEnlazada() {
		first = null;                 
		last = null;
		numNodos = 0;
	}
	// -------------------------------------------------------------
	public boolean isEmpty() {
		return (first == null);
	}
	// -------------------------------------------------------------
	public int size() {
		return numNodos;
	}
	// -------------------------------------------------------------
	public void insertFirst(T elemento) {
		NodoLista newLink = new NodoLista(elemento);

		if( isEmpty() )
			last = newLink;
		newLink.setNext( first );
		first = newLink;
		numNodos++;
	}
	// -------------------------------------------------------------
	public void insertLast(T elemento) {
		NodoLista newLink = new NodoLista(elemento);
		if( isEmpty() )
			first = newLink;
		else
			last.setNext( newLink );
		last = newLink;
		numNodos++;
	}
	// -------------------------------------------------------------
	public T deleteFirst() {                              // (assumes non-empty list)
		T temp = first.getData();
		if(first.getNext() == null)
			last = null;
		first = first.getNext();
		numNodos--;
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
	public T getElementByPosition(int posicion) {
		NodoLista current = first;
		for( int i = 0; i < posicion; ++i )
			if( current != null ) current=current.getNext();
		if( current == null ) return null;
		return current.getData();
	}
	// -------------------------------------------------------------
	public Iterator<T> iterator(){
        return new Iterador();  
    }
	// -------------------------------------------------------------
	public T[] toArray(T[] array){
		NodoLista provisional=first;
		for(int i=0;i<numNodos;i++){
			array[i]=provisional.Dato;
			provisional=provisional.siguiente;
		}
		return array;
	}
}


