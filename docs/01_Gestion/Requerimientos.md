## 1. Introducción
El propósito del sistema es proveer una plataforma centralizada para la gestión de ventas, clientes y catálogo de productos para un negocio minorista de tecnología. El sistema reemplaza el uso de planillas de cálculo manuales como fuente de datos primaria, pasando a una base de datos relacional robusta, manteniendo la sincronización con Google Sheets para reportes externos.

## 2. Actores del Sistema
* **Administrador:** Tiene acceso total al sistema, gestión de usuarios, catálogo de productos y reportes financieros completos.
* **Vendedor:** Tiene acceso restringido a la carga de ventas, creación de clientes y visualización de su propio historial/dashboard.
* **Sistema Externo (Google Sheets):** Actor pasivo que recibe actualizaciones periódicas de las ventas.

## 3. Requerimientos Funcionales (RF)

### Módulo A: Autenticación y Seguridad
* **RF-002 - Login:** El sistema debe permitir el ingreso mediante email y contraseña encriptada.
* **RF-002 - RBAC:** El sistema debe restringir las funcionalidades según el rol (Admin vs. Vendedor).
    * *Vendedor:* Solo puede cargar ventas y ver sus métricas.
    * *Admin:* Puede editar precios bases y ver métricas globales.

### Módulo B: Gestión de Productos 
* **RF-003 (Listado):** El sistema debe mantener un catálogo con los UPC de los productos 
* **RF-004 (Sin Stock):** El sistema **NO** gestionará control de inventario (stock) en esta versión (scope cerrado).
	* Aunque manejamos STOCK de iphones usados que se pueden obtener al tomarlos como forma de pago, guardaremos el UPC y el IMEI de los mismos

### Módulo C: Gestión de Ventas (Core)
* **RF-005 (Nueva Venta):** El sistema debe permitir registrar una venta seleccionando un Cliente existente y agregando uno o más Productos.
	* La carga de la nueva venta puede ser:
		* Manual
		* Scanneando el código de barras UPC
* **RF-006 - Resumen:** Debemos mostrar un resumen con el total de la venta
	* Total: Cantidad * PrecioVenta
	* Profit: ( PrecioVenta - PrecioCosto ) * Cantidad
* **RF-007 (Métodos de Pago):** Se debe registrar el método de pago (Efectivo, Transferencia, USDT) junto a la caja Destino
* RF-008 - Exportación en PDF de la Nota de Compra: Debemos detallar el cliente, el número de factura, los productos comprados y el total abonado

### Módulo D: Gestión de Clientes (CRM Light)
* **RF-008 (Alta Rápida):** Permitir crear un cliente nuevo desde la misma pantalla de venta para no interrumpir el flujo (modal).
* **RF-009 (Historial):** Permitir visualizar el historial de compras de un cliente específico.

### Módulo E: Gestión Financiera y Tesorería (Nuevo Crítico)
* **RF-014 (Multi-Moneda):** El sistema debe soportar operaciones explícitas en **USD, ARS y EUR**. No se debe realizar conversión automática implícita; se guarda la moneda original de la transacción.
* **RF-015 (Billeteras/Cajas):** El sistema debe permitir crear múltiples contenedores de dinero (Ej: "Caja Fuerte Oficina", "Cuenta Banco Galicia", "Billetera Virtual USDT", "Caja Chica Pesos").
* **RF-016 (Movimientos/Libro Diario):** Se debe registrar cada entrada y salida de dinero.
    * *Tipos:* INGRESO (Venta, Aporte), EGRESO (Gasto, Retiro), TRANSFERENCIA (Movimiento entre cajas).
* **RF-017 (Link Venta-Caja):** Cuando se confirma una venta, el sistema debe generar automáticamente un **Movimiento de Ingreso** en la Billetera seleccionada.
* **RF-018 (Cierre de Caja):** El sistema debe permitir realizar un "Cierre de Caja" (Arqueo) por turno o día, calculando: *Saldo Inicial + Entradas - Salidas = Saldo Final Teórico*.

### Módulo F: Reportes e Integración
* **RF-010 (Dashboard en Vivo):** Mostrar gráficos de ventas del día y del mes en la pantalla principal.
* **RF-011 (Sync Sheets):** El sistema debe exportar periódicamente (o por evento) las nuevas ventas a una hoja de Google Sheets específica para uso del cliente.
* RF-012 - Carga de Gastos: Se debe permitir escribir un nuevo gasto en el sistema

## 4. Requerimientos No Funcionales (RNF)

* **RNF-001 (Disponibilidad):** El sistema debe estar disponible 24/7 (Deploy en Railway/Render con Health Checks).
* **RNF-002 (Usabilidad):** La interfaz debe soportar "Modo Oscuro" y ser responsive para uso básico en móviles.
* **RNF-003 (Performance):** La carga de la pantalla de "Nueva Venta" no debe superar los 2 segundos.
* **RNF-004 (Seguridad de Datos):** La base de datos no debe ser accesible públicamente; solo a través del Backend Java.

## 5. Reglas de Negocio (Business Rules)

* **RN-001:** No se pueden registrar ventas con monto total menor o igual a cero.