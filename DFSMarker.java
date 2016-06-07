public class DFSMarker implements Comparable<DFSMarker>{
	
	public static int time;
	public String color;
	public Vertex vertex;
	public Vertex parent;
	public int discovery;
	public int finish;
	
	
	
	public DFSMarker(){
		this.color = "WHITE";
		this.parent = null;
	}
	
	public int compareTo(DFSMarker that){
		return (that.finish - this.finish);
	}
	
	public String toString(){
		return vertex + color + ":" + discovery + " / " + finish;
	}
}	