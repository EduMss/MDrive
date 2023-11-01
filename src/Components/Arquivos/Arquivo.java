package Components.Arquivos;
import java.io.Serializable;

public class Arquivo implements Serializable {
    private byte[] conteudo;
    private int NumeroPacote = 0;

    public Arquivo(int NumeroPacote, byte[] conteudo){
        this.NumeroPacote = NumeroPacote;
        this.conteudo = conteudo;
    }

    public byte[] getConteudo(){
        return this.conteudo;
    }

    public int getNumeroPacote(){
        return this.NumeroPacote;
    }

    public void setNumeroPacote(int NumeroPacote){
        this.NumeroPacote = NumeroPacote;
    }
}
