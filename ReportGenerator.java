import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Map;

public class ReportGenerator {
    public void generateReport(Map<String, String> vulnerabilityResults, String fileName, String format) {
        switch (format.toLowerCase()) {
            case "xml":
                XMLReportGenerator xmlReportGenerator = new XMLReportGenerator();
                xmlReportGenerator.generateXMLReport(vulnerabilityResults, fileName);
                break;
            case "csv":
                generateCSVReport(vulnerabilityResults, fileName);
                break;
            case "pdf":
                // Implement PDF generation here
                break;
            default:
                generateTXTReport(vulnerabilityResults, fileName);
                break;
        }
    }

    private void generateTXTReport(Map<String, String> vulnerabilityResults, String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (Map.Entry<String, String> entry : vulnerabilityResults.entrySet()) {
                writer.println("Service: " + entry.getKey());
                writer.println("Info: " + entry.getValue());
                writer.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateCSVReport(Map<String, String> vulnerabilityResults, String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("Service,Info");
            for (Map.Entry<String, String> entry : vulnerabilityResults.entrySet()) {
                writer.println(entry.getKey() + "," + entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generatePDFReport(Map<String, String> vulnerabilityResults, String fileName) {
        // Implement PDF generation using a library like iText or Apache PDFBox
    }
}

