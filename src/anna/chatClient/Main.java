package anna.chatClient;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static jdk.nashorn.internal.objects.NativeDate.toJSON;

public class Main {
	static String login;
	static String room;

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		try {
			System.out.println("Enter your login: ");
		 login = scanner.nextLine();
			User user = new User(login);
			if(user.autoriz() != 200){
				user.setStatus(false);
				System.out.println("Autorizaition failed");
				return;
			} else {
				System.out.println("How to use:");
				System.out.println("create room - to create chat room");
				System.out.println("user to CR - invite user to chat room");
				System.out.println("exit - to logout");
				System.out.println("allusers - to get user list");

			}

			Thread th = new Thread(new GetThread());
			th.setDaemon(true);
			th.start();
			System.out.println("Enter adressee: ");
			String to = scanner.nextLine();

			System.out.println("Enter your message: ");
			while (true) {

				String text = scanner.nextLine();
				if (text.isEmpty()) {
					break;
				} else if(text.equals("exit")){
					th.interrupt();
					User.logout(login, room);
					break;
				} else if(text.equals("user list")){
					User.getUsersList();
				} else if(text.equals("create room")){
					Chatroom.newChatRoom(login);
				}else if(text.equals("user to CR")){
					Chatroom.addUser();}
					else {

					Message m = new Message(login, to, text);
					int res = m.send(Utils.getURL() + "/add");

					if (res != 200) { // 200 OK
						System.out.println("HTTP error occured: " + res);
						return;
					}
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			scanner.close();
		}
	}





}
