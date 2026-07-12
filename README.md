# LlanquihueTour

Sistema en Java para la agencia turística **Llanquihue Tour**: gestiona guías,
vehículos, colaboradores externos, tours, clientes y servicios turísticos
desde una interfaz gráfica de escritorio (`JFrame`).

> Desarrollo Orientado a Objetos I — Duoc UC

---

## Descripción

La aplicación carga datos desde archivos de texto (`guias.txt`, `tours.txt`,
`clientes.txt`, `inscripciones.txt`), los organiza en colecciones y los
muestra en una ventana con menús. El código está organizado en paquetes
(`model`, `data`, `service`, `util`, `ui`) y aplica composición, herencia,
interfaces, polimorfismo, `instanceof`, validaciones y manejo de excepciones.

---

## Estructura del proyecto

```
LlanquihueTour/
├── resources/          → guias.txt, tours.txt, clientes.txt, inscripciones.txt
└── src/
    ├── model/          → Clases de dominio
    ├── data/           → Carga de archivos y colecciones (GestorDatos, GestorServicios, GestorEntidades)
    ├── service/        → GestorTours
    ├── util/           → Validador y excepciones propias
    └── ui/             → VentanaPrincipal (JFrame) y sus paneles
```

---

## Clases e interfaces

| Paquete   | Clase                                                          | Rol |
|-----------|------------------------------------------------------------------|-----|
| `model`   | `Persona`                                                        | Clase base de personas (tiene una `Direccion`) |
| `model`   | `GuiaTuristico`, `Cliente`                                       | Heredan de `Persona`. `GuiaTuristico` además implementa `Registrable` |
| `model`   | `Direccion`                                                      | Clase compuesta |
| `model`   | `Tour`                                                           | Tiene un `GuiaTuristico` y una lista de `Cliente` inscritos |
| `model`   | `ServicioTuristico`                                              | Superclase de servicios turísticos |
| `model`   | `RutaGastronomica`, `PaseoLacustre`, `ExcursionCultural`         | Subclases de `ServicioTuristico` |
| `model`   | `Registrable`                                                    | Interfaz: contrato `mostrarResumen()` |
| `model`   | `RecursoAgencia`                                                 | Superclase de recursos registrables |
| `model`   | `Vehiculo`, `ColaboradorExterno`                                 | Heredan de `RecursoAgencia` e implementan `Registrable` |
| `data`    | `GestorDatos`                                                    | Lee archivos y crea objetos |
| `data`    | `GestorServicios`                                                | Colección `List<ServicioTuristico>` |
| `data`    | `GestorEntidades`                                                | Colección `List<Registrable>`; usa `instanceof` para diferenciar por tipo |
| `service` | `GestorTours`                                                    | Buscar y filtrar tours |
| `util`    | `Validador`, `RutInvalidoException`, `FormatoArchivoInvalidoException` | Validación y excepciones propias |
| `ui`      | `VentanaPrincipal`                                               | Ventana única (`JFrame` + `CardLayout`) |
| `ui`      | `PanelMenuPrincipal`, `PanelMenuEntidades`, `PanelResultado`     | Menús y vista de resultados |
| `ui`      | `PanelFormularioGuia`, `PanelFormularioVehiculo`, `PanelFormularioColaborador` | Formularios de registro (heredan de `PanelFormularioBase`) |

### Composición

- `Persona` **tiene una** `Direccion`.
- `Tour` **tiene un** `GuiaTuristico` y una lista de `Cliente` inscritos.

### Herencia

```
Persona                  ServicioTuristico          RecursoAgencia
├── GuiaTuristico         ├── RutaGastronomica        ├── Vehiculo
└── Cliente               ├── PaseoLacustre           └── ColaboradorExterno
                          └── ExcursionCultural
```

### Polimorfismo

`ServicioTuristico` define `mostrarInformacion()`, y cada subclase lo
sobrescribe con `@Override`. `GestorServicios` recorre `List<ServicioTuristico>`
con `for-each` y cada objeto ejecuta su propia versión del método.

---

## Interfaz Registrable

`GuiaTuristico`, `Vehiculo` y `ColaboradorExterno` vienen de **dos jerarquías
distintas** (`Persona` y `RecursoAgencia`) pero implementan la misma interfaz:

```java
public interface Registrable {
    String mostrarResumen();
}
```

`GestorEntidades` los guarda a todos en una sola `List<Registrable>` y usa
`instanceof` para mostrar un detalle distinto según el tipo real de cada
objeto (idiomas de un guía, capacidad de un vehículo, empresa de un
colaborador).

`GuiaTuristico` es una única clase: la misma que se carga desde `guias.txt`,
se asigna a un `Tour`, y se gestiona en la colección `Registrable`.

---

## GUI

La ventana (`VentanaPrincipal`, un `JFrame` con `CardLayout`) da acceso a
Gestión, Sistema de Tours y Servicios Turísticos sin abrir ventanas nuevas.
Los formularios de registro validan cada campo mientras se escribe y no
piden re-ingresar los datos si uno de los campos queda mal.

---

## Formato de los archivos

**guias.txt** (12 campos):
```
nombre;apellido;rut;correo;calle;numero;ciudad;region;especialidad;idiomas;aniosExperiencia;disponible
```

**tours.txt** (6 campos; el último es el RUT del guía asignado):
```
nombre;tipo;destino;precio;cupos;rutGuia
```

**clientes.txt** (10 campos):
```
nombre;apellido;rut;correo;calle;numero;ciudad;region;nacionalidad;tipoTurismo
```

**inscripciones.txt** (2 campos):
```
rutCliente;nombreTour
```

Las líneas mal formadas se omiten y quedan registradas en
`GestorDatos.getAvisos()`, que `Main` muestra al iniciar.

---

## Cómo ejecutar

### Desde IntelliJ IDEA

1. Abrir la carpeta del proyecto.
2. Verificar el SDK de Java (probado con **Java 21**).
3. Ejecutar la clase `Main` (`src/ui/Main.java`).

### Desde terminal

```bash
javac -encoding UTF-8 -d out -sourcepath src src/ui/Main.java
java -cp out ui.Main
```

> Ejecutar desde la raíz del proyecto, para que las rutas `resources/*.txt` se resuelvan correctamente.

---

## Autora

**Kamila Villablanca**
Desarrollo Orientado a Objetos I
Duoc UC — 2026
