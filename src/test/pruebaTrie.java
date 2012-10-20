package test;

import estructurasDeDatos.Trie;
import estructurasDeDatos.ListaArray;

/**
 * Casos de prueba para la estructura de trie
 */
public class pruebaTrie {

	public static void main(String[] args) {
		Trie prueba = new Trie();
		
		// Insertar elementos
		System.out.println("Insertando valores en el trie...");
		prueba.insertar("hola",0);
		prueba.insertar("paco",1);
		prueba.insertar("galleta",2);
		prueba.insertar("holi",3);
		prueba.insertar("galletas",4);
		System.out.println();
		
		// Obtener sus valores
		System.out.println("Valor del string hola: " + prueba.obtenerValor("hola"));
		System.out.println("Valor del string holi: " + prueba.obtenerValor("holi"));
		System.out.println("Valor del string paco: " + prueba.obtenerValor("paco"));
		System.out.println("Valor del string miguel: " + prueba.obtenerValor("miguel"));
		System.out.println();
		
		// Recorrer el árbol en profundidad
		ListaArray<Integer> listaOrdenada = prueba.recorrerEnProfundidad();
		System.out.print("Valores del trie por orden alfabético de sus strings: ");
		for( int i = 0; i < listaOrdenada.size(); ++i )
			System.out.print( listaOrdenada.getElementByPosition(i) + " " );
		System.out.println();
	}

}
