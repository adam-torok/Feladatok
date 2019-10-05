import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Hianyzasok_stream {

	public static void main(String[] args) throws IOException {
		var hianyzasok = Files.lines(Path.of("szeptember.csv"))
							  .skip(1)
							  .map(Hianyzas::new)
							  .toArray(Hianyzas[]::new);
		
		System.out.println("2. Feladat: Hianyzott orak: " + Arrays.stream(hianyzasok).mapToInt(k -> k.mulasztottOrak).sum());
		System.out.println("3. Feladat: �rj be egy napot(1-30) �s egy nevet!");
		
		try(var input = new Scanner(System.in)){
			var bekertNap = input.nextInt();
			input.nextLine();
			var bekertNev = input.nextLine();
			
			System.out.println("4. Feladat");
			Arrays.stream(hianyzasok)
				  .filter(k -> k.nev.equals(bekertNev))
				  .findFirst()
				  .ifPresentOrElse(k -> System.out.println(bekertNev + " hianyzott"), 
						  		  () -> System.out.println(bekertNev + " nem hi�nyzott"));
			
			System.out.println("5. Feladat");
			var azonANaponHianyoztak = Arrays.stream(hianyzasok)
											 .filter(k -> bekertNap >= k.elsoNap && bekertNap <= k.utolsoNap)
											 .toArray(Hianyzas[]::new);
			
			if(azonANaponHianyoztak.length == 0) {
				System.out.println("Nem volt hi�nyz�");
			}else {
				Arrays.stream(azonANaponHianyoztak).forEach(hiany -> System.out.println(hiany.nev + " " + hiany.osztaly));
			}
		}
		
		var hianyzasokStat = Arrays.stream(hianyzasok)
								   .collect(Collectors.groupingBy(k -> k.osztaly, Collectors.summingInt(k -> k.mulasztottOrak)))
								   .entrySet().stream()
								   .map(k -> k.getKey() + ";" + k.getValue())
								   .collect(Collectors.toList());
		
		Files.write(Path.of("osszesites.csv"), hianyzasokStat);
	}
	
	public static class Hianyzas{
		public final String nev;
		public final String osztaly;
		public final int elsoNap;
		public final int utolsoNap;
		public final int mulasztottOrak;
		
		public Hianyzas(String sor) {
			var split = sor.split(";");
			
			this.nev = split[0];
			this.osztaly = split[1];
			this.elsoNap = Integer.parseInt(split[2]);
			this.utolsoNap = Integer.parseInt(split[3]);
			this.mulasztottOrak = Integer.parseInt(split[4]);
		}
	}
}