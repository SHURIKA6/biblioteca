package biblioteca;

public class Professor extends Usuario {

    public Professor(int id, String nome) {
        super(id, nome);
    }

    @Override
    public int getPrazoEmprestimo() {
        return 14; // Professores têm 14 dias de prazo
    }
}