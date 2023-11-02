import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;

public class Application {

	private final Clerk clerk;

	private final File ErrorFile;
	private final File outputFile;
	private final Console console;
	//Vertex Count + 1
	private final int ARRAY_SIZE = 12 + 1;

	//Dominant/Independent
	private long[][] DominantIndependent = new long[ARRAY_SIZE][ARRAY_SIZE];

	//Dominant/Dependent
	private long[][] DominantDependent = new long[ARRAY_SIZE][ARRAY_SIZE];

	//Dependent/Independent
	private long[][] DependentIndependent = new long[ARRAY_SIZE][ARRAY_SIZE];

	public Application(String outputFileName) {
		this.outputFile = new File(outputFileName);
		this.console = System.console();
		this.ErrorFile = new File("error_file.txt");
		this.clerk = new Clerk();
	}

	public int start() throws IOException {
		MyTimerTask timerTask = new MyTimerTask();
		new Timer().scheduleAtFixedRate(timerTask, 0, 600000);
		//600000
		String line = "";
		try(BufferedReader bi = new BufferedReader(new InputStreamReader(System.in))) {
			int dominantNumber = 0, independentNumber = 0, dependentNumber = 0;
			while ((line = bi.readLine()) != null) {
				if (!Graph6Converter.validate(line)) continue;
				FDS fds = new FDS(Graph6Converter.fromGraph6ToAdjacentMatrix(line));
				dominantNumber = fds.getDominantNumber();
				independentNumber = fds.getIndependentNumber();
				dependentNumber = fds.getDependentNumber();
				DominantIndependent[dominantNumber][independentNumber]++;
				DominantDependent[dominantNumber][dependentNumber]++;
				DependentIndependent[dependentNumber][independentNumber]++;
			}
		} catch (Exception e) {
			clerk.write(Arrays.asList(e.toString(), line), ErrorFile);
			return 1;
		}
		timerTask.run();
		return 0;
	}
	class MyTimerTask extends TimerTask {
		public void run() {
			try {
				clerk.write(Arrays.asList(
						Arrays.deepToString(DominantIndependent),
						Arrays.deepToString(DominantDependent),
						Arrays.deepToString(DependentIndependent)), outputFile);
			} catch (Exception e) {//Catch exception if any
				System.err.println("Error: " + e.getMessage() + e.getStackTrace()[0].toString());
			}
		}
	}
}