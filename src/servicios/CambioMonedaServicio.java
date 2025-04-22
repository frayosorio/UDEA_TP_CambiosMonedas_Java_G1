package servicios;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import entidades.CambioMoneda;

public class CambioMonedaServicio {

    public static List<CambioMoneda> getDatos(String nombreArchivo) {
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("d/M/yyyy");
        try {
            Stream<String> lineas = Files.lines(Paths.get(nombreArchivo));
            return lineas.skip(1)
                    .map(linea -> linea.split(","))
                    .map(textos -> new CambioMoneda(textos[0], LocalDate.parse(textos[1], formatoFecha),
                            Double.parseDouble(textos[2])))
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            return Collections.emptyList();
        }
    }

    public static List<String> getMonedas(List<CambioMoneda> datos) {
        return datos.stream()
                .map(CambioMoneda::getMoneda)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    public static List<CambioMoneda> filtrar(String moneda, LocalDate desde, LocalDate hasta,
            List<CambioMoneda> datos) {
        return datos.stream()
                .filter(dato -> dato.getMoneda().equals(moneda) && !dato.getFecha().isBefore(desde)
                        && !dato.getFecha().isAfter(hasta))
                .collect(Collectors.toList());
    }

    public static double getPromedio(List<Double> datos) {
        return datos.isEmpty() ? 0 : datos.stream().mapToDouble(Double::doubleValue).average().orElse(0);
    }

    public static double getDesviacionEstandar(List<Double> datos) {
        var promedio = getPromedio(datos);
        return datos.isEmpty() ? 0
                : Math.sqrt(datos.stream()
                        .mapToDouble(dato -> Math.pow(dato - promedio, 2))
                        .average()
                        .orElse(0));
    }

    public static double getMaximo(List<Double> datos) {
        return datos.stream().mapToDouble(Double::doubleValue).max().orElse(0);
    }

    public static double getMinimo(List<Double> datos) {
        return datos.stream().mapToDouble(Double::doubleValue).min().orElse(0);
    }


}
