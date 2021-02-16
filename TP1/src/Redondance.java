public class Redondance {

    private String sequence;
    private int nbRepetitions;
    private int[] decalages;

    public Redondance(String sequence,int decalage) {
        this.sequence=sequence;
        int[] temp = new int[4];
        temp[0]=decalage;
        this.decalages= temp;
        this.nbRepetitions= 1;
    }

    public String toString(){
        StringBuilder decalage = new StringBuilder();
        for (int i = 0 ; i<decalages.length;i++){
            decalage.append(this.getDecalages()[i]).append(" ");
        }
        return this.getSequence() + " " + this.getNbRepetitions() + " " + decalage;
    }

    public int getNbRepetitions() {
        return nbRepetitions;
    }

    public int[] getDecalages() {
        return decalages;
    }

    public String getSequence() {
        return sequence;
    }

    public void addDecalage(int decalage) {
        this.decalages[this.getNbRepetitions()] = decalage;
        this.nbRepetitions++;
    }
}
