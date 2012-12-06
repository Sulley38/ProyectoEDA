package gestionDeSentencias;

import estructurasDeDatos.*;
import java.util.Scanner;

public class Main {

	public static void main (String[] args) {
		Scanner in = new Scanner(System.in);
		String sujeto = "<http://swat.cse.lehigh.edu/onto/univ-bench.owl#AdministrativeStaff>",
				clase = "<http://www.w3.org/2002/07/owl#ObjectProperty>",
				profesor = "<http://www.Department0.University0.edu/FullProfessor0>",
				universidad = "<http://www.University0.edu>";
		String input = "data/in/";
		
		System.out.println("Escriba el nombre del fichero a cargar para las pruebas sobre almacén:");
		input += in.next();
			
		// Cargar el almacén
		System.out.println("Cargando el fichero \"" + input + "\".");
		long t;				
		t = System.currentTimeMillis();
		Almacen m = Almacen.cargar(input);
		t = System.currentTimeMillis() - t;
		if( m == null )
			return;  // Terminar la ejecución en caso de error
		System.out.print("Sentencias leídas en ");
		System.out.print(t);
		System.out.println(" ms");
		
		ListaEnlazada<String> le;
		ListaArray<String> la;
		while (!input.equals("0")) {

			System.out.println("Opciones de prueba válidas: [1, 2, 3, 4, 5, 6, 7a, 7b, 8, 9, 10, 11, 0 = Salir]");
			System.out.print("Introduzca la opción deseada: ");
			input = in.next();
			System.out.println();
			switch (input) {
			case "1":
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
				
			case "2":
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

			case "3":
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

			case "4":
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
			
			case "5":
				// Prueba 5
				System.out.println("Escribiendo entidades que son sujeto común al almacén cargado, A1 y A2...");
				Almacen almacenes[] = new Almacen[3];
				almacenes[0] = m;
				almacenes[1] = Almacen.cargar("data/in/A1.txt");
				almacenes[2] = Almacen.cargar("data/in/A2.txt");
				t = System.nanoTime();
				le = Almacen.entidadesSujetoEnTodos(almacenes);
				t = System.nanoTime() - t;
				le.printToFile("data/out/B5.txt");
				System.out.print("Escrito en ");
				System.out.print(t / 1e6);
				System.out.println(" ms");
				System.out.println();
				break;
				
			case "6":
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
				
			case "7a":
				// Prueba 7a
				System.out.println("Escribiendo las clases de " + sujeto + "...");
				t = System.nanoTime();
				le = m.clasesDe(sujeto);
				t = System.nanoTime() - t;
				le.printToFile("data/out/B7a.txt");
				System.out.print("Escrito en ");
				System.out.print(t / 1e6);
				System.out.println(" ms");
				System.out.println();
				break;

			case "7b":
				// Prueba 7b
				System.out.println("Escribiendo las superclases de " + sujeto + "...");
				t = System.nanoTime();
				le = m.superClasesDe(sujeto);
				t = System.nanoTime() - t;
				le.printToFile("data/out/B7b.txt");
				System.out.print("Escrito en ");
				System.out.print(t / 1e6);
				System.out.println(" ms");
				System.out.println();
				break;
				
			case "8":
				// Prueba 8
				System.out.println("Escribiendo entidades que son de la clase " + clase + "...");
				t = System.nanoTime();
				le = m.entidadesDeClase(clase);
				t = System.nanoTime() - t;
				le.printToFile("data/out/B8.txt");
				System.out.print("Escrito en ");
				System.out.print(t / 1e6);
				System.out.println(" ms");
				System.out.println();
				break;
				
			case "9":
				// Prueba 9
				System.out.println("Descargando las sentencias del almacén en el fichero B9.txt ...");
				t = System.nanoTime();
				m.descargar("data/out/B9.txt");
				t = System.nanoTime() - t;
				System.out.print("Escrito en ");
				System.out.print(t / 1e6);
				System.out.println(" ms");
				System.out.println();
				break;
				
			case "10":
				// Prueba 10
				System.out.println("Escribiendo los estudiantes distintos de alguna asignatura del profesor " + profesor + "...");
				t = System.nanoTime();
				le = m.estudiantesDelProfesor(profesor);
				t = System.nanoTime() - t;
				le.printToFile("data/out/B10.txt");
				System.out.print("Escrito en ");
				System.out.print(t / 1e6);
				System.out.println(" ms");
				System.out.println();
				break;
			
			case "11":
				// Prueba 11
				System.out.println("Escribiendo los profesores distintos que trabajan para algún departamento de la universidad " + universidad + "...");
				t = System.nanoTime();
				le = m.profesoresDeUniversidad(universidad);
				t = System.nanoTime() - t;
				le.printToFile("data/out/B11.txt");
				System.out.print("Escrito en ");
				System.out.print(t / 1e6);
				System.out.println(" ms");
				System.out.println();
				break;
				
			case "0":
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
