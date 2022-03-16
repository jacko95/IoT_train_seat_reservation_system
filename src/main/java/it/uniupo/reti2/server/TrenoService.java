package it.uniupo.reti2.server;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.google.gson.Gson;

import it.uniupo.reti2.api.BARTClient;
import it.uniupo.reti2.hue.HueClient;
import it.uniupo.reti2.model.Passeggero;
import it.uniupo.reti2.model.Treno;

public class TrenoService {
	
	static int idPass = 0;
	
	public static void main(String[] args) {
        
        Gson gson = new Gson();
        String baseURL = "/api/v1";
        TrenoDao db = new TrenoDao();
        BARTClient bc = new BARTClient();
        bc.start();
        HueClient hc = new HueClient();
    	hc.start();
    	HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);        

        // enable CORS
        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET, POST");
            response.header("Access-Control-Allow-Headers", "Content-Type");
        });

        // Prendo tutti i passeggeri
        get(baseURL + "/passeggeri", "application/json", (request, response) -> {
            // set a proper response code and type
            response.type("application/json");
            response.status(200);

            // Prendi tutti i passeggeri dal Database
            List<Passeggero> passeggeri = db.getAllPasseggero();
            
            // prepare the JSON-related structure to return
            Map<String, List<Passeggero>> Json = new HashMap<>();
            Json.put("passeggeri", passeggeri);

            return Json;
        }, gson::toJson);
        
        
        // Prendo tutti i treni da BART
        get(baseURL + "/treni", "application/json", (request, response) -> {
            // set a proper response code and type
            response.type("application/json");
            response.status(200);
            
            //Prendi tutti i treni
            List<Treno> treni = db.getAllTreno();
            
            // prepare the JSON-related structure to return
            Map<String, List<Treno>> Json = new HashMap<>();
            Json.put("treni", treni);

            return Json;
        }, gson::toJson);


        // Prendo un singolo treno
        get(baseURL + "/treni/:orario_partenza", "application/json", (request, response) -> {
        	Treno t = db.getTreno(String.valueOf(request.params(":orario_partenza")));

            // Se non c'è nessun treno restituiamo 404!
            if(t == null)
                halt(404);

            // prepare the JSON-related structure to return
            // and the suitable HTTP response code and type
            Map<String, Treno> Json = new HashMap<>();
            Json.put("treni", t);
            
            response.status(200);
            response.type("application/json");

            return Json;
        }, gson::toJson);

        // Prendo un singolo passeggero
        get(baseURL + "/passeggeri/:id", "application/json", (request, response) -> {
            // get the id from the URL
            Passeggero p = db.getPasseggero(Integer.valueOf(request.params(":id")));

            // Se non c'è nessun treno restituiamo 404!
            if(p == null)
                halt(404);

            // prepare the JSON-related structure to return
            // and the suitable HTTP response code and type
            Map<String, Passeggero> Json = new HashMap<>();
            Json.put("passeggeri", p);
            
            response.status(200);
            response.type("application/json");

            return Json;
        }, gson::toJson);
        

        post(baseURL + "/treni/:orario_partenza", "application/json", (request, response) -> {
            // get the body of the HTTP request        	
            Map addRequest = gson.fromJson(request.body(), Map.class);
            String orarioPartenza = String.valueOf(request.params(":orario_partenza"));

            // check whether everything is in place
            if(addRequest != null && addRequest.containsKey("bici")) {
            	
                int bici = ((Double)addRequest.get("bici")).intValue();
                             
                int noPasseggero = 0;
                if(db.getTrenoPieno(orarioPartenza) == 1 || (db.getTrenoPienoBici(orarioPartenza) == 1 && bici == 1)){
                	noPasseggero = 1;
                }
                
                if(noPasseggero == 0) {	
                	idPass++;
                	db.addPasseggero(new Passeggero(idPass, orarioPartenza, bici));
                
	                if(db.getAllPasseggeroTrenoBici(orarioPartenza).size() == db.getTrenoPostiBici(orarioPartenza)) {
	                    db.updateTrenoPienoBici(orarioPartenza);
	                }
	                if(db.getAllPasseggeroTreno(orarioPartenza).size() == db.getTrenoPosti(orarioPartenza)) {
	            		db.updateTrenoPieno(orarioPartenza);
	            	}
	                if(db.getAllPasseggeroTrenoBici(orarioPartenza).size() > 0) {
		                if(db.getTrenoPienoBici(orarioPartenza) == 0) {
		               		HueClient.verdeSingoloBri(db.getTrenoId(orarioPartenza), headers, db.getAllPasseggeroTrenoBici(orarioPartenza).size()*50);	
		               	}
		               	else if(db.getTrenoPienoBici(orarioPartenza) == 1) {
		                	HueClient.rossoSingolo(Integer.toString(db.getTrenoId(orarioPartenza)), headers);
		               	}
	                }
                }
            	
                // if success, prepare a suitable HTTP response code
                response.status(201);
                
            }
            else {
                halt(403);
            }

            return "";
        });      
    }
}
