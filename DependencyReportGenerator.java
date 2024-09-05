import java.io.File;
import java.util.Map;

public class DependencyReportGenerator {
    public void generateXMLReport(Map<String, String> vulnerabilityResults) {
        try {
            File file = new File("dependency_report.xml");
            XMLReportGenerator xmlReportGenerator = new XMLReportGenerator();
            xmlReportGenerator.generateXMLReport(vulnerabilityResults, file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

