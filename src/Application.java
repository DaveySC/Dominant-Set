import java.io.*;
import java.nio.file.Files;

public class Application {
	private final File inputFileName;
	private final File outputFileName;
	private final Console console;

	public Application(String inputFileName, String outputFileName) {
		this.inputFileName = new File(inputFileName);
		this.outputFileName = new File(outputFileName);
		this.console = System.console();
	}

	public int start() {
		try(InputStream is = Files.newInputStream(inputFileName.toPath());
		    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		    BufferedWriter writer = Files.newBufferedWriter(outputFileName.toPath())) {
			long counter = 0;
			String line = null;
			while ((line = reader.readLine()) != null){
				for (int j = 0; j < 10; j++) {
					console.printf("%s", '\u0008');
				}
				console.printf("%d", ++counter);
				FDS fds = new FDS(Graph6Converter.fromGraph6ToAdjacentMatrix(line));
				writer.write(fds.getDominantNumber() + " "
						+ fds.getDependentNumber() + " "
						+ fds.getIndependentNumber());
				writer.write(System.lineSeparator());
			}

		} catch (IOException e) {
			System.err.println(e.getMessage());
			return 1;
		}
		return 0;
	}
}
