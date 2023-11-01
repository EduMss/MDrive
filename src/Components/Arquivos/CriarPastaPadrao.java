package Components.Arquivos;

import java.io.File;

public class CriarPastaPadrao {
    public CriarPastaPadrao(){
        String dir = "Downloads";

        File diretorio = new File(dir); // ajfilho é uma pasta! 
        if (!diretorio.exists()) { 
            diretorio.mkdirs(); //mkdir() cria somente um diretório, mkdirs() cria diretórios e subdiretórios. 
        } else { 
            System.out.println("Diretório já existente");
        }
    }
}
