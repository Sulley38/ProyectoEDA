package gestionDeSentencias;

public class Main {

	public static void main (String[] args) {	
		Almacen m;
		String file = "data/A5.txt", sujeto = "<http://swat.cse.lehigh.edu/onto/univ-bench.owl#AdministrativeStaff>";
		
		// Cargar el almac�n
		System.out.println("Cargando el fichero \"" + file + "\"");
		long t=System.currentTimeMillis();
		m=new Almacen(file);
		System.out.print("Sentencias le�das en ");
		System.out.print(System.currentTimeMillis()-t);
		System.out.println(" ms");
		
		// Prueba 1
		System.out.println("Escribiendo sentencias que tienen el sujeto "+sujeto);
		t=System.currentTimeMillis();
		m.sentenciasPorSujeto(sujeto);
		System.out.print("Escrito en ");
		System.out.print(System.currentTimeMillis()-t);
		System.out.println(" ms");
		
		
		// Prueba 2
		System.out.println("Escribiendo sentencias distintas que tienen el sujeto "+sujeto);
		t=System.currentTimeMillis();
		m.sentenciasDistintasPorSujeto(sujeto);
		System.out.print("Escrito en ");
		System.out.print(System.currentTimeMillis()-t);
		System.out.println(" ms");
		
		
		// Prueba 3
		System.out.println("Escribiendo propiedades distintas que aparecen en el almac�n");
		t=System.currentTimeMillis();
		m.propiedadesDistintas();
		System.out.print("Escrito en ");
		System.out.print(System.currentTimeMillis()-t);
		System.out.println(" ms");
		
		
		// Prueba 4
		System.out.println("Escribiendo entidades distintas que son sujeto y tambi�n objeto de alguna sentencia");
		t=System.currentTimeMillis();
		m.entidadesSujetoObjeto();
		System.out.print("Escrito en ");
		System.out.print(System.currentTimeMillis()-t);
		System.out.println(" ms");
		
	}
	
}
