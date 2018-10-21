
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;

public class Server implements Correio {

	private LinkedList<Usuario> userList;

    public Server() {
		userList = new LinkedList<Usuario>();
	}
	
	private Usuario findUser(String userName) {
		return userList.stream().filter(lambdaUser -> lambdaUser.getUserName().contains(userName)).findFirst()
				.orElse(null);
	}

	public boolean cadastrarUsuario(Usuario u) {
		Usuario user = findUser(u.getUserName());
		if (user != null) {
			return false;
		}
		return userList.add(u);
	}

	public Mensagem getMensagem(String userName, String senha) {
		Usuario user = findUser(userName);
		if(user != null) {
			if (user.getPassword().equals(senha)) {
				return user.getMessage();
			}
		}
		return null;
	}

	public int getNMensagens(String userName, String senha) {
		Usuario user = findUser(userName);
		if(user != null) {
			if (user.getPassword().equals(senha)) {
				return user.numberOfMessages();
			}
		}
		return -1;
	}

	public boolean sendMensagem(Mensagem m, String senha, String userNameDestinatario) {
		Usuario userReceiver = findUser(userNameDestinatario);
		Usuario userSender = findUser(m.getSenderName());

		if (userReceiver != null && userSender != null) {
			if (userSender.getPassword().equals(senha)) {
				return userReceiver.addMessage(m);
			}
		}
		return false;

	}

	public static void main (String args[]) {
		
		try {
            System.setProperty("java.rmi.server.hostname","127.0.0.1");
			java.rmi.registry.LocateRegistry.createRegistry(1099); //linha necessária apenas para Windows

			//Create and export a remote object
			Server obj = new Server();
			Correio stub = (Correio) UnicastRemoteObject.exportObject(obj,0);
			
			//Register the remote object with a Java RMI registry
			Registry registry = LocateRegistry.getRegistry();
			registry.rebind("Correio", stub);
		
			System.out.println("Server Ready");
		}
			catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}
}
