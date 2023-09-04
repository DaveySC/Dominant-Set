import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class FDS {
	enum TypeOfGraph {DOMINANT, DEPENDENT, INDEPENDENT};

	private final boolean[][] graph;

	private final int vertex;

	private final Set<Integer> independentSet = new TreeSet<>();

	private final Set<Integer> dependentSet   = new TreeSet<>();

	private final Set<Integer> dominantSet    = new TreeSet<>();
	public FDS(boolean[][] graph) {
		this.graph = graph;
		this.vertex = graph.length;
		findDominantSets();
	}


	private void findDominantSets() {
		for (int i = 0; i < 1 << vertex; i++) {
			if (isDominant(i)) {
				dominantSet.add(i);
				if (isIndependent(i)) independentSet.add(i);
				if (isDependent(i)) dependentSet.add(i);
			}
		}

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
		return ((b << ~position) < 0);
	}

	private boolean isIndependent(int n) {
		for (int i = 0; i < vertex; i++) {
			if (!getBitFromInt(n, i)) continue;
			for (int j = i + 1; j < vertex; j++) {
				if (!getBitFromInt(n, j)) continue;
				if (graph[i][j] || graph[j][i]) return false;
			}
		}
		return true;
	}

	private boolean isDependent(int n) {
		boolean[] visited = new boolean[vertex];
		for (int i = 0; i < vertex; i++) {
			if (!getBitFromInt(n, i)) continue;
			for (int j = 0; j < vertex; j++) {
				if (!getBitFromInt(n, j) || i == j) continue;
				if (graph[i][j]) visited[j] = true;
			}
		}
		for (int i = 0; i < vertex; i++) {
			if (!getBitFromInt(n, i)) continue;
			if (!visited[i]) return false;
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
		int min = Integer.MAX_VALUE;
		for (int val : dominantSet) min = Math.min(min, Integer.bitCount(val));
		return (min == Integer.MAX_VALUE) ? 0 : min;
	}


	public int getIndependentNumber() {
		int min = Integer.MAX_VALUE;
		for (int val : independentSet) min = Math.min(min, Integer.bitCount(val));
		return (min == Integer.MAX_VALUE) ? 0 : min;
	}

	public int getDependentNumber() {
		int min = Integer.MAX_VALUE;
		for (int val : dependentSet) min = Math.min(min, Integer.bitCount(val));
		return (min == Integer.MAX_VALUE) ? 0 : min;
	}
}
