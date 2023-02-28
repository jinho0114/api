package apiRepository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@SpringBootApplication
public class callApi {
    public static void main(String[] args) {
        Document xml = null;
        String testURL = "http://api.nongsaro.go.kr/service/garden/gardenList?apiKey=202212290SGQFNOOOQJTTHHYQIRA";
        for (int q = 1; q < 23; q++) {
            String pageNo = "&pageNo=" + q;
            String test = testURL + pageNo;
            System.out.println("URL:" + test);
            try {
                URL url = new URL(test);
                URLConnection urlConnect = url.openConnection();
                urlConnect.connect();

                InputSource is = new InputSource(urlConnect.getInputStream());

                xml = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);

                // root element 취득
                Element element = xml.getDocumentElement();

                // child node 취득
                NodeList list = element.getChildNodes();

                //cntntsSj 데이터 받는 곳
                NodeList cntntsSj = xml.getElementsByTagName("cntntsSj");
                //cntntsNo 데이터 받는 곳
                NodeList cntntsNo = xml.getElementsByTagName("cntntsNo");

                // child node 가 1개 이상인 경우
                if (list.getLength() > 0) {
                    for (int i = 0; i < list.getLength(); i++) {

                        NodeList childList = list.item(i).getChildNodes();

                        if (childList.getLength() > 0) {
                            for (int j = 0; j < childList.getLength(); j++) {
                                System.out.println("[" + cntntsSj.item(j).getTextContent()+", "+ cntntsNo.item(j).getTextContent()+"]");

//                            System.out.println(childList.item(j).getNodeName() + ": "+childList.item(j).getTextContent());
//                            NodeList childList2 = childList.item(j).getChildNodes();
//                            if(childList2.getLength() > 0) {
//                                for (int k = 0; k < childList2.getLength(); k++) {
//                                    childList2 = xml.getElementsByTagName("cntntsSj");
//                                    System.out.println(childList2.item(k).getNodeName() + ": "+childList2.item(k).getTextContent());
//                                    // 데이터가 있는 애들만 출력되게 한다.
////                                    NodeList childList3 = childList2.item(k).getChildNodes();
////                                    if(childList3.getLength() > 0) {
////                                        for (int l = 0; l < childList3.getLength(); l++) {
////
////                                            // 데이터가 있는 애들만 출력되게 한다.
////                                            if(childList3.item(l).getNodeName().equals("#text")==false) {
////                                                System.out.println(childList3.item(l).getNodeName() + ": "+childList3.item(l).getTextContent());
////
////                                            }
////                                        }
////                                    }
//                                }
//                            }
                            }
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SAXException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            test = testURL;
        }
    }
}

