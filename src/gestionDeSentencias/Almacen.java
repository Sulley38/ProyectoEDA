package gestionDeSentencias;
import java.io.*;
import java.util.*;
import estructurasDeDatos.*;
import estructurasDeDatos.ListaEnlazada.Iterador;

/**
 * Almac�n de sentencias:
 * Contiene todas las sentencias de un fichero y las operaciones definidas para trabajar sobre ellas
 * @author Daniel, Iv�n, Asier
 */
public class Almacen {

	/**
	 * Estructura de arista del grafo.
	 * Indica el v�rtice de destino, su peso (la propiedad) y el n�mero de veces que aparece repetida
	 */
	private class Arista {
		private int verticeObjetivo, arista, repeticiones;
		public Arista(int Objetivo, int Propiedad) {
			verticeObjetivo = Objetivo;
			arista = Propiedad;
			repeticiones = 0;
		}
		public int obtenerVertice() {
			return verticeObjetivo;
		}
		public int obtenerArista() {
			return arista;
		}
		public int obtenerRepeticiones() {
			return repeticiones;
		}
		public void addRepeticion() {
			repeticiones++;
		}
	}
	
	/// ATRIBUTOS DE LA CLASE
	// Dos listas de adyacencia para representar el grafo.
	private ListaArray< ListaEnlazada<Arista>> nodosEntrantes, nodosSalientes; // nodosEntrantes[i][j] devuelve la j-�sima relaci�n del nodo i, cuyos valores son nodosEntrantes[i][j].nodoObjetivo, etc. 
	// N�mero de nodos (sujetos+objetos) y de aristas (propiedades); la suma de los tres es el n�mero de entidades
	private int sujetos, objetos, propiedades;
	// Relaciones entre entidad e �ndice correspondiente (Trie), y viceversa (array)
	private Trie arbolSujetosObjetos, arbolPropiedades;
	private ListaArray<String>listaSujetosObjetos, listaPropiedades;
	
	
	
	/**
	 * CONSTRUCTORA
	 * Carga las sentencias en el almac�n desde el fichero especificado.
	 * @param nombreDeArchivo de texto desde el que leer las entidades
	 */
	public Almacen( String nombreDeArchivo ) {
		sujetos = objetos = propiedades = 0;
		arbolSujetosObjetos = new Trie();
		arbolPropiedades = new Trie();
		//Listas enlazadas temporales antes de conocer el tama�o de los arrays definitivos
		ListaArray<String> tempSujetosObjetos = new ListaArray<String>(250000);
		ListaArray<String> tempPropiedades = new ListaArray<String>(100);
		ListaArray< ListaEnlazada<Arista> > tempNodosEntrantes = new ListaArray< ListaEnlazada<Arista> >(250000);
		ListaArray< ListaEnlazada<Arista> > tempNodosSalientes = new ListaArray< ListaEnlazada<Arista> >(250000);
		ListaEnlazada<Arista> tempLista;
		Arista tempArista;
		boolean encontrado;
		// Lee las sentencias desde el fichero y las a�ade al trie y a la lista de nodos del grafo
		try {
			Fichero.abrir(nombreDeArchivo, false);
			String sentencia, sujeto, propiedad, objeto;
			int idSujeto, idPropiedad, idObjeto;
			StringTokenizer tokenizador;
			while( (sentencia = Fichero.leerSentencia()) != null ) {
				tokenizador = new StringTokenizer(sentencia);
				sujeto = tokenizador.nextToken();
				propiedad = tokenizador.nextToken();
				objeto = tokenizador.nextToken();
				// MONTAR EL GRAFO: Necesita revisi�n
				if( arbolSujetosObjetos.insertar(sujeto, sujetos+objetos) ) {
					tempSujetosObjetos.insertLast(sujeto);					
					// A�adir su hueco en la lista de nodos entrantes y salientes
					tempNodosEntrantes.insertLast( new ListaEnlazada<Arista>() );
					tempNodosSalientes.insertLast( new ListaEnlazada<Arista>() );
					// Incrementar contador
					idSujeto = sujetos+objetos;
					sujetos++;
				} else {
					idSujeto = arbolSujetosObjetos.obtenerValor(sujeto); // Esto se puede mejorar si haces que insertar() devuelva el valor de la ya insertada en lugar de true/false
				}
				if( arbolPropiedades.insertar(propiedad, propiedades) ) {
					tempPropiedades.insertLast(propiedad);
					// Incrementar contador
					idPropiedad = propiedades;
					propiedades++;
				} else {
					idPropiedad = arbolPropiedades.obtenerValor(propiedad);
				}
				if( arbolSujetosObjetos.insertar(objeto, sujetos+objetos) ) {
					tempSujetosObjetos.insertLast(objeto);
					// A�adir su hueco en la lista de nodos entrantes y salientes
					tempNodosEntrantes.insertLast( new ListaEnlazada<Arista>() );
					tempNodosSalientes.insertLast( new ListaEnlazada<Arista>() );
					// Incrementar contador
					idObjeto = sujetos+objetos;
					objetos++;
				} else {
					idObjeto = arbolSujetosObjetos.obtenerValor(objeto);
				}
				// Insertar la relaci�n en sus sitios
				encontrado = false;
				tempLista = tempNodosSalientes.getElementByPosition(idSujeto);
				tempLista.reset();
				while(tempLista.hasNext()){					
					tempArista=tempLista.next();
					if(tempArista.obtenerVertice()==idObjeto && tempArista.obtenerArista()==idPropiedad){
						tempArista.addRepeticion();
						encontrado=true;
						break;
					}
				}				
				//si no lo ha encontrado crea la arista
				if (!encontrado){					
					tempLista.insertLast(new Arista(idObjeto,idPropiedad));
				}				
				
				// Lo mismo con tempNodosSalientes
				encontrado = false;
				tempLista = tempNodosEntrantes.getElementByPosition(idObjeto);
				tempLista.reset();
				while(tempLista.hasNext()){					
					tempArista=tempLista.next();
					if(tempArista.obtenerVertice()==idSujeto && tempArista.obtenerArista()==idPropiedad){
						tempArista.addRepeticion();
						encontrado=true;
						break;
					}
				}				
				//si no lo ha encontrado crea la arista
				if (!encontrado){					
					tempLista.insertLast(new Arista(idSujeto,idPropiedad));
				}				
			}
			//Convertir las estructuras enlazadas en Arrays
			nodosEntrantes = tempNodosEntrantes;
			nodosSalientes = tempNodosSalientes;
			listaSujetosObjetos = tempSujetosObjetos;
			listaPropiedades = tempPropiedades;
			
			Fichero.cerrar();
		} catch (IOException e) {
			System.err.println("Error: Imposible acceder al fichero especificado.");
			return;
		}
		
		// TODO: Pasar las listas enlazadas a arrays est�ticos
	}
	
