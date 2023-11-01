package Components.Server;

import Components.Rede.*;
import Components.Arquivos.*;
import Components.SocketsRede.*;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket servidor;
    private Socket socket;
    private int porta;
    private int tamanhoMaximoByte = 8129;//Tamanho Maximo de padeços do arquivo a ser enviado por pacote //maximo : 65536 - Tamanho necessario para o pacote ser enviado ex.: 86 ->  65536 - 86 = 65450 (esse 86 e ficticio, pode ser diferente de acordo com o tamanho das informações dentro do pacote)
    // private String DiretorioPadrao = "C:\\Users\\Eduar\\OneDrive\\Área de Trabalho\\testando\\Servidor\\";
    private String DiretorioPadrao = "/home/Compartilhamentos/Eduardo/";
    // private String NomeArquivo = "TCE EDUARDO SILVA.pdf";

    public Server(){
        //definindo a porta do servidor
        this.porta = 1239;

        try {
            //crinado o server Socket, aqui ele vai precisar a porta que vc quer q ele escute
            this.servidor = new ServerSocket(this.porta);
            
        } catch (IOException e) {
            System.out.println("Erro ao criar ServerSocket");
            e.printStackTrace();
        }

        //loop para o servidor ficar sempre ativo
        while (true) {
            System.out.println("Iniciando loop");
            try {
                //caso alguem faça alguma requisição o servidor aceitar e guardar essas informações do cliente dentro do socket
                this.socket = servidor.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //printando na tela o ip do cliente conectado
            System.out.println("Cliente conectado: " + socket.getInetAddress().getHostAddress());

            //Inicaindo a conexão enviando o meno do que o cliente deseja fazer
            boolean Continuar = true;
            while (true) {
                //Aguardando Informação do que o cliente quer
                new Mensagem(socket);
                Mensagem.ReceberMensagem();
                ObjetoRede objRede = Mensagem.GetObjetoRede();
                int ID = objRede.GetID();
                switch (ID) {

                    case 1:
                        // bloco de código que será executado
                        new Mensagem(this.socket);
                        new ListandoArquivos(DiretorioPadrao);
                        ObjetoRede objetoRede = ListandoArquivos.ObterListar();
                        Mensagem.DefinirObjetoRede(objetoRede);
                        Mensagem.EnviarMensagem(0);
                        
                        System.out.println("Enviando arquivos do diretorios ");
                    break;    

                    case 2:
                        System.out.println(objRede.ObterMensagem());
                        new ReceberArquivo(this.socket, this.DiretorioPadrao);
                    break;
                    
                    case 3:

                        String arquivoDesejado = this.DiretorioPadrao + objRede.ObterMensagem();
                        System.out.println(arquivoDesejado);

                        File arquivoFile = new File(arquivoDesejado);

                        if(arquivoFile.exists()){
                            new Mensagem(this.socket);
                            Mensagem.DefinirMensagem("Iniciando Download!"); 
                            Mensagem.EnviarMensagem(15);
                            new EnviarArquivo(this.socket, this.tamanhoMaximoByte, arquivoDesejado, false, null);
                        } else {
                            new Mensagem(this.socket);
                            Mensagem.DefinirMensagem("Não foi possivel encontrar o arquivo desejado!"); 
                            Mensagem.EnviarMensagem(16);
                        }
                    break;

                    case 999:
                        System.out.println("Cliente desconectado! " + socket.getInetAddress().getHostAddress());
                        Continuar = false;
                    break;
                    
                    default:
                        System.out.println("Opção não e valida!\nPor favor digite uma opção valida:");
                }

                if(!Continuar){
                    break;
                }

            }
            

            
            try {
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        
    }






    

}
