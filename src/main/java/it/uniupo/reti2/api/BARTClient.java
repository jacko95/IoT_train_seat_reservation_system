package it.uniupo.reti2.api;

import org.springframework.web.client.RestTemplate;

import it.uniupo.reti2.server.TrenoDao;
import it.uniupo.reti2.model.Treno;

import java.util.ArrayList;

public class BARTClient extends Thread{
	
    private static RestTemplate rest;// RestTemplate instance
    
    private static String BARTUrl;
    
    static int idTreno = 0;

    public void run() {
            	
    	String baseURL = "http://api.bart.gov/api/";//api.bart.gov, a chi fare la richiesta (server), 
    	
        String cmd = "depart";
        String orig = "OAKL";
        String dest = "COLS";
        String key = "MW9S-E7SL-26DU-VV8V";//"/key=MW9S-E7SL-26DU-VV8V" + ; la key (API registration key) dove la trovo?
        
        BARTUrl = baseURL + "sched.aspx?" + "cmd=" + cmd + "&orig=" + orig + "&dest=" + dest + "&key=" + key + "&json=y";//"/key=MW9S-E7SL-26DU-VV8V" + ; //"/api/sched.aspx?" va messo? la key (API registration key) dove la trovo?
        rest = new RestTemplate();
        
        JsonOb tutto = rest.getForObject(BARTUrl, JsonOb.class);      
                
        ArrayList<Treno> treni = new ArrayList<Treno>();
        
        TrenoDao db = new TrenoDao();

        int i = 0;
        try {
	        while(true) {
	            
	        	idTreno++;
	        	String stazionePartenza = tutto.getRoot().getSchedule().request.trip.get(i).origin;
	            String orarioPartenza = tutto.getRoot().getSchedule().request.trip.get(i).origTimeMin;
	        	String dataPartenza = tutto.getRoot().getSchedule().request.trip.get(i).origTimeDate;
	        	String stazioneDestinazione = tutto.getRoot().getSchedule().request.trip.get(i).destination;
	            String orarioArrivo = tutto.getRoot().getSchedule().request.trip.get(i).destTimeMin;
	        	String dataArrivo = tutto.getRoot().getSchedule().request.trip.get(i).destTimeDate;
	        	String durata = tutto.getRoot().getSchedule().request.trip.get(i).tripTime;
	        	String tariffa = tutto.getRoot().getSchedule().request.trip.get(i).fare;
	        			
	        	Treno t = new Treno(idTreno, stazionePartenza, orarioPartenza, dataPartenza, stazioneDestinazione, orarioArrivo, dataArrivo, durata, tariffa, 0, 0, 5, 3);
	            treni.add(t);
	            
	            i++;
	        }
        }catch (IndexOutOfBoundsException e){
        }
        
        db.deleteAllTreno("OAKL"); //posso anche dare orig in ingresso
        db.deleteAllPasseggero();
        
        for(Treno e: treni) {
        	db.addTreno(e);
        }
        
    }

}
