package utils;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TestNGResultParser {

    public static class TestResult {
        public String name;
        public String status;
        public String className;

        public TestResult(String name, String status ) {
            this.name = name;
            this.status = status;
           
           
        }
    }

    public static List<TestResult> getResults() throws Exception {

        List<TestResult> results = new ArrayList<>();

        File file = new File("test-output/testng-results.xml");
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);

        NodeList testMethods = doc.getElementsByTagName("test-method");

        for (int i = 0; i < testMethods.getLength(); i++) {

            Element element = (Element) testMethods.item(i);

            String status = element.getAttribute("status");
            String name = element.getAttribute("name");
            //String className= element.getAttribute("class");
          

            // Ignore config methods
            if (element.getAttribute("is-config").equals("true")) continue;

            results.add(new TestResult(name, status.toUpperCase()));
        }

        return results;
    }
}