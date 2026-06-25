# LlanquihueTour

Sistema modular en Java para la agencia turística **Llanquihue Tour**. Carga
guías, tours, clientes e inscripciones desde archivos de texto, los almacena en
colecciones y muestra, busca y filtra los resultados por consola. Además modela
los distintos tipos de servicios turísticos mediante una jerarquía de clases con
herencia.

> Desarrollo Orientado a Objetos I — Duoc UC

---

## Descripción

La aplicación lee cuatro archivos de datos (`guias.txt`, `tours.txt`,
`clientes.txt`, `inscripciones.txt`), convierte cada línea en objetos del modelo
y los guarda en colecciones genéricas. La lógica está separada por
responsabilidades en paquetes (`model`, `data`, `service`, `util`, `ui`),
aplicando composición entre clases, herencia, validaciones y manejo de
excepciones.

---

## Estructura del proyecto

```
LlanquihueTour/
├── resources/              → Archivos de datos externos
│   ├── guias.txt
│   ├── tours.txt
│   ├── clientes.txt
│   └── inscripciones.txt
└── src/
    ├── model/              → Clases de dominio
    │   ├── Persona.java             → Clase base (tiene una Direccion)
    │   ├── Guia.java                → Hereda de Persona
    │   ├── Cliente.java             → Hereda de Persona
    │   ├── Direccion.java           → Clase compuesta
    │   ├── Tour.java                → Tiene un Guia y una lista de Cliente inscritos
    │   ├── ServicioTuristico.java   → Superclase: nombre, duracionHoras
    │   ├── RutaGastronomica.java    → Hereda de ServicioTuristico (+ numeroDeParadas)
    │   ├── PaseoLacustre.java       → Hereda de ServicioTuristico (+ tipoEmbarcacion)
    │   └── ExcursionCultural.java   → Hereda de ServicioTuristico (+ lugarHistorico)
    ├── data/              → Lectura de archivos y construcción de objetos
    │   ├── GestorDatos.java         → Carga guías, tours, clientes e inscripciones
    │   └── GestorServicios.java     → Crea los servicios turísticos de prueba
    ├── service/           → Operaciones de negocio (mostrar, buscar, filtrar)
    │   └── GestorTours.java
    ├── util/              → Librería propia reutilizable
    │   ├── Validador.java
    │   ├── RutInvalidoException.java
    │   └── FormatoArchivoInvalidoException.java
    └── ui/                → Punto de entrada (coordina y muestra resultados)
        └── Main.java
```

---

## Paquetes y clases

| Paquete   | Clase                            | Responsabilidad |
|-----------|----------------------------------|-----------------|
| `model`   | `Persona`                        | Clase base; **tiene una** `Direccion` |
| `model`   | `Guia`, `Cliente`                | Heredan de `Persona` |
| `model`   | `Direccion`                      | Dirección física (clase compuesta) |
| `model`   | `Tour`                           | **Tiene un** `Guia` y **tiene una** lista de `Cliente` inscritos |
| `model`   | `ServicioTuristico`              | Superclase: atributos comunes `nombre` y `duracionHoras` |
| `model`   | `RutaGastronomica`               | **Es un** `ServicioTuristico` (+ `numeroDeParadas`) |
| `model`   | `PaseoLacustre`                  | **Es un** `ServicioTuristico` (+ `tipoEmbarcacion`) |
| `model`   | `ExcursionCultural`              | **Es un** `ServicioTuristico` (+ `lugarHistorico`) |
| `data`    | `GestorDatos`                    | Lee archivos y crea objetos |
| `data`    | `GestorServicios`                | Crea los servicios turísticos de prueba |
| `service` | `GestorTours`                    | Mostrar, buscar y filtrar la colección |
| `util`    | `Validador`                      | Validación reutilizable de campos (método `static`) |
| `util`    | `RutInvalidoException`           | Excepción personalizada (RUT inválido) |
| `util`    | `FormatoArchivoInvalidoException`| Excepción personalizada (línea mal formada) |
| `ui`      | `Main`                           | Coordina la ejecución y muestra los resultados |

### Composición entre clases (*tiene un/a*)

- `Persona` **tiene una** `Direccion`.
- `Tour` **tiene un** `Guia` (y ese guía, a su vez, tiene su `Direccion`).
- `Tour` **tiene una** lista (`ArrayList`) de `Cliente` inscritos.

### Herencia (*es un/a*)

