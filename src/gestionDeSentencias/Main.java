package gestionDeSentencias;

import java.util.Scanner;

public class Main {

	public static void main (String[] args) {	
		Almacen m;
		int prueba;
		Scanner in = new Scanner(System.in);
		String sujeto = "<http://swat.cse.lehigh.edu/onto/univ-bench.owl#AdministrativeStaff>";
		String file = "data/in/";
		
		System.out.println("Escriba el nombre del fichero a cargar:");
		file += in.next();
			
		// Cargar el almacén
		System.out.println("Cargando el fichero \"" + file + "\"");
		long t;				
		t=System.currentTimeMillis();
		m=new Almacen(file);
		t=System.currentTimeMillis()-t;
		System.out.print("Sentencias leídas en ");
		System.out.print(t);
		System.out.println(" ms");
		
		while(true){
			
			System.out.println("Escriba el numero de la prueba a realizar:");
			prueba = in.nextInt();
			switch(prueba){
			case 1:
				// Prueba 1
				System.out.println("Escribiendo sentencias que tienen el sujeto "+sujeto);
				t=System.nanoTime();
				m.sentenciasPorSujeto(sujeto).imprimirEnFichero("data/out/B0.txt");
				t=System.nanoTime()-t;
				System.out.print("Escrito en ");
				System.out.print(t/1e6);
				System.out.println(" ms");
				break;
			case 2:	
				// Prueba 2
				System.out.println("Escribiendo sentencias distintas que tienen el sujeto "+sujeto);
				t=System.nanoTime();
				m.sentenciasDistintasPorSujeto(sujeto).imprimirEnFichero("data/out/B1.txt");
				t=System.nanoTime()-t;
				System.out.print("Escrito en ");
				System.out.print(t/1e6);
				System.out.println(" ms");
				break;
				
			case 3:	
				// Prueba 3
				System.out.println("Escribiendo propiedades distintas que aparecen en el almacén");
				t=System.nanoTime();
				m.propiedadesDistintas().imprimirEnFichero("data/out/B2.txt");
				t=System.nanoTime()-t;
				System.out.print("Escrito en ");
				System.out.print(t/1e6);
				System.out.println(" ms");
				break;
				
			case 4:	
				// Prueba 4
				System.out.println("Escribiendo entidades distintas que son sujeto y también objeto de alguna sentencia");
				t=System.nanoTime();
				m.entidadesSujetoObjeto().imprimirEnFichero("data/out/B3.txt");
				t=System.nanoTime()-t;
				System.out.print("Escrito en ");
				System.out.print(t/1e6);
				System.out.println(" ms");
				break;
				
			default:
				System.out.println("Escriba un numero del 1 al 4");				
				break;
			}
		}
	}
	
}
