package Components.Arquivos;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ByteArquivos {
    public static byte[] JuntarBytes(Arquivo[] Arquivos, InfoArquivo infoArquivo){
        ByteArrayOutputStream ArrayBytes = new ByteArrayOutputStream();
        for(int i = 0; i < infoArquivo.getNumeroPacotes(); i++){//para fazer um loop utilizando o numero de pacotes
            try {
                ArrayBytes.write(Arquivos[i].getConteudo());//escrevendo o pacote de acordo com a posição dele que esta seguindo o ID do pacote, no array, assim ele não vai ficar fora de ordem
                // System.out.println("Loop : " + i + " Sendo adicionado o pacote Numero : " + Arquivos[i].getNumeroPacote());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ArrayBytes.toByteArray();//retornando o byte[] estruturado
    }

    public static byte[] BytesArquivo(Arquivo arquivo){
        try {
            ByteArrayOutputStream BAOS = new ByteArrayOutputStream();//criando um array de byte para a saida de Stream
            ObjectOutputStream OOS = new ObjectOutputStream(BAOS); // criando um Objeto de saida stream com o ByteArrayOutputStream
            OOS.writeObject(arquivo); //Inserindo o arquivo dentro do Objeto de saida
            BAOS.toByteArray();//pegando os bytes que foram gerando apartir desse arquivo inserido em ObjectOutputStream.writeObject()
            return BAOS.toByteArray();//Arqui sera um metodo para obter esse valor mais facilmente ja q ele esta dentro de um try/catch {metodo BytesArquivo}

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;//para informar se der algum erro dentro do try/catch, ele ira retornar null
    }



}
