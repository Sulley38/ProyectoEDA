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
		System.out.println("Insertar elementos al principio: 7, 5, 1");
		lista.insertFirst(7);
		lista.insertFirst(5);
		lista.insertFirst(1);
		System.out.print("La lista queda así: ");
		it.load(lista);
		while( it.hasNext() ) System.out.print(it.next() + " ");
		System.out.println(); System.out.println();
		
		// Inserción por detrás
		System.out.println("Insertar elementos al final: 9, 13, 16");
		lista.insertLast(9);
		lista.insertLast(13);
		lista.insertLast(16);
		System.out.print("La lista queda así: ");
		it.load(lista);
		while( it.hasNext() ) System.out.print(it.next() + " ");
		System.out.println(); System.out.println();

		// Inserción en orden creciente
		System.out.println("Insertar elementos en orden creciente: 0, 4, 10, 18");
		lista.insertOrdered(0);
		lista.insertOrdered(4);
		lista.insertOrdered(10);
		lista.insertOrdered(18);
		System.out.print("La lista queda así: ");
		it.load(lista);
		while( it.hasNext() ) System.out.print(it.next() + " ");
		System.out.println(); System.out.println();
		
		// Obtención de elementos
		System.out.println("Primer elemento: " + lista.getFirstElement());
		System.out.println("Último elemento: " + lista.getLastElement());
		System.out.println("Quinto elemento: " + lista.getElementByPosition(4));
		System.out.println("Se obtiene el valor 5 de la lista: " + lista.getElementByValue(5));
		System.out.println(); 
		
		// Convertir en array
		System.out.print("Lista en forma de array: ");
		Integer[] array = lista.toArray(Integer.class);
		for( int i = 0; i < array.length; ++i )
			System.out.print(array[i] + " ");
		System.out.println(); System.out.println();
		
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
