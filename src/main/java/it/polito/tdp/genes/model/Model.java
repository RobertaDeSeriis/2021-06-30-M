package it.polito.tdp.genes.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import it.polito.tdp.genes.db.GenesDao;

public class Model {
	GenesDao dao;
	List<Integer> vertici;
	List<Adiacenza> archi;
	List<Adiacenza> adiacenze;
	Graph <Integer, DefaultWeightedEdge> grafo;
	
	public Model() {
		this.dao= new GenesDao();
	}
	
	public String creaGrafo() {
		this.grafo= new SimpleDirectedGraph<>(DefaultWeightedEdge.class);
		adiacenze=new LinkedList<>();
		vertici= dao.listVertici();
		archi=dao.getArchi();
		
		Graphs.addAllVertices(this.grafo, vertici);
		
		for(Adiacenza a: archi) {
			Graphs.addEdge(this.grafo, a.getC1(), a.getC2(), a.getPeso());
			adiacenze.add(a);
		}
		Collections.sort(adiacenze);
		
		return "Grafo creato!\n# Vertici:"+grafo.vertexSet().size()+ "\n# Archi: "+grafo.edgeSet().size();	
		
	}



	
}