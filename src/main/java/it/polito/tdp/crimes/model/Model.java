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
	 private List<String> soluzione;
	 
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
	public List<Adiacenze> getAdiacenzeP() {
		return archiPeso;
	}
	
	
	public List<String> getAllCategory() {
		return dao.listAllCategoryId();
	}
	public List<Integer> getAllMunth() {
		return dao.listAllMonth();
	}
	
	public void cerca(Adiacenze a) {
		 String v1 = a.getId1();
		 String v2 = a.getId2();
		 soluzione = new ArrayList<>();
		 List<String> parziale = new ArrayList<>();
		 parziale.add(v1);
		ricorsivo(parziale,v2);
		// ricorsivo1(parziale,v2,0);
		 
		 
	}



	private void ricorsivo(List<String> parziale, String v2) {
		if(parziale.get(parziale.size()-1).equals(v2)) {
			if(parziale.size()>soluzione.size()) {
				this.soluzione=new ArrayList<>(parziale);
				return;
			} 
			return;
		}
		else {
		String ultimo = parziale.get(parziale.size()-1);
		List<String> vertVicini = Graphs.neighborListOf(this.grafo, ultimo);
		
		for(String s:vertVicini) {
			
			if(!parziale.contains(s)) {
				parziale.add(s);
				ricorsivo(parziale, v2);
				parziale.remove(parziale.size()-1);
			}
			
			
			
			
		}
		}
		
		
	}
	public List<String> getSoluzione(){
		return soluzione;
	}
	
	private void ricorsivo1(List<String> parziale, String v2,int liv) {
		if(parziale.get(liv).equals(v2)) {
			if(parziale.size()>soluzione.size()) {
				this.soluzione=new ArrayList<>(parziale);
			
			} 
			return;
		}
		else {
		String ultimo = parziale.get(liv);
		List<String> vertVicini = Graphs.neighborListOf(this.grafo, ultimo);
		
		for(String s:vertVicini) {
			
			if(!parziale.contains(s)) {
				parziale.add(s);
				ricorsivo1(parziale, v2,liv++);
				parziale.remove(liv);
			}
			
			
			
			
		}
	}
	
	}
	
}
