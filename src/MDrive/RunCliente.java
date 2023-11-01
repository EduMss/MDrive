package MDrive;
import Components.Client.*;
import Components.Server.Server;

// public class RunCliente {
//     public static void main(String[] args) throws Exception {
//         Cliente cliente = new Cliente();
//     }
// }

import JanelaControles.JanelaControles;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class RunCliente extends Application {
    public static Cliente cliente;
    public static void main(String[] args) throws Exception {
        launch();
    }
 
    @Override
    public void start(Stage primaryStage) throws Exception {
        // FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("janela.fxml"));
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("janela MDrive.fxml"));
            Parent root = fxmlLoader.load();
            Scene tela = new Scene(root);
            
            primaryStage.setTitle("Janelinha 123");
            primaryStage.setScene(tela); 
            primaryStage.show();    
            
        // cliente = new Cliente();
        // JanelaControles controles = fxmlLoader.getController();
        // cliente.DefControler(controles);
        // cliente.EnviarMensagem1();

    }
}
