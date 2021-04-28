/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fetch;

import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.shaded.json.parser.JSONParser;
import com.nimbusds.jose.shaded.json.parser.ParseException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Bruger
 */
public class FactsFetcher {
    private static EntityManagerFactory emf;
    
    
    private static String URL = "https://dog-api.kinduff.com/api/facts";
    
     public static JSONObject getFact() throws MalformedURLException, IOException, ParseException {
        JSONParser parser = new JSONParser();
        
        URL url = new URL(URL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json");
        Scanner scan = new Scanner(con.getInputStream());
        String jsonStr = null;
        if (scan.hasNext()) {
            jsonStr = scan.nextLine();
        }
        
        scan.close();
        
        JSONObject jsonObject = (JSONObject)parser.parse(jsonStr);

        
        System.out.println(jsonStr);
        return jsonObject;
    }
}
