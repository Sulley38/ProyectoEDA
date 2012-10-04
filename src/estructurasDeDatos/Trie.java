package estructurasDeDatos;

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
	
	//Crea un nuevo árbol
	public Trie() {
		inicio = new NodoTrie(-1);
		numNodos = 0;
	}
	
	//devuelve el número de nodos en el árbol
	public int size() {
		return numNodos;
	}
	
	//devuelve el siguiente nodo (correspondiente a la letra)
	public int obtenerValor(String s){
		int l=s.length();
		int indiceRamas;
		NodoTrie puntero = inicio;
		
		for(int i=0;i<l;i++){
			indiceRamas = (int)s.charAt(i);
			puntero = puntero.ramas[indiceRamas];		
		}
		
		return puntero.valor;
	}
	
	//devuelve true si s ya está en el árbol
	public boolean existe(String s) {
		int l=s.length();
		int indiceRamas;
		NodoTrie puntero = inicio;
		
		for(int i=0;i<l;i++){
			indiceRamas = (int)s.charAt(i);
			if (puntero.ramas[indiceRamas]==null){
				return false;
			}
			puntero = puntero.ramas[indiceRamas];		
		}
		return true;
	}
	
	//devuelve 1 si ha insertado s, 0 si ya estaba en el árbol
	public boolean insertar(String s, int posicion){
		boolean insertado = false;
		int l=s.length(), indiceRamas;		//para no recalcular EFICIENCIA!!!!!
		NodoTrie puntero = inicio;
		
		for(int i=0;i<l;i++){
			indiceRamas = (int)s.charAt(i);
			if (puntero.ramas[indiceRamas]==null){		//si no existe el nodo, la palabra no existe y se van insertando los nodos necesarios
				puntero.ramas[indiceRamas] = new NodoTrie(-1);
				puntero = puntero.ramas[indiceRamas];
				insertado = true;
			}
			else{
				puntero = puntero.ramas[indiceRamas];	//existe ese prefijo, se avanza un caracter
			}			
		}
		if (insertado)
			puntero.valor=posicion;

		return insertado;
	}
	
}
