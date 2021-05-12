package dds.monedero.model;

import java.time.LocalDate;

public abstract class Movimiento {
  protected LocalDate fecha;
  //En ningún lenguaje de programación usen jamás doubles para modelar dinero en el mundo real
  //siempre usen numeros de precision arbitraria, como BigDecimal en Java y similares
  protected double monto;

  public double getMonto() {
    return monto;
  }

  public LocalDate getFecha() {
    return fecha;
  }

  public boolean esDeLaFecha(LocalDate fecha) {
    return this.getFecha().equals(fecha);
  }

  public boolean extraccionDeFecha(LocalDate fecha) {
    return !this.isDeposito() && this.esDeLaFecha(fecha);
  }

  public boolean depositoDeHoy() {
    return this.isDeposito() && this.esDeLaFecha(LocalDate.now());
  }

  public abstract boolean isDeposito();

}
