/*
 * Almac�n de sentencias
 */
public class Almacen {

	// Matriz de adyacencia
	// Puede que haga falta cambiar el tipo de dato contenido, de int a 'Lista de enteros'
	// i -> Sujeto, j -> Objeto, dato -> Propiedad
	private int[][] Relaciones;
	// N�mero de nodos (sujetos+objetos) y de aristas (propiedades)
	private int sujetos, objetos, propiedades;
	// Relaciones biyectivas entre �ndices y entidades correspondientes
	/***
	 * TODO: Implementar una estructura de datos que permita encontrar
	 *	de forma eficiente el �ndice dado un string
	 **/
	
	
	/**
	 * CONSTRUCTORA
	 */
	public Almacen() {
		sujetos = objetos = propiedades = 0;
	}
	
	
	/**
	 * 1) Colecci�n de sentencias del almac�n que tienen un sujeto determinado
	 * @param Sujeto
	 * @return
	 */
	public String[] sentenciasPorSujeto( String Sujeto ) {
		// TODO: Implementar
		return null;
	}
	
	/**
	 * 2) Colecci�n de sentencias distintas del almac�n que tienen un sujeto determinado
	 * @param Sujeto
	 * @return
	 */
	public String[] sentenciasDistintasPorSujeto( String Sujeto ) {
		// TODO: Implementar
		return null;
	}
	
	/**
	 * 3) Colecci�n de propiedades distintas que aparecen en las sentencias del almac�n
	 * @return
	 */
	public String[] propiedadesDistintas() {
		// TODO: Implementar
		return null;
	}
	
	/**
	 * 4) Colecci�n de entidades distintas que son sujeto de alguna sentencia y tambi�n
	 * son objeto de alguna sentencia de ese almac�n
	 * @return
	 */
	public String[] entidadesSujetoObjeto() {
		// TODO: Implementar
		return null;
	}
	
}
