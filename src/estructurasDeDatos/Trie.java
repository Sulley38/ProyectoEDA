package estructurasDeDatos;

/**
 * Estructura de árbol de prefijos:
 * Operaciones para codificar/descodificar Strings con enteros.
 */
public class Trie {	

	/**
	 * Clase interna: nodo del árbol, contiene un valor númerico y un array de punteros a nodos hijos.
	 */
	private class NodoTrie {
		// Atributos
		private int valor;
		private final NodoTrie[] ramas;
		// Constructora
		public NodoTrie(final int valorInicial) {
			valor = valorInicial;
			ramas = new NodoTrie[128];
		}
	}
	
	// ATRIBUTOS DE LA CLASE
	private final NodoTrie inicio;
	private int numElementos;
	
	
	/**
	 * Constructora
	 */
	public Trie() {
		inicio = new NodoTrie(-1);
		numElementos = 0;
	}
	
	/**
	 * Devuelve el número de nodos del árbol que contienen un valor distinto a -1.
	 * @return el número de elementos insertados
	 */
	public int size() {
		return numElementos;
	}
	
	/**
	 * Devuelve el valor numérico correspondiente a un String, o -1 si el String no existe en el trie.
	 * @param s - String a codificar
	 * @return el entero asignado al string s
	 */
	public int obtenerValor( final String s ) {
		NodoTrie puntero = inicio;
		for( int i = 0; i < s.length(); i++ ) {
			if( puntero == null )
				return -1;
			else
				puntero = puntero.ramas[((int)s.charAt(i))];
		}
		if( puntero == null ) return -1;
		return puntero.valor;
	}
	
	/**
	 * Comprueba si s está en el árbol.
	 * @param s - String a buscar
	 * @return true si s está en el árbol, false en caso contrario
	 */
	// NO SE USA NI HACE FALTA
	/*public boolean existe( String s ) {
		NodoTrie puntero = inicio;
		for( int i = 0; i < s.length(); i++ ) {
			if( puntero.ramas[((int)s.charAt(i))] == null )
				return false;
			puntero = puntero.ramas[((int)s.charAt(i))];		
		}
		return true;
	}*/
	
	/**
	 * Inserta el String s en el árbol, asignándole el entero valor.
	 * Si ya se encontraba en el árbol, devuelve su entero correspondiente sin modificarlo.
	 * @param s - String a insertar
	 * @param valor - Entero que se le asignará 
	 * @return el valor correspondiente al String s
	 */
	public int insertar( final String s, final int valor ) {
		boolean insertado = false;
		NodoTrie puntero = inicio;
		for( int i = 0; i < s.length(); i++ ) {
			if( puntero.ramas[((int)s.charAt(i))] == null ) {
				// Si el nodo no existe, la palabra no existe y se inserta el nodo hijo necesario
				puntero.ramas[((int)s.charAt(i))] = new NodoTrie(-1);
				insertado = true;
			}
			puntero = puntero.ramas[((int)s.charAt(i))];
		}
		if( insertado ) {
			puntero.valor = valor;
			numElementos++;
		}
		return puntero.valor;
	}
	
}
