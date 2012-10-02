package gestionDeSentencias;

public class Trie {	

	private class NodoTrie {
		int valor;
		NodoTrie[] ramas;
		
		public NodoTrie(int valorInicial) {
			valor = valorInicial;
			ramas = new NodoTrie[128];
		}
		
	}
		
	private NodoTrie inicio;
	private int numNodos;
	
	//Crea un nuevo �rbol
	public Trie() {
		inicio = new NodoTrie(-1);
		numNodos = 0;
	}
	
	//devuelve el n�mero de nodos en el �rbol
	public int size() {
		return numNodos;
	}
	
	//devuelve el siguiente nodo (correspondiente a la letra)
	public int obtenerValor(String s){
		// TODO: Intxi, trabaja
		return 0;
	}
	
	//devuelve true si s ya est� en el �rbol
	public boolean existe(String s) {
		// TODO: Intxi, trabaja
		return false;
	}
	
	//devuelve 1 si ha insertado s, 0 si ya estaba en el �rbol
	public int insertar(String s){
		// TODO: Intxi, trabaja
		return 0;
	}
	
}
