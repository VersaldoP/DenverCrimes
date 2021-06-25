package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	
	
	
	 private EventsDao dao = new EventsDao();
	 private Graph<String,DefaultWeightedEdge> grafo;
	 private List<Adiacenze> archi;
	 private List<Adiacenze> archiPeso;
	 
	 public void creaGrafo(String id,int m) {
		 grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		 
		 Graphs.addAllVertices(this.grafo, dao.listAllVertex(id, m));
		 archi = new ArrayList<>(dao.listAllAdiacenze(id, m));
		 double media =0.0;
		 for (Adiacenze a : archi) {
			 
			 Graphs.addEdge(this.grafo,a.getId1(), a.getId2(), a.getPeso());
			 media+=a.getPeso();
		 }
		 media = media/archi.size();
		 archiPeso= new ArrayList<>();
		 for(Adiacenze a :archi) {
			 if(a.getPeso()>media)
				 archiPeso.add(a);
		 }
		 
		 
		 
		
		 
	 }
	 
	
	
	public String getAdiacenzePeso(){
		String re="";
		for(Adiacenze a :archiPeso) {
			re+= a.toString();
		}
		return re;
	}
	
	
	public List<String> getAllCategory() {
		return dao.listAllCategoryId();
	}
	public List<Integer> getAllMunth() {
		return dao.listAllMonth();
	}
	
	
	
	
	
	
	
	
}
