package Components.SocketsRede;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;

import Components.Arquivos.Arquivo;
import Components.Arquivos.ByteArquivos;
import Components.Arquivos.InfoArquivo;
import Components.Rede.Pacotes;
import Components.Rede.PacotesFaltando;

public class ReceberArquivo {
    private Socket socket;
    private ObjectInputStream OIS = null;
    private InfoArquivo infoArquivo;

    public ReceberArquivo(Socket socket, String Diretorio){
        this.socket = socket;

        //Servidor Receber Arquivo
        Pacotes.ReceberInfoArquivo(this.socket, this.OIS);
        this.infoArquivo = Pacotes.GetInfoArquivo();

        byte[] objetoBytes;        

        //essa pasta Downloads tem que ser criada dentro de bin
        // String diretorio = "Downloads\\" + Nome_arquivo_Diretorio;
        String caminho = Diretorio + this.infoArquivo.getNome();
        System.out.println(caminho);

        int NumeroPacotes = this.infoArquivo.getNumeroPacotes();//definindo o Numero de Pacotes de acordo que recebemos na informação
        int tamanhoUltimoPacote = this.infoArquivo.getTamanhoUltimoPacote();//definindo o tamanho do ultimo Pacote de acordo que recebemos na informação
        int diferencaByte = this.infoArquivo.getTamanhoInfo();//definindo a diferença para aumentar os Pacotes de acordo que recebemos na informação
        Arquivo[] Arquivos = new Arquivo[NumeroPacotes];//criando um array do tamanho do numero de pacotes para caber todos os pacotes
        Arquivo arquivo;
        int pacote = 0;
        int TamanhoPacote = this.infoArquivo.getTamanhoPacote();//Pegando o tamanho "padrão" dos pacotes antes do ultimo de acordo com o que recebos na informação
        while(pacote < NumeroPacotes){//verificando se o pacote e menor q a QuantidadePacotes, não foi ultilizado o '==' pois o pacota esta começando a contar apartir do 0 enquanto o QuantidadePacotes ta começando apartir do 1

            if(pacote == (NumeroPacotes - 1)){//vai verificar se e o ultimo pacote sendo enviado, pois ele e menor, (QuantidadePacotes - 1) pois o pacote esta sendo contado desde 0, então tive que diminuir para fazer esse reajuste
                objetoBytes = new byte[tamanhoUltimoPacote + diferencaByte];//definindo o byte do tamanho do ultimo pacote
            } else {
                objetoBytes = new byte[TamanhoPacote + diferencaByte];//definindo o byte com o tamanho Maximo definido inicialmente
            }

            try {
                BufferedInputStream BIS = new BufferedInputStream(socket.getInputStream());//definindo o buffer de entrada stream com oque foi recebido com socket
                BIS.read(objetoBytes);//esta lendo o conteudo dentro do buffer e passando para o 'objetoBytes'
                
                arquivo = (Arquivo) Pacotes.getObjectFromByte(objetoBytes);//pegar o objeto apartir do byte //ta me gerando error
                
                Arquivos[arquivo.getNumeroPacote()] = arquivo;//definido o novo arquivo dentro da lista de arquivos criado, e guardando ele de acordo com id dele
                
                // System.out.println("Numero do pacote: " + arquivo.getNumeroPacote());
                
                System.out.println("Numero do pacote: " + arquivo.getNumeroPacote() + " | Recendo pacote : " + pacote + " \\ " + (NumeroPacotes - 1));
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                pacote++;
            }
        }

        while(true){
            PacotesFaltando pacotesFaltando = Pacotes.VerficiarPacotes(Arquivos,this.infoArquivo);
            if(pacotesFaltando.getQuantidadeFaltando() == 0){
                Pacotes.PrecisaPacotesRecebidor(false, this.socket);
                break;
            } else {
                Pacotes.PrecisaPacotesRecebidor(true, this.socket);
                Pacotes.EnviarInformativoPacotesFaltando(pacotesFaltando, this.socket);
                break;
            }
        }

        byte[] arquivoByte = ByteArquivos.JuntarBytes(Arquivos, this.infoArquivo);//obter o byte[] construido para converter em arquivo

        FileOutputStream FOS;
        try {
            FOS = new FileOutputStream(caminho);
            try {
                FOS.write(arquivoByte);// CONTEUDO EM BYTES RECEBIDO);//Escrevendo o arquivo no 'diretodio'
                FOS.close();//fechando o FOS
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
