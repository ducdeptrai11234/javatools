import org.apache.commons.cli.*;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Options options = new Options();
        options.addOption("i", "ip", true, "IP hoặc dải IP");
        options.addOption("p", "port", true, "Cổng hoặc dải cổng (ví dụ: 80, 443, 1-1024)");
        options.addOption("t", "type", true, "Loại quét (SYN, Connect, UDP, Banner)");
        options.addOption("d", "dir", true, "Thư mục dự án để quét phụ thuộc");
        options.addOption("f", "file", true, "Tên file cấu hình để lưu báo cáo (ví dụ: report.xml)");
        options.addOption("o", "output", true, "Định dạng báo cáo (txt, csv, xml, pdf)");

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);

            if (cmd.hasOption("i") && cmd.hasOption("p") && cmd.hasOption("t")) {
                String ipRange = cmd.getOptionValue("i");
                String portRange = cmd.getOptionValue("p");
                String scanType = cmd.getOptionValue("t");
                String outputFormat = cmd.hasOption("o") ? cmd.getOptionValue("o") : "xml";

                // Khởi tạo và thực hiện quét mạng
                NetworkScanner networkScanner = new NetworkScanner(ipRange, portRange, scanType);
                networkScanner.scan();
                List<String> scanResults = networkScanner.getScanResults();
                
                // Kiểm tra lỗ hổng
                VulnerabilityChecker vulnerabilityChecker = new VulnerabilityChecker();
                vulnerabilityChecker.check(scanResults);
                Map<String, String> vulnerabilityResults = vulnerabilityChecker.getVulnerabilityResults();
                
                // Tạo báo cáo
                String reportFileName = cmd.hasOption("f") ? cmd.getOptionValue("f") : "vulnerability_report." + outputFormat;
                ReportGenerator reportGenerator = new ReportGenerator();
                reportGenerator.generateReport(vulnerabilityResults, reportFileName, outputFormat);

                System.out.println("Quá trình hoàn tất. Báo cáo đã được tạo thành công.");
            } else if (cmd.hasOption("d")) {
                String projectPath = cmd.getOptionValue("d");

                // Kiểm tra phụ thuộc
                DependencyChecker dependencyChecker = new DependencyChecker();
                dependencyChecker.checkDependencies(projectPath);
                
                // Tạo báo cáo phụ thuộc
                DependencyReportGenerator dependencyReportGenerator = new DependencyReportGenerator();
                dependencyReportGenerator.generateXMLReport(dependencyChecker.getVulnerabilityResults());

                System.out.println("Quá trình kiểm tra phụ thuộc hoàn tất.");
            } else {
                formatter.printHelp("Utility-name", options);
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("Utility-name", options);
        }
    }
}

