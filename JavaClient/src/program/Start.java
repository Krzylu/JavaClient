package program;
import java.io.*;
import java.net.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.xpath.*;
import java.util.*;

public class Start {
	public static void main ( String[] args ) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
		URL url;
        InputStream is = null;
        String doc = "";
        url = new URL( "http://www.imagefap.com/categories.php" );
        is = url.openStream( );
        String line = "";
        int lineNum = 1;
        BufferedReader br = new BufferedReader( new InputStreamReader( is ) );
        while ( ( line = br.readLine( ) ) != null ) {
            System.out.println( lineNum++ + line );
            if ( line.equals( "" ) ) {
                continue;
            }
            if ( line.contains( "><" ) ) {
                line = line.replaceAll( "><", ">\n<" );
            }
            doc += ( line + "\n" );
        }
        List<String> docArray = new ArrayList( Arrays.asList( doc.split( "\n" ) ) );
        doc = "";
        for ( int i = 0; i < docArray.size( ); i++ ) {
            if ( docArray.get( i ).contains( "<img" ) || docArray.get( i ).contains( "<input" ) 
                || docArray.get(i).contains("center>") || docArray.get(i).contains("div")) {
                docArray.add( i, "" );
                docArray.remove( i + 1 );
            }
            if ( docArray.get( i ).toLowerCase( ).contains( "<br>" ) ) {
                docArray.add( i, docArray.get( i ).toLowerCase( ).replaceAll( "<br>", "<br/>" ) );
                docArray.remove( i + 1 );
            }
            if ( docArray.get( i ).contains( "nowrap" ) ) {
                docArray.add( i, docArray.get( i ).replaceAll( "nowrap", "n=''" ) );
                docArray.remove( i + 1 );
            }
        }
        boolean skip = false;
        for ( int i = 0; i < docArray.size( ); i++ ) {
            if ( docArray.get( i ).contains( "</script" ) ) {
                skip = false;
                continue;
            }
            if ( docArray.get( i ).contains( "<script" ) ) {
                skip = true;
            }

            if ( skip ) {
                continue;
            }
            doc += docArray.get( i ) + "\n";
        }

        System.out.println( doc );
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance( );
        DocumentBuilder docBuilder = factory.newDocumentBuilder( );
        Document html = docBuilder.parse( new InputSource( new StringReader( doc ) ) );

        XPath path = XPathFactory.newInstance( ).newXPath( );
        NodeList nodes = (NodeList) path.evaluate( "//td[@id = 'cat']/b/text()", html.getDocumentElement( ), XPathConstants.NODESET );
        for ( int i = 0; i < nodes.getLength( ); i++ ) {
            System.out.println( nodes.item( i ).getNodeValue( ) );
        }
    }
}
