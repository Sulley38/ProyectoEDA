package estructurasDeDatos;

/**
 * Estructura de lista enlazada.
 * Contiene referencias al primer y al último elemento.
 */
public class ListaEnlazada<T> {
	
		private class NodoLista<E> {
			   private E Dato;
			   private NodoLista<E> siguiente;
			   public NodoLista(E dato) {
				   Dato = dato;
			   }
			   
			   public E getData() {
				   return Dato;
			   }
			   public NodoLista<E> getNext() {
				   return siguiente;
			   }
			   public void setNext(NodoLista<E> nodo) {
				   siguiente = nodo;
			   }
		}
		
	   private NodoLista<T> first;
	   private NodoLista<T> last;
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
		   NodoLista<T> newLink = new NodoLista<T>(elemento);

		   if( isEmpty() )
			   last = newLink;
		   newLink.setNext( first );
		   first = newLink;
	   }
	// -------------------------------------------------------------
	   public void insertLast(T elemento) {
		   NodoLista<T> newLink = new NodoLista<T>(elemento);
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


