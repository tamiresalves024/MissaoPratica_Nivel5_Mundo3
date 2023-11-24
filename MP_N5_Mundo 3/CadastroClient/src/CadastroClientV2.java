import java.io.*;
import java.math.BigDecimal;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import javax.swing.JDialog;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import model.Produtos;

public class CadastroClientV2 {

    public static void main(String[] args) throws ClassNotFoundException {
        try {
            Socket socket = new Socket("localhost", 4321);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Scanner scanner = new Scanner(System.in);

            SaidaFrame saidaFrame = new SaidaFrame();
            SwingUtilities.invokeLater(() -> {
                saidaFrame.setVisible(true);
            });

            Thread messageThread = new Thread(new ThreadClient(in, saidaFrame.texto));
            messageThread.start();

            System.out.print("Login: ");
            String login = scanner.nextLine();
            System.out.print("Senha: ");
            String senha = scanner.nextLine();

            out.writeObject(login);
            out.writeObject(senha);

            boolean continuar = true;
            while (continuar) {
                String opcao = scanner.nextLine().toUpperCase();
                switch (opcao) {
                    case "X":
                        out.writeObject("X");
                        continuar = false;
                        break;

                    case "L":
                        out.writeObject("L");
                        break;

                    case "2":
                        realizarMovimentacao(out, "E", scanner);
                        break;

                    case "3":
                        realizarMovimentacao(out, "S", scanner);
                        break;

                    default:
                        out.writeObject("Opção inválida");
                        break;
                }
            }

            out.close();
            in.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void realizarMovimentacao(ObjectOutputStream out, String movimentacao, Scanner scanner) throws IOException {
        System.out.print("ID da Pessoa: ");
        int idPessoa = scanner.nextInt();
        System.out.print("ID do Produto: ");
        int idProduto = scanner.nextInt();
        System.out.print("Quantidade: ");
        int quantidade = scanner.nextInt();
        System.out.print("Valor Unitário: ");
        BigDecimal valorUnitario = scanner.nextBigDecimal();

        out.writeObject(movimentacao);
        out.writeObject(idPessoa);
        out.writeObject(idProduto);
        out.writeObject(quantidade);
        out.writeObject(valorUnitario);
    }

    public static class SaidaFrame extends JDialog {
        public JTextArea texto;

        public SaidaFrame() {
            super();
            texto = new JTextArea();
            setBounds(100, 100, 400, 300);
            setModal(false);
            add(texto);
        }
    }

    public static class ThreadClient extends Thread {
        private ObjectInputStream entrada;
        private JTextArea textArea;

        public ThreadClient(ObjectInputStream entrada, JTextArea textArea) {
            this.entrada = entrada;
            this.textArea = textArea;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    Object receivedObject = entrada.readObject();

                    if (receivedObject instanceof String) {
                        String message = (String) receivedObject;
                        SwingUtilities.invokeLater(() -> {
                            textArea.append(message + "\n");
                        });
                    } else if (receivedObject instanceof List) {
                        List<Produtos> produtos = (List<Produtos>) receivedObject;
                        SwingUtilities.invokeLater(() -> {
                            for (Produtos produto : produtos) {
                                textArea.append("Nome: " + produto.getNomeProdutos() + ", Quantidade: " + produto.getQuantidadeProdutos() + "\n");
                            }
                        });
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
