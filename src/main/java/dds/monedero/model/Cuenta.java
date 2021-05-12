package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cuenta {

  private double saldo = 0;
  private double limiteExtraccion = 1000;
  private int depositosMaximos = 3;
  private List<Movimiento> movimientos = new ArrayList<>();

  public Cuenta() {
    saldo = 0;
  }

  public Cuenta(double montoInicial) {
    saldo = montoInicial;
  }

  public void setMovimientos(List<Movimiento> movimientos) {
    this.movimientos = movimientos;
  }

  public void poner(double cuanto) {
    validarMontoPositivo(cuanto);
    validarCantidadDepositos();

    new Deposito(LocalDate.now(), cuanto).agregateA(this);
  }

  public void sacar(double cuanto) {
    validarMontoPositivo(cuanto);
    validarMontoExtraccionMenorASaldo(cuanto);
    validarMontoExtraccionMenorALimite(cuanto);

    new Extraccion(LocalDate.now(), cuanto).agregateA(this);
  }

  public double montoExtraidoHoy() {
    return getMontoExtraidoA(LocalDate.now());
  }

  public long cantidadDepositosHoy() {
    return this.getMovimientos().stream().filter(movimiento -> movimiento.isDeposito()).count();
  }

  public void validarMontoPositivo(double monto) {
    if (monto <= 0) {
      throw new MontoNegativoException(monto + ": El monto a ingresar debe ser un valor positivo.");
    }
  }

  public void validarCantidadDepositos() {
    if (this.cantidadDepositosHoy() >= this.getDepositosMaximos()) {
      throw new MaximaCantidadDepositosException("Ya excedio los " + this.getDepositosMaximos() + " depositos diarios");
    }
  }

  public void validarMontoExtraccionMenorASaldo(double monto) {
    if (this.getSaldo() - monto < 0) {
      throw new SaldoMenorException("No puede sacar mas de " + this.getSaldo() + " $");
    }
  }

  public void validarMontoExtraccionMenorALimite(double monto) {
    double limite = this.getLimiteExtraccion() - this.montoExtraidoHoy();
    if (monto > limite) {
      throw new MaximoExtraccionDiarioException("No puede extraer mas de $ " + this.getLimiteExtraccion()
          + " diarios, límite: " + limite);
    }
  }

  public void agregarExtraccion(LocalDate fecha, double cuanto) {
    Extraccion extraccion = new Extraccion(fecha, cuanto);
    movimientos.add(extraccion);
  }

  public void agregarDeposito(LocalDate fecha, double cuanto) {
    Deposito deposito = new Deposito(fecha, cuanto);
    movimientos.add(deposito);
  }

  public double getMontoExtraidoA(LocalDate fecha) {
    return getMovimientos().stream()
        .filter(movimiento -> movimiento.extraccionDeFecha(fecha))
        .mapToDouble(Movimiento::getMonto)
        .sum();
  }

  public List<Movimiento> getMovimientos() {
    return movimientos;
  }

  public double getSaldo() {
    return saldo;
  }

  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }

  public int getDepositosMaximos() {
    return this.depositosMaximos;
  }

  public double getLimiteExtraccion() {
    return this.limiteExtraccion;
  }

}
