import java.text.Normalizer;

/**
 * Created by BGADIL-PC on 01/09/2016.
 */
public class ParserAdresse {

    public static int numVoie(String adresse)
    {

        String numeroDeVoie = "";


        for (int i = 0; i < adresse.length(); i++)
        {

            char tested = adresse.charAt(i);
            if(!Character.isDigit(tested))
            {

            }

            else
            {
                for(i = i; (i < (adresse.length() - 1)) && Character.isDigit(tested);i++)
                {
                    tested = adresse.charAt(i);
                    if (Character.isDigit(tested))
                    {
                        numeroDeVoie+=tested;
                    }
                }

                break;
            }
        }
        int numVoie = Integer.parseInt(numeroDeVoie);
        return numVoie;
    }


    public static String voie(String adresse)
    {

        String finalchar = "";
        String libeleVoie = "";

        int debutChaine = 0;

        for (int i = 0; i < adresse.length(); i++)
        {

            char tested = adresse.charAt(i);
            if(!Character.isDigit(tested))
            {

            }

            else
            {
                for(i = i; i < adresse.length() && Character.isDigit(tested);i++)
                {
                    tested = adresse.charAt(i);
                }

                debutChaine = i;

                break;
            }
        }

        for(debutChaine=debutChaine; debutChaine < adresse.length();debutChaine++)
        {
            libeleVoie+=adresse.charAt(debutChaine);
        }

        return libeleVoie;
    }

    public static String codePostal(String CP)
    {
        String CPreturn ="";

        for(int i = 0;i<CP.length();i++)
        {
            if(Character.isDigit(CP.charAt(i)))
            {
                CPreturn+=CP.charAt(i);
            }
        }

        return CPreturn;


    }



    public static String identify_me(String cellule)
    {
        cellule  = cellule.replaceAll(" ","");
        String typeCellule;

        int i = 0;



        while(Character.isDigit(cellule.charAt(i)) && (i < (cellule.length() - 1)))
        {
            i++;
        }


        if(i + 1 == 1)
        {
            typeCellule = "nom";
            return typeCellule;
        }

        else if (i + 1 == 6)
        {
            typeCellule = "CP";
            return typeCellule;
        }

        else
        {
            typeCellule = "adresse";
            return typeCellule;
        }

    }




    public static String stripAccents(String text)
    {
        return text == null ? null
                : Normalizer.normalize(text, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

}