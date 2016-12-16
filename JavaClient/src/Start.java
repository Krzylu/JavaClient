import java.io.*;
import java.net.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class Start
{
	public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException
	{
		URL url;
        InputStream is = null;
        BufferedReader br;
        String doc = "";

        try {
            url = new URL("http://www.imagefap.com/categories.php");
            is = url.openStream();  // throws an IOException
            br = new BufferedReader(new InputStreamReader(is));

            String line;
            while ((line = br.readLine()) != null) {
                doc += "\n" + line;
            }
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException ioe) {
                // nothing to see here
            }
        }
        System.out.println(doc);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        Document html = docBuilder.parse(new InputSource(new StringReader(doc)));
        html.getElementsByTagName("");
    }
}
