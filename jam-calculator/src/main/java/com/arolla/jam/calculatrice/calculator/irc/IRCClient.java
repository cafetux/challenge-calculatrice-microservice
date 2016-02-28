package com.arolla.jam.calculatrice.calculator.irc;

import com.arolla.jam.calculatrice.calculator.Calculator;
import com.arolla.jam.calculatrice.calculator.calcul.Addition;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.Socket;

/**
 * Created by MicroOnde on 21/02/2016.
 */
public class IRCClient {


    private BufferedReader reader;
    private BufferedWriter writer;

    private final String channel;
    private final String server;
    private final String login;
    private final Calculator calculator;


    public IRCClient(String server, String channel, String login, Calculator calculator) {
        this.channel = channel;
        this.server = server;
        this.login = login;
        this.calculator = calculator;
    }

    public static void main(String[] args) throws Exception {

        final String SERVER = "irc.freenode.net";
        final String CHANNEL = "#jam_events";
        final String LOGIN = "my_simple_bot";

        // The server to connect to and our details.
        final Calculator calculator = new Calculator(new Addition());
        final IRCClient ircClient = new IRCClient(SERVER, CHANNEL, LOGIN, calculator);
        ircClient.connectToServer();
        ircClient.chooseUserNick();
        ircClient.logConnectionAttempt();
        ircClient.joinChannel();
        ircClient.manageChat();
    }

    private void manageChat() throws IOException {
        String line;
        // Keep reading lines from the server.
        while ((line = reader.readLine()) != null) {
//            if (line.toUpperCase().startsWith("PING ")) {
//                respondToPing(line);
//            } else {
            parseForCalculation(line);
//                Print the raw line received by the bot.
//                System.out.println(line);
//            }
        }
    }

    private void parseForCalculation(String line) throws IOException {
        if (line.contains("PRIVMSG " + channel + " :")) {
            final String command = line.replaceFirst(".+? PRIVMSG " + channel + " :", "");
            respondToCalculation(command);
        }
    }

    private void respondToCalculation(String command) throws IOException {
        if (StringUtils.isNotBlank(command)) {
            final String result = calculator.calculate(command);
            if (StringUtils.isNotBlank(result)) {
                writer.write("PRIVMSG " + channel + " :" + result + "\r\n");
                writer.flush();
            }
        }
    }

    private void respondToPing(String line) throws IOException {
        // We must respond to PINGs to avoid being disconnected.
        writer.write("PONG " + line.substring(5) + "\r\n");
        writer.write("PRIVMSG " + channel + " :I got pinged!\r\n");
        writer.flush();
    }

    private void joinChannel() throws IOException {
        // Join the channel.
        writer.write("JOIN " + channel + "\r\n");
        writer.flush();
    }

    private void chooseUserNick() throws IOException {
        // Log on to the server.
        writer.write("NICK " + login + "\r\n");
        writer.write("USER " + login + " 8 * : Java IRC Hacks Bot\r\n");
        writer.flush();
    }

    private void connectToServer() throws IOException {
        // Connect directly to the IRC server.
        Socket socket = new Socket(server, 6667);
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    private void logConnectionAttempt() throws IOException {
        // Read lines from the server until it tells us we have connected.
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.indexOf("004") >= 0) {
                // We are now logged in.
                return;
            } else if (line.indexOf("433") >= 0) {
//                System.out.println("Nickname is already in use.");
                throw new RuntimeException("Nickname is already in use.");
            }
        }
    }


}
