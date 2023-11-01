package Components.Server;

import Components.Rede.*;
import Components.Arquivos.*;
import Components.SocketsRede.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerBKP {
    private ServerSocket servidor;
    private Socket socket;
    private int porta;
    private int tamanhoMaximoByte = 8129;//Tamanho Maximo de padeços do arquivo a ser enviado por pacote //maximo : 65536 - Tamanho necessario para o pacote ser enviado ex.: 86 ->  65536 - 86 = 65450 (esse 86 e ficticio, pode ser diferente de acordo com o tamanho das informações dentro do pacote)
    private String DiretorioPadrao = "C:\\Users\\Eduar\\OneDrive\\Área de Trabalho\\testando\\Servidor\\";
    private String NomeArquivo = "TCE EDUARDO SILVA.pdf";

    public ServerBKP(){
        //definindo a porta do servidor
        this.porta = 1234;

        try {
            //crinado o server Socket, aqui ele vai precisar a porta que vc quer q ele escute
            this.servidor = new ServerSocket(this.porta);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //loop para o servidor ficar sempre ativo
        while (true) {
            try {
                //caso alguem faça alguma requisição o servidor aceitar e guardar essas informações do cliente dentro do socket
                this.socket = servidor.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //printando na tela o ip do cliente conectado
            System.out.println("Cliente conectado: " + socket.getInetAddress().getHostAddress());


            //Enviando Arquivo
            // new Server_EnviandoArquivo(this.socket, this.tamanhoMaximoByte, this.DiretorioPadrao, this.NomeArquivo);
            // new EnviarArquivo(this.socket, this.tamanhoMaximoByte, this.DiretorioPadrao, this.NomeArquivo);


            //Enviando/Recebendo mensagem
            // new Server_Mensagem(this.socket);

            // new ListandoArquivos(DiretorioPadrao);

            // ObjetoRede objRede = ListandoArquivos.ObterListar();
            // objRede.Mensagem(DiretorioPadrao);

            // Server_Mensagem.Server_EnviarMensagem(objRede);
            // Server_Mensagem.Server_ReceberMensagem();


            // new Mensagem(this.socket);
            // new ListandoArquivos(DiretorioPadrao);
            // ObjetoRede objRede = ListandoArquivos.ObterListar();
            // Mensagem.DefinirObjetoRede(objRede);
            // Mensagem.EnviarMensagem(0);

            // Mensagem.ReceberMensagem();

            //Recebendo Arquivo
            // new Server_RecebendoArquivo(this.socket);
            // new ReceberArquivo(socket, DiretorioPadrao);


            try {
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        
    }






    

}
