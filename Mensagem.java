import java.util.Date;
import java.io.Serializable;

public class Mensagem implements Serializable {
    private String userNameRemetente;
    private String titulo;
    private String texto;
    private Date data;

    public Mensagem(String userSender, String title, String text) {
        userNameRemetente = userSender;
        titulo = title;
        texto = text;
        data = new Date();
    }

    public String getSenderName() {
        return userNameRemetente;
    }

    public String getTitle() {
        return titulo;
    }

    public String getText() {
        return texto;
    }

    public Date getDate() {
        return data;
    }
}
