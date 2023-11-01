package JanelaControles;
import java.io.File;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import Components.Client.Cliente;
import Components.Rede.ObjetoRede;
import Components.SocketsRede.EnviarArquivo;
import Components.SocketsRede.Mensagem;
import Components.SocketsRede.ReceberArquivo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class JanelaControles implements Initializable {
    public static Cliente cliente;
    private static Socket socket;
    private int tamanhoMaximoByte = 8129;
    private String DiretorioPadrao = "C:\\Users\\Eduar\\OneDrive\\Área de Trabalho\\testando\\Cliente\\";
    private int TamanhoMaxBarraDownload;
    private int TamanhoMaxBarraUpload;

    public JanelaControles(){
        System.out.println("Metodo Contruto! JanelaControles");
        new Thread(StartServer).start();
        System.out.println("Acabou Metodo Contruto! JanelaControles");
    }

    private static Runnable StartServer = new Runnable() {
        public void run(){
            try {
                cliente = new Cliente();
                socket = cliente.ObterSocket();
            } catch (Exception e) {
            }
        }
    };

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ProgressBar BarraCarregamentoDownload;

    @FXML
    private ProgressBar BarraCarregamentoUpload;

    @FXML
    private Button Config;

    @FXML
    private Button Download;

    @FXML
    private TextField InputDownload;

    @FXML
    private TextField InputUpload;

    @FXML
    private TextFlow ListaArquivosServidor;

    @FXML
    private Text Texttextoarquivos;

    @FXML
    private Button Upload;

    @FXML
    void Configurar(ActionEvent event) {

    }

    @FXML
    void Download(ActionEvent event) {
        String NomeArquivoServidor = InputDownload.getText();
        new Mensagem(socket);
        Mensagem.DefinirMensagem(NomeArquivoServidor); 
        Mensagem.EnviarMensagem(3);

        Mensagem.ReceberMensagem();

        if(Mensagem.GetObjetoRede().GetID() == 15){
            System.out.println(Mensagem.GetObjetoRede().ObterMensagem());
            new ReceberArquivo(socket, this.DiretorioPadrao);
        } else if (Mensagem.GetObjetoRede().GetID() == 16){
            System.out.println(Mensagem.GetObjetoRede().ObterMensagem());
        }
    }

    @FXML
    void Upload(ActionEvent event) {
        String nome_Arquivo = InputUpload.getText();
        File arquivoFile = new File(nome_Arquivo);

        if(arquivoFile.exists()){
            new Mensagem(socket);
            Mensagem.DefinirMensagem(nome_Arquivo); 
            Mensagem.EnviarMensagem(2);
            
            new EnviarArquivo(socket, this.tamanhoMaximoByte, nome_Arquivo, true, this);   
            
        } else {
            System.out.println("Não foi possivel encontrar o arquivo para enviar!");
        }

    }

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        EnviarMensagem1();
    }

    public void DifinirArquivos(String arquivos){
        Texttextoarquivos.setText(arquivos);
    }

    public void EnviarMensagem1(){                 
        new Mensagem(socket);
        Mensagem.EnviarMensagem(1);
        Mensagem.ReceberMensagem();
        ObjetoRede objetoRede = Mensagem.GetObjetoRede();

        int index = 0;
        String textoDir = "";
        for(String item : objetoRede.GetItens()){
            textoDir = textoDir + item +" \n";
            System.out.println(item+ "  -  "+ objetoRede.GetTipoItens()[index]);
            index ++;
        }

        DifinirArquivos(textoDir);
    }

    public void DefTamanhoMaxBarraDownload(int tamanho){
        this.TamanhoMaxBarraDownload = tamanho;
    }

    public void DefTamanhoMaxBarraUpload(int tamanho){
        this.TamanhoMaxBarraUpload = tamanho;
    }

    public void AtualizarBarraDownload(int atual){
        // int porcentagem = (this.TamanhoMaxBarraDownload / atual);
        this.BarraCarregamentoDownload.setProgress(atual);
    }

    public void AtualizarBarraUpload(int atual){
        // int porcentagem = (this.TamanhoMaxBarraUpload / atual)*100;
        this.BarraCarregamentoUpload.setProgress(atual);
        // System.out.println(atual);
    }
}
