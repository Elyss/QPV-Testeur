import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by BGADIL-PC on 19/10/2016.
 */
public class CheckAdresse {

    public static String Check(String CP, String voie, String numero) throws IOException {


        URL url = new URL("http://sig.ville.gouv.fr/recherche-adresses-qp-polville");
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("code_postal", CP);
        params.put("num_adresse", numero);
        params.put("nom_voie", voie);

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);

        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        String corps = "";

        for (int c; (c = in.read()) >= 0;)
            corps += (char) c;


        corps = corps.replaceAll("\n","");

        corps = corps.replaceAll("\r","");

        Pattern p=Pattern.compile("resultat_recherche_zus.*<strong>(.*)</strong> - ");
        Matcher m=p.matcher(corps);
        //while(m.find())
            //System.out.println(m.group(1));


        return IsQPV(corps);
    }


    public static String IsQPV(String corps)
    {
        String yes = "<li class=\"green\">";
        String erreur_adresse = "<li class=\"yellow\">";
        String ancien = "Avant le 1er janvier 2015";
        String AP = "était située";
        String limiteAP = "était située en limite";
        String choix = "Choisissez une voie parmi la liste";
        String result ="";



        if(corps.toLowerCase().contains(yes.toLowerCase()))
        {
            result = "Oui";
        }

        else if(corps.toLowerCase().contains(ancien.toLowerCase()))
        {
            result = "Ancien Perimetre";
        }

        else if(corps.toLowerCase().contains(AP.toLowerCase()))
        {
            if(corps.toLowerCase().contains(limiteAP.toLowerCase()))
            {
                result = "Non";
            }

            else
            {
                result = "Ancien Perimetre";
            }
        }

        else if(corps.toLowerCase().contains(erreur_adresse.toLowerCase()))
        {
            result = "Adresse Incorrecte";
        }

        else if(corps.toLowerCase().contains(choix.toLowerCase()))
        {
            result = "Plusieurs voies";
        }

        else
        {
            result = "Non";
        }

        return result;
    }




    public static String identify_me(String cellule) {
        cellule = cellule.replaceAll(" ", "");

        String typeCellule;

        int i = 0;
        int j = 0;
        int k = 0;


        while (i < cellule.length()) {
            if (Character.isDigit(cellule.charAt(i))) {
                j++;
            }

            if (Character.isLetter(cellule.charAt(i))) {
                k++;
            }

            i++;
        }


        if (j == 5 && k == 0) {
            typeCellule = "CP";
            //System.out.println("CP");
            return typeCellule;
        } else if (j > 0 && k > 0) {
            typeCellule = "adresse";
            //System.out.println("adresse");
            return typeCellule;
        } else {
            typeCellule = "autre";
            //System.out.println("autre");
            return typeCellule;
        }

    }









}
