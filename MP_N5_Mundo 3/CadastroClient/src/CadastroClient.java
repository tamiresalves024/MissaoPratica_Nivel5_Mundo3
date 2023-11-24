

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Vector;
import model.Produtos;

public class CadastroClient {

    public static void main(String[] args) {
        try {

            Socket socket = new Socket("localhost", 4321);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("------------------------------");
            System.out.print("Login: ");
            String login = reader.readLine();
            System.out.print("Senha: ");
            String senha = reader.readLine();
            System.out.print("Comando do cliente: ");
            String mensagem = reader.readLine();
            out.writeObject(login);
            out.writeObject(senha);
            out.writeObject(mensagem);

            System.out.println(in.readObject());
            Object objetoRecebido = in.readObject();

            if (true) {
                Vector<Produtos> produtoslist = (Vector<Produtos>) objetoRecebido;
                for (Produtos produto : produtoslist) {
                    System.out.println(produto.getNomeProdutos());
                }
            }

            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
