# Conversor de Formatos

Converte dados entre CSV, JSON e XML sem dependencias externas.

## Executar

Rodar `Main` — gera arquivos em `target/` e faz round-trip test.

## Suporte a Excel (.xlsx)

Requer Apache POI. Adicionar no `pom.xml`:

```xml
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml</artifactId>
    <version>5.2.5</version>
</dependency>
```

Depois basta criar `ConversorExcel.java` similar aos outros, usando `XSSFWorkbook` e `XSSFSheet` para ler/escrever `.xlsx`.

