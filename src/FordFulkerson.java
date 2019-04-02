import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.stream.*;

public class FordFulkerson {
	
	private Pit pit;
	
	public FordFulkerson(Pit pit) {
		this.pit = pit;
	}
	
	private boolean[] bfs(int parent[]) {
		boolean visited[] = new boolean[pit.getSize()];
		for(int i = 0; i < pit.getSize(); i++)
			visited[i] = false;
		LinkedList<Integer> queue = new LinkedList<Integer>(); 
		queue.add(pit.getSource()); 
		visited[pit.getSource()] = true; 
		parent[pit.getSource()]=-1;
		while(!queue.isEmpty()) {
			int u = queue.poll();
			for (int v = 0; v < pit.getSize(); v++) {
				if (!visited[v]
						&& this.pit.getNetwork().isLinked(u, v)
						&& this.pit.getNetwork().getEdge(u, v) > 0) {
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
		while (bfs(parent)[pit.getSink()]) {
			int pathFlow = Main.MAX_VALUE;
			for (v = t; v != s; v = parent[v]) {
				u = parent[v];
				pathFlow = Math.min(pathFlow, this.pit.getNetwork().getEdge(u, v));
			}
			for (v = t; v != s; v = parent[v]) {
				u = parent[v];
				int currentuv = this.pit.getNetwork().getEdge(u, v);
				int currentvu = this.pit.getNetwork().getEdge(v, u);
				this.pit.getNetwork().updateEdge(u, v, currentuv-pathFlow);
				this.pit.getNetwork().updateEdge(v, u, currentvu+pathFlow);
			}
		}
		return computeScore();
	}
	
	private int computeScore() {
		int score = 0;
		for (Map.Entry<Integer, Integer> entry : this.pit.getNetwork().getResidualGraph().get(this.pit.getSource()).entrySet()) {
			score += entry.getValue();
		}
		return score;
	}
	
	public Set<Integer> getPath() {
		Set<Integer> path = new HashSet<>();
		for (Map.Entry<Integer, Integer> entry : this.pit.getNetwork().getResidualGraph().get(this.pit.getSource()).entrySet()) {
			if (entry.getValue() > 0) {
				int parent[] = new int[this.pit.getSize()];
				boolean[] visited = bfs(parent);
				for (int i = 2; i < visited.length; i++) {
					if (visited[i]) {
						path.add(i);
					}
				}
			}
		}
		return path;
	}


}
