import java.util.*;

public class FDS {
	enum TypeOfGraph {DOMINANT, DEPENDENT, INDEPENDENT};

	private final boolean[][] graph;

	private final int vertex;

	private int dominantNumber = Integer.MAX_VALUE, independentNumber = Integer.MAX_VALUE, dependentNumber = Integer.MAX_VALUE;

	public FDS(boolean[][] graph) {
		this.graph = graph;
		this.vertex = graph.length;
		findDominantSets();
	}


	private void findDominantSets() {
		for (int i = 0; i < 1 << vertex; i++) {
			if (isDominant(i)) {
				int count = Integer.bitCount(i);
				if (count == 0) continue;
				if (count < dominantNumber) dominantNumber = count;
				if (count < independentNumber)
					if (isIndependent(i)) independentNumber = count;

				if (count < dependentNumber)
					if (isDependent(i)) dependentNumber = count;
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
		List<Integer> vertexes = new ArrayList<>();
		for (int i = 0; i < vertex; i++) if (getBitFromInt(n, i)) vertexes.add(i);
		boolean[] visited = new boolean[vertex];
		Queue<Integer> queue = new ArrayDeque<>();
		queue.add(vertexes.get(0));
		while (queue.size() > 0) {
			int currentVertex = queue.poll();
			visited[currentVertex] = true;
			for (int vertexToCheck : vertexes) {
				if (!visited[vertexToCheck] && graph[vertexToCheck][currentVertex]) queue.add(vertexToCheck);
			}
		}
		for (int v : vertexes) if (!visited[v]) return false;
		return true;
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
		return dominantNumber == Integer.MAX_VALUE ? 0 : dominantNumber;
	}


	public int getIndependentNumber() {
		return independentNumber == Integer.MAX_VALUE ? 0 : independentNumber;
	}

	public int getDependentNumber() {
		return dependentNumber == Integer.MAX_VALUE ? 0 : dependentNumber;
	}
}
