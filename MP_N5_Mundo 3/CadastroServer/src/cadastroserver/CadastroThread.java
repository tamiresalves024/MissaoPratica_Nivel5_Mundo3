package cadastroserver;

import controller.MovimentosJpaController;
import controller.UsuariosJpaController;
import controller.ProdutosJpaController;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Movimentos;
import model.Produtos;

public class CadastroThread extends Thread {

    private final ProdutosJpaController ctrlProd;
    private final UsuariosJpaController ctrlUsu;
    private final MovimentosJpaController ctrlMov;
    private final Socket s1;

    public CadastroThread(ProdutosJpaController ctrlProd, UsuariosJpaController ctrlUsu, MovimentosJpaController ctrlMov, Socket socket) {
        this.ctrlProd = ctrlProd;
        this.ctrlUsu = ctrlUsu;
        this.ctrlMov = ctrlMov;
        this.s1 = socket;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(s1.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(s1.getInputStream());

            String login = (String) in.readObject();
            String senha = (String) in.readObject();
            
            if (isValidUser(login, senha)) {
                enviarResposta(out,"Login Correto!");
            } else {
                enviarResposta(out, "Login Incorreto! saindo...");
                s1.close();
            }
            enviarResposta(out,"Menu:");
            enviarResposta(out,"L - Lista todos os Produtos");
            enviarResposta(out,"2 - Realizar Entrada");
            enviarResposta(out,"3 - Realizar Saída");
            enviarResposta(out,"X - Finalizar");
            enviarResposta(out,"Escolha uma opção: ");
            String opcao = (String) in.readObject();

            switch (opcao) {
                case "L":
                    enviarProdutos(out);
                    break;
                case "X":
                    break;              
                case "E":
                    realizarEntrada(out, in);
                    break;
                case "S":
                    realizarSaida(out, in);
                    break;
                default:                   
                    enviarResposta(out, "Opção inválida");
                    break;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(CadastroThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void enviarResposta(ObjectOutputStream out, String resposta) throws IOException {
        out.writeObject(resposta);
    }

    private boolean isValidUser(String login, String senha) {
        return ctrlUsu.findUsuario(login, senha) != null;

    }

    private void enviarProdutos(ObjectOutputStream out) throws IOException {
        List<Produtos> produtos = ctrlProd.findProdutosEntities();
        out.writeObject(produtos);
    }

    private void realizarEntrada(ObjectOutputStream out, ObjectInputStream in) throws IOException, ClassNotFoundException, Exception {
        int idPessoa = (int) in.readObject();
        int idProduto = (int) in.readObject();
        int quantidade = (int) in.readObject();
        BigDecimal valorUnitario = (BigDecimal) in.readObject();

        Movimentos movimento = new Movimentos();
        movimento.setOperadorID(ctrlUsu.findUsuarioById(idPessoa));
        movimento.setProdutoID(ctrlProd.getProdutoById(idProduto));
        movimento.setQuantidadeMovimento(quantidade);
        movimento.setPreçoMovimento(valorUnitario);
        movimento.setMovimentacao("E");

        ctrlMov.create(movimento);

      
        Produtos produto = ctrlProd.getProdutoById(idProduto);
        produto.setQuantidadeProdutos(produto.getQuantidadeProdutos() + quantidade);
        ctrlProd.edit(produto);

        out.writeObject("Entrada registrada com sucesso!");
    }

    private void realizarSaida(ObjectOutputStream out, ObjectInputStream in) throws IOException, ClassNotFoundException, Exception {
        int idPessoa = (int) in.readObject();
        int idProduto = (int) in.readObject();
        int quantidade = (int) in.readObject();
        BigDecimal valorUnitario = (BigDecimal) in.readObject();

        Movimentos movimento = new Movimentos();
        movimento.setOperadorID(ctrlUsu.findUsuarioById(idPessoa));
        movimento.setProdutoID(ctrlProd.getProdutoById(idProduto));
        movimento.setQuantidadeMovimento(quantidade);
        movimento.setPreçoMovimento(valorUnitario);
        movimento.setMovimentacao("S");

        ctrlMov.create(movimento);

      
        Produtos produto = ctrlProd.getProdutoById(idProduto);
        produto.setQuantidadeProdutos(produto.getQuantidadeProdutos() - quantidade);
        ctrlProd.edit(produto);

        out.writeObject("Saída registrada com sucesso!");
    }
}
