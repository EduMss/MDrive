package Components.SocketsRede;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

import Components.Arquivos.Arquivo;
import Components.Arquivos.ByteArquivos;
import Components.Rede.Pacotes;
import Components.Rede.PacotesFaltando;
import JanelaControles.JanelaControles;

public class EnviarArquivo {
    private int tamanhoMaximoByte;
    private Socket socket;
    private String DiretorioArquivo;
    private Boolean Cliente;
    private static JanelaControles controles;
    private static int pacoteAtual;


    public EnviarArquivo(Socket socket, int tamanhoMaximoByte, String DiretorioArquivo, Boolean Cliente, JanelaControles control){
        this.socket = socket;
        this.tamanhoMaximoByte = tamanhoMaximoByte;
        this.DiretorioArquivo = DiretorioArquivo;
        this.Cliente = Cliente;
        controles = control;
             
        // File arquivoFile = new File("C:\\Users\\Eduar\\OneDrive\\Área de Trabalho\\testando\\Servidor\\img1.png");//pegando o arquivo
        // File arquivoFile = new File("C:\\Users\\Eduar\\OneDrive\\Área de Trabalho\\testando\\Servidor\\manual delphi 2006 - Atualizado 2023.docx");//pegando o arquivo
        File arquivoFile = new File(this.DiretorioArquivo);//pegando o arquivo

        if(arquivoFile.exists()){

            int tamanho_arquivo = (int) arquivoFile.length(); //Pegando o tamanho do arquivo
            int ultimoPacote = 0;
            int QuantidadePacotes =  tamanho_arquivo / this.tamanhoMaximoByte;//dividindo o tamanho do arquivo pelo tamanho maximo de padaços que pode ser envia por pacote
            
            
            while(true){
                if(QuantidadePacotes * this.tamanhoMaximoByte < tamanho_arquivo){//se o QuantidadePacotes*tamanhoMaximoByte for menor que o tamanho do arquivo, significa q ainda falta um pacote a ser enviado, e esse pacote e menor que o valor definido como maximo
                    ultimoPacote = tamanho_arquivo - (QuantidadePacotes * this.tamanhoMaximoByte);//pegando a quantidade de bytes que falta para se o ultimo pacote
                    QuantidadePacotes++;//almentando o pacote que ficou faltando
                } else {
                    break;
                }
            }
            if(this.Cliente == true){
                controles.DefTamanhoMaxBarraUpload(QuantidadePacotes);
            }

            if(tamanho_arquivo > this.tamanhoMaximoByte){//verificar se o tamanho do arquivo e maior do que o socket consegue enviar, se for maior ele vair quebrar o arquivo em varios pedaços.

                Pacotes.EnviarInfoArquivo(this.socket,arquivoFile.getName(), QuantidadePacotes, tamanho_arquivo, ultimoPacote, this.tamanhoMaximoByte);

                System.out.println("Nome do arquivo: " + arquivoFile.getName());
                System.out.println("tamanho Maximo Byte: " + tamanhoMaximoByte);
                System.out.println("tamanho do arquivo: " + tamanho_arquivo);
                System.out.println("Quantidade de pacotes: " + QuantidadePacotes);

                int pacote = 0;
                byte[] byteArquivo; //Criando um byte do tamanho do arquivo

                try {
                    FileInputStream FIS = new FileInputStream(arquivoFile);//definindo o arquivo dentro do arquivo de entrada da Stream

                    while(pacote < QuantidadePacotes){//verificando se o pacote e menor q a QuantidadePacotes, não foi ultilizado o '==' pois o pacota esta começando a contar apartir do 0 enquanto o QuantidadePacotes ta começando apartir do 1

                        if(pacote == (QuantidadePacotes - 1)){//vai verificar se e o ultimo pacote sendo enviado, pois ele e menor, (QuantidadePacotes - 1) pois o pacote esta sendo contado desde 0, então tive que diminuir para fazer esse reajuste
                            byteArquivo = new byte[ultimoPacote];//definindo o byte do tamanho do ultimo pacote
                        } else {
                            byteArquivo = new byte[this.tamanhoMaximoByte];//definindo o byte com o tamanho Maximo definido inicialmente
                        }
                        
                        FIS.read(byteArquivo);//definindo o quanto ele vai pegar do arquivo e inserir os bytes em 'byteArquivo'
                        Arquivo arquivo = new Arquivo(pacote, byteArquivo);//Criando o objeto Arquivo para ser enviado pelo pacote
                        byte[] bytea = ByteArquivos.BytesArquivo(arquivo);//criando um byte[] com os bytes q serão retornado no metodo {BytesArquivos}
                            try {
                                BufferedOutputStream BOS = new BufferedOutputStream(this.socket.getOutputStream());//definindo o buffer de saida com a saida stream do socket
                                // System.out.println("Tamanho do pacote em byte sendo enviado  :" + bytea.length);
                                BOS.write(bytea);//escrendo o byte dentro da saida para ser enviado
                                BOS.flush();//enviando
                                System.out.println("Recendo pacote : " + pacote + " \\ " + (QuantidadePacotes - 1));
                                pacoteAtual = pacote;
                                if(this.Cliente == true){
                                    // this.controles.AtualizarBarraUpload(pacote);
                                    new Thread(UpdateUpload).start();
                                }
                                
                            } catch (IOException e) {
                                // e.printStackTrace();
                                // System.out.println("Error no envio do pacote:" + pacote);
                            }

                        pacote++;
                    }

                    boolean precisaPacote = Pacotes.PrecisaPacotesEnviador(this.socket);
                    if(precisaPacote){
                        PacotesFaltando pacotesFaltando = Pacotes.ReceberInformativoPacotesFaltando(this.socket);
                        pacotesFaltando.print();
                        System.out.println("Precisa de mais pacotes");
                    } else {
                        System.out.println("Todos os pacotes chegaram");
                    }
                    
                    FIS.close();//fechando o FileInputStream
                    // this.socket.close();//fechando o socket

                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            } else {//verificar se o tamanho do arquivo esta dentro do que o socket consegue enviar, se sim, enviar em um pacote so.

                Pacotes.EnviarInfoArquivo(this.socket, arquivoFile.getName(), QuantidadePacotes, tamanho_arquivo, 0, this.tamanhoMaximoByte);//Enviar as info do arquivo para o cliente

                byte[] byteArquivo = new byte[tamanho_arquivo]; //Criando um byte do tamanho do arquivo
                Arquivo arquivo = new Arquivo(0, byteArquivo);//Criando o objeto Arquivo para ser enviado pelo pacote

                try {
                    FileInputStream FIS = new FileInputStream(arquivoFile);//definindo o arquivo dentro do arquivo de entrada da Stream
                    FIS.read(byteArquivo);//definindo o quanto ele vai pegar do arquivo e inserir os bytes em 'byteArquivo'
                    FIS.close();//fechando o FileInputStream
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                byte[] bytea = ByteArquivos.BytesArquivo(arquivo);//criando um byte[] com os bytes q serão retornado no metodo {BytesArquivos}

                try {
                    BufferedOutputStream BOS = new BufferedOutputStream(this.socket.getOutputStream());//definindo o buffer de saida com a saida stream do socket
                    BOS.write(bytea);//escrendo o byte dentro da saida para ser enviado
                    //essa parte do inicio e um "cast to"
                    // ((ObjectOutput) BOS).writeObject(arquivo);//escrendo o byte dentro da saida para ser enviado
                    BOS.flush();//enviando
                    BOS.close();//fechando o BufferedOutputStream
                    if(this.Cliente == true){
                        controles.AtualizarBarraUpload(1);
                    }
                    // this.socket.close();//fechando o socket
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static Runnable UpdateUpload = new Runnable() {
        public void run(){
            try {
                controles.AtualizarBarraUpload(pacoteAtual);
            } catch (Exception e) {
            }
        }
    };

    

}
