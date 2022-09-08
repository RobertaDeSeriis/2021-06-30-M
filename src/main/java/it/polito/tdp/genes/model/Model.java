package it.polito.tdp.genes.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.genes.db.GenesDao;

public class Model {
	GenesDao dao;
	List<Integer> vertici;
	List<Adiacenza> archi;
	List<Adiacenza> adiacenti;
	Graph <Integer, DefaultWeightedEdge> grafo;
	double pesoMax;
	double pesoMin;
	
	public Model() {
		this.dao= new GenesDao();
	}
	
	public String creaGrafo() {
		this.grafo= new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		adiacenti=new LinkedList<>();
		vertici= dao.listVertici();
		
		Graphs.addAllVertices(this.grafo, vertici);
		
		archi=dao.getArchi();
		
		for(Adiacenza a: archi) {
			Graphs.addEdge(this.grafo, a.getC1(), a.getC2(), a.getPeso());
			adiacenti.add(a);
		}

		Collections.sort(adiacenti);
		
		return "Grafo creato!\n# Vertici:"+grafo.vertexSet().size()+ "\n# Archi: "+grafo.edgeSet().size();	
		
	}
	
	public String pesoMaxMin() {
		pesoMax= 0.0;
		pesoMin= 0.0;
		
		pesoMax=adiacenti.get(0).getPeso();
		pesoMin=adiacenti.get(adiacenti.size()-1).getPeso();
		
		return "\nPeso max= "+pesoMax+", peso min= "+pesoMin;
	}



	
}