# LlanquihueTourApp

## Descripción

Aplicación Java que carga los tours de **Llanquihue Tour** desde un archivo de texto, crea objetos `Tour` y los almacena en una colección `ArrayList`. Luego recorre la colección y la filtra según distintas condiciones, mostrando los resultados por consola.

---

## Estructura del proyecto

```
LlanquihueTourApp/
├── resources/
│   └── tours.txt        → Datos de entrada
└── src/
    ├── model/
    │   └── Tour.java     → Clase de dominio
    ├── data/
    │   └── GestorDatos.java → Lee tours.txt y construye el ArrayList
    └── ui/
        └── Main.java      → Recorrido y filtros sobre la colección
```

---

## Formato de tours.txt

Cada línea representa un tour, con los datos separados por `;` (sin espacios):

```
nombre;tipo;destino;precio;cupos
```

Ejemplo:

```
Tour Volcan Osorno;Aventura;Volcan Osorno;45000;8
```

---

## Cómo ejecutar

### Desde IntelliJ IDEA

1. Abrir la carpeta del proyecto en IntelliJ IDEA.
2. Verificar que el SDK de Java esté configurado (File → Project Structure → SDK).
3. Ejecutar la clase `Main` ubicada en `src/ui/Main.java`.

### Desde terminal

```bash
javac -encoding UTF-8 -d out -sourcepath src src/model/Tour.java src/data/GestorDatos.java src/ui/Main.java
java -cp out ui.Main
```

---

## Funcionalidad

- `GestorDatos` lee `resources/tours.txt` línea por línea, separa los datos con `split(";")` y crea objetos `Tour`.
- Los objetos se almacenan en un `ArrayList<Tour>`.
- `Main` recorre la colección completa con un `for-each` y la muestra por consola.
- `Main` aplica dos filtros:
  - Tours de tipo **Aventura**.
  - Tours con **cupos disponibles** (`cupos > 0`).

---

## Salida esperada

```
--- TOURS DISPONIBLES - LLANQUIHUE TOUR ---

--- Listado completo ---
Tour: Tour Volcan Osorno | Tipo: Aventura | Destino: Volcan Osorno | Precio: $45000 | Cupos disponibles: 8
Tour: Tour Saltos del Petrohue | Tipo: Naturaleza | Destino: Saltos del Petrohue | Precio: $25000 | Cupos disponibles: 15
Tour: Tour Lago Llanquihue | Tipo: Lacustre | Destino: Lago Llanquihue | Precio: $30000 | Cupos disponibles: 0
Tour: Tour Degustacion Local | Tipo: Gastronomico | Destino: Frutillar | Precio: $20000 | Cupos disponibles: 12
Tour: Tour Centro Historico | Tipo: Cultural | Destino: Puerto Varas | Precio: $15000 | Cupos disponibles: 20
Tour: Tour Kayak Familiar | Tipo: Familiar | Destino: Rio Maullin | Precio: $28000 | Cupos disponibles: 6

--- Tours de tipo Aventura ---
Tour: Tour Volcan Osorno | Tipo: Aventura | Destino: Volcan Osorno | Precio: $45000 | Cupos disponibles: 8

--- Tours con cupos disponibles ---
Tour: Tour Volcan Osorno | Tipo: Aventura | Destino: Volcan Osorno | Precio: $45000 | Cupos disponibles: 8
Tour: Tour Saltos del Petrohue | Tipo: Naturaleza | Destino: Saltos del Petrohue | Precio: $25000 | Cupos disponibles: 15
Tour: Tour Degustacion Local | Tipo: Gastronomico | Destino: Frutillar | Precio: $20000 | Cupos disponibles: 12
Tour: Tour Centro Historico | Tipo: Cultural | Destino: Puerto Varas | Precio: $15000 | Cupos disponibles: 20
Tour: Tour Kayak Familiar | Tipo: Familiar | Destino: Rio Maullin | Precio: $28000 | Cupos disponibles: 6
```

---

## Autora

**Kamila Villablanca**
Desarrollo Orientado a Objetos I — Semana 4
Duoc UC — 2026
