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
	double mediaMigliore;
	List<Integer> migliore;
	
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
		//pesoMax= 0.0;
		//pesoMin= 0.0;
		
		pesoMax=adiacenti.get(adiacenti.size()-1).getPeso();
		pesoMin=adiacenti.get(0).getPeso();
		
		return "\nPeso max= "+pesoMax+", peso min= "+pesoMin;
	}

	public double getPesoMax() {
		return pesoMax;
	}

	public double getPesoMin() {
		return pesoMin;
	}
	
	public String MaxMin(double x){
		List<Adiacenza> lMax= new LinkedList<>();
		List<Adiacenza> lMin= new LinkedList<>();
		for(Adiacenza a: archi) {
			if(a.peso<x) {
				lMin.add(a);
			}
			else if(a.peso>x) {
				lMax.add(a);
			}
		}
		return "\nSoglia "+ x + " Maggiori: "+lMax.size()+ " Minori: "+lMin.size();
	}
	
	

	public List<Integer> calcolaPercorso(double p)
	{
		migliore = new LinkedList<Integer>();
		mediaMigliore=0.0;
		
		List<Integer> parziale = new LinkedList<>();
		cercaRicorsiva(parziale,0,p);
		return migliore;
	}
	
	private void cercaRicorsiva(List<Integer> parziale,int L, double p) {
		 
				//condizione di terminazione
				
					int pesoParziale = pesoTot(parziale);
					if(pesoParziale > pesoTot(migliore))//la strada piú lunga é la migliore
					{
						migliore = new LinkedList<>(parziale);
						
					}
				
				int ultimo= parziale.get(parziale.size()-1);
				for(Integer v:Graphs.neighborListOf(this.grafo, parziale.get(parziale.size()-1))) //scorro sui vicini dell'ultimo nodo sulla lista
				{
					if(!parziale.contains(v) && this.grafo.getEdgeWeight(this.grafo.getEdge(ultimo, v))>p);
					{
						parziale.add(v);
						cercaRicorsiva(parziale,L+1, p);
						parziale.remove(parziale.size()-1);
					}
					
				}
		
	}

	private int pesoTot(List<Integer> parziale) {
		
		int peso = 0;
		
		for(int i=0; i<parziale.size()-1; i++) {
			peso+= grafo.getEdgeWeight(grafo.getEdge(parziale.get(i), parziale.get(i-1)));
		}
		return peso;
	}

	
}