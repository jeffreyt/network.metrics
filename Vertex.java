import java.util.HashSet;
import java.util.Iterator;

public class Vertex{
	
	private Object key;
	private Object data;
	private Object annotation;
	private HashSet<Arc> arcsIn;
	private HashSet<Arc> arcsOut;
	/**
	* Constructor method
	*/
	public Vertex(Object key, Object data){
		this.key = key;
		this.data = data;
		this.annotation = null;
		this.arcsIn = new HashSet<Arc>();
		this.arcsOut = new HashSet<Arc>();
	}
	/**
	* Accessor method for Vertex key
	* @return key Object
	*/
	public Object getKey(){
		return key;
	}
	/**
	* Accessor method for Vertex data
	* @return data Object
	*/	
	public Object getData(){
		return data;
	}
	/**
	* Accessor method for Annotation data
	* @return annotation Object
	*/	
	public Object getAnnotation(){
		return annotation;
	}
	/**
	*
	*/	
	public boolean addArcIn(Arc a){
		return arcsIn.add(a);
	}
	/**
	*
	*/	
	public boolean addArcOut(Arc a){
		return arcsOut.add(a);
	}	
	/**
	*
	*/
	public HashSet<Arc> getArcs(){
		HashSet<Arc> output = new HashSet<Arc>();
		output.addAll(arcsIn);
		output.addAll(arcsOut);
		return output;
	}
	/**
	*
	*/	
	public HashSet<Arc> getArcsIn(){
		return arcsIn;
	}
	/**
	*
	*/	
	public HashSet<Arc> getArcsOut(){
		return arcsOut;
	}
	/**
	*
	*/		
	public HashSet<Vertex> adjVertsIn(){
		HashSet<Vertex> result = new HashSet<Vertex>();
		
		for(Arc a : arcsIn){
			result.add(a.getOrigin());
		}
		return result;
	}
	/**
	*
	*/	
	public HashSet<Vertex> adjVertsOut(){
		HashSet<Vertex> result = new HashSet<Vertex>();
		
		for(Arc a : arcsOut){
			result.add(a.getDestination());
		}
		return result;
	}
	
	public HashSet<Vertex> adjVerts(){
		HashSet<Vertex> output = new HashSet<Vertex>();
		output.addAll(adjVertsIn());
		output.addAll(adjVertsOut());
		return output;
	}
	/**
	*
	*/
	public void setKey(Object key){
		this.key = key;
	}
	/**
	*
	*/	
	public void setData(Object data){
		this.data = data;
	}
	/**
	*
	*/	
	public void setAnnotation(Object annotation){
		this.annotation = annotation;
	}
	/**
	*
	*/	
	public void drawArcOut(Vertex v){
		Arc a = new Arc(this, v);
		arcsOut.add(a);
	}
	/**
	*
	*/	
	public void drawArcIn(Vertex v){
		Arc a = new Arc(v, this);
		arcsIn.add(a);
	}
	/**
	*
	*/	
	public void removeIn(Arc a){
		arcsIn.remove(a);
	}
	/**
	*
	*/
	public void removeOut(Arc a){
		arcsOut.remove(a);
	}
	/**
	*
	*/
	public void removeArcs(){
		for(Arc a : arcsOut){
			a.getDestination().removeIn(a);
		}
		for(Arc a : arcsIn){
			a.getOrigin().removeOut(a);
		}
	}
	/**
	*
	*/	
	public String toString(){
		return "(" + key + ", " + data + ")";
	}
}