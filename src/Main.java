import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class Main {

	public static void palabrasMismoSujeto(String x){
		
		String miPalabra;		
		try {
			
			Scanner inPut = new Scanner(new File("src/lab2/datos/extractoDatosUniv.txt"));
			PrintWriter outPut = new PrintWriter ( new File ("src/lab2/datos/resultado.txt") );
			
			while (inPut.hasNext()){
				miPalabra = inPut.next();
				
				if(x == miPalabra){
					miPalabra= inPut.nextLine();
					outPut.println(miPalabra);
				}
			}
			
			inPut.close();
			outPut.close();
			
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		}
		
		
	}
	
	public static void main (String [] args){
			String y = "<http://swat.cse.lehigh.edu/onto/univ-bench.owl#AdministrativeStaff>";	
			
			
		
		
	}
	
	
}
