import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class FordFulkerson {

	private Pit pit;
	private Set<Integer> path = new HashSet<>();
	private Map<Integer, Map<Integer, Integer>> residualGraph = new HashMap<>();

	public FordFulkerson(Pit pit) {
		this.pit = pit;
	}

	private boolean[] bfs(int parent[]) {
		boolean visited[] = new boolean[pit.getSize()];
		for (int i = 0; i < pit.getSize(); i++)
			visited[i] = false;
		LinkedList<Integer> queue = new LinkedList<Integer>();
		queue.add(pit.getSource());
		visited[pit.getSource()] = true;
		parent[pit.getSource()] = -1;
		while (!queue.isEmpty()) {
			int u = queue.poll();
			for (int v = 0; v < pit.getSize(); v++) {
				if (!visited[v] && this.isLinked(u, v) && this.getEdge(u, v) > 0) {
					queue.add(v);
					parent[v] = u;
					visited[v] = true;
				}
			}
		}
		return visited;
	}

	public int compute() {
		int u, v;
		int parent[] = new int[this.pit.getSize()];
		int s = this.pit.getSource();
		int t = this.pit.getSink();
		buildResidualGraphFromGraph();
		while (bfs(parent)[pit.getSink()]) {
			int pathFlow = Main.MAX_VALUE;
			for (v = t; v != s; v = parent[v]) {
				u = parent[v];
				pathFlow = Math.min(pathFlow, this.getEdge(u, v));
			}
			for (v = t; v != s; v = parent[v]) {
				u = parent[v];
				int currentuv = this.getEdge(u, v);
				int currentvu = this.getEdge(v, u);
				this.updateEdge(u, v, currentuv - pathFlow);
				this.updateEdge(v, u, currentvu + pathFlow);
			}
		}
		return computeResult(residualGraph);
	}

	private int computeResult(Map<Integer, Map<Integer, Integer>> residualGraph) {
		int score = 0;
		path = new HashSet<>();
		for (Map.Entry<Integer, Integer> entry : residualGraph.get(this.pit.getSource()).entrySet()) {
			score += entry.getValue();
			int parent[] = new int[this.pit.getSize()];
			boolean[] visited = bfs(parent);
			for (int i = 2; i < visited.length; i++) {
				if (visited[i]) {
					path.add(i);
				}
			}
		}
		return score;
	}

	private Map<Integer, Map<Integer, Integer>> buildResidualGraphFromGraph() {
		for (int i = 0; i < this.pit.getSize(); i++) {
			this.residualGraph.put(i, new HashMap<>());
		}
		for (int i = 0; i < this.pit.getSize(); i++) {
			Map<Integer, Integer> entryGraph = this.pit.getNetwork().getGraph().get(i);
			for (Map.Entry<Integer, Integer> entry : entryGraph.entrySet()) {
				this.residualGraph.get(i).put(entry.getKey(), entry.getValue());
				this.residualGraph.get(entry.getKey()).put(i, entry.getValue());
			}
		}
		return residualGraph;
	}
	
	public boolean isLinked(Integer u, Integer v) {
		return this.pit.getNetwork().isLinked(u, v);
	}

	public Integer getEdge(Integer u, Integer v) {
		return this.residualGraph.get(u).get(v);
	}

	public void updateEdge(Integer u, Integer v, Integer e) {
		this.residualGraph.get(u).put(v, e);
	}

	public Set<Integer> getPath() {
		return path;
	}

	public void setPath(Set<Integer> path) {
		this.path = path;
	}
	
	public void printDiggingPath() {
		System.err.println("Digging profile : ");
		for (Integer i : this.path) {
			System.err.println((i-2)%this.pit.getWidth() + " " + (i-2)/this.pit.getWidth());
		}
	}

}
