package gestionDeSentencias;

public class Main {

	public static void main (String[] args) {	
		Almacen m;
		String file = "data/in/A5.txt", sujeto = "<http://swat.cse.lehigh.edu/onto/univ-bench.owl#AdministrativeStaff>";
		
		// Cargar el almacén
		System.out.println("Cargando el fichero \"" + file + "\"");
		long t=System.currentTimeMillis();
		m=new Almacen(file);
		System.out.print("Sentencias leídas en ");
		System.out.print(System.currentTimeMillis()-t);
		System.out.println(" ms");
		
		// Prueba 1
		System.out.println("Escribiendo sentencias que tienen el sujeto "+sujeto);
		t=System.currentTimeMillis();
		m.sentenciasPorSujeto(sujeto).imprimirEnFichero("data/out/B0.txt");
		System.out.print("Escrito en ");
		System.out.print(System.currentTimeMillis()-t);
		System.out.println(" ms");
		
		
		// Prueba 2
		System.out.println("Escribiendo sentencias distintas que tienen el sujeto "+sujeto);
		t=System.currentTimeMillis();
		m.sentenciasDistintasPorSujeto(sujeto).imprimirEnFichero("data/out/B1.txt");
		System.out.print("Escrito en ");
		System.out.print(System.currentTimeMillis()-t);
		System.out.println(" ms");
		
		
		// Prueba 3
		System.out.println("Escribiendo propiedades distintas que aparecen en el almacén");
		t=System.currentTimeMillis();
		m.propiedadesDistintas().imprimirEnFichero("data/out/B2.txt");
		System.out.print("Escrito en ");
		System.out.print(System.currentTimeMillis()-t);
		System.out.println(" ms");
		
		
		// Prueba 4
		System.out.println("Escribiendo entidades distintas que son sujeto y también objeto de alguna sentencia");
		t=System.currentTimeMillis();
		m.entidadesSujetoObjeto().imprimirEnFichero("data/out/B3.txt");
		System.out.print("Escrito en ");
		System.out.print(System.currentTimeMillis()-t);
		System.out.println(" ms");
		
	}
	
}
