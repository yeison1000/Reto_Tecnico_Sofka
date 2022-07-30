package formulario;

import connectiondb.ConnectionDB;
import naves.CrearNaves;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Formulario {
    private static ConnectionDB cn_postgres;
    JPanel formulario;
    private JComboBox slcListaNaves;
    private JLabel txtNaveSeleccionada;
    private JButton slcActivarNaves;
    private JButton btnSeleccionarNave;
    private JTextField txtNombreNave;
    private JTextField txtObjetivo;
    private JTextField txtFechaLanzamiento;
    private JTextField txtPeso;
    private JTextField txtEmpuje;
    private JTextField txtDistancia;
    private JButton btnAgregar;
    private JButton btnEliminar;
    private JButton btnMostrarBD;
    private JTable gridInformation;
    private JButton btnBuscarNaves;

    CrearNaves crearNaves = new CrearNaves();

    String [][] data ={{"null","null", "null", "null", "null", "null", "null", "null"}};
    String [] columnas = {"id","idNave","Nombre", "Empuje", "Objetivo", "Fecha", "Fuente Poder", "Tipo Transporte"};

    public Formulario() {

        slcActivarNaves.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JComboBox<String> cbox_nombres = new JComboBox();
                String [] nombre = new String[4];
                int c =1;

                ArrayList<ArrayList> nomb = new ArrayList();
                cbox_nombres.addItem("Seleccionar Nave");
                cn_postgres.getSQLRows("SELECT " + "\"NombreNave\"" + " FROM " +
                        "\"TiposNaves\"", nomb, "Nombres de tipos de naves", true,520);

                for (int i = 0; i < nombre.length; i++) {
                    nombre [i] = String.valueOf(nomb.get(c));
                    c++;

                    slcListaNaves.addItem(nombre[i]);
                }
            }
        });

        btnSeleccionarNave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtNaveSeleccionada.setText("" + slcListaNaves.getSelectedItem());
            }
        });

        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (txtNombreNave.getText().equals("")){
                    JOptionPane.showMessageDialog(null,"Campo nombre en blanco");
                }else if (txtObjetivo.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Campo objetivo en blanco");
                }else if (txtFechaLanzamiento.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Campo fecha en blanco");
                }else if (txtEmpuje.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Campo empuje en blanco");
                }else if (txtPeso.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Campo peso en blanco");
                }else if (txtDistancia.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Campo distancia en blanco");
                }else{
                    try {
                        int c = (Integer.parseInt(txtDistancia.getText()));
                        int c1 = (Integer.parseInt(txtPeso.getText()));
                        int c3 = (Integer.parseInt(txtEmpuje.getText()));

                        if (c >= 0  && c1 >= 0){
                            int idNave = Integer.parseInt(crearNaves.elegirNave(slcListaNaves.getSelectedItem()).toString());

                            System.out.println("Ingresa condición mayor a  00000000000000");
                            for (int i = 0; i < 4; i++) {
                                if ((i+1) == idNave){
                                    cn_postgres.executeSQLStatement("INSERT INTO \"Naves\" ( \"Nombre\", \"Empuje\"," +
                                            "\"Objetivo\", \"FuentePoder\", \"TipoTransporte\",  idnave, \"Fecha\")" +
                                            "VALUES ('" +
                                            txtNombreNave.getText() + "', '" +
                                            txtEmpuje.getText() + "', '" +
                                            txtObjetivo.getText() + "', '" +
                                            crearNaves.fuentePoder(Integer.parseInt(txtDistancia.getText())) + "', '" +
                                            crearNaves.tipoTransporte(Integer.parseInt(txtPeso.getText())) + "', " +
                                            idNave + ", '" + txtFechaLanzamiento.getText() + "');"
                                            ,"Crear nave en su respectiva tabla");

                                    String [][] newData = new String[data.length][8];

                                    newData[0][2] = txtNombreNave.getText();
                                    newData[0][3] = txtEmpuje.getText();
                                    newData[0][4] = txtObjetivo.getText();
                                    newData[0][6] = crearNaves.fuentePoder(Integer.parseInt(txtDistancia.getText()));
                                    newData[0][7] = crearNaves.tipoTransporte(Integer.parseInt(txtPeso.getText()));
                                    newData[0][1] = String.valueOf(idNave);
                                    newData[0][5] = txtFechaLanzamiento.getText();

                                    llenarTabla(newData, columnas);
                                    txtNombreNave.setText("");
                                    txtEmpuje.setText("");
                                    txtObjetivo.setText("");
                                    txtFechaLanzamiento.setText("");

                                }
                            }
                        }else {
                            int v = 1/(c - 1);
                            int v3 = 1/(c1 - 1);
                            int v1 = 1/c;
                            int v2 = 1/c1;
                        }
                    }catch (Exception ex){
                        JOptionPane.showMessageDialog(null,
                                "El dato ingresado en el campo distancia, peso o empuje no es valido o debes seleccionar nave");
                        txtDistancia.setText("");
                        txtPeso.setText("");
                    }
                }
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtNombreNave.setText("");
                txtEmpuje.setText("");
                txtObjetivo.setText("");
                txtFechaLanzamiento.setText("");
                txtDistancia.setText("");
                txtPeso.setText("");
            }
        });

        btnBuscarNaves.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        btnBuscarNaves.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<ArrayList> dataTotal = new ArrayList();

                int idNave = Integer.parseInt(crearNaves.elegirNave(slcListaNaves.getSelectedItem()).toString());

                cn_postgres.getSQLRows("SELECT * FROM \"Naves\" WHERE idnave = " + idNave, dataTotal, "Traer naves de base de datos", true, 500);

                String [][] newData = new String[dataTotal.size()-1][8];

                String nave = crearNaves.elegirNave(idNave);

                for (int i = 1; i <dataTotal.size(); i++) {
                    if (nave.equals(slcListaNaves.getSelectedItem())) {
                        newData[i - 1][0] = String.valueOf(dataTotal.get(i).get(0));
                        newData[i - 1][1] = String.valueOf(dataTotal.get(i).get(6));
                        newData[i - 1][2] = String.valueOf(dataTotal.get(i).get(1));
                        newData[i - 1][3] = String.valueOf(dataTotal.get(i).get(2));
                        newData[i - 1][4] = String.valueOf(dataTotal.get(i).get(3));
                        newData[i - 1][5] = String.valueOf(dataTotal.get(i).get(7));
                        newData[i - 1][6] = String.valueOf(dataTotal.get(i).get(4));
                        newData[i - 1][7] = String.valueOf(dataTotal.get(i).get(5));
                    }
                }
                llenarTabla(newData,columnas);
            }
        });

        btnMostrarBD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<ArrayList> dataTotal = new ArrayList();

                cn_postgres.getSQLRows("SELECT * FROM \"Naves\"", dataTotal, "Traer informacion base de datos", true, 500);

                String [][] newData = new String[dataTotal.size()-1][8];

                for (int i = 1; i <dataTotal.size(); i++) {


                    newData[i - 1][0] = String.valueOf(dataTotal.get(i).get(0));
                    newData[i - 1][1] = String.valueOf(dataTotal.get(i).get(6));
                    newData[i - 1][2] = String.valueOf(dataTotal.get(i).get(1));
                    newData[i - 1][3] = String.valueOf(dataTotal.get(i).get(2));
                    newData[i - 1][4] = String.valueOf(dataTotal.get(i).get(3));
                    newData[i - 1][5] = String.valueOf(dataTotal.get(i).get(7));
                    newData[i - 1][6] = String.valueOf(dataTotal.get(i).get(4));
                    newData[i - 1][7] = String.valueOf(dataTotal.get(i).get(5));
                }
                llenarTabla(newData,columnas);
            }
        });
    }

    public static void getConectionPostgres(){
        try {
            if (cn_postgres != null){
                cn_postgres.close();
                cn_postgres = null;
            }
            cn_postgres = new ConnectionDB("jdbc:postgresql://localhost/Naves", "", "postgres","1000");
            cn_postgres.open();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void llenarTabla (String [][] data, String [] columnas){
        gridInformation.setModel(new DefaultTableModel(data,columnas));
    }
}
