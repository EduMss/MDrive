package Components.Rede;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Components.Arquivos.Arquivo;
import Components.Arquivos.ByteArquivos;
import Components.Arquivos.InfoArquivo;

public class Pacotes {
    private static InfoArquivo infoArquivo;
    public static PacotesFaltando VerficiarPacotes(Arquivo[] arquivos, InfoArquivo infoArquivo){
        PacotesFaltando pacotes;
        int[] pacote = new int[infoArquivo.getNumeroPacotes()];//cria um Array do tamanho do Numero de Pacotes que deveriam ter
        int QuantidadeFaltando = 0;
        for(int i = 0; i < infoArquivo.getNumeroPacotes(); i++){//quero que voce faça um loop do tamanho da quantidade de pacotes
            if(arquivos[i] == null){//se o arquivo[i] == null significa q n veio o pacote
                pacote[QuantidadeFaltando] = i;//adicionando qual pacote esta faltando 
                QuantidadeFaltando++;//adicionando 1 na contagem para saber quantos pacotes terei que receber
            } else {
                // System.out.println(arquivos[i].getNumeroPacote());
            }
        }
        pacotes = new PacotesFaltando(QuantidadeFaltando, pacote);
        return pacotes;
    }

        //enviar uma mensagem para o servidor dizendo quais pacotes estão faltando
    public static void EnviarInformativoPacotesFaltando(PacotesFaltando pacotesFaltando, Socket socket){
        //definindo o nosso objeto <ObjetoRede> que foi um objeto que eu criei
        try {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //Pegando a saida Stream do socket e definindo no ObjetoOutputStream
            ObjectOutputStream OOS = new ObjectOutputStream(socket.getOutputStream());
            //não sei para que serve esse flush, mas sem ele eu não estava conseguindo ter sucesso no envio da mensagem
            OOS.flush();
            //Definindo o objeto desejado dentro do ObjectOutputStream e envaido
            OOS.writeObject(pacotesFaltando);
            //fechando o ObjectOutputStream
            // OOS.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PacotesFaltando ReceberInformativoPacotesFaltando(Socket socket){
        try {
            
            //pegando o objeto de entrada do socket
            ObjectInputStream OIS = new ObjectInputStream(socket.getInputStream());
            try {
                //pengando o Objeto do pacote recebido
                Object PacoteRecebido = OIS.readObject();
                //Definingo o Objeto recebido para o utilizado no envio
                PacotesFaltando pacotesFaltando = (PacotesFaltando) PacoteRecebido;
                //printando na tela a mensagem escrita

                // this.OIS.close();//se deixar fechado, ele encerra a comunicação com o servidor

                return pacotesFaltando;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean PrecisaPacotesEnviador(Socket socket){
        try {
            //pegando o objeto de entrada do socket
            ObjectInputStream OIS = new ObjectInputStream(socket.getInputStream());
            try {
                //pengando o Objeto do pacote recebido
                Object PacoteRecebido = OIS.readObject();
                //Definingo o Objeto recebido para o utilizado no envio
                PrecisaPacote precisaPacote = (PrecisaPacote) PacoteRecebido;
                //printando na tela a mensagem escrita

                // this.OIS.close();//se deixar fechado, ele encerra a comunicação com o servidor

                return precisaPacote.getPrecisaPacote();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void PrecisaPacotesRecebidor(boolean PrecisaPacote, Socket socket){
        //definindo o nosso objeto <ObjetoRede> que foi um objeto que eu criei
        PrecisaPacote precisaPacote = new PrecisaPacote(PrecisaPacote);
        try {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //Pegando a saida Stream do socket e definindo no ObjetoOutputStream
            ObjectOutputStream OOS = new ObjectOutputStream(socket.getOutputStream());
            //não sei para que serve esse flush, mas sem ele eu não estava conseguindo ter sucesso no envio da mensagem
            OOS.flush();
            //Definindo o objeto desejado dentro do ObjectOutputStream e envaido
            OOS.writeObject(precisaPacote);
            //fechando o ObjectOutputStream
            // OOS.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void EnviarInfoArquivo(Socket socket,String nomeArquivo, int QuantidadePacotes, int tamanhoArquivo, int tamanhoUltimoPacote, int tamanhoMaximoByte){
        //Fazendo um Arquivo "vazio" so par obter o tamanho em bytes que o a variavel arquivo tem, isso e para somar com o tamanhoMaximoByte para ser recebido o pacote do tamanho correto pelo cliente
        byte[] byteArquivo = new byte[0];
        Arquivo arquivo = new Arquivo(0, byteArquivo);
        byte[] bytea = ByteArquivos.BytesArquivo(arquivo);

        //definindo o nosso objeto <ObjetoRede> que foi um objeto que eu criei
        InfoArquivo infoArquivo = new InfoArquivo(nomeArquivo, QuantidadePacotes, tamanhoArquivo, tamanhoUltimoPacote, bytea.length, tamanhoMaximoByte);
        try {
            //Pegando a saida Stream do socket e definindo no ObjetoOutputStream
            ObjectOutputStream OOS = new ObjectOutputStream(socket.getOutputStream());
            //não sei para que serve esse flush, mas sem ele eu não estava conseguindo ter sucesso no envio da mensagem
            OOS.flush();
            //Definindo o objeto desejado dentro do ObjectOutputStream e envaido
            OOS.writeObject(infoArquivo);
            //fechando o ObjectOutputStream
            // OOS.close();//Não posso fechar pois estou perdendo a comunicação com o socket
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void ReceberInfoArquivo(Socket socket, ObjectInputStream OIS){
        try {
            //pegando o objeto de entrada do socket
            OIS = new ObjectInputStream(socket.getInputStream());

            try {
                //pengando o Objeto do pacote recebido
                Object PacoteRecebido = OIS.readObject();
                //Definingo o Objeto recebido para o utilizado no envio
                infoArquivo = (InfoArquivo) PacoteRecebido;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static InfoArquivo GetInfoArquivo(){
        return infoArquivo;
    }

    //Converter o objeto em bytes
    public static Object getObjectFromByte(byte[] objectAsByte) {
        //estou definindo como null, pois como esta sendo utilizado o try/catch o compilador reclama por não ter inicializado essas variaveis
        Object obj = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            //Construindo uma array de entrada de byte apartir do byte[]
            bis = new ByteArrayInputStream(objectAsByte);
            //convertendo esse array de bytes em objeto
            ois = new ObjectInputStream(bis);
            //lendo esse objeto e definindo em Object
            obj = ois.readObject();

            //fechando o ByteArrayInputStream e o ObjectInputStream
            // bis.close();
            // ois.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return obj;

    }


}
