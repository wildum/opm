import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
	
	public static final boolean DEBUG = true;
	public static final int MAX_VALUE = 100000;

	public static void main(String[] args) {
		
		List<String> lines = new ArrayList<>();
		
		if(DEBUG) {
			try {
				lines = Files.readAllLines(Paths.get("input2.txt"));
			} catch (Exception e) {
				System.out.println("No file with the name: "+args[0]);
				System.exit(-1);
			}
		} else {
			if (args.length < 1) {
				System.out.println("No file. Exiting program.");
				System.exit(-1);
			}
			
			try {
				lines = Files.readAllLines(Paths.get(args[0]));
			} catch (Exception e) {
				System.out.println("No file with the name: "+args[0]);
				System.exit(-1);
			}
		}
		Pit pit = new Pit();
		pit.buildGraph(lines);
		FordFulkerson fordFulkerson = new FordFulkerson(pit);
		System.out.println("Best score : " + fordFulkerson.compute());
		System.err.println("Digging profile : " + fordFulkerson.getPath().toString());
		pit.printDebug();
	}
}
