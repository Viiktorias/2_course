import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static List<Player> parseDOM(File file) throws IOException, SAXException, ParserConfigurationException {
        List<Player> players = new ArrayList<>();
        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        f.setValidating(false);

        DocumentBuilder builder = f.newDocumentBuilder();
        Document doc = builder.parse(file);
        Node root = doc.getFirstChild();
        NodeList items = root.getChildNodes();
        for (int i = 0; i < items.getLength(); ++i) {
            Node node = items.item(i);
            if (!node.getNodeName().equals("player"))
                continue;
            Player curPlayer = new Player();
            curPlayer.setId(Integer.parseInt(node.getAttributes().getNamedItem("id").getTextContent()));
            curPlayer.setSurname((node.getAttributes().getNamedItem("surname").getTextContent()));
            curPlayer.setName((node.getAttributes().getNamedItem("name").getTextContent()));
            curPlayer.setPatronymic((node.getAttributes().getNamedItem("patronymic").getTextContent()));
            NodeList fields = node.getChildNodes();
            for (int j = 0; j < fields.getLength(); ++j) {
                switch (fields.item(j).getNodeName()) {
                    case "team":
                        curPlayer.setTeam(fields.item(j).getTextContent());
                        break;
                    case "rating":
                        curPlayer.setRating(Integer.parseInt(fields.item(j).getTextContent()));
                        break;

                }
            }
            players.add(curPlayer);
        }
        return players;
    }

    public static List<Player> parseSAX(File file) throws IOException, SAXException, ParserConfigurationException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        PlayerSAXHandler playerHandler = new PlayerSAXHandler();
        parser.parse(file, playerHandler);
        return playerHandler.getPlayers();
    }

    public static void validate(File file) throws IOException, SAXException {
        SchemaFactory fact = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        Schema schema = fact.newSchema(PlayerModel.class.getClassLoader().getResource("schema.xsd"));
        Validator validator = schema.newValidator();
        validator.validate(new StreamSource(file));
    }

    public static void saveToXML(File file, List<Player> players) throws IOException {
        try (PrintWriter pw = new PrintWriter(file, StandardCharsets.UTF_8)) {
            pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            StringBuilder sb = new StringBuilder();
            sb.append("<players>\n");
            for (Player player : players) {
                sb.append("\t<player id=\"").append(player.getId()).append("\" surname=\"").append(player.getSurname()).append("\" name=\"").append(player.getName()).append("\" patronymic=\"").append(player.getPatronymic()).append("\">\n");
                sb.append("\t\t<team>").append(player.getTeam()).append("</team>\n").append("\t\t<rating>").append(player.getRating()).append("</rating>\n");
                sb.append("\t</player>\n");
            }
            sb.append("</players>\n");
            pw.println(sb);
        }
    }

    public static StringBuilder count(File file) throws IOException, SAXException, ParserConfigurationException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        PlayerCountSAXHandler playerHandler = new PlayerCountSAXHandler();
        parser.parse(file, playerHandler);
        StringBuilder answer = new StringBuilder();
        answer.append("Количество игроков: ").append(playerHandler.getPlayerCount()).append('\n');
        answer.append("Средний рейтинг игрока: ").append(playerHandler.getAvgRating());
        return answer;
    }

    public static List<Player> loadFromBinary(File file) throws IOException, ClassNotFoundException {
        ObjectInputStream stream = new ObjectInputStream(new FileInputStream(file));
        List<Player> players = (List<Player>) stream.readObject();
        stream.close();
        return players;
    }

    public static void saveToBinary(File file, List<Player> players) throws IOException {
        ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file));
        stream.writeObject(players);
        stream.close();
    }

    public static void convert(File source, File target, URL xsltURL) throws IOException, TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Source styleSource = new StreamSource(xsltURL.openStream(), xsltURL.toExternalForm());
        Transformer transformer = factory.newTransformer(styleSource);
        transformer.transform(new StreamSource(source), new StreamResult(target));

    }
}
