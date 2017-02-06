package anna.chatClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Created by Anna on 24.01.2017.
 */
public class User {
    private  String login;
    private boolean status;
    private String room;

    public User(String login) {
        this.login = login;
        this.status = true;
        this.room = room;

    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    //процесс авторизации со стороны клиента
    public int autoriz() throws IOException {

        URL object = new URL(Utils.getURL() + "/autoriz?login=" + this.login + "/room="+ room);
         HttpURLConnection con = (HttpURLConnection) object.openConnection(); //запрос на сервер
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        OutputStream outputStream = con.getOutputStream();  //получаем ответ
        try {
            String json = toJSON();
            outputStream.write(json.getBytes(StandardCharsets.UTF_8));
            return con.getResponseCode();
        } finally {
            outputStream.close();
        }
    }
    public static void logout(String login, String room) {
        URL object = null;
        try {
            object = new URL(Utils.getURL() + "/loguot?login=" + login + "/room=" + room);
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
    public static void getUsersList() {
        URL object = null;
        try {
            object = new URL(Utils.getURL() + "/userlist");
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
