package test;
import java.io.IOException;

import gestionDeSentencias.Fichero;

public class pruebaFichero {

	
	
	public static void main (String[] args) {
		
		Fichero miFichero = new Fichero();
			
	try {
		//Escribir en el fichero
		miFichero.abrir("docs/PruebaFichero.txt",true);		
		miFichero.escribirSentencia(" Dani mola mazo");
		miFichero.cerrar();
		
		//Escribir la sentencia que se ha guardado previamente en el fichero
		miFichero.abrir("docs/PruebaFichero.txt",false);
		System.out.println(miFichero.leerSentencia());
		miFichero.cerrar();
	} catch (IOException e) {e.printStackTrace();}
	
	
	}
}
