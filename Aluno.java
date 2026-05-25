package biblioteca;

public class Aluno extends Usuario {

    public Aluno(int id, String nome) {
        super(id, nome);
    }

    @Override
    public int getPrazoEmprestimo() {
        return 7;
    }
}