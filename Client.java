import java.rmi.registry.Registry;
import java.util.Scanner;
import java.rmi.registry.LocateRegistry;

public class Client {

    private Client() {}

	public static void main (String args[]) {
		String host = (args.length < 1) ? null : args[0];
        Scanner input = new Scanner( System.in );

		try {
						
			Registry registry = LocateRegistry.getRegistry(host);
			Correio stub = (Correio) registry.lookup("Correio");
			int option;
			do {
				String userName;
				String psw;
				System.out.println("1 - Cadastrar um novo usuario");
				System.out.println("2 - Receber nova mensagem");
				System.out.println("3 - Enviar nova mensagem");
				System.out.println("4 - Conferir numero de mensagens no Correio");
				System.out.println("0 - Sair");
				System.out.print("Escolha uma acao: ");
				option = input.nextInt();
				switch (option) {
					// Cadastrar Usuário
					case 1:
						System.out.print("Digite nome de usuario: ");
						userName = input.next();
						System.out.print("Digite a senha: ");
						psw = input.next(); 
						Usuario u = new Usuario(userName, psw);
						boolean userExists = stub.cadastrarUsuario(u);
						System.out.println();
						if(!userExists) {
							System.out.println("Nome de usuario ja cadastrado");
						} else {
							System.out.println("Usuario cadastrado com sucesso!");
						}
						System.out.println();
						break;
					// Receber Mensagem
					case 2:
						System.out.print("Digite nome de usuario: ");
						userName = input.next();
						System.out.print("Digite sua senha: ");
						psw = input.next();
						Mensagem message = stub.getMensagem(userName, psw);
						if(message == null) {
							System.out.println("Nome de usuario ou senha inválidos");
						} else {
							if(message.getTitle() == null) {
								System.out.println("Nao existem novas mensagens!");
							} else {
								System.out.println();
								System.out.println("Titulo: " + message.getTitle());
								System.out.println("Corpo: " + message.getText());
								System.out.println("Enviado por: " + message.getSenderName() + " em " + message.getDate());
							}
						}
						System.out.println();
						break;
					// Enviar Mensagem 
					case 3:
						System.out.print("Digite o titulo da mensagem: ");
						input.nextLine();
						String title = input.nextLine();
						System.out.print("Digite o corpo da mensagem: ");
						String text = input.nextLine();
						System.out.print("Digite o usuario destinatario: ");
						String userNameReceiver = input.nextLine();
						System.out.print("Digite seu nome de usuario: ");
						String userSender = input.nextLine();
						System.out.print("Digite sua senha: ");
						psw = input.nextLine();

						Mensagem m = new Mensagem(userSender, title, text);
						if(stub.sendMensagem(m, psw, userNameReceiver)) {
							System.out.println("Mensagem enviada com sucesso!");
						} else {
							System.out.println("Ocorreu um erro, tente outra vez.");
						}
						System.out.println();
						break;
					// Conferir numero de mensagens no Correio
					case 4:
						System.out.print("Digite nome de usuario: ");
						userName = input.next();
						System.out.print("Digite sua senha: ");
						psw = input.next();
						int numberOfMessages = stub.getNMensagens(userName, psw);
						System.out.println();
						if (numberOfMessages == -1) {
							System.out.println("Usuario invalido");
						} else {
							System.out.println("O numero de mensagens no seu Correio e: " + numberOfMessages);
						}
						System.out.println();
						break;
						// Sair
						case 0: 
						System.out.println("Tchau, ate a proxima!");
						break;
						
						default:
						System.out.println("Por favor, digite um numero valido!");
						System.out.println();
						break;
				}
				System.out.println("Aperte enter para continuar");
				input.nextLine();
				input.nextLine();
			} while(option != 0);		
		}
		catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}
}