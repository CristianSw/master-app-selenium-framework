package selenium.framework.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {
    public static ExtentReports getReportObject(){
        String path = System.getProperty("user.dir") + "//reports//index.html";

        ExtentSparkReporter report = new ExtentSparkReporter(path);
        report.config().setReportName("Cristian Dolinta");
        report.config().setDocumentTitle("Test Results");

        ExtentReports extentReports = new ExtentReports();
        extentReports.attachReporter(report);
        extentReports.setSystemInfo("Tester", "Cristian Dolinta");
        return extentReports;
    }
}
