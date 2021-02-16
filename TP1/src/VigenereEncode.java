import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class VigenereEncode {

    public static final int TAILLE_ALPHABET = 26;

    public static void main(String[] args) throws IOException {

        String texte_clair = SaisirTexte("Veuillez saisir le texte clair");

        if (texte_clair.length()!=0) {
            String cle = SaisirTexte("Veuillez saisir la cle de chiffrement");
            if (cle.length()!=0) {

                char[] texte_array = setTextArray(texte_clair);
                char[] cle_array = setTextArray(cle);

                StringBuilder texte_encode = new StringBuilder();
                for (int i = 0; i < getLength(texte_array); i++) {
                    int index_lettre_cle = VigenereEncode.trouverPosition(cle_array[i % getLength(cle_array)]);
                    int index_lettre_texte_clair = VigenereEncode.trouverPosition(texte_array[i]);

                    int index_lettre_encode = VigenereEncode.encode(index_lettre_cle,index_lettre_texte_clair);
                    char lettre_encode = (char) index_lettre_encode;
                    texte_encode.append(lettre_encode);
                }
                System.out.println(texte_encode.toString());
            }else {
                System.out.println("Clé invalide");
            }
        } else{
            System.out.println("Texte invalide");
        }
    }

    private static int getLength(char[] texte_array) {
        return texte_array.length;
    }

    private static char[] setTextArray(String texte_clair) {
        String texte_clair_uniform = texte_clair.toLowerCase();
        return texte_clair_uniform.replace(" ", "").toCharArray();
    }

    private static String SaisirTexte(String s) throws IOException {
        System.out.println(s);

        BufferedReader entree = new BufferedReader
                (new InputStreamReader(System.in));

        return entree.readLine();
    }

    public static int trouverPosition(char lettre){
        return (int)lettre-97;
    }

    public static int encode(int index_cle,int index_lettre_clair){
        return ((index_cle + index_lettre_clair) % TAILLE_ALPHABET) + 97;
    }
}
