import java.util.HashMap;
import java.util.Map;

public class AdjacencyNetwork<Vertex, Edge> {

	private Map<Vertex, Map<Vertex, Edge>> graph = new HashMap<>();

	public void addVertex(Vertex u) {
		graph.put(u, new HashMap<>());
	}

	public void addEdge(Vertex u, Vertex v, Edge e) {
		graph.get(u).put(v, e);
	}
	
	public boolean isLinked(Integer u, Integer v) {
		return this.graph.get(u).containsKey(v);
	}

	public Map<Vertex, Map<Vertex, Edge>> getGraph() {
		return graph;
	}

	public void setGraph(Map<Vertex, Map<Vertex, Edge>> graph) {
		this.graph = graph;
	}

}
