# Simulador de Arduino

Simula a API classica do Arduino (`pinMode`, `digitalRead`, `analogWrite`, etc.) sem precisar da placa fisica.

Ideal para prototipar/testar logica de sketches em Java.

## Executar

Rodar `Main` — mostra 3 sketches: LED piscando, botao+LED e termometro com PWM.

## Integracao com hardware real

Para comunicar com uma placa Arduino real via serial, adicione a biblioteca [jSerialComm](https://fazecast.github.io/jSerialComm/):

```xml
<dependency>
    <groupId>com.fazecast</groupId>
    <artifactId>jSerialComm</artifactId>
    <version>2.10.4</version>
</dependency>
```

E crie uma classe `ArduinoSerial` que abre `SerialPort` no dispositivo `COMx` (Windows) ou `/dev/ttyUSB0` (Linux).

