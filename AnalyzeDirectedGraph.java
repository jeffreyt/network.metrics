import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Collection;
import java.util.HashSet;
import java.util.Comparator;
import java.util.PriorityQueue;

public class AnalyzeDirectedGraph{
	public static void main(String[] args) throws IOException{
		if(args.length < 1){
			System.out.println("This takes an argument");
			return;
		}
		Scanner input = null;
		String token;
		DirectedGraph diagraph = new DirectedGraph();
		
		Object key;
		Object data;
		Object from;
		Object to;
		
		Vertex v;
		
// Importing file		
		try{
			input = new Scanner(new File(args[0]));
		}
		catch(Exception e){
			System.out.println(e);
			return;
		}
		input.nextLine();
		input.nextLine();
		
		while(input.hasNextLine()){
			key = input.next();
			if(((String)key).charAt(0) == '*'){
				input.nextLine();
				input.nextLine();
				break;
			}
			data = input.nextLine();
			diagraph.insertVertex(key, data);
		}
		while(input.hasNextLine()){
			from = input.next();
			to = input.next();
			data = input.nextLine();
			diagraph.insertArc(diagraph.getVertex(from), diagraph.getVertex(to), data);
		}	
		
		input.close();
		
// Setup Graph Output		
		int V = diagraph.numVertices();
		int A = diagraph.numArcs();

		System.out.println("----------------------------------------------------");
		System.out.println("Graph " + args[0]);
		System.out.println("----------------------------------------------------");
		
		//Basic Graph Data
		System.out.println("|V| = " + V);
		System.out.println("|A| = " + A);
		System.out.printf("Density = %.8f",(A / (V * (V - 1.0))));
		System.out.println(degreeDistribution(diagraph));
		
		//Strongly Connected Components
		ArrayList<HashSet<Vertex>> sccs = stronglyConnectedComponents(diagraph);
		System.out.println("\nNumber of Strongly Connected Components: " + sccs.size());  
		System.out.printf("Percent Vertices in Largest SCC: %.2f\n",(1.0 * sccs.get(0).size() / V) * 100);
		
		//Network Metrics
		System.out.printf("\nReciprocity: %f\n", 1.0 * reciprocity(diagraph) / A);
		System.out.printf("Degree Correlation: %f\n", degreeCorrelation(diagraph));
		System.out.printf("Clustering Coefficient: %f\n", clusteringCoefficient(diagraph));
		geodesicDistance(diagraph);
	
	} //end of main method
	
	
    /**
     * Degree Distribution
     * @param dg 
     * @return String
     */
	private static String degreeDistribution(DirectedGraph dg){
		int total = dg.numVertices();
		int minIn = total; //upper bound
		int maxIn = 0;
		double avgIn = 0.0;
		int totalIn = 0;
		int minOut = total; //upper bound
		int maxOut = 0;
		double avgOut = 0.0;
		int totalOut = 0;
		int n;
		
		Iterator verts = dg.vertices();
		Vertex v;
		
		while(verts.hasNext()){
			v = (Vertex)verts.next();
			
			n = dg.inDegree(v);
			minIn = n < minIn ? n : minIn;			
			maxIn = n > maxIn ? n : maxIn;
			totalIn = totalIn + n;
			
			n = dg.outDegree(v);
			minOut = n < minOut ? n : minOut;			
			maxOut = n > maxOut ? n : maxOut;
			totalOut = totalOut + n;		
		}
		avgIn = totalIn / (total * 1.0);
		avgOut = totalOut / (total * 1.0);

		String output = String.format("\n%-20s %-10s %-10s %-10s","Degree Distribution:","minimum", "average", "maximum");
		
		output += String.format("\n%-20s %-10d %-10.2f %-10d","inDegree",minIn, avgIn, maxIn);
		
		output += String.format("\n%-20s %-10d %-10.2f %-10d","outDegree",minOut, avgOut, maxOut);
		
		return output;
	}
    /**
     * Strongly Connected Components
     * @param dg DirectedGraph
     * @return ArrayList<HashSet<Vertex>>
     */
	private static ArrayList<HashSet<Vertex>> stronglyConnectedComponents(DirectedGraph dg){
// First DFS pass
		ArrayList<HashSet<Vertex>> sccs = new ArrayList<HashSet<Vertex>>();
		ArrayList<DFSMarker> markers = new ArrayList<DFSMarker>();
		DFSMarker mark;
		
		for(Vertex vert : dg.iterableVertices()){
			mark = new DFSMarker(); //sets color to white and parent to null
			mark.vertex = vert;
			markers.add(mark);
			vert.setAnnotation(mark);
		}
		
		DFSMarker.time = 0;
		
		for(DFSMarker u : markers){
			if(u.color.equals("WHITE")){
				dfsVisit(dg, null, u, false);
			}
		}
		
		Collections.sort(markers); //Sorts vertices in non-decreasing order by finish time

//Second DFS Pass on transpose	
		for(DFSMarker m : markers){
			m.color = "WHITE";
			m.parent = null;
		}
		for(DFSMarker m : markers){
			if(m.color.equals("WHITE")){
				sccs.add(0, new HashSet<Vertex>());
				dfsVisit(dg, sccs.get(0), m, true);
			}
		}

//Sorts SCCs in order of most to least		
		Collections.sort(sccs, new Comparator<HashSet<Vertex>>(){
			public int compare(HashSet<Vertex> hs1, HashSet<Vertex> hs2) {
				return hs2.size() - hs1.size();
			}
		});
			
		return sccs;
	}
    /**
     * DFS Visit
     * @param dg 
	 * @param scc
     * @param u 
     * @param transpose 
     */
	private static void dfsVisit(DirectedGraph dg, HashSet<Vertex> scc, DFSMarker u, boolean transpose){
		DFSMarker v;
		
		DFSMarker.time = DFSMarker.time + 1;
		u.discovery = DFSMarker.time;
		u.color = "GRAY";
		Vertex tempVertex;
		
		Collection<Vertex> adjacencies = transpose ? dg.iterableAdjVertsIn(u.vertex) : dg.iterableAdjVertsOut(u.vertex);
		
		for(Vertex vert : adjacencies){
			v = (DFSMarker)vert.getAnnotation();
			if(v.color.equals("WHITE")){
				v.parent = u.vertex;
				dfsVisit(dg, scc, v, transpose);
			}
		}
		u.color = "BLACK";
		DFSMarker.time = DFSMarker.time + 1;
		u.finish = DFSMarker.time;
		if(transpose){
			scc.add(u.vertex);
		}
	}
    /**
     * Calculates total number of edges (i,j) for which (j,i) exists
     * @param dg DirectedGraph to use
     * @return int number of instances (used in calculation)
     */
	private static int reciprocity(DirectedGraph dg){
		int count = 0;
		Vertex o;
		Vertex d;
	
		for(Arc a : dg.iterableArcs()){
			o = a.getOrigin();
			d = a.getDestination();
			if(d.adjVertsOut().contains(o)){
				count++;
			}
		}
		return count;
	}
    /**
     * Calculates degree correlation for a given graph
     * @param dg Directed Graph to use
     * @return double for degree correlation
     */
	private static double degreeCorrelation(DirectedGraph dg){
		double s1 = 0;
		double s2 = 0;
		double s3 = 0;
		double sE = 0;
		int i, j;
		
		for(Vertex v : dg.iterableVertices()){
			i = dg.allDegrees(v);
			
			s1 += i;
			s2 += i * i;
			s3 += i * i * i;
		}
		
		for(Arc a : dg.iterableArcs()){
			i = dg.allDegrees(a.getOrigin());
			j = dg.allDegrees(a.getDestination());

			sE += (2 * i * j);
		}
				
		return ((s1*sE) - (s2*s2))/((s1*s3) - (s2*s2)); //Newman (8.27)
	}
    /**
     * 
     * 
     * @param dg Directed Graph
     * @return double
     */
	private static double clusteringCoefficient(DirectedGraph dg){
		
		int path1;
		int path2;
		int path3;
		int cluster1;
		int cluster2;
		int cluster3;
		int totalPaths = 0;
		int totalClusters = 0;
		
		for(Vertex v : dg.iterableVertices()){
		path1 = 0;
		path2 = 0;
		path3 = 0;
		cluster1 = 0;
		cluster2 = 0;
		cluster3 = 0;
		
		//case 1: out vs out, with duplicity*
			for(Arc a1 : dg.iterableAdjArcsOut(v)){
				for(Arc a2 : dg.iterableAdjArcsOut(v)){
					if(a1 != a2){
						path1++;
						if(dg.isArc(a1.getDestination(),a2.getDestination())){
							cluster1++;
						}
					}
				}
			}
			path1 = path1 / 2;
			cluster1 = cluster1 / 2;
		//case 2: in vs in, with duplicity*
			for(Arc a1 : dg.iterableAdjArcsIn(v)){
				for(Arc a2 : dg.iterableAdjArcsIn(v)){
					if(a1 != a2){
						path2++;
						if(dg.isArc(a1.getOrigin(),a2.getOrigin())){
							cluster2++;
						}
					}
				}
			}
			path2 = path2 / 2;
			cluster2 = cluster2 / 2;
		//case 3: in vs out, check for parallels	
			for(Arc a1 : dg.iterableAdjArcsOut(v)){
				for(Arc a2 : dg.iterableAdjArcsIn(v)){
					if(a1.getDestination() != a2.getOrigin()){
						path3++;
						if(dg.isArc(a1.getDestination(),a2.getOrigin())){
							cluster3++;
						}						
					}
				}
			}
			totalPaths += path1 + path2 + path3;
			totalClusters += cluster1 + cluster2 + cluster3;
		} //end outermost loop
		
		return 1.0 * totalClusters / totalPaths;
	}
    /**
     * 
     * 
     * @param dg Directed Graph ADT
     * @return String
     */
	public static String geodesicDistance(DirectedGraph dg){
		
		PriorityQueue<DistanceMarker> pQueue;
		HashSet<DistanceMarker> markers;
		DistanceMarker mark, u, v;
		double maxDistance = 0;
		double totalDistance = 0;
		double totalPaths = 0;
		int i = 0;
		
		
		for(Vertex s : dg.iterableVertices()){
			i++;
			markers = new HashSet<DistanceMarker>();
			pQueue = new PriorityQueue<DistanceMarker>();
			for(Vertex vertex : dg.iterableVertices()){
				mark = new DistanceMarker(vertex);
				vertex.setAnnotation(mark);
				pQueue.add(mark);
			}
			//System.out.println("Loop " + i + " initialized");
			((DistanceMarker)s.getAnnotation()).distance = new Double(0);
			while(pQueue.size() > 0){
				u = pQueue.poll();
				markers.add(u);
				for(Vertex vertex : dg.iterableAdjVertsAll(u.vertex)){
					v = (DistanceMarker)vertex.getAnnotation();
					//relax
					if(v.distance > u.distance + 1){
						v.distance = u.distance + 1;
						pQueue.remove(v);
						pQueue.add(v);
					}					
				}
				//System.out.println("Loop " + i + " relaxing");
			}
			//System.out.println("Loop " + i + " Queue empty");			
			for(DistanceMarker dm : markers){
				if(dm.distance != Double.POSITIVE_INFINITY && dm.distance != 0){
					totalPaths++;
					totalDistance += dm.distance;
					if(dm.distance > maxDistance){
						maxDistance = dm.distance;
					}
				}
			}
			//System.out.println("Loop " + i + " metrics counted");
			
		}
		
		System.out.printf("Mean Geodesic Distance: %.3f\n", (totalDistance / totalPaths));
		System.out.printf("Diameter: %.0f\n", maxDistance);
		return "";
	}
}
	