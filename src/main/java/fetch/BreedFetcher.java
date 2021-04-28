/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fetch;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import static com.google.protobuf.Any.parser;
import static com.google.protobuf.Api.parser;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.shaded.json.parser.JSONParser;
import com.nimbusds.jose.shaded.json.parser.ParseException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.eclipse.persistence.exceptions.JSONException;
import static org.eclipse.persistence.expressions.ExpressionOperator.Log;

/**
 *
 * @author Bruger
 */
public class BreedFetcher {
    
     private static final String URL = "https://dog-info.cooljavascript.dk/api/breed";
   // private static Gson GSON = new GsonBuilder().create();

    public static JSONObject getAllBreeds() throws MalformedURLException, IOException, ParseException {
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
    public static JSONObject getBreedByName(String breed) throws MalformedURLException, IOException, ParseException {
        JSONParser parser = new JSONParser();
        
        URL url = new URL(URL+"/"+breed);
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
