package test;

import estructurasDeDatos.ListaArray;

/**
 * Casos de prueba para la estructura de vector
 */
public class pruebaListaArray {

	public static void main(String[] args) {
		System.out.println("Prueba del vector con enteros.");
		System.out.println("----------------------------------------");
		ListaArray<Integer> lista = new ListaArray<Integer>();
		int i;
		
		// Inserción al final
		System.out.println("Insertar elementos al final: 3, 7, 10, 16");
		lista.insertLast(3);
		lista.insertLast(7);
		lista.insertLast(10);
		lista.insertLast(16);
		System.out.print("La lista queda así: ");
		for( i = 0; i < lista.size(); ++i )
			System.out.print(lista.get(i) + " ");
		System.out.println(); System.out.println();
		
		// Inserción en índice concreto
		System.out.println("Insertar elementos en las posiciones 2 y 6: 6, 21");
		lista.set(1,6);
		lista.set(5,21);
		System.out.print("La lista queda así: ");
		for( i = 0; i < lista.size(); ++i )
			System.out.print(lista.get(i) + " ");
		System.out.println(); System.out.println();
		
		// Inserción en orden creciente
		System.out.println("Insertar elementos en orden creciente: 0, 4, 18");
		lista.insertOrdered(0);
		lista.insertOrdered(4);
		lista.insertOrdered(18);
		System.out.print("La lista queda así: ");
		for( i = 0; i < lista.size(); ++i )
			System.out.print(lista.get(i) + " ");
		System.out.println(); System.out.println();
		
		// Obtención de elementos
		System.out.println("Primer elemento: " + lista.first());
		System.out.println("Último elemento: " + lista.last());
		System.out.println("Quinto elemento: " + lista.get(4));
		System.out.println("Posición del valor 5 en la lista: " + lista.find(5));
		System.out.println();
		
		// Convertir en array
		System.out.print("Lista en forma de array: ");
		Object[] array = lista.toArray();
		for( i = 0; i < array.length; ++i )
			System.out.print(array[i] + " ");
		System.out.println(); System.out.println();
		
		// Eliminar un elemento
		System.out.println("Tamaño de la lista: " + lista.size());
		System.out.println("Se elimina el último elemento.");
		lista.removeLast();
		System.out.print("La lista queda así: ");
		for( i = 0; i < lista.size(); ++i )
			System.out.print(lista.get(i) + " ");
		System.out.println();
		System.out.println("¿La lista es vacía? " + lista.isEmpty());
		System.out.println();
		
		// Eliminar todos los elementos
		System.out.println("Tamaño de la lista: " + lista.size());
		System.out.println("Se eliminan todos los elementos.");
		lista.removeAll();
		System.out.print("La lista queda así: ");
		for( i = 0; i < lista.size(); ++i )
			System.out.print(lista.get(i) + " ");
		System.out.println();
		System.out.println("¿La lista es vacía? " + lista.isEmpty());
		System.out.println();
		
		// Ordenación del array
		System.out.println("Nueva lista: 3 1 9 5 18 8 22 0 10");
		lista.insertLast(3); lista.insertLast(1); lista.insertLast(9);
		lista.insertLast(5); lista.insertLast(18); lista.insertLast(8);
		lista.insertLast(22); lista.insertLast(0); lista.insertLast(10);
		ListaArray<Integer> indicesOrdenados = lista.sort();
		System.out.print("Índices de los elementos en orden: ");
		for( i = 0; i < indicesOrdenados.size(); ++i )
			System.out.print(indicesOrdenados.get(i) + " ");
		System.out.println();
		System.out.print("Elementos en orden: ");
		for( i = 0; i < lista.size(); ++i )
			System.out.print(lista.get( indicesOrdenados.get(i) ) + " ");
		System.out.println();
		
		System.out.println("----------------------------------------");
		System.out.println("Fin de la prueba.");
	}

}
