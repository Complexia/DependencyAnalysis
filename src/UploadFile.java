
import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class UploadFile
{

   private static Document doc1;
   private static ArrayList<Service> elements = new ArrayList();
   private static ArrayList<String> variables = new ArrayList();

   public static Document getDoc()
   {
      return doc1;
   }

   public static ArrayList<Service> getElements()
   {
      return elements;
   }

   public static void uploadL0(File file)
   {
      try
      {

         File fXmlFile = file;
         DocumentBuilderFactory dbFactory =
               DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document doc = dBuilder.parse(fXmlFile);

         // optional, but recommended
         // read this -
         // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
         doc.getDocumentElement().normalize();

         doc1 = doc;

         System.out.println("Root element :" +
                            doc.getDocumentElement().getNodeName());

         NodeList nList = doc.getElementsByTagName("staff");

         System.out.println("----------------------------");

         for (int temp = 0; temp < nList.getLength(); temp++)
         {

            Node nNode = nList.item(temp);

            System.out.println("\nCurrent Element :" + nNode.getNodeName());

            if (nNode.getNodeType() == Node.ELEMENT_NODE)
            {

               Element eElement = (Element) nNode;

               // System.out.println("Staff id : " +
               // eElement.getAttribute("id"));
               //
               // System.out.println("First Name : " +
               // eElement.getElementsByTagName("firstname").item(0).getTextContent());
               // System.out.println("Last Name : " +
               // eElement.getElementsByTagName("lastname").item(0).getTextContent());
               // System.out.println("Nick Name : " +
               // eElement.getElementsByTagName("nickname").item(0).getTextContent());
               // System.out.println("Salary : " +
               // eElement.getElementsByTagName("salary").item(0).getTextContent());

               variables.add(eElement.getElementsByTagName("firstname").item(0)
                     .getTextContent());
               variables.add(eElement.getElementsByTagName("lastname").item(0)
                     .getTextContent());
               variables.add(eElement.getElementsByTagName("nickname").item(0)
                     .getTextContent());
               variables.add(eElement.getElementsByTagName("salary").item(0)
                     .getTextContent());

               elements.add(new Service(nNode.getNodeName(), variables));

            }
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }

   }

   public static void main(String argv[])
   {

      // doStuff();

   }
}
