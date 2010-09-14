package tigase.jaxmpp.core.client.criteria;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import tigase.jaxmpp.core.client.xml.Element;

public class ElementCriteria implements Criteria {

	public static final ElementCriteria empty() {
		return new ElementCriteria(null, null, null);
	}

	public static final ElementCriteria name(String name) {
		return new ElementCriteria(name, null, null);
	}

	public static final ElementCriteria name(String name, String xmlns) {
		return new ElementCriteria(name, new String[] { "xmlns" }, new String[] { xmlns });
	}

	public static final ElementCriteria name(String name, String[] attNames, String[] attValues) {
		return new ElementCriteria(name, attNames, attValues);
	}

	public static final ElementCriteria xmlns(String xmlns) {
		return new ElementCriteria(null, new String[] { "xmlns" }, new String[] { xmlns });
	}

	private HashMap<String, String> attrs = new HashMap<String, String>();

	private String name;

	private Criteria nextCriteria;

	public ElementCriteria(String name, String[] attname, String[] attValue) {
		this.name = name;
		if (attname != null && attValue != null) {
			for (int i = 0; i < attname.length; i++) {
				attrs.put(attname[i], attValue[i]);
			}
		}
	}

	public Criteria add(Criteria criteria) {
		if (this.nextCriteria == null) {
			this.nextCriteria = criteria;
		} else {
			Criteria c = this.nextCriteria;
			c.add(criteria);
		}
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tigase.criteria.Criteria#match(tigase.xml.Element)
	 */
	public boolean match(Element element) {
		if (name != null && !name.equals(element.getName())) {
			return false;
		}
		boolean result = true;
		Iterator<Entry<String, String>> attrIterator = this.attrs.entrySet().iterator();
		while (result && attrIterator.hasNext()) {
			Entry<String, String> entry = attrIterator.next();

			String aName = entry.getKey().toString();
			String at = "xmlns".equals(aName) ? element.getXMLNS() : element.getAttribute(aName);
			if (at != null) {
				if (at == null || !at.equals(entry.getValue())) {
					result = false;
					break;
				}
			} else {
				result = false;
			}
		}

		if (this.nextCriteria != null) {
			final List<? extends Element> children = element.getChildren();
			boolean subres = false;
			if (children != null) {
				for (Element sub : children) {
					if (this.nextCriteria.match(sub)) {
						subres = true;
						break;
					}
				}
			}
			result &= subres;
		}

		return result;
	}
}
