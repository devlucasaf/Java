package application.utilitarios.monitorsistema;

import com.sun.management.OperatingSystemMXBean;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.RuntimeMXBean;
import java.time.Duration;

public class Monitor {

    private final OperatingSystemMXBean os;
    private final MemoryMXBean memoria;
    private final RuntimeMXBean runtime;

    public Monitor() {
        this.os = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        this.memoria = ManagementFactory.getMemoryMXBean();
        this.runtime = ManagementFactory.getRuntimeMXBean();
    }

    public String snapshot() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== SISTEMA ===\n");
        sb.append("SO:            ").append(os.getName()).append(" ")
                .append(os.getVersion()).append(" (").append(os.getArch()).append(")\n");
        sb.append("Processadores: ").append(os.getAvailableProcessors()).append("\n");
        sb.append("Load medio:    ").append(String.format("%.2f", os.getSystemLoadAverage())).append("\n");
        sb.append("CPU sistema:   ").append(pct(os.getCpuLoad())).append("\n");
        sb.append("CPU JVM:       ").append(pct(os.getProcessCpuLoad())).append("\n");

        sb.append("\n=== MEMORIA (fisica) ===\n");
        long totalFisica = os.getTotalMemorySize();
        long livreFisica = os.getFreeMemorySize();
        long usadaFisica = totalFisica - livreFisica;
        sb.append("Total: ").append(mb(totalFisica)).append("\n");
        sb.append("Usada: ").append(mb(usadaFisica)).append(" (")
                .append(String.format("%.1f%%", 100.0 * usadaFisica / totalFisica)).append(")\n");
        sb.append("Livre: ").append(mb(livreFisica)).append("\n");

        sb.append("\n=== HEAP JVM ===\n");
        MemoryUsage heap = memoria.getHeapMemoryUsage();
        sb.append("Usada:  ").append(mb(heap.getUsed())).append("\n");
        sb.append("Commit: ").append(mb(heap.getCommitted())).append("\n");
        sb.append("Max:    ").append(mb(heap.getMax())).append("\n");

        sb.append("\n=== DISCOS ===\n");
        for (File raiz : File.listRoots()) {
            long total = raiz.getTotalSpace();
            long livre = raiz.getFreeSpace();
            long usado = total - livre;
            if (total == 0) continue;
            sb.append(raiz.getAbsolutePath()).append("  ").append(gb(usado))
                    .append(" / ").append(gb(total)).append(" (")
                    .append(String.format("%.1f%%", 100.0 * usado / total))
                    .append(" usado)\n");
        }

        sb.append("\n=== JVM ===\n");
        sb.append("Java:     ").append(runtime.getVmName()).append(" ")
                .append(System.getProperty("java.version")).append("\n");
        sb.append("Uptime:   ").append(Duration.ofMillis(runtime.getUptime())).append("\n");
        return sb.toString();
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

