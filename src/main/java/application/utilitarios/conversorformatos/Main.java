package application.utilitarios.conversorformatos;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        Path csv = Path.of("target", "conversor-demo.csv");
        Path json = Path.of("target", "conversor-demo.json");
        Path xml = Path.of("target", "conversor-demo.xml");
        Path.of("target").toFile().mkdirs();

        List<Registro> dados = List.of(
                new Registro().set("id", "1").set("nome", "Ana").set("cidade", "Brasilia"),
                new Registro().set("id", "2").set("nome", "Bruno, Jr").set("cidade", "Sao Paulo"),
                new Registro().set("id", "3").set("nome", "Carla").set("cidade", "Rio")
        );

        System.out.println("Escrevendo CSV: " + csv.toAbsolutePath());
        ConversorCSV.escrever(dados, csv);

        System.out.println("Escrevendo JSON: " + json.toAbsolutePath());
        ConversorJSON.escrever(dados, json);

        System.out.println("Escrevendo XML: " + xml.toAbsolutePath());
        ConversorXML.escrever(dados, xml);

        System.out.println("\n=== Round-trip test ===");
        List<Registro> deCsv = ConversorCSV.ler(csv);
        List<Registro> deJson = ConversorJSON.ler(json);
        List<Registro> deXml = ConversorXML.ler(xml);

        System.out.println("Lidos do CSV: " + deCsv.size());
        deCsv.forEach(r -> System.out.println("  " + r));

        System.out.println("Lidos do JSON: " + deJson.size());
        deJson.forEach(r -> System.out.println("  " + r));

        System.out.println("Lidos do XML: " + deXml.size());
        deXml.forEach(r -> System.out.println("  " + r));

        System.out.println("\nSuporte a Excel (.xlsx) requer Apache POI - ver README.md");
    }
}

