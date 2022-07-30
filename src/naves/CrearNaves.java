package naves;

public class  CrearNaves extends Nave implements ComportamientoNave {

    private int peso;

    private int distancia;

   public CrearNaves(){}

    public CrearNaves( int peso, int distancia) {
        this.peso = peso;
        this.distancia = distancia;
    }

    public Object elegirNave(Object tipoNave){
        if (tipoNave.equals("[NaveLanzadera]")){
            return 1;
        } else if(tipoNave.equals("[NaveNoTripulada]")){
            return 2;
        } else if(tipoNave.equals("[NaveTripulada]")) {
            return 3;
        } else{
            return 4;
        }
    }

    public String elegirNave(int idNave){
       if (idNave == 1){
           return "[NaveLanzadera]";
       }else if (idNave == 2){
           return "[NaveNoTripulada]";
       }else if (idNave == 3){
           return "[NaveTripulada]";
       }else {
           return "[NaveOvni]";
       }
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    @Override
    public String fuentePoder(int distancia) {

       if (distancia >= 3000){
           return "Paneles Solares";
       } else {
           return "Combustible";
       }
    }

    @Override
    public String tipoTransporte(int peso) {

       if (peso <= 1000) {
           return "Tripulada";
       }else {
           return "Carga";
       }
    }
}
