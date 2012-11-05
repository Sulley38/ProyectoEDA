package gestionDeSentencias;

import estructurasDeDatos.*;
import java.util.Scanner;

public class Main {

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
				System.out.print("Escrito en ");
				System.out.print(t / 1e6);
				System.out.println(" ms");
				System.out.println();
				break;
				
			case 2:
				// Prueba 2
				System.out.println("Escribiendo sentencias distintas que tienen el sujeto " + sujeto + " ...");
				t = System.nanoTime();
				le = m.sentenciasDistintasPorSujeto(sujeto);
				t = System.nanoTime() - t;
				le.printToFile("data/out/B2.txt");
				System.out.print("Escrito en ");
				System.out.print(t / 1e6);
				System.out.println(" ms");
				System.out.println();
				break;

			case 3:
				// Prueba 3
				System.out.println("Escribiendo propiedades distintas que aparecen en el almacén...");
				t = System.nanoTime();
				la = m.propiedadesDistintas();
				t = System.nanoTime() - t;
				la.printToFile("data/out/B3.txt");
				System.out.print("Escrito en ");
				System.out.print(t / 1e6);
				System.out.println(" ms");
				System.out.println();
				break;

			case 4:
				// Prueba 4
				System.out.println("Escribiendo entidades distintas que son sujeto y también objeto de alguna sentencia...");
				t = System.nanoTime();
				le = m.entidadesSujetoObjeto();
				t = System.nanoTime() - t;
				le.printToFile("data/out/B4.txt");
				System.out.print("Escrito en ");
				System.out.print(t / 1e6);
				System.out.println(" ms");
				System.out.println();
				break;
			
			case 5:
				//Prueba 5
				System.out.println("Escribiendo entidades que son sujeto en este almacen y en el 1 y el 2");
				Almacen almacenes[]=new Almacen[2];
				almacenes[0]=new Almacen("data/in/A1.txt");
				almacenes[1]=new Almacen("data/in/A2.txt");
				t = System.nanoTime();
				le = m.interseccion(almacenes);
				t = System.nanoTime() - t;
				le.printToFile("data/out/B5.txt");
				System.out.print("Escrito en ");
				System.out.print(t / 1e6);
				System.out.println(" ms");
				System.out.println();
				break;
			case 6:
				// Prueba 6
				System.out.println("Escribiendo en orden las sentencias que aparecen en el almacén...");
				t = System.nanoTime();
				la = m.sentenciasOrdenadas();
				t = System.nanoTime() - t;
				la.printToFile("data/out/B6.txt");
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
	
}
