package org.zenframework.z8.server.engine;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.ConnectException;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;

import org.zenframework.z8.server.types.datespan;

public class ServerInfo implements IServerInfo {

	private static final long serialVersionUID = 5011706173964296365L;
	private static final long TenMinutes = 10 * datespan.TicksPerMinute;
	private static final long ThreeDays = 3 * datespan.TicksPerDay;

	private IApplicationServer server;
	private String id;
	private String[] domains;

	private long lastChecked = 0;
	
	public ServerInfo() {
	}

	public ServerInfo(IApplicationServer server, String id) {
		this.server = server;
		this.id = id;
	}

	public ServerInfo(IApplicationServer server, String[] domains) {
		this.server = server;
		this.domains = domains;
	}

	public IApplicationServer getServer() {
		return server;
	}

	public String getId() {
		return id;
	}

	public String[] getDomains() {
		return domains;
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		serialize(out);
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		deserialize(in);
	}

	public boolean isAlive() throws RemoteException {
		if(lastChecked != 0 && System.currentTimeMillis() - lastChecked < TenMinutes)
			return false;
		
		try {
			server.probe();
			lastChecked = 0;
			return true;
		} catch(NoSuchObjectException e) {
		} catch(ConnectException e) {
		}
		
		lastChecked = System.currentTimeMillis();
		return false;
	}

	public boolean isDead() throws RemoteException {
		return System.currentTimeMillis() - lastChecked > ThreeDays;
	}

	@Override
	public void serialize(ObjectOutputStream out) throws IOException {
		RmiIO.writeLong(out, serialVersionUID);

		RmiIO.writeString(out, id);
		out.writeObject(domains);
		out.writeObject(server);
	}

	@Override
	public void deserialize(ObjectInputStream in) throws IOException, ClassNotFoundException {
		@SuppressWarnings("unused")
		long version = RmiIO.readLong(in);

		id = RmiIO.readString(in);
		domains = (String[])in.readObject();
		server = (IApplicationServer)in.readObject();
	}

	public String toString() {
		return "[id: " + id + ", " + server.toString() + "]";
	}
}
