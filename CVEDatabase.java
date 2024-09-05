import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class CVEDatabase {
    private static final String BASE_URL = "https://cve.circl.lu/api/";

    public String getCVEInfo(String cveId) {
        try {
            URL url = new URL(BASE_URL + cveId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            JSONObject jsonResponse = new JSONObject(response.toString());
            if (jsonResponse.has("description")) {
                return jsonResponse.getString("description");
            } else {
                return "Thông tin không tìm thấy";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Thông tin không tìm thấy";
        }
    }
}

