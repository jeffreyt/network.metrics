public class DistanceMarker implements Comparable<DistanceMarker>{
	
	public Vertex vertex;
	public Double distance;
	public boolean wasSource;
	
	public DistanceMarker(Vertex vertex){
		this.distance = Double.POSITIVE_INFINITY;
		this.vertex = vertex;
		this.wasSource = false;
	}
	
	public int compareTo(DistanceMarker that){
		return this.distance.compareTo(that.distance);
	}
	
	public String toString(){
		return "\n" + vertex + " : distance - " + distance;
	}
}	