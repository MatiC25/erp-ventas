# Diccionario de Datos

## 1. Entidades Financieras

### Tabla: CAJAS 
* **moneda:** Enum estricto. Valores permitidos: `['USD', 'ARS', 'EUR']`. No se permite NULL.
* **saldo_actual:** `BigDecimal`. Admite negativos (para representar deudas o sobregiros si la regla de negocio lo permite). Precisión: 2 decimales.
* **activa:** `Boolean`. Si es `false`, la caja no aparece en el selector de ventas, pero mantiene su historial.

### Tabla: OPERACIONES (TRANSACTIONS)
* **tipo_movimiento:** Enum.
    * `INGRESO`: Suma al saldo de la caja.
    * `EGRESO`: Resta al saldo de la caja.
    * `TRANSFERENCIA`: Requiere una operación par (un Egreso de caja A y un Ingreso en caja B).
* **monto:** `BigDecimal`. **Siempre positivo**. La matemática de suma/resta la define el `tipo_movimiento`, no el signo del número.
* **id_venta_cobro:** FK. Si no es NULL, esta operación fue generada automáticamente por una venta. No se debe editar manualmente.

## 2. Entidades Comerciales

### Tabla: VENTAS
* **estado:** Enum.
    * `PENDIENTE`: Venta iniciada pero no cobrada totalmente (Seña).
    * `PAGADO`: Venta cobrada al 100% y entregada.
    * `ANULADO`: Venta cancelada. Revierte los movimientos de caja asociados.
* **total_final:** Suma de los subtotales de los items. Debe coincidir con la suma de pagos en `VENTA_COBROS` si el estado es `PAGADO`.

### Tabla: VENTA_COBROS
* **cotizacion_aplicada:** `BigDecimal`. Es el valor de conversión utilizado *en ese momento*.
    * *Ejemplo:* Si el total es 100 USD y pagan en Pesos, aquí se guarda a cuánto tomaron el dólar (ej: 1200). Esto es crítico para reportes de rentabilidad.