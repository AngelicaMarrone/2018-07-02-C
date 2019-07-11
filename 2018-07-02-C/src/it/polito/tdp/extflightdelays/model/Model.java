package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {

	//inserire tipo di dao

		private ExtFlightDelaysDAO dao;

		

		//scelta valore mappa

		private Map<Integer,Airport> idMap;

		

		//scelta tipo valori lista

		private List<Airport> vertex;

		

		//scelta tra uno dei due edges

		private List<Adiacenza> edges;

		

		//scelta tipo vertici e tipo archi

		private Graph<Airport, DefaultWeightedEdge> graph;


		public Model() {

			

			//inserire tipo dao

			dao  = new ExtFlightDelaysDAO();

			//inserire tipo values

			idMap = new HashMap<Integer,Airport>();

		}
		
	public List<Airport> creaGrafo(Integer min) {
		//scelta tipo vertici e archi

				graph = new SimpleWeightedGraph<Airport,DefaultWeightedEdge>(DefaultWeightedEdge.class);

				

				//scelta tipo valori lista

				vertex = new ArrayList<Airport>(dao.getVertex(min, idMap));

				Graphs.addAllVertices(graph,vertex);

				

				edges = new ArrayList<Adiacenza>(dao.getEdges());

				

				for(Adiacenza a : edges) {

					if (idMap.containsKey(a.getId1()) && idMap.containsKey(a.getId2()))
					{

					//CASO BASE POTRESTI DOVER AGGIUNGERE CONTROLLI

					Airport source = idMap.get(a.getId1());

					Airport target = idMap.get(a.getId2());

					double peso = a.getPeso();
					
					
						DefaultWeightedEdge e= graph.getEdge(target, source);
					
					if (e==null)

					{Graphs.addEdge(graph,source,target,peso);

					System.out.println("AGGIUNTO ARCO TRA: "+source.toString()+" e "+target.toString());}
					
					else
					{
						double nuovoPeso= calcolaNuovoPeso(source,target, peso, e);
						graph.setEdgeWeight(e, nuovoPeso);
						
					}

					}
					

				}

				

				System.out.println("#vertici: "+graph.vertexSet().size());

				System.out.println("#archi: "+graph.edgeSet().size());
				
				return vertex;

				
		
	}

	private double calcolaNuovoPeso(Airport source, Airport target, double peso, DefaultWeightedEdge e) {
				
		double vecchioPeso= graph.getEdgeWeight(e);
		double ris= peso+vecchioPeso;
		return ris;
	}

	public String componenteConnessa(Airport a) {
		
		List<Airport> connessi= Graphs.neighborListOf(graph, a);
		
		List<ConnessoPeso> cp= new ArrayList<>();
		
		for (Airport c: connessi)
		{
			cp.add(new ConnessoPeso(c,graph.getEdgeWeight(graph.getEdge(a, c))));
			
		}
		
		Collections.sort(cp);
		
		String ris= "Gli aeroporti connessi sono:\n";
		
		for (ConnessoPeso p: cp)
		{
			ris+= "- "+ p.getA().getAirportName()+ " "+p.getPeso() + "\n";
		}
		
		return ris;
		
	}

}
