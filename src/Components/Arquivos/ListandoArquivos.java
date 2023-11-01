package Components.Arquivos;

import java.io.File;

import Components.Rede.ObjetoRede;

public class ListandoArquivos {
    private static ObjetoRede ListaDir;
    private static String Diretorio;

    public ListandoArquivos(String DDiretorio){
        Diretorio = DDiretorio;
    }

    public static ObjetoRede ObterListar(){
        File pasta = new File(Diretorio);
        ListarTodosItensDentroDiretorios(pasta);
        return ListaDir;
    }

	public static void ListarTodosItensDentroDiretorios(File pasta) {
        int c = 0;
		for (File item : pasta.listFiles()) {
            c++;
		}

        // System.out.println(c);
        ListaDir = new ObjetoRede(1); 
        ListaDir.QuantidadeItens(c);
        ListaDir.Mensagem(Diretorio);

        c = 0;
		for (File item : pasta.listFiles()) {
            if(item.isFile()){
                ListaDir.addItem(c, item.getName(), "Arquivo");
            } else {
                ListaDir.addItem(c, item.getName(), "Diretorio");
            }
            c++;
		}
	}
}
