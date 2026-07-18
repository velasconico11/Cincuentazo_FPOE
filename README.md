# Cincuentazo 🃏

Juego de cartas "Cincuentazo" desarrollado en JavaFX como proyecto de la asignatura FPOE (Fundamentos de Programación Orientada a Eventos).

## Características

- **Modo de juego:** 1 jugador humano vs 1-3 jugadores máquina
- **Pantalla completa** con interfaz gráfica intuitiva
- **Regla principal:** La suma de cartas en la mesa no puede exceder 50
- **Cartas especiales:**
  - **As:** Puede valer 1 o 10 (elección del jugador)
  - **Sota (J):** Vale 10
  - **Caballo (Q):** Vale 10
  - **Rey (K):** Vale 10
- **Sistema de turnos** con jugadores automáticos (máquina)
- **Reloj de partida** integrado
- **Log de juego** para seguimiento de jugadas

## Requisitos Previos

- **Java JDK 25** o superior
- **Maven 3.8+**
- **JavaFX 21** (incluido en las dependencias)

## Instalación

1. Clona el repositorio:
   ```bash
   git clone https://github.com/tu-usuario/Cincuentazo_FPOE.git
   cd Cincuentazo_FPOE
   ```

2. Compila el proyecto:
   ```bash
   mvn clean compile
   ```

## Ejecución

Para ejecutar el juego:

```bash
mvn javafx:run
```

O utilizando el script incluido:

```bash
./mvnw javafx:run
```

## Estructura del Proyecto

```
Cincuentazo_FPOE/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/example/cincuentazo_fpoe/
│   │   │       ├── controller/          # Controladores MVC
│   │   │       │   ├── ConfiguracionController.java
│   │   │       │   └── JuegoController.java
│   │   │       ├── model/               # Modelo de datos
│   │   │       │   ├── Carta.java
│   │   │       │   ├── IJuego.java
│   │   │       │   ├── Juego.java
│   │   │       │   ├── Jugador.java
│   │   │       │   ├── Mazo.java
│   │   │       │   ├── Mesa.java
│   │   │       │   ├── Palo.java
│   │   │       │   └── Rango.java
│   │   │       ├── exception/           # Excepciones personalizadas
│   │   │       │   ├── CartaInvalidaException.java
│   │   │       │   ├── JugadorEliminadoException.java
│   │   │       │   └── MazoVacioException.java
│   │   │       ├── thread/              # Hilos para animaciones
│   │   │       │   ├── RelojPartidaThread.java
│   │   │       │   └── TurnoMaquinaThread.java
│   │   │       ├── HelloApplication.java
│   │   │       └── Launcher.java
│   │   └── resources/
│   │       └── org/example/cincuentazo_fpoe/
│   │           ├── configuracion.fxml   # Pantalla de configuración
│   │           ├── juego.fxml           # Pantalla principal del juego
│   │           └── styles.css           # Estilos CSS
│   └── test/                            # Pruebas unitarias
├── pom.xml                              # Configuración Maven
└── README.md
```

## Reglas del Juego

1. **Objetivo:** Ser el último jugador en pie (no quedar eliminado)
2. **Reparto:** Cada jugador recibe 4 cartas inicialmente
3. **Turnos:** Los jugadores juegan una carta por turno en orden
4. **Suma:** La suma de las cartas jugadas en la mesa no puede exceder 50
5. **Eliminación:** Si un jugador no tiene cartas jugables, queda eliminado
6. **Fin del juego:** Cuando solo queda un jugador activo

## Arquitectura

El proyecto sigue el patrón **MVC (Model-View-Controller)**:

- **Model:** `Juego`, `Jugador`, `Carta`, `Mazo`, `Mesa`
- **View:** Archivos FXML (`configuracion.fxml`, `juego.fxml`)
- **Controller:** `ConfiguracionController`, `JuegoController`

## Autores

- **Nicolás Velasco**
- **Daniel Toro**

## Licencia

Proyecto académico - Universidad del Valle

