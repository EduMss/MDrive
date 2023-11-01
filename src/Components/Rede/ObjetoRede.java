package Components.Rede;
import java.io.Serializable;

public class ObjetoRede implements Serializable{
    private String msg;
    private String[] itens;
    private String[] TipoItens;
    private int QuantidadeItens;
    private int ID;//vai server para o que o cliente quer fazer/ a resposta do servidor

    //Lista de ID
    //1 -> lista de arquivos/diretorios | Feito
    //2 -> Enviar arquivo | Não Feito
    //3 -> Baixar arquivo | Não Feito
    //4 -> editar arquivo | Não Feito
    //5 -> excluir arquivo | Não Feito
    //6 -> entrar no diretorio (acredito que de para fazer isso na mesma chamada, se não, eu crio outra)| Não Feito
    //7 -> sair do diretorio| Não Feito
    //10 ->  Só uma mensagem | Não Feito
    
    public ObjetoRede(int ID){
        this.ID = ID;
    }

    public void Mensagem(String msg){
        this.msg = msg;
    }

    public String ObterMensagem(){
        return this.msg;
    }

    public void ID(int ID){
        this.ID = ID;
    }

    public int GetID(){
        return this.ID;
    }

    public void QuantidadeItens(int quantidade){
        this.itens = new String[quantidade];
        this.TipoItens = new String[quantidade];
        this.QuantidadeItens = quantidade;
    }

    public void addItem(int index, String item, String tipo){
        this.itens[index] = item;
        this.TipoItens[index] = tipo;
    }

    public String[] GetItens(){
        return this.itens;
    }

    public String[] GetTipoItens(){
        return this.TipoItens;
    }

    public int GetQuantidadeItens(){
        return this.QuantidadeItens;
    }
}
