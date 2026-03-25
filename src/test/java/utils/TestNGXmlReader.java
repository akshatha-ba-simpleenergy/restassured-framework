package utils;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TestNGXmlReader {

    public static List<String> getClassOrder() throws Exception {

        List<String> order = new ArrayList<>();

        File file = new File("testng.xml"); // adjust path if needed
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);

        NodeList classNodes = doc.getElementsByTagName("class");

        for (int i = 0; i < classNodes.getLength(); i++) {
            Element element = (Element) classNodes.item(i);
            String fullName = element.getAttribute("name");

            // extract class name only
            String className = fullName.substring(fullName.lastIndexOf(".") + 1);
            order.add(className);
        }

        return order;
    }
}