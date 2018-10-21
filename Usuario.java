import java.util.LinkedList;
import java.io.Serializable;

public class Usuario implements Serializable {

	private String userName;
    private String senha;
    private LinkedList<Mensagem> listaDeMensagens;

    public Usuario(String user, String psw) {
        userName = user;
        this.senha = psw;
        listaDeMensagens = new LinkedList<Mensagem>();
    }

    public String getUserName() {
        return this.userName;
    }

    public String getPassword() {
        return this.senha;
    }

    public Mensagem getMessage () {
        if(listaDeMensagens.isEmpty()) {
            return new Mensagem(null, null, null);
        }
        return listaDeMensagens.pop();
    }

    public boolean addMessage(Mensagem message) {
        return listaDeMensagens.add(message);
    }

    public int numberOfMessages() {
        if(listaDeMensagens.isEmpty()) {
            return 0;
        }
        return listaDeMensagens.size();
    }
}