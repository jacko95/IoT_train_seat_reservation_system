package it.uniupo.reti2.client;

import static spark.Spark.get;
import static spark.Spark.port;

import java.util.Map;

import org.springframework.web.client.RestTemplate;

import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class TrenoClient {

    public static void main(String[] args) {

        port(5000);// change the default port
        RestTemplate rest = new RestTemplate();// init resttemplate

        get("/", (req, res) ->{
            // get and parse the JSON from the "external" server
            Map<?, ?> model = rest.getForObject("http://localhost:4567/api/v1/treni", Map.class);

            // handle the views
            return new HandlebarsTemplateEngine().render(
            		new ModelAndView(model, "prenot.hbs"));
        });
    }
}