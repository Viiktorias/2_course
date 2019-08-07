
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class PlayerCountSAXHandler extends DefaultHandler {
    private int playerCount;
    private int totalRating;
    private boolean isParsingRank;

    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        super.startElement(namespaceURI, localName, qName, atts);
        if (qName.equals("players")) {
            playerCount = 0;
            totalRating = 0;
        }
        if (qName.equals("player")) {
            playerCount++;
        }
        if (qName.equals("rating")) {
            isParsingRank = true;
        }
    }

    @Override
    public void endElement(String s, String s1, String qName) throws SAXException {
        super.endElement(s, s1, qName);
        if (qName.equals("rating")) {
            isParsingRank = false;
        }
    }

    @Override
    public void characters(char[] chars, int i, int i1) throws SAXException {
        super.characters(chars, i, i1);
        if (isParsingRank)
            totalRating += Integer.parseInt(new String(chars, i, i1));
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public double getAvgRating() {
        return ((double) totalRating)/ playerCount;
    }
}
