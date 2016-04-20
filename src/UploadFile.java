
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
