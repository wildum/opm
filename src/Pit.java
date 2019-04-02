import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Pit {

	private AdjacencyNetwork<Integer, Integer> network;
	private final Integer source = 0;
	private final Integer sink = 1;
	private int size = 0;
	private int width = 0;

	public void buildGraph(List<String> lines) {
		this.network = new AdjacencyNetwork<Integer, Integer>();
		this.network.addVertex(source);
		this.network.addVertex(sink);
		Integer currentVertex = 2;
		List<Integer> previousBlocks = new ArrayList<>();
		for (String l : lines) {
			Integer[] values = Stream.of(l.split("\\s+")).map(Integer::valueOf).toArray(Integer[]::new);
			this.width = values.length;
			List<Integer> currentBlocks = new ArrayList<>();
			for (int i = 0; i < width; i++) {
				this.network.addVertex(currentVertex);
				currentBlocks.add(currentVertex);
				if (!previousBlocks.isEmpty()) {
					if (i > 0)
						this.network.addEdge(currentVertex, previousBlocks.get(i - 1), Main.MAX_VALUE);
					this.network.addEdge(currentVertex, previousBlocks.get(i), Main.MAX_VALUE);
					if (i < previousBlocks.size() - 1)
						this.network.addEdge(currentVertex, previousBlocks.get(i + 1), Main.MAX_VALUE);
				}
				if (values[i] > 0) {
					this.network.addEdge(source, currentVertex, values[i]);
				} else if (values[i] < 0) {
					this.network.addEdge(currentVertex, sink, -values[i]);
				}
				currentVertex++;
			}
			previousBlocks = currentBlocks;
		}
		this.size = currentVertex;
	}

	public AdjacencyNetwork<Integer, Integer> getNetwork() {
		return this.network;
	}

	public void setNetwork(AdjacencyNetwork<Integer, Integer> network) {
		this.network = network;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Integer getSource() {
		return source;
	}

	public Integer getSink() {
		return sink;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
}
