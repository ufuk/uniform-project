package uniform.util.email;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SmtpServerConfiguration {

	private String starttlsEnable = "false";

	private String host = "";

	private String port = "";

	private String authentication = "false";

	private String user = "";

	private String password = "";

	public String getStarttlsEnable() {
		return starttlsEnable;
	}

	public String getHost() {
		return host;
	}

	public String getPort() {
		return port;
	}

	public String getAuthentication() {
		return authentication;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	private File configurationFile;

	public SmtpServerConfiguration(File configurationFile) {
		this.configurationFile = configurationFile;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(configurationFile);
			doc.getDocumentElement().normalize();

			NodeList nodeList = doc.getElementsByTagName("smtp-configuration");

			Element element = (Element) nodeList.item(0);

			starttlsEnable = getTagValue("starttls-enable", element);
			host = getTagValue("host", element);
			port = getTagValue("port", element);
			authentication = getTagValue("authentication", element);
			user = getTagValue("user", element);
			password = getTagValue("password", element);

			if (!(starttlsEnable.equals("true") || starttlsEnable
					.equals("false"))) {
				starttlsEnable = "false";
			}
			if (!(authentication.equals("true") || authentication
					.equals("false"))) {
				authentication = "false";
			}

		} catch (IOException e) {
			System.out.println("E-posta konfigürasyon dosyasý bulunamadý! Açýklama: " + e.getMessage());
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	private String getTagValue(String tagName, Element element) {
		NodeList nodeList = element.getElementsByTagName(tagName).item(0)
				.getChildNodes();
		if (nodeList.item(0) == null) {
			return "";
		}
		return ((Node) nodeList.item(0)).getNodeValue();
	}

	public Properties configure() {
		Properties props = System.getProperties();
		props.put("mail.smtp.starttls.enable", starttlsEnable);
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.auth", authentication);
		props.put("mail.smtp.user", user);
		props.put("mail.smtp.password", password);

		return props;
	}

	public Boolean modifyConfiguration(String starttlsEnable, String host,
			String port, String authentication, String user, String password) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(configurationFile);

			doc.getElementsByTagName("smtp-configuration");

			Element element = (Element) doc.getElementsByTagName(
					"smtp-configuration").item(0);

			NodeList nodeList = element.getElementsByTagName("starttls-enable")
					.item(0).getChildNodes();
			if (nodeList.item(0) != null) {
				nodeList.item(0).setNodeValue(starttlsEnable);
			} else {
				element.getElementsByTagName("starttls-enable").item(0)
						.appendChild(doc.createTextNode(starttlsEnable));
			}

			nodeList = element.getElementsByTagName("host").item(0)
					.getChildNodes();
			if (nodeList.item(0) != null) {
				nodeList.item(0).setNodeValue(host);
			} else {
				element.getElementsByTagName("host").item(0)
						.appendChild(doc.createTextNode(host));
			}

			nodeList = element.getElementsByTagName("port").item(0)
					.getChildNodes();
			if (nodeList.item(0) != null) {
				nodeList.item(0).setNodeValue(port);
			} else {
				element.getElementsByTagName("port").item(0)
						.appendChild(doc.createTextNode(port));
			}

			nodeList = element.getElementsByTagName("authentication").item(0)
					.getChildNodes();
			if (nodeList.item(0) != null) {
				nodeList.item(0).setNodeValue(authentication);
			} else {
				element.getElementsByTagName("authentication").item(0)
						.appendChild(doc.createTextNode(authentication));
			}

			nodeList = element.getElementsByTagName("user").item(0)
					.getChildNodes();
			if (nodeList.item(0) != null) {
				nodeList.item(0).setNodeValue(user);
			} else {
				element.getElementsByTagName("user").item(0)
						.appendChild(doc.createTextNode(user));
			}

			nodeList = element.getElementsByTagName("password").item(0)
					.getChildNodes();
			if (nodeList.item(0) != null) {
				nodeList.item(0).setNodeValue(password);
			} else {
				element.getElementsByTagName("password").item(0)
						.appendChild(doc.createTextNode(password));
			}

			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			doc.setXmlStandalone(true);
			doc.normalizeDocument();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(configurationFile.getPath());
			transformer.transform(source, result);
			return true;
		} catch (Exception e) {
			// e.printStackTrace();
			return false;
		}
	}

}
