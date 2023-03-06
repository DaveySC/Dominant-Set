import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class FDS {
	enum TypeOfGraph {DOMINANT, DEPENDENT, INDEPENDENT, NOT_DOMINANT};

	private final boolean[][] graph;

	private final int vertex;

	private final Set<Integer> independentSet = new TreeSet<>();

	private final Set<Integer> dependentSet   = new TreeSet<>();

	private final Set<Integer> dominantSet = new TreeSet<>();

	public FDS(boolean[][] graph) {
		this.graph = graph;
		this.vertex = graph.length;
		findDominantSets();
	}


	public void findDominantSets() {
		for (int i = 0; i < 1 << vertex; i++) {
			TypeOfGraph type = identifyTypeOfGraph(i);
			if (type == TypeOfGraph.DEPENDENT) dependentSet.add(i);
			if (type == TypeOfGraph.INDEPENDENT) independentSet.add(i);
			if (type == TypeOfGraph.DOMINANT) dominantSet.add(i);
		}

	}

	private TypeOfGraph identifyTypeOfGraph(int n) {
		if (!isDominant(n)) return TypeOfGraph.NOT_DOMINANT;
		if (isIndependent(n)) return TypeOfGraph.INDEPENDENT;
		if (isDependent(n)) return TypeOfGraph.DEPENDENT;
		return TypeOfGraph.DOMINANT;
	}


	private boolean isDominant(int n) {
		boolean[] adjacent = new boolean[vertex];
		for (int i = 0; i < vertex; i++) {
			if (!getBitFromInt(n, i)) continue;
			for (int j = 0; j < vertex; j++) {
				if (graph[i][j]) adjacent[j] = true;
			}
			adjacent[i] = true;
		}
		for (boolean b : adjacent) if (!b) return false;
		return true;
	}


	private boolean getBitFromInt(int b, int position) {
		return ((b & (1 << position)) >> position) == 1;
	}

	private boolean isIndependent(int n) {
		boolean[] adjacent = new boolean[vertex];
		for (int i = 0; i < vertex; i++) {
			if (!getBitFromInt(n, i)) continue;
			for (int j = 0; j < vertex; j++) {
				if (graph[i][j]) adjacent[j] = true;
			}
		}

		for (int i = 0; i < vertex; i++) {
			if (getBitFromInt(n, i))
				if (adjacent[i]) return false;
		}

		return true;
	}

	private boolean isDependent(int n) {
		for (int i = 0; i < vertex; i++) {
			if (!getBitFromInt(n, i)) continue;
			for (int j = i + 1; j < vertex; j++) {
				if (!getBitFromInt(n, j)) continue;
				if (!graph[i][j]) return false;
			}
		}
		return true;
	}

	public void printIndependentSet() {
		System.out.println("THIS IS INDEPENDENT SET : ");
		for (Integer set : independentSet) convertSetIntToString(set);
		System.out.println();
	}


	public void printDependentSet() {
		System.out.println("THIS IS DEPENDENT SET : ");
		for (Integer set : dependentSet) convertSetIntToString(set);
		System.out.println();
	}

	private void convertSetIntToString(int set) {
		StringBuilder str = new StringBuilder("{");
		for (int i = 0; i < vertex; i++) {
			if (getBitFromInt(set, i)) str.append(i).append(", ");
		}
		str.setLength(str.length() - 2);
		str.append("} ");
		System.out.print(str);
	}


	public int getDominantNumber() {
		return dominantSet.size();
	}

	public int getIndependentNumber() {
		return independentSet.size();
	}

	public int getDependentNumber() {
		return dependentSet.size();
	}
}
