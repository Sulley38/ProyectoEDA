package test;

import estructurasDeDatos.ListaEnlazada;

/**
 * Casos de prueba para la estructura de lista enlazada
 */
public class pruebaListaEnlazada {

	public static void main(String[] args) {
		System.out.println("Prueba de la lista enlazada con enteros.");
		System.out.println("----------------------------------------");
		ListaEnlazada<Integer> lista = new ListaEnlazada<Integer>();
		ListaEnlazada.Iterador<Integer> it = new ListaEnlazada.Iterador<Integer>();

		// Inserción por delante
		System.out.println("Insertar elementos al principio: 1, 5, 17");
		lista.insertFirst(1);
		lista.insertFirst(5);
		lista.insertFirst(17);
		System.out.print("La lista queda así: ");
		it.load(lista);
		while( it.hasNext() ) System.out.print(it.next() + " ");
		System.out.println(); System.out.println();
		
		// Inserción por detrás
		System.out.println("Insertar elementos al final: 3, 9, 6");
		lista.insertLast(3);
		lista.insertLast(9);
		lista.insertLast(6);
		System.out.print("La lista queda así: ");
		it.load(lista);
		while( it.hasNext() ) System.out.print(it.next() + " ");
		System.out.println(); System.out.println();

		// Obtención de elementos
		System.out.println("Primer elemento: " + lista.getFirstElement());
		System.out.println("Último elemento: " + lista.getLastElement());
		System.out.println("Cuarto elemento: " + lista.getElementByPosition(3));
		System.out.println(); 
		
		// Eliminar un elemento
		System.out.println("Tamaño de la lista: " + lista.size());
		System.out.println("Se elimina el primer elemento.");
		lista.deleteFirst();
		System.out.print("La lista queda así: ");
		it.load(lista);
		while( it.hasNext() ) System.out.print(it.next() + " ");
		System.out.println();
		System.out.println("¿La lista es vacía? " + lista.isEmpty());
		System.out.println();
		
		// Eliminar todos los elementos
		System.out.println("Tamaño de la lista: " + lista.size());
		System.out.println("Se eliminan todos los elementos.");
		lista.deleteAll();
		System.out.print("La lista queda así: ");
		it.load(lista);
		while( it.hasNext() ) System.out.print(it.next() + " ");
		System.out.println();
		System.out.println("¿La lista es vacía? " + lista.isEmpty());
		System.out.println();
		
		System.out.println("----------------------------------------");
		System.out.println("Fin de la prueba.");
	}

}
