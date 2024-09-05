import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;
import java.util.Map;

public class XMLReportGenerator {
    public void generateXMLReport(Map<String, String> vulnerabilityResults, String fileName) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("Vulnerabilities");
            doc.appendChild(rootElement);

            for (Map.Entry<String, String> entry : vulnerabilityResults.entrySet()) {
                Element vulnerability = doc.createElement("Vulnerability");
                rootElement.appendChild(vulnerability);

                Element service = doc.createElement("Service");
                service.appendChild(doc.createTextNode(entry.getKey()));
                vulnerability.appendChild(service);

                Element info = doc.createElement("Info");
                info.appendChild(doc.createTextNode(entry.getValue()));
                vulnerability.appendChild(info);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(fileName));

            transformer.transform(source, result);

            System.out.println("Báo cáo XML đã được tạo thành công.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

