
public class Arc{
	
	private Object data;
	private Object annotation;
	protected Vertex origin;
	private Vertex destination;
	/**
	*
	*/	
	public Arc(Vertex origin, Vertex destination, Object data){
		this.origin = origin;
		this.destination = destination;
		origin.addArcOut(this);
		destination.addArcIn(this);
		this.data = data;
	}
	public Arc(Vertex origin, Vertex destination){
		this(origin, destination, null);
	}
	/**
	*
	*/
	public Object getData(){
		return data;
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
	public Vertex getOrigin(){
		return origin;
	}
	/**
	*
	*/	
	public Vertex getDestination(){
		return destination;
	}
	/**
	*
	*/
	public Object getAnnotation(){
		return annotation;
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
	public String toString(){
		return "(" + origin.getKey() + ") ---> (" + destination.getKey()+ ")::(" + data + ")"; 
	}
}