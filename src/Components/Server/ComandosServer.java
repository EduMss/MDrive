package Components.Server;

import java.net.Socket;

import Components.SocketsRede.EnviarArquivo;
import Components.SocketsRede.Mensagem;

public class ComandosServer {
    private static Socket socket;
    private static int tamanhoMaximoByte;
    private static String DiretorioPadrao;

    public ComandosServer(Socket Ssocket, int MaximoBytes, String DirPadrao){
        socket = Ssocket;
        DiretorioPadrao = DirPadrao;
        tamanhoMaximoByte = MaximoBytes;


        new Mensagem(Ssocket);
    }

    public static void SetDiretorio(String dir){
        DiretorioPadrao = dir;
    }

    public static void EnviarArquivo(String DiretorioArquivo){
        new EnviarArquivo(socket,  tamanhoMaximoByte, DiretorioArquivo, false, null);
    }
}
