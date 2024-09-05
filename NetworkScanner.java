import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class NetworkScanner {
    private String ipRange;
    private String portRange;
    private String scanType;
    private List<String> scanResults;

    public NetworkScanner(String ipRange, String portRange, String scanType) {
        this.ipRange = ipRange;
        this.portRange = portRange;
        this.scanType = scanType;
        this.scanResults = new ArrayList<>();
    }

    public void scan() {
        try {
            List<String> commands = new ArrayList<>();
            commands.add("nmap");
            commands.add("-p" + portRange);
            commands.add("-T4");
            
            // Chọn loại quét
            if (scanType.equalsIgnoreCase("SYN")) {
                commands.add("-sS");
            } else if (scanType.equalsIgnoreCase("Connect")) {
                commands.add("-sT");
            } else if (scanType.equalsIgnoreCase("UDP")) {
                commands.add("-sU");
            } else if (scanType.equalsIgnoreCase("Banner")) {
                commands.add("-sV");
            } else {
                System.out.println("Loại quét không hợp lệ, sử dụng quét SYN theo mặc định.");
                commands.add("-sS");
            }
            
            commands.add(ipRange);

            ProcessBuilder processBuilder = new ProcessBuilder(commands);
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                if (line.contains("open") || line.contains("Service")) {
                    String[] parts = line.split("\\s+");
                    if (parts.length >= 2) {
                        scanResults.add(parts[0] + ":" + parts[1]);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getScanResults() {
        return scanResults;
    }
}

