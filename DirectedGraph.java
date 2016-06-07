import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Collection;

/**
* DirectedGraph ADT
* @author Jeffrey Tamashiro
*/
public class DirectedGraph{
	
	private HashMap<Object, Vertex> verts;
	private HashSet<Arc> arcs;
	
	/**
	* Constructor method
	*/
	public DirectedGraph(){
		verts = new HashMap<Object, Vertex>();
		arcs = new HashSet<Arc>();
	}
	/**
	* numVertices method
	* @return Returns the number of vertices
	*/	public int numVertices(){
		return verts.size();
	}
	/**	* numArcs method
	* @return Returns the number of arcs
	*/	public int numArcs(){
		return arcs.size();
	}	/**
	* vertices method
	* @return Returns an iterator over the vertices	*/	public Iterator vertices(){
		return new VertexIterator(verts.values().iterator());
	}
	/**	* arcs method
	* @return Returns an iterator over the arcs (directed edges) A of G.
	*/	public Iterator arcs(){
		return new ArcIterator(arcs.iterator());
	}	/**
	* getVertex method
	* @param key
	*  @return Returns the Vertex associated with client key
	*/	public Vertex getVertex(Object key){
		if(key == null){
			return null;
		}
		else{
			return verts.get(key);
		}
	}	/**
	* getArc method
	* @param source
	* @param target
	* @return Returns the Arc that connects client keys source and target, or null if none
	*/	public Arc getArc(Object source, Object target){
		
		Vertex vSource = verts.get(source);
		Vertex vTarget = verts.get(target);
		if(vSource == null || vTarget == null){
			return null;
		}
		for(Arc a : arcs){
			if(a.getOrigin() == vSource && a.getDestination() == vTarget){
				return a;
			}
		}
		return null;
	}	/**
	* getVertexData
	* @param v
	* @return ￼Returns the client data Object associated with Vertex v
	*/	public Object getVertexData(Vertex v){
		return v.getData();
	}	/**
	* getArcData method
	* @param a
	* @return Returns the client data Object associated with Arc a
	*/	public Object getArcData(Arc a){
		return a.getData();
	}	/**
	* inDegree method
	* @param v
	* @return Returns the number of arcs incoming to v
	*/	public int inDegree(Vertex v){
		return v.getArcsIn().size();
	}
	/**
	* outDegree method
	* @param v
	* @return Returns the number of arcs outgoing from v
	*/	public int outDegree(Vertex v){
		return v.getArcsOut().size();
	}
	/**
	* allDegrees method
	* @param v
	* @return Returns the total number of arcs in and out-going for v
	*/	
	public int allDegrees(Vertex v){
		return inDegree(v) + outDegree(v);
	}
	/**	* inAdjacentVertices
	* @param v
	* @return Returns an iterator over the vertices adjacent to v by incoming arcs
	*/		public Iterator inAdjacentVertices(Vertex v){
		return new VertexIterator(v.adjVertsIn().iterator());
	}	/**
	* outAdjacentVertices method
	* @param v
	* @return  Returns an iterator over the vertices adjacent to v by outgoing arcs
	*/	public Iterator outAdjacentVertices(Vertex v){
		return new VertexIterator(v.adjVertsOut().iterator());
	}
	/**
	* inAdjacentArcs
	* @param v
	* @return Returns an iterator over the arcs directed towards v
	*/	
	public Iterator inAdjacentArcs(Vertex v){
		return new ArcIterator(v.getArcsIn().iterator());
	}
	/**
	* outAdjacentArcs
	* @param v
	* @return Returns an iterator over the arcs directed away from v
	*/	
	public Iterator outAdjacentArcs(Vertex v){
		return new ArcIterator(v.getArcsOut().iterator());
	}		/**
	* origin method
	* @param a
	* @return  Returns the origin (start) vertex of Arc a
	*/	public Vertex origin(Arc a){
		return a.getOrigin();
	}
	/**	* destination method
	* @param a
	* @return Returns the destination (end) vertex of Arc a.
	*/	public Vertex destination(Arc a){
		return a.getDestination();
	}
	// Mutators - These are the methods by which you build and change graphs.
	/**
	* insertVertex method
	* Inserts a new isolated vertex indexed under (retrievable via) key and optionally containing an object data (which defaults to null)
	* @param key
	* @param data
	* @return Returns the new Vertex, null if key was null
	*/	public Vertex insertVertex(Object key, Object data){
		if(key == null){
			return null;
		}
		Vertex v = new Vertex(key, data);
		verts.put(key, v);
		return v;
	}
	public Vertex insertVertex(Object key){
		return insertVertex(key, null);
	}	/**
	* insertArc method
	* Inserts a new arc from an existing vertex to another, optionally containing an object data
	* @param u
	* @param v
	* @return Returns the new Arc
	*/	public Arc insertArc(Vertex u, Vertex v){
		Arc a = new Arc(u,v);
		arcs.add(a);
		return a;
	}	public Arc insertArc(Vertex u, Vertex v, Object data){
		Arc a = new Arc(u,v);
		a.setData(data);
		arcs.add(a);
		return a;
	}
	/**	* setVertexData
	*  Changes the data Object associated with Vertex v to data
	* @param v
	* @param data
	*/	public void setVertexData(Vertex v, Object data){
		v.setData(data);
	}	/**
	* setArcData
	* Changes the data Object associated with Arc a to data
	* @param a
	* @param data
	*/	public void setArcData(Arc a, Object data){
		a.setData(data);
	}	/**
	* removeArc
	* Removes an arc
	* @param a
	* @return Returns the client data object formerly stored at a.
	*/	public Object removeArc(Arc a){
		Object result = a.getData();
		a.getOrigin().removeOut(a);
		a.getDestination().removeIn(a);
		arcs.remove(a);
		return result;
	}
	/**
	* removeVertex
	* Deletes a vertex and all its incident arcs (and edges under the undirected extension)
	* @return Returns the client data object formerly stored at v
	*/
	public Object removeVertex(Vertex v){
		Object result = v.getData();
		
		for(Arc a : v.getArcs()){
			removeArc(a);
		}
		verts.remove(v.getKey());
		
		return result;
	}		/**
	* reverseDirection
	* Reverse the direction of an arc
	* @param a
	*/	public void reverseDirection(Arc a){
		this.insertArc(a.getDestination(),a.getOrigin(),a.getData());
		this.removeArc(a);
		
		return;
	}
    /**
     * Checks if an arc exists in either direction between two vertices
     * @param v1 
     * @param v2 
     * @return boolean
     */
	public boolean isArc(Vertex v1, Vertex v2){
		
		for(Vertex vMatch : iterableAdjVertsIn(v1)){
			if(vMatch == v2){
				return true;
			}
		}
		for(Vertex vMatch : iterableAdjVertsOut(v1)){
			if(vMatch == v2){
				return true;
			}
		}
		return false;
	}
    /**
     * Alternative to iterator version which is iterable
     * @return Collection of all graph Vertices
     */
	protected Collection<Vertex> iterableVertices(){
		return verts.values();
	}
    /**
     * Alternative to iterator version which is iterable
     * @return Collection of all graph Arcs
     */	
	protected Collection<Arc> iterableArcs(){
		return arcs;
	}
    /**
     * Alternative to iterator version which is iterable
	 * @parm v Vertex Object
     * @return Collection of Arcs adjacent into given Vertex
     */	
	protected Collection<Arc> iterableAdjArcsIn(Vertex v){
		return v.getArcsIn();
	}
    /**
     * Alternative to iterator version which is iterable
	 * @parm v Vertex Object
     * @return Collection of Arcs adjacent out of given Vertex
     */		
	protected Collection<Arc> iterableAdjArcsOut(Vertex v){
		return v.getArcsOut();
	}
	/**
     * Alternative to iterator version which is iterable
	 * @parm v Vertex Object
     * @return Collection of Vertices adjacent into given Vertex
     */	
	protected Collection<Vertex> iterableAdjVertsIn(Vertex v){
		return v.adjVertsIn();
	}
	/**
     * Alternative to iterator version which is iterable
	 * @parm v Vertex Object
     * @return Collection of Vertices adjacent out of given Vertex
     */	
	protected Collection<Vertex> iterableAdjVertsOut(Vertex v){
		return v.adjVertsOut();
	}
	/**
     * Alternative to iterator version which is iterable
	 * @parm v Vertex Object
     * @return Collection of all Vertices adjacent to a given Vertex
     */	
	protected Collection<Vertex> iterableAdjVertsAll(Vertex v){
		return v.adjVerts();
	}	
    /**
     * toString method returns visual representation of graph ADT
     * @return String
     */
	public String toString(){
		return "\nVertices:" + verts + "\nArcs:" + arcs;
	}
    /**
     * Nested class VertexIterator
	 * Based on Iterator Interface and makes adjustments for unique graph implementations
     */
	private class VertexIterator implements Iterator{
		
		private Iterator it;
		private Vertex current;
        /**
         * 
         * 
         * @param it 
         */
		public VertexIterator(Iterator it){
			this.it = it;
			current = null;
		}
		
		public void remove(){
			if(current == null){
				throw new IllegalStateException();
			}
			removeVertex(current);
			it.remove(); //neccessary???
		}
		
		public Vertex next(){
			if(!hasNext()){
				return null;
			}		
			current = (Vertex)it.next();
			return current;
		}
		
		public boolean hasNext(){
			return it.hasNext();
		}
		
	} //end inner class
    /**
     * Nested class ArcIterator
     */
	private class ArcIterator implements Iterator{
		
		private Iterator it;
		private Arc current;

		public ArcIterator(Iterator it){
			this.it = it;
			current = null;
		}
		
		public void remove(){
			if(current == null){
				throw new IllegalStateException();
			}
			removeArc(current);
			it.remove(); //redundant???
		}
		
		public Arc next(){
			if(!hasNext()){
				return null;
			}
			current = (Arc)it.next();
			return current;
		}
		
		public boolean hasNext(){
			return it.hasNext();
		}
		
	} //end inner class	
} //end class