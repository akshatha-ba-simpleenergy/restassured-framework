package utils;

import org.testng.IExecutionListener;
import org.testng.ITestContext;

public class ReportListener implements IExecutionListener {

    @Override
    public void onExecutionFinish() {
        try {

            int pass = TestResultManager.passCount;
            int fail = TestResultManager.failCount;
            int skip = TestResultManager.skipCount;

            ChartGenerator.generateChart(pass, fail, skip);
            PdfReportGenerator.generatePdf(pass, fail, skip);
            
         // ✅ SEND EMAIL
            EmailUtil.sendEmail();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


