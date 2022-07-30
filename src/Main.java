import formulario.AbrirFormulario;
import formulario.Formulario;

public class Main {
    public static void main(String[] args) {

        AbrirFormulario openForm = new AbrirFormulario();
        Formulario formulario = new Formulario();
        try {
            openForm.abrirFormulario();
            Formulario.getConectionPostgres();

        }catch (Exception e){
            System.out.println("Error= " + e);
        }
    }
}