	/**
	 * 1) Colecci�n de sentencias del almac�n que tienen un sujeto determinado
	 * @param Sujeto
	 * @return
	 * @throws IOException 
	 */
	public String[] sentenciasPorSujeto( String Sujeto, BufferedWriter out ) throws IOException {
		int index = arbolSujetosObjetos.obtenerValor(Sujeto);
		Arista prov;
		Iterator<Arista> it= nodosSalientes.getElementByPosition(index).iterator();
		while (it.hasNext()){
			prov= it.next();
			for(int i=0;i<prov.obtenerRepeticiones();i++){
				out.write( listaSujetosObjetos.getElementByPosition(index)+" "+ listaPropiedades.getElementByPosition(prov.arista)+" "+listaSujetosObjetos.getElementByPosition(prov.verticeObjetivo)+" .\n");
			}
		}
		return null;
	}
	
	/**
	 * 2) Colecci�n de sentencias distintas del almac�n que tienen un sujeto determinado
	 * @param Sujeto
	 * @return
	 */
	public String[] sentenciasDistintasPorSujeto( String Sujeto, BufferedWriter out ) throws IOException {
		int index = arbolSujetosObjetos.obtenerValor(Sujeto);
		Arista prov;
		Iterator<Arista> it= nodosSalientes.getElementByPosition(index).iterator();
		while (it.hasNext()){
			prov= it.next();
			out.write( listaSujetosObjetos.getElementByPosition(index)+" "+ listaPropiedades.getElementByPosition(prov.arista)+" "+listaSujetosObjetos.getElementByPosition(prov.verticeObjetivo)+" .\n");
		}
		return null;
	}
	
	/**
	 * 3) Colecci�n de propiedades distintas que aparecen en las sentencias del almac�n
	 * @return
	 */
	public String[] propiedadesDistintas(BufferedWriter out) throws IOException {
		for(int i=0;i<listaPropiedades.size();i++){
			out.write(listaPropiedades.getElementByPosition(i)+"\n");
		}
		return null;
	}
	
	/**
	 * 4) Colecci�n de entidades distintas que son sujeto de alguna sentencia y tambi�n
	 * son objeto de alguna sentencia de ese almac�n
	 * @return
	 * @throws IOException 
	 */
	public String[] entidadesSujetoObjeto(BufferedWriter out) throws IOException {
		for(int i = 0; i<nodosSalientes.size();i++){
			if(!nodosSalientes.getElementByPosition(i).isEmpty() && !nodosEntrantes.getElementByPosition(i).isEmpty()){
				out.write(listaSujetosObjetos.getElementByPosition(i)+"\n");
			}
		}
		return null;
	}
	
}
