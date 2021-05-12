package dds.monedero.model;

import java.time.LocalDate;

public class Extraccion extends Movimiento {

  public Extraccion(LocalDate fecha, double monto) {
    this.fecha = fecha;
    this.monto = monto;
  }

  public boolean fueExtraido(LocalDate fecha) {
    return esDeLaFecha(fecha);
  }

  public void agregateA(Cuenta cuenta) {
    cuenta.setSaldo(calcularValor(cuenta));
    cuenta.agregarExtraccion(fecha, monto);
  }

  public double calcularValor(Cuenta cuenta) {
    return cuenta.getSaldo() - this.getMonto();
  }

  public boolean isDeposito() {
    return false;
  }
}
