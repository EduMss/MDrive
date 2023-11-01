package Components.SocketsRede;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Components.Rede.ObjetoRede;

public class Mensagem {
    private static Socket socket;
    private static ObjetoRede objetoRede;
    private static String msg;

    public Mensagem(Socket Ssocket){
        socket = Ssocket;
    }

    public static void DefinirMensagem(String mensagem){
        msg = mensagem;
    }

    public static void DefinirObjetoRede(ObjetoRede ObjRede){
        objetoRede = ObjRede;
    }

    public static ObjetoRede GetObjetoRede(){
        return objetoRede;
    }

    //metodo para enviar a mensagem para o cliente
    public static void EnviarMensagem(int ID){
        //definindo o nosso objeto <ObjetoRede> que foi um objeto que eu criei
        if(ID != 0){
            objetoRede = new ObjetoRede(ID);
            if (ID == 2){
                objetoRede.Mensagem(msg);
            } else if (ID == 3){
                objetoRede.Mensagem(msg);
            } else if (ID == 16 || ID == 15){
                objetoRede.Mensagem(msg);
            }
        } 
        
        try {
            //Pegando a saida Stream do socket e definindo no ObjetoOutputStream
            ObjectOutputStream OOS = new ObjectOutputStream(socket.getOutputStream());
            //não sei para que serve esse flush, mas sem ele eu não estava conseguindo ter sucesso no envio da mensagem
            OOS.flush();
            //Definindo o objeto desejado dentro do ObjectOutputStream e envaido
            OOS.writeObject(objetoRede);
            //fechando o ObjectOutputStream
            // OOS.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //metodo para receber o pacote 
    public static void ReceberMensagem(){
        try {
            //pegando o objeto de entrada do socket
            ObjectInputStream OIS = new ObjectInputStream(socket.getInputStream());
            try {
                //pengando o Objeto do pacote recebido
                Object PacoteRecebido = OIS.readObject();
                //Definingo o Objeto recebido para o utilizado no envio
                objetoRede = (ObjetoRede) PacoteRecebido;
                //printando na tela a mensagem escrita
                // Cliente_PrintMensagem(objetoRede);

                // OIS.close();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
