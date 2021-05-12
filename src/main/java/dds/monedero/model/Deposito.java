package dds.monedero.model;

import java.time.LocalDate;

public class Deposito extends Movimiento {

  public Deposito(LocalDate fecha, double monto) {
    this.fecha = fecha;
    this.monto = monto;
  }

  public boolean isDeposito() {
    return true;
  }
}
