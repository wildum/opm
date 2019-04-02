import java.util.HashMap;
import java.util.Map;

public class AdjacencyNetwork<Vertex, Edge> {
	
	private Map<Vertex, Map<Vertex, Edge>> graph = new HashMap<>();
	private Map<Vertex, Map<Vertex, Edge>> residualGraph = new HashMap<>();
	
	public void addVertex(Vertex u) {
		graph.put(u, new HashMap<>());
		if (!residualGraph.containsKey(u))
			residualGraph.put(u, new HashMap<>());
	}
	
	public void addEdge(Vertex u, Vertex v, Edge e, Edge residualInitValue) {
		graph.get(u).put(v, e);
		residualGraph.get(u).put(v, residualInitValue);
		if (!residualGraph.containsKey(v))
			residualGraph.put(v, new HashMap<>());
		residualGraph.get(v).put(u, residualInitValue);
	}
	
	public boolean isLinked(Vertex u, Vertex v) {
		return this.graph.get(u).containsKey(v);
	}
	
	public Edge getEdge(Vertex u, Vertex v) {
		return this.residualGraph.get(u).get(v);
	}
	
	public void updateEdge(Vertex u, Vertex v, Edge e) {
		this.residualGraph.get(u).put(v, e);
	}

	public Map<Vertex, Map<Vertex, Edge>> getGraph() {
		return graph;
	}

	public void setGraph(Map<Vertex, Map<Vertex, Edge>> graph) {
		this.graph = graph;
	}

	public Map<Vertex, Map<Vertex, Edge>> getResidualGraph() {
		return residualGraph;
	}

	public void setResidualGraph(Map<Vertex, Map<Vertex, Edge>> residualGraph) {
		this.residualGraph = residualGraph;
	}

}
