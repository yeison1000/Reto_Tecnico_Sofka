package formulario;

import javax.swing.*;

public class AbrirFormulario {
    static JFrame form = new JFrame("INVENTARIO NAVES");
    public void abrirFormulario() {
        form.setContentPane(new Formulario().formulario);
        form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        form.pack();
        form.setSize(800,500);
        form.setVisible(true);
    }
}
