package test;

import java.io.IOException;
import gestionDeSentencias.Fichero;

public class pruebaFichero {

	public static void main (String[] args) {
		try {
			//Escribir en el fichero
			Fichero.abrir("data/pruebaFichero.txt",true);		
			Fichero.escribirSentencia("Dani no mola mazo, Mati sí.");
			Fichero.cerrar();
			
			//Escribir la sentencia que se ha guardado previamente en el fichero
			Fichero.abrir("data/pruebaFichero.txt",false);
			System.out.println(Fichero.leerSentencia());
			Fichero.cerrar();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
