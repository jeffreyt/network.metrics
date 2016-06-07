import java.util.Iterator;

public class TestDirectedGraph{
	
	public static void main(String args[]){
		
		Vertex vtx;
		Arc arc;
		Iterator iter;
		int i;
		
		System.out.println("\nTest 0 ********************* Empty Graph");
		DirectedGraph graph = new DirectedGraph();
		System.out.println(graph);
		
		System.out.println("\nTest 1 ********************* Adding A-F vertices");
		Vertex a = graph.insertVertex("A", "Apple");
		Vertex b = graph.insertVertex("B", "Bee");
		Vertex c = graph.insertVertex("C", "Clown");
		Vertex d = graph.insertVertex("D", "Deepak Chopra");
		Vertex e = graph.insertVertex("E", "Escobar");
		Vertex f = graph.insertVertex("F","Fruit Salad");
		System.out.println(graph);

		System.out.println("\nTest 2 ********************* Creating Arcs");		
		Arc ab = graph.insertArc(a, b, "Abba");
		Arc bc = graph.insertArc(b, c, "Broccoli");
		Arc cd = graph.insertArc(c, d, "Cadmium");
		Arc de = graph.insertArc(d, e,"Delight");
		Arc ef = graph.insertArc(e, f, "Efficacy");
		Arc fa = graph.insertArc(f, a, "Fart");
		System.out.println(graph);

		System.out.println("\nTest 3 ********************* Counting Degrees, should both be 6");		
		System.out.println("Number of Vertices: " + graph.numVertices());
		System.out.println("Number of Arcs: " + graph.numArcs());

		System.out.println("\nTest 4 ********************* Getters");		
		vtx = graph.getVertex("A");
		arc = graph.getArc("A","B");
		System.out.println("Get Vertex: " + vtx);
		System.out.println("Get Arc: " + arc);
		System.out.println("Get Vertex Data: " + graph.getVertexData(vtx));
		System.out.println("Get Arc Data: " + graph.getArcData(arc));	
		System.out.println("Arc origin: " + graph.origin(arc));
		System.out.println("Arc destination: " + graph.destination(arc));
		
		vtx = graph.getVertex("Z");
		arc = graph.getArc("B","A");
		System.out.println("Get non-existent Vertex: " + vtx);
		System.out.println("Get non-existent Arc: " + arc);
		//System.out.println("Get non-existent Vertex Data: " + graph.getVertexData(vtx));
		//System.out.println("Get non-existent Arc Data: " + graph.getArcData(arc));	
		//System.out.println("Arc non-existent origin: " + graph.origin(arc));
		//System.out.println("Arc non-existent destination: " + graph.destination(arc));
		
		System.out.println("\nTest 5 ********************* Setters");		
		graph.setVertexData(a, "Not an Apple");
		graph.setArcData(ab, "Abba Sucks");
		System.out.println("Get Vertex Data: " + graph.getVertexData(a));
		System.out.println("Get Arc Data: " + graph.getArcData(ab));
		
		System.out.println("\nTest 6 ********************* Adding G vertex arcing OUT to all other vertices");		
		Vertex g = graph.insertVertex("G", "Gameraa");
		graph.insertArc(g,a);
		graph.insertArc(g,b);
		graph.insertArc(g,c);
		graph.insertArc(g,d);
		graph.insertArc(g,e);
		graph.insertArc(g,f);
		System.out.println("G degrees in: " + graph.inDegree(g));
		System.out.println("G degrees out: " + graph.outDegree(g));
		
		System.out.println("\nTest 7 ********************* Adding H vertex arcing IN to all other vertices");		
		Vertex h = graph.insertVertex("H", "Hoopla");
		graph.insertArc(a,h);
		graph.insertArc(b,h);
		graph.insertArc(c,h);
		graph.insertArc(d,h);
		graph.insertArc(e,h);
		graph.insertArc(f,h);
		System.out.println("H degrees in: " + graph.inDegree(h));
		System.out.println("H degrees out: " + graph.outDegree(h));
		System.out.println(graph);
		
		System.out.println("\nTest 8 ********************* Test iterators");		
		System.out.println("ALL");
		i = 0;
		iter = graph.vertices();
		while(iter.hasNext()){
			System.out.println("Iteration " + i++ + iter.next());
		}		
		System.out.println("G-IN");
		i = 0;
		iter = graph.inAdjacentVertices(g);
		while(iter.hasNext()){
			System.out.println("Iteration " + i++ + iter.next());
		}
		System.out.println("G-OUT");
		i = 0;
		iter = graph.outAdjacentVertices(g);
		while(iter.hasNext()){
			System.out.println("Iteration " + i++ + iter.next());
		}		
		System.out.println("H-IN");
		i = 0;
		iter = graph.inAdjacentVertices(h);
		while(iter.hasNext()){
			System.out.println("Iteration " + i++ + iter.next());
		}
		System.out.println("H-OUT");		
		iter = graph.outAdjacentVertices(h);
		i = 0;
		while(iter.hasNext()){
			System.out.println("Iteration " + i++ + iter.next());
		}
		
		System.out.println("\nTest 9 ********************* Arc Removal F-A");		
		
		System.out.println("Before");
		System.out.println("Trying getter:" + graph.getArc("F","A"));
		System.out.println("A degrees in: " + graph.inDegree(a));
		System.out.println("A degrees out: " + graph.outDegree(a));
		System.out.println("F degrees in: " + graph.inDegree(f));
		System.out.println("F degrees out: " + graph.outDegree(f));			
		System.out.println("Graph Vertices: " + graph.numVertices());
		System.out.println("Graph Arcs: " + graph.numArcs());
		
		System.out.println("Removing: " + graph.removeArc(fa));
		
		System.out.println("After");	
		System.out.println("Trying getter:" + graph.getArc("F","A"));
		System.out.println("A degrees in: " + graph.inDegree(a));
		System.out.println("A degrees out: " + graph.outDegree(a));
		System.out.println("F degrees in: " + graph.inDegree(f));
		System.out.println("F degrees out: " + graph.outDegree(f));			
		System.out.println("Graph Vertices: " + graph.numVertices());
		System.out.println("Graph Arcs: " + graph.numArcs());
		
		System.out.println("\nTest 10 ********************* Vertex Removal H");		
		System.out.println("Before");
		System.out.println("Trying getter:" + graph.getVertex("H"));
		System.out.println("Graph Vertices: " + graph.numVertices());
		System.out.println("Graph Arcs: " + graph.numArcs());
		
		System.out.println("Removing:" + graph.removeVertex(h));
		
		System.out.println("After");
		System.out.println("Trying getter:" + graph.getVertex("H"));
		System.out.println("Graph Vertices: " + graph.numVertices());
		System.out.println("Graph Arcs: " + graph.numArcs());
		
		System.out.println("\nTest 11 ********************* Arc reversal");				
		System.out.println("Before");
		System.out.println("Trying getter G->A: " + graph.getArc("G","A"));		
		System.out.println("Trying getter A->G: " + graph.getArc("A","G"));
		System.out.println("G degrees in: " + graph.inDegree(g));
		System.out.println("G degrees out: " + graph.outDegree(g));
		System.out.println("A degrees in: " + graph.inDegree(a));
		System.out.println("A degrees out: " + graph.outDegree(a));	
		
		graph.reverseDirection(graph.getArc("G","A"));
		
		System.out.println("After");
		System.out.println("Trying getter G->A: " + graph.getArc("G","A"));		
		System.out.println("Trying getter A->G: " + graph.getArc("A","G"));
		System.out.println("G degrees in: " + graph.inDegree(g));
		System.out.println("G degrees out: " + graph.outDegree(g));
		System.out.println("A degrees in: " + graph.inDegree(a));
		System.out.println("A degrees out: " + graph.outDegree(a));
		
		System.out.println("\nTest 12 ********************* Iterable Tests");				
		System.out.println("All Vertices");		
		for(Vertex vI : graph.iterableVertices()){
			System.out.println(vI);
		}
		System.out.println("All Arcs");
		for(Arc aI : graph.iterableArcs()){
			System.out.println(aI);
		}
	}
}