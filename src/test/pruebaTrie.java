package test;

import estructurasDeDatos.Trie;

/**
 * Casos de prueba para la estructura de trie
 */
public class pruebaTrie {

	public static void main(String[] args) {
		Trie prueba = new Trie();
		
		// Insertar elementos
		System.out.println("Insertando valores en el trie...");
		prueba.insertar("hola",0);
		prueba.insertar("holi",1);
		prueba.insertar("holu",2);
		System.out.println();
		
		// Obtener sus valores
		System.out.println("Valor del string holi: " + prueba.obtenerValor("holi"));
		System.out.println("Valor del string hola: " + prueba.obtenerValor("hola"));
		System.out.println("Valor del string holu: " + prueba.obtenerValor("holu"));
		System.out.println("Valor del string hole: " + prueba.obtenerValor("hole"));
		System.out.println();

	}

}
