package utils;

import java.util.List;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class PdfReportGenerator {

    public static void generatePdf(int pass, int fail, int skip) throws Exception {

        String dest = "test-output/AutomationReport.pdf";

        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // ✅ TITLE
        document.add(new Paragraph("Automation Test Report")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(18)
                .setBold());

        // ✅ SUMMARY
//        document.add(new Paragraph("\nExecution Summary").setBold());
//        document.add(new Paragraph("Total: " + (pass + fail + skip)));
//        document.add(new Paragraph("Passed: " + pass));
//        document.add(new Paragraph("Failed: " + fail));
//        document.add(new Paragraph("Skipped: " + skip));

        
     

        // ✅ FORMAT DATE
        String date = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"))
        	    .format(DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy"));

        // ✅ SUMMARY TITLE
        document.add(new Paragraph("\nExecution Summary:")
                .setBold()
                .setFontSize(16));

        // ✅ DETAILS
        document.add(new Paragraph("Generated on: " + date));
        document.add(new Paragraph("Build: "+ "Website API's"));

        document.add(new Paragraph("Total: " + (pass + fail + skip)));
        document.add(new Paragraph("Passed: " + pass));
        document.add(new Paragraph("Failed: " + fail));
        document.add(new Paragraph("Skipped: " + skip));
        
        
        // ✅ CHART
        Image chart = new Image(
                com.itextpdf.io.image.ImageDataFactory.create("test-output/chart.png")
        ).scaleToFit(400, 250);

        document.add(new Paragraph("\nExecution Results:").setBold()
        .setFontSize(16));
        document.add(chart);

        // ✅ TABLE
        Table table = new Table(new float[]{1, 5, 2});
        table.setWidth(UnitValue.createPercentValue(100));

        table.addHeaderCell("SL NO");
        table.addHeaderCell("Test Name");
        table.addHeaderCell("Status");

        // ✅ DYNAMIC DATA
        
        List<TestNGResultParser.TestResult> results = TestNGResultParser.getResults();
        
//     // ✅ ADD ORDER HERE
        
        //List<String> classOrder = TestNGXmlReader.getClassOrder();
//        List<String> classOrder = List.of(
//            "LoginTest",
//            "VerifyOtpTest",
//            "updateUserDetailsTest",
//            "ListAllVehicleVariantsTest",
//            "ListAccessoriesTest",
//            "getNearestDealerTest",
//            "createOrderTest",
//            "startPaymentTest",
//            "test_signatureTest",
//            "PaymentSuccessWebhookTest",
//            "verifyPaymentTest"
//        );
        
     // ✅ SORT RESULTS BEFORE LOOP
//        results.sort((r1, r2) -> {
//            int i1 = classOrder.indexOf(r1.className);
//            int i2 = classOrder.indexOf(r2.className);
//
//            if (i1 == -1) i1 = Integer.MAX_VALUE;
//            if (i2 == -1) i2 = Integer.MAX_VALUE;
//
//            return Integer.compare(i1, i2);
//        });
//
//        int sl = 1;
//
//        // ✅ LOOP (unchanged)
//        for (TestNGResultParser.TestResult r : results) {
//
//            table.addCell(String.valueOf(sl++));
//            table.addCell(r.name);
//
//            Cell statusCell = new Cell().add(new Paragraph(r.status));
//
//            if (r.status.equals("PASS")) {
//                statusCell.setBackgroundColor(new DeviceRgb(0, 153, 0));
//            } else if (r.status.equals("FAIL")) {
//                statusCell.setBackgroundColor(ColorConstants.RED);
//            } else {
//                statusCell.setBackgroundColor(ColorConstants.ORANGE);
//            }
//
//            statusCell.setFontColor(ColorConstants.WHITE);
//
//            table.addCell(statusCell);
//        }
////        
        

        int sl = 1;

        for (TestNGResultParser.TestResult r : results) {

            table.addCell(String.valueOf(sl++));
            table.addCell(r.name);

            Cell statusCell = new Cell().add(new Paragraph(r.status));

            if (r.status.equals("PASS")) {
                statusCell.setBackgroundColor(new DeviceRgb(0, 153, 0));
            } else if (r.status.equals("FAIL")) {
                statusCell.setBackgroundColor(ColorConstants.RED);
            } else {
                statusCell.setBackgroundColor(ColorConstants.ORANGE);
            }

            statusCell.setFontColor(ColorConstants.WHITE);

            table.addCell(statusCell);
        }

        document.add(new Paragraph("\n"));
        document.add(table);

        document.close();

        System.out.println("✅ Professional PDF Generated!");
    }
}