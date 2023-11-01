package Components.Client;

import Components.SocketsRede.*;

import java.net.Socket;

public class ComandosCliente {
    private Socket socket;
    private String DiretorioPadrao;

    public ComandosCliente(Socket Ssocket, String dir){
        this.socket = Ssocket;
        this.DiretorioPadrao = dir;

        new Mensagem(this.socket);
    }

    public void SelecionarComando(int ID, String arquivo, String DiretorioPadrao){
        if(ID == 3){
            Baixar(arquivo);
        }
    }

    public void Baixar(String arquivo){
        Mensagem.DefinirMensagem(arquivo);
        Mensagem.EnviarMensagem(3);
        new ReceberArquivo(this.socket, this.DiretorioPadrao);
    }
}
