package estructurasDeDatos;

/**
 * Estructura de árbol de prefijos:
 * Operaciones para codificar/descodificar Strings con enteros.
 */
public class Trie {	

	/**
	 * Clase interna: nodo del árbol, contiene un valor númerico y un array de punteros a nodos hijos ordenados.
	 */
	private class NodoTrie implements Comparable<NodoTrie> {
		// Atributos
		private int valor;
		private final char letra;
		private final ListaEnlazada<NodoTrie> ramas;
		// Constructora
		public NodoTrie(final int valorInicial, final char valorCaracter) {
			valor = valorInicial;
			letra = valorCaracter;
			ramas = new ListaEnlazada<NodoTrie>();
		}
		// Comparadora de orden
		@Override
		public int compareTo(final NodoTrie a) {
			return (letra - a.letra);
		}
		// Comparadora de igualdad
		@Override
		public boolean equals(final Object a) {
			return letra == ((NodoTrie)a).letra;
		}
	}
	
	// ATRIBUTOS DE LA CLASE
	private final NodoTrie inicio;
	private int numElementos;
	
	
	/**
	 * Constructora
	 */
	public Trie() {
		inicio = new NodoTrie(-1,'\0');
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
				puntero = puntero.ramas.getElementByValue( new NodoTrie(-1,s.charAt(i)) );							
		}
		if( puntero == null ) return -1;
		return puntero.valor;
	}

	/**
	 * Inserta el String s en el árbol, asignándole el entero valor.
	 * Si ya se encontraba en el árbol, devuelve su entero correspondiente sin modificarlo.
	 * @param s - String a insertar
	 * @param valor - Entero que se le asignará 
	 * @return el valor correspondiente al String s
	 */
	public int insertar( final String s, final int valor ) {
		boolean insertado = false;
		NodoTrie puntero = inicio, buscar;
		for( int i = 0; i < s.length(); i++ ) {
			buscar = puntero.ramas.getElementByValue( new NodoTrie(-1,s.charAt(i)) );
			if( buscar == null ) {
				// Si el nodo no existe, la palabra no existe y se inserta el nodo hijo necesario
				NodoTrie nuevo = new NodoTrie(-1,s.charAt(i));
				puntero.ramas.insertOrdered(nuevo);
				puntero = nuevo;
				insertado = true;
			} else {
				puntero = buscar;
			}
		}
		if( insertado ) {
			puntero.valor = valor;
			numElementos++;
		}
		return puntero.valor;
	}
	
	/**
	 * Recorre el árbol y devuelve una lista con los enteros acorde al orden lexicográfico de sus correspondientes strings.
	 * @return un array de enteros según el orden de los strings que representan
	 */
	public ListaArray<Integer> recorrerEnProfundidad() {
		ListaArray<Integer> recorrido = new ListaArray<Integer>(numElementos);
		DFS(inicio, recorrido);
		return recorrido;
	}
	
	/**
	 * Función recursiva que recorre el árbol.
	 * @param n - Nodo del árbol desde el que recorrer
	 * @param lista - Array en el que guardar el resultado del recorrido
	 */
	private void DFS( final NodoTrie n, final ListaArray<Integer> lista ) {
		if( n.ramas.isEmpty() ) {
			lista.insertLast(n.valor);
		} else {
			if( n.valor != -1 )
				lista.insertLast(n.valor);
			ListaEnlazada.Iterador<NodoTrie> it = new ListaEnlazada.Iterador<NodoTrie>();
			it.load(n.ramas);
			while( it.hasNext() )
				DFS( it.next(), lista );
		}
	}
	
}
