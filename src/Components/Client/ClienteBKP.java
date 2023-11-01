package Components.Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import Components.Arquivos.ListandoArquivos;
import Components.Rede.ObjetoRede;
import Components.SocketsRede.EnviarArquivo;
import Components.SocketsRede.Mensagem;
import Components.SocketsRede.ReceberArquivo;

public class ClienteBKP {
    private Socket socket;
    private ObjectInputStream OIS = null;
    private int porta;
    private String ip;
    private int tamanhoMaximoByte = 8129;//Tamanho Maximo de padeços do arquivo a ser enviado por pacote //maximo : 65536 - Tamanho necessario para o pacote ser enviado ex.: 86 ->  65536 - 86 = 65450 (esse 86 e ficticio, pode ser diferente de acordo com o tamanho das informações dentro do pacote)
    private String DiretorioPadrao = "C:\\Users\\Eduar\\OneDrive\\Área de Trabalho\\testando\\Cliente\\";
    private String NomeArquivo = "Site de Passagens aéreas.mp4";
    //optei por utilizar o byte[] pois ele e reconhecido mais facilmente no banco de dados

    public ClienteBKP(){
        //definindo a porta do server
        this.porta = 1234;
        //defininfo o ip do server
        this.ip = "localhost";

        try {
            //definindo o socket com o ip e a porta do Servidor
            this.socket = new Socket(this.ip , this.porta);
            //Receber arquivo
            // new Cliente_RecebendoArquivo(this.socket);
            // new ReceberArquivo(this.socket, this.DiretorioPadrao);

            //Enviar Mensagem
            // new Cliente_Mensagem(this.socket);
            // Cliente_Mensagem.Cliente_ReceberMensagem();
            // Cliente_Mensagem.Cliente_EnviarMensagem("Opa, recebi sim!");


            // new Mensagem(this.socket);
            // Mensagem.ReceberMensagem();

            // Mensagem.DefinirMensagem("Recebi suar informações"); 
            // Mensagem.EnviarMensagem(2);

            //Enviando Arquivo
            // new Cliente_EnviandoArquivo(this.socket, this.tamanhoMaximoByte, this.DiretorioPadrao, this.NomeArquivo);
            // new EnviarArquivo(this.socket, this.tamanhoMaximoByte, this.DiretorioPadrao, this.NomeArquivo);


            


            socket.close();
        } catch (UnknownHostException e) {
            // e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Não foi possivel se conectar ao servidor!");
            //e.printStackTrace();
        }
    }



}
