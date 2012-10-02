package test;

import gestionDeSentencias.Trie;


public class pruebaTrie {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Trie prueba = new Trie();
		prueba.insertar("hola",0);
		prueba.insertar("holi",1);
		prueba.insertar("holu",2);
		System.out.println("\n"+prueba.obtenerValor("holi"));
		System.out.println("\n"+prueba.obtenerValor("hola"));
		System.out.println("\n"+prueba.obtenerValor("holu"));
		System.out.println(prueba.existe("hola"));
		System.out.println(prueba.existe("holi"));
		System.out.println(prueba.existe("holo"));

	}

}
