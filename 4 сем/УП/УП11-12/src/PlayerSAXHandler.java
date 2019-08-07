import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class PlayerSAXHandler extends DefaultHandler {
    private int id;
    private String name;
    private String surname;
    private String patronymic;
    private List<Player> players = new ArrayList<>();
    private String team;
    private int rating;
    private boolean isTeam;
    private boolean isRating;

    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        super.startElement(namespaceURI, localName, qName, atts);
        if (qName.equals("player")) {
            id = Integer.parseInt(atts.getValue("id"));
            name = atts.getValue("name");
            surname = atts.getValue("surname");
            patronymic = atts.getValue("patronymic");

        }
        if (qName.equals("team")) {
            isTeam = true;
        }
        if (qName.equals("rating")) {
            isRating = true;
        }

    }

    @Override
    public void endElement(String s, String s1, String qName) throws SAXException {
        super.endElement(s, s1, qName);
        if (qName.equals("player")) {
            players.add(new Player(surname, name, patronymic, id, team, rating));
        }
    }

    @Override
    public void characters(char[] chars, int i, int i1) throws SAXException {
        super.characters(chars, i, i1);
        if (isTeam) {
            team = new String(chars, i, i1);
            isTeam = false;
        }
        if (isRating) {
            String s = new String(chars, i, i1);
            rating = Integer.parseInt(s);
            isRating = false;
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

}
