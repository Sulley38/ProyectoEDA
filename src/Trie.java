
public class Trie {	

	private class NodoTrie {
		int valor;
		NodoTrie[] ramas;
		
		public NodoTrie(int valorInicial) {
			valor = valorInicial;
			ramas = new NodoTrie[128];
		}
		
	}
		
	NodoTrie inicio;
	
	//Crea un nuevo �rbol
	public Trie() {
		inicio = new NodoTrie(-1);
	}
	
	//devuelve el siguiente nodo (correspondiente a la letra)
	public int obtenerValor(String s){
		return 0;
	}
	
	//devuelve true si s ya est� en el �rbol
	public boolean existe(String s) {
		return false;
	}
	
	public void insertar(String s){
		
	}
	
}
