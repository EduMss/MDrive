package Components.Client;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import Components.Arquivos.CriarPastaPadrao;
import Components.Arquivos.ListandoArquivos;
import Components.Rede.ObjetoRede;
import Components.SocketsRede.EnviarArquivo;
import Components.SocketsRede.Mensagem;
import Components.SocketsRede.ReceberArquivo;


import JanelaControles.JanelaControles;

public class Cliente {
    private Socket socket;
    private ObjectInputStream OIS = null;
    private int porta;
    private String ip;
    private int tamanhoMaximoByte = 8129;//Tamanho Maximo de padeços do arquivo a ser enviado por pacote //maximo : 65536 - Tamanho necessario para o pacote ser enviado ex.: 86 ->  65536 - 86 = 65450 (esse 86 e ficticio, pode ser diferente de acordo com o tamanho das informações dentro do pacote)
    private String DiretorioPadrao = "C:\\Users\\Eduar\\OneDrive\\Área de Trabalho\\testando\\Cliente\\";
    private String NomeArquivo = "Site de Passagens aéreas.mp4";
    public static JanelaControles controler;

    public Cliente(){
        new CriarPastaPadrao();

        //definindo a porta do server
        this.porta = 1239;
        //defininfo o ip do server
        // this.ip = "localhost";
        this.ip = "192.168.0.127";

        

        try {
            //definindo o socket com o ip e a porta do Servidor
            this.socket = new Socket(this.ip , this.porta);
            
            // while(true){

            //     System.out.println("Digite um número para selecionar as opções desejadas:");
            //     System.out.println("1 - Visualizar todos os arquivos dentro do diretório");
            //     System.out.println("2- Enviar um arquivo");
            //     System.out.println("3- Baixar um arquivo");
            //     System.out.println("88- para configurar o programa");
            //     System.out.println("999- Sair");
            //     //String nome = EntradaInput.nextLine();



            //     // switch (Entrada) {

            //     //     case 1:

            //     //     // bloco de código que será executado                        
            //     //         new Mensagem(this.socket);
            //     //         Mensagem.EnviarMensagem(1);
            //     //         Mensagem.ReceberMensagem();

            //     //         ObjetoRede objetoRede = Mensagem.GetObjetoRede();

            //     //         System.out.println("Lista de arquivos e pastas");
            //     //         System.out.println(objetoRede.ObterMensagem());
            //     //         int index = 0;
            //     //         String textoDir = "";
            //     //         for(String item : objetoRede.GetItens()){
            //     //             textoDir = textoDir + item +" \n";
            //     //             System.out.println(item+ "  -  "+ objetoRede.GetTipoItens()[index]);
            //     //             index ++;
            //     //         }
            //     //         controler.DifinirArquivos(textoDir);
                    
            //     //     break;
                    
            //     //     case 2:

                        
            //     //         System.out.print("Digite o nome do arquivo(com o diretorio completo): ");
                            
            //     //         String nome_Arquivo = this.EntradaInput.nextLine();
            //     //         System.out.println(nome_Arquivo);

            //     //         File arquivoFile = new File(nome_Arquivo);

            //     //         if(arquivoFile.exists()){

            //     //             new Mensagem(this.socket);
            //     //             Mensagem.DefinirMensagem(nome_Arquivo); 
            //     //             Mensagem.EnviarMensagem(2);
                            
            //     //             //editar o enviar arquivo para colocar o diretorio completo!
            //     //             new EnviarArquivo(this.socket, this.tamanhoMaximoByte, nome_Arquivo);   
                          
            //     //         } else {
            //     //             System.out.println("Não foi possivel encontrar o arquivo para enviar!");
            //     //         }

            //     //     break;
                    
            //     //     case 3:
            //     //         System.out.print("Digite o nome do arquivo: ");

            //     //         String NomeArquivoServidor = this.EntradaInput.nextLine();
            //     //         new Mensagem(this.socket);
            //     //         Mensagem.DefinirMensagem(NomeArquivoServidor); 
            //     //         Mensagem.EnviarMensagem(3);

            //     //         Mensagem.ReceberMensagem();

            //     //         if(Mensagem.GetObjetoRede().GetID() == 15){
            //     //             System.out.println(Mensagem.GetObjetoRede().ObterMensagem());
            //     //             new ReceberArquivo(this.socket, this.DiretorioPadrao);
            //     //         } else if (Mensagem.GetObjetoRede().GetID() == 16){
            //     //             System.out.println(Mensagem.GetObjetoRede().ObterMensagem());
            //     //         }

            //     //     break;

            //     //     case 999:
            //     //         new Mensagem(this.socket);
            //     //         Mensagem.EnviarMensagem(999);
                        
            //     //         Continuar = false;
            //     //     break;
                    
            //     //     default:
            //     //         // bloco de código que será executado se nenhum dos cases for aceito
            //     //         System.out.println("Opção não e valida!\nPor favor digite uma opção valida:");
                    
            //     // }

            //     if(!Continuar){
            //         break;
            //     }
                
                
            // }

            // this.socket.close();
            
        } catch (UnknownHostException e) {
        } catch (IOException e) {
            System.out.println("Não foi possivel se conectar ao servidor!");
        }
    }

    public void DefControler(JanelaControles control){
        controler = control;
    }

    public void EnviarMensagem1(){
        // bloco de código que será executado                        
        new Mensagem(this.socket);
        Mensagem.EnviarMensagem(1);
        Mensagem.ReceberMensagem();

        ObjetoRede objetoRede = Mensagem.GetObjetoRede();

        System.out.println("Lista de arquivos e pastas");
        System.out.println(objetoRede.ObterMensagem());
        int index = 0;
        String textoDir = "";
        for(String item : objetoRede.GetItens()){
            textoDir = textoDir + item +" \n";
            System.out.println(item+ "  -  "+ objetoRede.GetTipoItens()[index]);
            index ++;
        }
        controler.DifinirArquivos(textoDir);
    }

    public Socket ObterSocket(){
        return this.socket;
    }

}
