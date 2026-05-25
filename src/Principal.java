package biblioteca;

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Principal {
    private static ArrayList<Livro> colecaoLivros = new ArrayList<>();
    private static ArrayList<Usuario> listaUsuarios = new ArrayList<>();

    public static void main(String[] args) {
        inicializarBancoDadosMock();

        while (true) {
            String menu = "SISTEMA DE GESTÃO DE BIBLIOTECA\n\n" +
                    "1. Consultar Livros no Acervo\n" +
                    "2. Executar Empréstimo\n" +
                    "3. Executar Devolução\n" +
                    "4. Encerrar Sistema\n\n" +
                    "Selecione uma operação:";

            String entrada = JOptionPane.showInputDialog(null, menu, "Painel de Controle", JOptionPane.INFORMATION_MESSAGE);

            if (entrada == null || entrada.equals("4")) {
                JOptionPane.showMessageDialog(null, "Encerrando aplicação.");
                break;
            }

            switch (entrada) {
                case "1":
                    exibirAcervo();
                    break;
                case "2":
                    processarEmprestimo();
                    break;
                case "3":
                    processarDevolucao();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Operação inválida! Tente novamente.");
            }
        }
    }

    private static void inicializarBancoDadosMock() {
        colecaoLivros.add(new Livro(101, "Java: Como Programar", "Deitel"));
        colecaoLivros.add(new Livro(102, "Código Limpo", "Robert C. Martin"));
        colecaoLivros.add(new Livro(103, "Padrões de Projetos", "GoF"));

        listaUsuarios.add(new Aluno(1, "Dyenifer Silva (Estudante)"));
        listaUsuarios.add(new Professor(2, "Rony (Docente)"));
    }

    private static void exibirAcervo() {
        StringBuilder relatorio = new StringBuilder("--- ACERVO DE LIVROS ---\n\n");
        for (Livro livro : colecaoLivros) {
            String status = livro.isDisponivel() ? "DISPONÍVEL" : "EMPRESTADO";
            relatorio.append("ID: ").append(livro.getId())
                    .append(" | ").append(livro.getTitulo())
                    .append(" [").append(status).append("]\n");
        }
        JOptionPane.showMessageDialog(null, relatorio.toString());
    }

    private static void processarEmprestimo() {
        try {
            int idUsuario = Integer.parseInt(JOptionPane.showInputDialog("Informe o ID do Usuário (1 ou 2):"));
            int idLivro = Integer.parseInt(JOptionPane.showInputDialog("Informe o ID do Livro (101 a 103):"));

            Usuario usuarioSelecionado = null;
            for (Usuario u : listaUsuarios) {
                if (u.getId() == idUsuario) {
                    usuarioSelecionado = u;
                    break;
                }
            }

            if (usuarioSelecionado != null) {
                for (Livro livro : colecaoLivros) {
                    if (livro.getId() == idLivro) {
                        if (livro.isDisponivel()) {
                            livro.setDisponivel(false);

                            String comprovante = "=== COMPROVANTE DE EMPRÉSTIMO ===\n\n" +
                                    "Beneficiário: " + usuarioSelecionado.getNome() + "\n" +
                                    "Obra: " + livro.getTitulo() + "\n" +
                                    "Prazo de Devolução: " + usuarioSelecionado.getPrazoEmprestimo() + " dias.";

                            JOptionPane.showMessageDialog(null, comprovante);
                            return;
                        } else {
                            JOptionPane.showMessageDialog(null, "Alerta: O livro já se encontra emprestado.");
                            return;
                        }
                    }
                }
                JOptionPane.showMessageDialog(null, "Erro: Livro não cadastrado no acervo.");
            } else {
                JOptionPane.showMessageDialog(null, "Erro: Usuário não localizado.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Erro: Entrada inválida. Digite apenas números.");
        }
    }

    private static void processarDevolucao() {
        try {
            int idLivro = Integer.parseInt(JOptionPane.showInputDialog("Informe o ID do Livro para devolução:"));

            for (Livro livro : colecaoLivros) {
                if (livro.getId() == idLivro) {
                    if (!livro.isDisponivel()) {
                        livro.setDisponivel(true);
                        JOptionPane.showMessageDialog(null, "Sucesso: O livro \"" + livro.getTitulo() + "\" foi devolvido ao acervo.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Aviso: Este livro já consta como disponível.");
                    }
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Erro: Livro não localizado.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Erro: Entrada inválida. Digite apenas números.");
        }
    }
}