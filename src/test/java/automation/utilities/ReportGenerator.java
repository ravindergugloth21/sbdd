package automation.utilities;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ReportGenerator {
    public static void main(String... args) throws Exception {
        openReport();

    }

    public static void openReport() {
        String reportPath = System.getProperty("user.dir") + File.separator + "AutomationReports" + File.separator + "LatestResults" + File.separator + "SerenityReports" + File.separator + "index.html";
        File report = new File(reportPath);
        if (report.exists()) {
            try {
                Desktop.getDesktop().browse(report.toURI());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("something wrong with report");
        }
    }

}