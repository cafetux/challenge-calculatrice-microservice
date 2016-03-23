package com.arolla.jam.calculatrice.infra.irc.stub;

import com.arolla.jam.calculatrice.model.operation.Operateur;

import java.util.Random;

/**
 * Created by cafetux on 29/02/2016.
 */
public class MainBotUser {

    public static final String HOST = "irc.freenode.net";
    public static final String INPUT_CHANNEL = "#jam_input";
    private static Random random = new Random();

    public static void main(String[] args) {
        try {
            UserBot bot = new UserBot(HOST, INPUT_CHANNEL, "AlfredBot");
            bot.joinChannel(INPUT_CHANNEL);
            Operateur[] operateurs = Operateur.values();
            while(true){
                StringBuilder operation = new StringBuilder(String.valueOf(random.nextInt(100)));
                int nbOperande=random.nextInt(6)+1;
                for(int operandeIndex=0;operandeIndex<nbOperande;operandeIndex++){
                    String operateur = operateurs[random.nextInt(operateurs.length)].signe();
                    operation.append(" ").append(operateur);
                    int operande = random.nextInt(100);
                    operation.append(" ").append(operande);
                }
                bot.say(operation.toString());
                Thread.sleep(random.nextInt(30)*1000);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
