package tp1_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {

    public static final int TAILLE_ALPHABET = 26;

    public static void main(String[] args) throws IOException {
        boolean stop = true;
        while (stop) {
            String Question = "Que voulez-vous faire ? \n - Chiffrer un texte avec une clé (1) \n - Déchiffrer un texte avec la clé (2) \n - Trouver la longueur d'une clé à partir d'un texte chiffré (3) \n - Quitter \n";
            String choix = SaisirTexte(Question);
            switch (choix) {
                case "1":
                    String texte_clair = SaisirTexte("Veuillez saisir le texte clair \n");
                    String cleC = SaisirTexte("Veuillez saisir la cle de chiffrement \n");
                    Chiffrement(texte_clair, cleC);
                    break;
                case "2":
                    String texte_chiffre = SaisirTexte("Veuillez saisir le texte à déchiffrer \n");
                    String cleD = SaisirTexte("Veuillez saisir la cle de chiffrement \n");
                    Dechiffrement(texte_chiffre, cleD);
                    break;
                case "3":
                    String texte = SaisirTexte("Veuillez saisir un texte chiffré");
                    Kasiski(texte);
                    break;
                case "4":
                    System.out.println("Au revoir. \n");
                    stop = false;
                    break;
                default:
                    System.out.println("Erreur : tapez 1, 2, 3 ou 4. \n");
            }
        }
    }

    private static void Chiffrement(String texte_clair, String cle) {
        if (texte_clair.length() != 0) {
            if (cle.length() != 0) {
                char[] texte_array = setTextArray(texte_clair);
                char[] cle_array = setTextArray(cle);
                StringBuilder texte_chiffre = new StringBuilder();
                int i = 0;
                while (i < getLength(texte_array) && valid(cle_array[i % getLength(cle_array)]) && valid(texte_array[i])) {
                    int index_lettre_cle = trouverPosition(cle_array[i % getLength(cle_array)]);
                    int index_lettre_texte_clair = trouverPosition(texte_array[i]);
                    int index_lettre_chiffree = encode(index_lettre_cle, index_lettre_texte_clair);
                    char lettre_chiffree = (char) index_lettre_chiffree;
                    texte_chiffre.append(lettre_chiffree);
                    i++;
                }
                if (!valid(cle_array[(i - 1) % getLength(cle_array)])) {
                    System.out.println("Un caractére de la clé n'est pas une lettre");
                }
                if (!valid(texte_array[i - 1])) {
                    System.out.println("Un caractére du texte à chiffrer n'est pas une lettre");
                }
                System.out.println(texte_chiffre.toString());
            } else {
                System.out.println("La clé est invalide \n");
            }
        } else {
            System.out.println("Le texte est invalide \n");
        }
    }

    private static void Dechiffrement(String texte_chiffre, String cle) {
        if (texte_chiffre.length() != 0) {
            if (cle.length() != 0) {
                char[] texte_array = setTextArray(texte_chiffre);
                char[] cle_array = setTextArray(cle);
                StringBuilder texte_clair = new StringBuilder();
                int i = 0;
                while (i < getLength(texte_array) && valid(cle_array[i % getLength(cle_array)]) && valid(texte_array[i])) {
                    int index_lettre_cle = trouverPosition(cle_array[i % getLength(cle_array)]);
                    int index_lettre_texte_chiffre = trouverPosition(texte_array[i]);
                    int index_lettre_dechiffree = decode(index_lettre_cle, index_lettre_texte_chiffre);
                    char lettre_dechiffree = (char) index_lettre_dechiffree;
                    texte_clair.append(lettre_dechiffree);
                    i++;
                }
                if (!valid(cle_array[(i - 1) % getLength(cle_array)])) {
                    System.out.println("Un caractére de la clé n'est pas une lettre");
                }
                if (!valid(texte_array[i - 1])) {
                    System.out.println("Un caractére du texte à déchiffrer n'est pas une lettre");
                }
                System.out.println(texte_clair.toString());
            } else {
                System.out.println("La clé est invalide \n");
            }
        } else {
            System.out.println("Le texte est invalide \n");
        }
    }

    public static void Kasiski(String texte) {
        int taille_cle = texte.length() / 2;
        String temp, temp1;
        ArrayList<Integer> rangRepet = new ArrayList<>();
        ArrayList<Integer> distances = new ArrayList<>();
        int[] pgcd = new int[texte.length()];

        while (taille_cle >= 2) {
            for (int rangPremiereLettre = 0; rangPremiereLettre < texte.length() - taille_cle; rangPremiereLettre++) {
                if (!rangRepet.contains(rangPremiereLettre)) {
                    int nbRepetitions = 0;
                    temp = texte.substring(rangPremiereLettre, rangPremiereLettre + taille_cle);
                    for (int decalage = 1; decalage < texte.length() - rangPremiereLettre - taille_cle; decalage++) {
                        temp1 = texte.substring(rangPremiereLettre + decalage, rangPremiereLettre + decalage + taille_cle);
                        if (temp.equals(temp1)) {
                            System.out.println(temp + " " + decalage);
                            distances.add(decalage);
                            nbRepetitions++;
                            for (int i = 0; i < taille_cle; i++)
                                rangRepet.add(rangPremiereLettre + i);
                        }
                    }
                    if (nbRepetitions != 0)
                        System.out.println("nombre de répétitions " + nbRepetitions);
                }
            }
            taille_cle--;
        }
        for (int i = 0; i < distances.size(); i++) {
            for (int j = i + 1; j < distances.size(); j++) {
                if (pgcd(distances.get(i), distances.get(j)) > 1) {
                    pgcd[pgcd(distances.get(i), distances.get(j))]++;
                }
            }
        }
        int max = 0;
        int PGCDMax = 0;
        for (int i = 3; i < pgcd.length; i++) {
            if (pgcd[i] > PGCDMax) {
                max = i;
                PGCDMax = pgcd[i];
            }
        }
        System.out.println("Taille de la clé " + max);
    }

    private static String SaisirTexte(String s) throws IOException {
        System.out.println(s);
        BufferedReader entree = new BufferedReader(new InputStreamReader(System.in));
        return entree.readLine();
    }

    private static boolean valid(char c) {
        if ((int)c>=65 && (int)c<=90 || (int)c<=32) {
            return true;
        }
        return (int) c >= 97 && (int) c <= 122;
    }

    private static int getLength(char[] texte_array) {
        return texte_array.length;
    }

    private static char[] setTextArray(String texte_clair) {
        return texte_clair.replace(" ", "").toCharArray();
    }

    public static int trouverPosition(char c){
        if ((int)c>=65 && (int)c<=90 || (int)c<=32) {
            return (int)c-65;
        }else {
            return (int)c-97;
        }
    }

    public static int encode(int index_cle,int index_lettre_clair){
        return ((index_cle + index_lettre_clair) % TAILLE_ALPHABET) + 97;
    }

    public static int decode(int index_cle,int index_lettre_chiffree){
        return (((index_lettre_chiffree - index_cle)+TAILLE_ALPHABET) % TAILLE_ALPHABET) + 97;
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