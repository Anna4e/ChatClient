package anna.chatClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Anna on 25.01.2017.
 */
public class Chatroom {
    private String roomName;
    public Chatroom() {
        this.roomName = roomName;
    }

    public Chatroom(String room) {
    }

    public static void main(String[] args) {

    }
    public static void newChatRoom(String login) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter room name: ");
        String room = scanner.nextLine();
        URL object = null;
        try {
            object = new URL(Utils.getURL() + "/chatroom?room=" + room  + "&login=" + login);
            HttpURLConnection con = (HttpURLConnection) object.openConnection(); //запрос на сервер
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            OutputStream outputStream = con.getOutputStream();  //получаем ответ
            int res = con.getResponseCode();
            if (res != 200) { // 200 OK
                System.out.println("HTTP error occured: " + res);
                return;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void addUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter room name: ");
        String room = scanner.nextLine();
        System.out.println("Enter user name: ");
        String userToCr = scanner.nextLine();
        URL object = null;
        try {
            object = new URL(Utils.getURL() + "/chatroomusers?room=" + room + "&user=" + userToCr);
            HttpURLConnection con = (HttpURLConnection) object.openConnection(); //запрос на сервер
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            OutputStream outputStream = con.getOutputStream();  //получаем ответ
            int res = con.getResponseCode();
            if (res != 200) { // 200 OK
                System.out.println("HTTP error occured: " + res);
                return;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //переводим на Json
    public String toJSON(){
        GsonBuilder builder;
        builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(this);}

        //переводим из Json
    public User fromJSON(String str){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(str, User.class);
    }
}
