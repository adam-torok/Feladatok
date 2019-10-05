import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Szavak_stream {
	
	public static void main(String[] args) throws IOException{
		var conPut = new Scanner(System.in);
		System.out.println("�rj be 1 sz�t");
		
		var bekert = conPut.next().toLowerCase();
		if(bekert.contains("a") || bekert.contains("e") || bekert.contains("i") || bekert.contains("o") || bekert.contains("u")) {
			System.out.println("Van benne mag�nhangz�");
		}else{
			System.out.println("Nincs benne mag�nhangz�");
		}
		
		Files.lines(Path.of("szoveg.txt"))
			 .max(Comparator.comparingInt(String::length))
			 .ifPresent(longest -> System.out.println("A leghoszabb sz�: " + longest + ", hossza: " + longest.length()));
		
		var lineCount = Files.lines(Path.of("szoveg.txt")).count();
		System.out.println("�sszes sz�: " + lineCount);
		
		var mghWordCount = Files.lines(Path.of("szoveg.txt"))
							 	.filter(k -> k.contains("a") || k.contains("e"))
							 	.filter(k -> k.contains("i") || k.contains("o"))
							 	.filter(k -> k.contains("u"))
							 	.count();
		
		System.out.println(Files.lines(Path.of("szoveg.txt"))
							 	.filter(k -> k.contains("a") || k.contains("e"))
							 	.filter(k -> k.contains("i") || k.contains("o"))
							 	.filter(k -> k.contains("u"))
							 	.collect(Collectors.joining(" ")));
		
		System.out.println("Mgh-s szavak sz�ma: " + mghWordCount);
		System.out.printf(mghWordCount + "/" + lineCount + ", ez sz�zal�kban %.2f\n", (float)mghWordCount / lineCount * 100);
		
		var otbetus = Files.lines(Path.of("szoveg.txt"))
				 		   .filter(k -> k.length() == 5)
				 		   .toArray(String[]::new);
		
		System.out.println("�rj be 1 sz�r�szletet");
		var meh = conPut.next();
		
		System.out.println(Arrays.stream(otbetus)
								 .filter(k -> k.contains(meh))
								 .collect(Collectors.joining(" ")));

		conPut.close();
		
		try(var output = new PrintWriter("letra.txt")){
			var used = new ArrayList<String>();
			var buffer = new ArrayList<String>();
			
			for(var check : otbetus) {
				var threeLetters = check.substring(1, 4);
				
				if(!used.contains(threeLetters)) {
					used.add(threeLetters);
					
					for(var sajt : otbetus) {
						if(sajt.regionMatches(1, threeLetters, 0, 3)) {
							buffer.add(sajt);
						}
					}
					if(buffer.size() > 1) {
						for(var print : buffer) {
							output.println(print);
						}
						output.println();
					}
					buffer.clear();
				}
			}
		}
	}
}