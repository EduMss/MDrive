package Components.Rede;
import java.io.Serializable;

public class PacotesFaltando implements Serializable{
    private int QuantidadeFaltando;
    private int[] pacote;


    public PacotesFaltando(int QuantidadeFaltando, int[] pacote){
        this.QuantidadeFaltando = QuantidadeFaltando;
        this.pacote = pacote;
    }

    public int getQuantidadeFaltando(){
        return this.QuantidadeFaltando;
    }

    public int[] getPacote(){
        return this.pacote;
    }

    public void print(){
        System.out.println("QuantidadeFaltando = " + this.QuantidadeFaltando);
        for(int i = 0; i < this.QuantidadeFaltando; i++){
            System.out.println("pacotes = " +(this.pacote[i]));
        }
    }
}
