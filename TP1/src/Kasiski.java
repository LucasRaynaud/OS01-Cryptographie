import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Kasiski {

    public static void main(String[] args) throws IOException {

        String texte = SaisirTexte("Veuillez saisir un texte chiffré");
        int taille_cle=texte.length()/2;
        String temp,temp1;
        ArrayList<Integer> rangRepet = new ArrayList<>();
        ArrayList<Integer> distances = new ArrayList<>();
        int[] pgcd = new int[texte.length()];

        while (taille_cle>=2){
            for (int rangPremiereLettre = 0 ; rangPremiereLettre<texte.length()-taille_cle;rangPremiereLettre++) {
                if (!rangRepet.contains(rangPremiereLettre)) {
                    int nbRepetitions = 0;
                    temp = texte.substring(rangPremiereLettre, rangPremiereLettre + taille_cle);
                    for (int decalage = 1; decalage < texte.length() - rangPremiereLettre - taille_cle; decalage++) {
                        temp1 = texte.substring(rangPremiereLettre + decalage, rangPremiereLettre + decalage + taille_cle);
                        if (temp.equals(temp1)) {
                            System.out.println(temp + " " + decalage);
                            distances.add(decalage);
                            nbRepetitions++;
                            for (int i = 0; i<taille_cle;i++)
                                rangRepet.add(rangPremiereLettre+i);
                        }
                    }
                    if (nbRepetitions != 0)
                        System.out.println("nombre de répétitions " + nbRepetitions);
                }
            }
            taille_cle--;
        }
        for (int i = 0 ; i < distances.size() ; i++){
            for (int j = i+1 ; j < distances.size();j++){
                if (pgcd(distances.get(i), distances.get(j)) > 1){
                    pgcd[pgcd(distances.get(i),distances.get(j))]++;
                }
            }
        }
        int max = 0;
        int PGCDMax = 0;
        for (int i = 3 ; i < pgcd.length ; i++){
            if (pgcd[i]>PGCDMax) {
                max = i;
                PGCDMax = pgcd[i];
            }
        }
        System.out.println("Taille de la clé " + max);
    }

    private static String SaisirTexte(String s) throws IOException {
        System.out.println(s);

        BufferedReader entree = new BufferedReader
                (new InputStreamReader(System.in));

        return entree.readLine();
    }

    public static int pgcd(int m, int n) {
        int r;
        while(n!=0){
            r=m%n;
            m=n;
            n=r;
        }
        return m;
    }
}