```
Persona                       ServicioTuristico (superclase)
├── Guia                      ├── RutaGastronomica   (+ numeroDeParadas)
└── Cliente                   ├── PaseoLacustre      (+ tipoEmbarcacion)
                              └── ExcursionCultural  (+ lugarHistorico)
```

Lo común vive una sola vez en la superclase y lo específico en cada subclase.
Cada subclase llama a `super(...)` para inicializar lo heredado y sobrescribe
`toString()` con `@Override`, reutilizando `super.toString()`.

---

## Formato de los archivos

**guias.txt** (12 campos separados por `;`):
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

**inscripciones.txt** (2 campos; vincula un cliente a un tour):
```
rutCliente;nombreTour
```

Si una línea viene mal formada (campos incompletos, número inválido, RUT con mal
formato, guía/tour/cliente inexistente, tour sin cupos o cliente ya inscrito), se
lanza una excepción, se avisa por consola y se continúa con las demás líneas.

---

## Cómo ejecutar

### Desde IntelliJ IDEA

1. Abrir la carpeta del proyecto en IntelliJ IDEA.
2. Verificar el SDK de Java (File → Project Structure → SDK). Probado con **Java 21**.
3. Ejecutar la clase `Main` ubicada en `src/ui/Main.java`.

### Desde terminal

```bash
javac -encoding UTF-8 -d out -sourcepath src src/ui/Main.java
java -cp out ui.Main
```

> Ejecutar desde la raíz del proyecto, para que las rutas `resources/*.txt` se resuelvan correctamente.

---

## Funcionalidad

- `GestorDatos` lee cada archivo línea por línea, separa con `split(";")`, valida
  la cantidad de campos, convierte números con `parseInt` y crea los objetos.
- Los objetos se almacenan en `ArrayList<Guia>`, `ArrayList<Tour>` y `ArrayList<Cliente>`.
- Cada `Tour` mantiene su propia lista (`ArrayList<Cliente>`) de inscritos,
  controla los cupos disponibles y evita inscripciones duplicadas.
- `GestorTours` recorre la colección con `for-each` y ofrece:
  - Mostrar todos los tours.
  - Buscar un tour por nombre.
  - Filtrar por tipo, por cupos disponibles y por precio máximo.
  - Contar tours por tipo usando un mapa clave-valor (`HashMap` ordenado con `TreeMap`).
- Las inscripciones se cargan desde `inscripciones.txt` y se muestran agrupadas por tour.
- `GestorServicios` crea los servicios turísticos y `Main` los recorre mostrando
  cada uno con su `toString()`, donde cada subclase imprime su propia información.

Salida de los servicios turísticos en consola:

```
--- SERVICIOS TURÍSTICOS DISPONIBLES ---
Servicio turístico: Sabores del Lago | Duración: 4 horas | Número de paradas: 5
Servicio turístico: Ruta del Queso Artesanal | Duración: 3 horas | Número de paradas: 4
Servicio turístico: Navegación Lago Llanquihue | Duración: 2 horas | Tipo de embarcación: Lancha turística
Servicio turístico: Atardecer Lacustre | Duración: 3 horas | Tipo de embarcación: Catamarán
Servicio turístico: Historia de Frutillar | Duración: 3 horas | Lugar histórico: Teatro del Lago
Servicio turístico: Patrimonio de Puerto Varas | Duración: 4 horas | Lugar histórico: Iglesia del Sagrado Corazón
```

---

## Demostración de validaciones

Al final de la ejecución, `Main` incluye una sección **DEMOSTRACIÓN DE VALIDACIONES**
que provoca errores a propósito (dentro de bloques `try-catch`) para evidenciar que
las validaciones y excepciones del sistema funcionan. Por ejemplo:

```
Dirección rechazada -> El número debe ser mayor que cero.
Guía rechazado -> RUT inválido: 'SINFORMATO'. El formato debe ser XXXXXXXX-X.
Tour rechazado -> El precio debe ser mayor a 0.
Inscripción rechazada -> El tour "Tour Lago Llanquihue" no tiene cupos disponibles.
Inscripción rechazada -> El cliente Asuna Yuuki ya está inscrito en el tour "Tour Volcan Osorno".
```

Estos errores son intencionales: los archivos de datos reales permanecen válidos.

---

## Autora

**Kamila Villablanca**
Desarrollo Orientado a Objetos I
Duoc UC — 2026
