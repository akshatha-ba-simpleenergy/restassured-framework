package utils;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    public void onTestSuccess(ITestResult result) {
        TestResultManager.passCount++;
    }

    public void onTestFailure(ITestResult result) {
        TestResultManager.failCount++;
    }

    public void onTestSkipped(ITestResult result) {
        TestResultManager.skipCount++;
    }
}

