package test;

import estructurasDeDatos.*;
import gestionDeSentencias.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Scanner;

public class hashTest{

	public static void main (String[] args) {
		Almacen m;
		Scanner in = new Scanner(System.in);
		String sujeto = "<http://swat.cse.lehigh.edu/onto/univ-bench.owl#AdministrativeStaff>";
		String file = "data/in/";
		
		System.out.println("Escriba el nombre del fichero a cargar:");
		file += in.next();
			
		// Cargar el almacén
		System.out.println("Cargando el fichero \"" + file + "\".");
		long t;				
		t = System.currentTimeMillis();
		m = new Almacen(file);
		t = System.currentTimeMillis() - t;
		System.out.print("Sentencias leídas en ");
		System.out.print(t);
		System.out.println(" ms");
		File fis;
		int respuesta = 1;
		ListaEnlazada<String> le;
		ListaArray<String> la;
		while (respuesta > 0) {

			System.out.print("Introduzca un número del 1 al 4 para ejecutar las pruebas, o 0 para salir: ");
			respuesta = in.nextInt();
			System.out.println();
			switch (respuesta) {
			case 1:
				// Prueba 1
				System.out.println("Escribiendo sentencias que tienen el sujeto " + sujeto + " ...");
				t = System.nanoTime();
				le = m.sentenciasPorSujeto(sujeto);
				t = System.nanoTime() - t;
				le.printToFile("data/out/B1.txt");
				
				fis = new File("data/out/B1.txt");
				System.out.println(checksum(fis));
				System.out.println();
				break;
				
			case 2:
				// Prueba 2
				System.out.println("Escribiendo sentencias distintas que tienen el sujeto " + sujeto + " ...");
				t = System.nanoTime();
				le = m.sentenciasDistintasPorSujeto(sujeto);
				t = System.nanoTime() - t;
				le.printToFile("data/out/B2.txt");
				
				fis = new File("data/out/B2.txt");
				System.out.println(checksum(fis));
				System.out.println();
				break;

			case 3:
				// Prueba 3
				System.out.println("Escribiendo propiedades distintas que aparecen en el almacén...");
				t = System.nanoTime();
				la = m.propiedadesDistintas();
				t = System.nanoTime() - t;
				la.printToFile("data/out/B3.txt");
				
				fis = new File("data/out/B3.txt");
				System.out.println(checksum(fis));
				System.out.println();
				break;

			case 4:
				// Prueba 4
				System.out.println("Escribiendo entidades distintas que son sujeto y también objeto de alguna sentencia...");
				t = System.nanoTime();
				le = m.entidadesSujetoObjeto();
				t = System.nanoTime() - t;
				le.printToFile("data/out/B4.txt");
				fis = new File("data/out/B4.txt");
				System.out.println(checksum(fis));
				System.out.print("Escrito en ");
				System.out.print(t / 1e6);
				System.out.println(" ms");
				System.out.println();
				break;

			case 0:
				// Salir
				System.out.println("Fin del programa de pruebas.");
				break;
				
			default:
				System.out.println("No es una opción válida.");
				break;
			}
		}
	}
	
	public static String checksum(File file) {
		  try {
		    InputStream fin = new FileInputStream(file);
		    java.security.MessageDigest md5er =
		        MessageDigest.getInstance("MD5");
		    byte[] buffer = new byte[1024];
		    int read;
		    do {
		      read = fin.read(buffer);
		      if (read > 0)
		        md5er.update(buffer, 0, read);
		    } while (read != -1);
		    fin.close();
		    byte[] digest = md5er.digest();
		    if (digest == null)
		      return null;
		    String strDigest = "0x";
		    for (int i = 0; i < digest.length; i++) {
		      strDigest += Integer.toString((digest[i] & 0xff) 
		                + 0x100, 16).substring(1).toUpperCase();
		    }
		    return strDigest;
		  } catch (Exception e) {
		    return null;
		  }
		}

	
}