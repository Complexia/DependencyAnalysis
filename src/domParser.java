import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class domParser
{
   DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();try
   {
   DocumentBuilder builder = factory.newDocumentBuilder();
   Document doc = builder.parse(UploadFile);
   NodeList serviceList = doc.getElementsByTagName("name");
   for (int i = 0; i < serviceList.getLength(); i++){
      Node n = serviceList.item(i);
      if(n.getNodeType() == Node.ELEMENT_NODE){
         Element name = (Element) n;
         String outputs = name.getAttribute("outputs");
               NodeList serviceList = name.getChildNodes();
                  for(int j = 0; j < serviceList.getLength(); j++){
                     Node o = serviceList.item(j);
                     if(o.getNodeType() == Node.ELEMENT_NODE){
                        Element outputs = (Element) o;
                        String inputs = name.getAttribute("inputs");
                        NodeList serviceList = name.getChildNodes();
                           for(int k = 0; k < serviceList.getLength(); k++){
                              Node ip = serviceList.item(k);
                              if(ip.getNodeType() == Node.ELEMENT_NODE){
                                 Element inputs = (Element) ip;
                                 String localVariable = name.getAttribute("localVariable");
                                 NodeList serviceList = name.getChildNodes();
                                    for(int l = 0; l < serviceList.getLength(); l++){
                                       Node lv = serviceList.item(l);
                                       if(lv.getNodeType() == Node.ELEMENT_NODE){
                                          Element localVariable = (Element) lv;
                                          String inputVariable = name.getAttribute("inputVariable");
                                          NodeList serviceList = name.getChildNodes();
                                             for(int m = 0; m < serviceList.getLength(); m++){
                                                Node iv = serviceList.item(m);
                                                if(iv.getNodeType() == Node.ELEMENT_NODE){
                                                   Element inputVariable = (Element) iv;
                                                   String outputVariable = name.getAttribute("outputVariable");
                                                   NodeList serviceList = name.getChildNodes();
                                                      for(int n = 0; n < serviceList.getLength(); n++){
                                                         Node ov = serviceList.item(n);
                                                         if(ov.getNodeType() == Node.ELEMENT_NODE){
                                                            Element outputVariable = (Element) ov;
                                                }}
                                       }
                                       }
                                    
                              }
                              }
                     }
                  }
      }
   }
}}
