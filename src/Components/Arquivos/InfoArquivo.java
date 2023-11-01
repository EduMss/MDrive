package Components.Arquivos;
import java.io.Serializable;

public class InfoArquivo implements Serializable {
    private String nome;
    private int NumeroPacotes;
    private int tamanhoArquivo;
    private int tamanhoUltimoPacote;
    private int TamanhoInfo;
    private int TamanhoPacote;

    public InfoArquivo(String nome, int NumeroPacotes, int tamanhoArquivo, int tamanhoUltimoPacote, int TamanhoInfo, int TamanhoPacote){
        this.nome = nome;
        this.NumeroPacotes = NumeroPacotes;
        this.tamanhoArquivo = tamanhoArquivo;
        this.tamanhoUltimoPacote = tamanhoUltimoPacote;
        this.TamanhoInfo = TamanhoInfo;
        this.TamanhoPacote = TamanhoPacote;
    }

    public String getNome(){
        return this.nome;
    }

    public int getNumeroPacotes(){
        return this.NumeroPacotes;
    }

    public int getTamanhoArquivo(){
        return this.tamanhoArquivo;
    }

    public int getTamanhoUltimoPacote(){
        return this.tamanhoUltimoPacote;
    }

    public int getTamanhoInfo(){
        return this.TamanhoInfo;
    }

    public int getTamanhoPacote(){
        return this.TamanhoPacote;
    }
    
    public void printInfoArquivo(){
        System.out.println("nome: " + this.nome + "\nNumero de Pacotes: " + this.NumeroPacotes + "\nTamanho do Arquivo:" + this.tamanhoArquivo + "\nTamanho do Cabeçalho:" + this.TamanhoInfo + "\nTamanho de bytes Trabalhando:" + this.TamanhoPacote);
    }
}
