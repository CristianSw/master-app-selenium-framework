package selenium.framework.test_components;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {
    @Override
    public boolean retry(ITestResult iTestResult) {
        return false;
    }
}
