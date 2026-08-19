package application.utilitarios.monitor;

import com.sun.management.OperatingSystemMXBean;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.RuntimeMXBean;
import java.time.Duration;

public class Monitor {

    private final OperatingSystemMXBean os;
    private final MemoryMXBean          memoria;
    private final RuntimeMXBean         runtime;

    public Monitor() {
        this.os = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        this.memoria = ManagementFactory.getMemoryMXBean();
        this.runtime = ManagementFactory.getRuntimeMXBean();
    }

    public String snapshot() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("=== SISTEMA ===\n");
        stringBuilder.append("SO:            ")
                .append(os.getName())
                .append(" ")
                .append(os.getVersion())
                .append(" (")
                .append(os.getArch())
                .append(")\n");

        stringBuilder.append("Processadores: ")
                .append(os.getAvailableProcessors())
                .append("\n");

        stringBuilder.append("Load medio:    ")
                .append(String.format("%.2f", os.getSystemLoadAverage()))
                .append("\n");

        stringBuilder.append("CPU sistema:   ")
                .append(pct(os.getCpuLoad()))
                .append("\n");

        stringBuilder.append("CPU JVM:       ")
                .append(pct(os.getProcessCpuLoad()))
                .append("\n");

        stringBuilder.append("\n=== MEMORIA (fisica) ===\n");
        long totalFisica = os.getTotalMemorySize();
        long livreFisica = os.getFreeMemorySize();
        long usadaFisica = totalFisica - livreFisica;

        stringBuilder.append("Total: ")
                .append(mb(totalFisica))
                .append("\n");

        stringBuilder.append("Usada: ")
                .append(mb(usadaFisica))
                .append(" (")
                .append(String.format("%.1f%%", 100.0 * usadaFisica / totalFisica))
                .append(")\n");

        stringBuilder.append("Livre: ")
                .append(mb(livreFisica))
                .append("\n");

        stringBuilder.append("\n=== HEAP JVM ===\n");
        MemoryUsage heap = memoria.getHeapMemoryUsage();
        stringBuilder.append("Usada:  ").append(mb(heap.getUsed())).append("\n");
        stringBuilder.append("Commit: ").append(mb(heap.getCommitted())).append("\n");
        stringBuilder.append("Max:    ").append(mb(heap.getMax())).append("\n");

        stringBuilder.append("\n=== DISCOS ===\n");
        for (File raiz : File.listRoots()) {
            long total = raiz.getTotalSpace();
            long livre = raiz.getFreeSpace();
            long usado = total - livre;
            if (total == 0) {
                continue;
            }

            stringBuilder.append(raiz.getAbsolutePath())
                    .append("  ")
                    .append(gb(usado))
                    .append(" / ")
                    .append(gb(total))
                    .append(" (")
                    .append(String.format("%.1f%%", 100.0 * usado / total))
                    .append(" usado)\n");
        }

        stringBuilder.append("\n=== JVM ===\n");
        stringBuilder.append("Java:     ")
                .append(runtime.getVmName())
                .append(" ")
                .append(System.getProperty("java.version"))
                .append("\n");

        stringBuilder.append("Uptime:   ")
                .append(Duration.ofMillis(runtime.getUptime()))
                .append("\n");

        return stringBuilder.toString();
    }

    private static String pct(double v) {
        return v < 0 ? "n/d" : String.format("%.1f%%", v * 100);
    }

    private static String mb(long bytes) {
        return String.format("%.1f MB", bytes / 1024.0 / 1024.0);
    }

    private static String gb(long bytes) {
        return String.format("%.2f GB", bytes / 1024.0 / 1024.0 / 1024.0);
    }
}

