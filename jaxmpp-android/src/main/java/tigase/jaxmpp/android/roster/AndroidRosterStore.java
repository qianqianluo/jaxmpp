package tigase.jaxmpp.android.roster;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tigase.jaxmpp.core.client.BareJID;
import tigase.jaxmpp.core.client.Context;
import tigase.jaxmpp.core.client.xmpp.modules.ContextAware;
import tigase.jaxmpp.core.client.xmpp.modules.roster.RosterItem;
import tigase.jaxmpp.core.client.xmpp.modules.roster.RosterStore;

public class AndroidRosterStore extends RosterStore {
	
	private final RosterProvider provider;
	
	public AndroidRosterStore(RosterProvider provider) {
		this.provider = provider;
	}
	
	@Override
	protected Set<String> addItem(RosterItem item) {
		return this.provider.addItem(sessionObject, item);
	}

	@Override
	protected Set<String> calculateModifiedGroups(HashSet<String> groupsOld) {
		return groupsOld;
	}

	@Override
	public RosterItem get(BareJID jid) {
		return this.provider.getItem(sessionObject, jid);
	}

	@Override
	public List<RosterItem> getAll(Predicate predicate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCount() {
		return this.provider.getCount(sessionObject);
	}

	@Override
	public Collection<? extends String> getGroups() {
		return this.provider.getGroups(sessionObject);
	}

	@Override
	public void removeAll() {
		this.provider.removeAll(sessionObject);
	}

	@Override
	protected void removeItem(BareJID jid) {
		RosterItem item = this.provider.getItem(sessionObject, jid);
		this.provider.removeItem(sessionObject, item);
	}

}
