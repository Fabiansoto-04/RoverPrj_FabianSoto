import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Rover
{
    private String codigo;
    private String nombrePila;
    private double potenciaInicial;
    private double potenciaActual;
    private int posicionInicialX;
    private int posicionInicialY;
    private int posicionActualX;
    private int posicionActualY;
    private int recargasRealizadas;
    private int contadorDetecciones;
    private List<List<String>> mandatosRealizados;
    private List<List<String>> mandatosNoRealizados;
    
    //constantes
    public static final int RECARGAS_MAXIMAS = 5;
    public static final double COSTO_DETECCION = 0.25;
    public static final double COSTO_MOVIMIENTO = 0.5;
    
    //
    private static int id = 0;
    private static int totalRoversCreados = 0;
    
    //Constructores
    public Rover(String nombrePila) {
        this(nombrePila,100.0);
    }
    
    public Rover(String nombrePropio, double potenciaInicial) {
        this.nombrePila = nombrePropio;
        this.potenciaInicial = potenciaInicial;
        this.potenciaActual = potenciaInicial;
        this.posicionInicialX = 0;
        this.posicionInicialY = 0;
        this.posicionActualX = 0;
        this.posicionActualY = 0;
        this.recargasRealizadas = 0;
        this.contadorDetecciones = 0;
        this.mandatosRealizados = new ArrayList<>();
        this.mandatosNoRealizados = new ArrayList<>();
        // Generar código único
        id++;
        totalRoversCreados++;
        this.codigo = "Ro-" + id;
    }
    
    //Metodos publicos
    public void moverArriba() {
        if (verificarPotencia()) {
            if (!detectarFugaCalor()) {
                posicionActualY += 1;
                potenciaActual -= COSTO_MOVIMIENTO;
                registrarMandato("Mover Arriba", "Posible");
            } else {
                registrarMandato("Mover Arriba", "No posible: fuga detectada");
            }
        } else {
            registrarMandato("Mover Arriba", "No posible: potencia insuficiente");
        }
    } 
    
     public void moverAbajo() {
        if (verificarPotencia()) {
            if (!detectarFugaCalor()) {
                posicionActualY -= 1;
                potenciaActual -= COSTO_MOVIMIENTO;
                registrarMandato("Mover Abajo", "Posible");
            } else {
                registrarMandato("Mover Abajo", "No posible: fuga detectada");
            }
        } else {
            registrarMandato("Mover Abajo", "No posible: potencia insuficiente");
        }
    }
    
     public void moverDerecha() {
        if (verificarPotencia()) {
            if (!detectarFugaCalor()) {
                posicionActualX += 1;
                potenciaActual -= COSTO_MOVIMIENTO;
                registrarMandato("Mover Derecha", "Posible");
            } else {
                registrarMandato("Mover Derecha", "No posible: fuga detectada");
            }
        } else {
            registrarMandato("Mover Derecha", "No posible: potencia insuficiente");
        }
    }
    
    public void moverIzquierda() {
        if (verificarPotencia()) {
            if (!detectarFugaCalor()) {
                posicionActualX -= 1;
                potenciaActual -= COSTO_MOVIMIENTO;
                registrarMandato("Mover Izquierda", "Posible");
            } else {
                registrarMandato("Mover Izquierda", "No posible: fuga detectada");
            }
        } else {
            registrarMandato("Mover Izquierda", "No posible: potencia insuficiente");
        }
    }
    
    public String posicionActual() {
     return "Posición actual (x,y): " + posicionActualX + ", " + posicionActualY;
    }
    
    public double getPotenciaActual() {
        return potenciaActual;
    }
    
     public void recargarUnidadesPotencia(double cantidad) {
        if (validarRecarga()) {
            potenciaActual += cantidad;
            recargasRealizadas++;
            registrarMandato("Recarga (" + cantidad + ")", "Posible");
        } else {
            registrarMandato("Recarga (" + cantidad + ")", "No posible: recargas agotadas");
        }
    }
    
    // Metodos privados
    private boolean detectarFugaCalor() {
        contadorDetecciones++;
        potenciaActual -= COSTO_DETECCION;
        // Generar número aleatorio entre 0 y 1
        Random random = new Random();
        double valor = random.nextDouble();
        return valor >= 0.5;
    }
    
    private boolean verificarPotencia() {
        return potenciaActual >= (COSTO_MOVIMIENTO + COSTO_DETECCION);
    }
    
     private boolean validarRecarga() {
        return recargasRealizadas < RECARGAS_MAXIMAS;
    }
    
    
    private void registrarMandato(String tipoMandato, String estatusMandato) {
        List<String> mandato = new ArrayList<>();
        mandato.add(tipoMandato);
        mandato.add(estatusMandato);
        mandato.add(obtenerFechaHoraActual());
        
        if ("Posible".equals(estatusMandato)) {
            mandatosRealizados.add(mandato);
        } else {
            mandatosNoRealizados.add(mandato);
        }
    }
    
    private String obtenerFechaHoraActual() {
        Date fecha = new Date();
        DateFormat formato = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        return formato.format(fecha);
    }
}
