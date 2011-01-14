package tigase.jaxmpp.core.client.xmpp.modules.muc;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tigase.jaxmpp.core.client.JID;
import tigase.jaxmpp.core.client.xml.Element;
import tigase.jaxmpp.core.client.xml.ElementWrapper;
import tigase.jaxmpp.core.client.xml.XMLException;
import tigase.jaxmpp.core.client.xmpp.stanzas.Stanza;

public class XMucUserElement extends ElementWrapper {

	public static XMucUserElement extract(Stanza stanza) throws XMLException {
		final Element x = stanza.getChildrenNS("x", "http://jabber.org/protocol/muc#user");
		if (x == null)
			return null;
		return new XMucUserElement(x);
	}

	private final Set<Integer> statuses = new HashSet<Integer>();

	private XMucUserElement(Element element) throws XMLException {
		super(element);
		fillStatuses();
	}

	private void fillStatuses() throws XMLException {
		List<Element> sts = getChildren("status");
		if (sts != null)
			for (Element s : sts) {
				String v = s.getAttribute("code");
				if (v != null)
					statuses.add(Integer.parseInt(v));
			}
	}

	public Affiliation getAffiliation() throws XMLException {
		Element item = getFirstChild("item");
		if (item == null)
			return null;
		String tmp = item.getAttribute("affiliation");
		return tmp == null ? null : Affiliation.valueOf(tmp);
	}

	public JID getJID() throws XMLException {
		Element item = getFirstChild("item");
		if (item == null)
			return null;
		String tmp = item.getAttribute("jid");
		return tmp == null ? null : JID.jidInstance(tmp);
	}

	public String getNick() throws XMLException {
		Element item = getFirstChild("item");
		if (item == null)
			return null;
		String tmp = item.getAttribute("nick");
		return tmp;
	}

	public Role getRole() throws XMLException {
		Element item = getFirstChild("item");
		if (item == null)
			return null;
		String tmp = item.getAttribute("role");
		return tmp == null ? null : Role.valueOf(tmp);
	}

	public Set<Integer> getStatuses() {
		return statuses;
	}

}
