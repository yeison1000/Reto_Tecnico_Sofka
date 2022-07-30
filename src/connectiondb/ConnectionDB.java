package connectiondb;

import java.sql.*;
import java.util.ArrayList;

public class ConnectionDB {
    private String m_db_connection_string = "";
    private String m_db_owner = "";
    private String m_usuario = "";
    private String m_password = "";
    private Connection conexion = null;

    public ConnectionDB(String db_connection_string, String db_owner, String usuario, String password) {
        this.m_db_connection_string = db_connection_string;
        this.m_db_owner = db_owner;
        this.m_usuario = usuario;
        this.m_password = password;

        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            System.out.println("error =" + e);
        }
    }

    //Se encarga de ingresar a la base de datos
    public boolean open(){
        try {
            if (conexion != null){
                if (!conexion.isClosed()){
                    conexion.close();
                }
                conexion = null;
            }

            conexion = DriverManager.getConnection(m_db_connection_string, m_usuario, m_password);
            System.out.println("Se conectó correctamente");
        }catch (Exception e){
            System.out.println("Error= " + e);
        }
        return false;
    }

    public boolean isOpen(){
        if (conexion != null){
            try {
                return !conexion.isClosed();
            }catch (Exception e){
                System.out.println("Error= " + e);
            }
        }
        return false;
    }

    public boolean getSQLRows(String SQLIn, ArrayList<ArrayList> registros, String accion, boolean show_err, long maxrows){
        synchronized (this){
            for (int y = 0; y < 2; y++) {
                try {
                    if (conexion == null){
                        open();
                    }
                    Statement statement = conexion.createStatement();

                    SQLIn = SQLIn.replace("#owner#", m_db_owner);

                    //state,emt.serQueryTimeout(120);
                    ResultSet cursor = statement.executeQuery(SQLIn);
                    ResultSetMetaData rsmd = cursor.getMetaData();

                    registros.clear();

                    int nr_columnas = rsmd.getColumnCount();
                    int nr_filas = 0;

                    ArrayList Tupla = new ArrayList();

                    for (int x = 0; x < nr_columnas; x++) {
                        Tupla.add(x, rsmd.getColumnName(x + 1));
                    }
                    registros.add(0, Tupla);

                    while (cursor.next()){
                        Tupla = new ArrayList();

                        for (int x = 0; x < nr_columnas; x++) {
                            Tupla.add(x, cursor.getObject(x + 1));
                        }
                        registros.add(Tupla);
                        if (nr_filas > maxrows){
                            break;
                        }
                        nr_filas++;
                    }
                    statement.close();
                    statement = null;
                    return true;
                }catch (SQLException e) {
                    try {
                        if (conexion.isClosed()) {
                            open();
                        }
                    } catch (SQLException ex) {}
                    if (y == 1) {
                        System.out.println("Error= " + e);
                    }
                }
            }
            return false;
        }
    }

    public boolean executeSQLStatement(String SQLIn, String a_accion){
        synchronized (this){
            try {
                if (conexion == null){
                    open();
                }
                Statement statement = conexion.createStatement();

                SQLIn = SQLIn.replace("#owner#", m_db_owner);

                boolean r = statement.execute(SQLIn);

                statement.close();
                statement = null;

                return r;
            }catch (SQLException e){
                System.out.println("Error= " + e);
            }

            return false;
        }
    }

    public String executeSQL(String SQLIn){
        synchronized (this){
            try {
                if (conexion == null){
                    open();
                }
                Statement statement = conexion.createStatement();

                SQLIn = SQLIn.replace("#owner#", m_db_owner);

                String r = String.valueOf(statement.execute(SQLIn));

                statement.close();
                statement = null;

                return r;
            }catch (SQLException e){
                System.out.println("Error= " + e);
            }

            return null;
        }
    }

    public boolean executeStoreProcedure(String SQLprocedure, Integer identificador, Integer servidor){
        synchronized (this){
            try {
                if (conexion == null){
                    open();
                }
                boolean r;
                CallableStatement stmt = conexion.prepareCall("{call " + SQLprocedure + "(?,?)}");
                stmt.setInt(1, identificador);
                stmt.setInt(2,servidor);

                r = stmt.execute();

                stmt.close();

                return  r;
            }catch (SQLException e){
                System.out.println("Error= " + e);
            }

            return false;
        }
    }

    //Cerrar la conexión y destruirla para que no quede nada almacenado
    public void close() {
        if (conexion != null) {
            try {
                conexion.close();
                conexion = null;
            } catch (Exception e) {
                System.out.println("Error= " + e);
            }
        }
    }
}
