package dds.monedero.model;

import java.time.LocalDate;

public class Deposito extends Movimiento {

  public Deposito(LocalDate fecha, double monto) {
    this.fecha = fecha;
    this.monto = monto;
  }

  public boolean fueDepositado(LocalDate fecha) {
    return esDeLaFecha(fecha);
  }

  public void agregateA(Cuenta cuenta) {
    cuenta.setSaldo(calcularValor(cuenta));
    cuenta.agregarDeposito(fecha, monto);
  }

  public double calcularValor(Cuenta cuenta) {
    return cuenta.getSaldo() + this.getMonto();
  }

  public boolean isDeposito() {
    return true;
  }
}
