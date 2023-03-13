package com.example.projeto_super_banco_imobiliario.model;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class StarBank {

    private ArrayList<CreditCard> cards = new ArrayList<>();

    private static StarBank instance = null;

    private StarBank(){}

    public static StarBank getInstance() {
        if(instance == null){
            instance = new StarBank();
        }
        return instance;
    }

    public void startCreditCards(){
        //criação de 6 cartões
        for(int i = 0; i<6; i++){
            cards.add(new CreditCard());
        }

    }

    //Recuperar um cartão na lista de cartões pelo seu ID;
    public CreditCard getCard( int id){
        CreditCard card;

        card = cards.get(id);

        return card;
    }


    public void roundCompleted(CreditCard card, double value){
        card.creditValue(value);
    }


    public boolean transfer (CreditCard player, CreditCard receiver, double value) throws InvalidValueException{
        //variaveis de controle de transferencia
        double playerIncial = player.getBalance();
        double receiverInicial = receiver.getBalance();

        //transferencia
        player.debitValue(value);
        receiver.creditValue(value);

        //confirmação de transferencia
        if( player.getBalance() == playerIncial && receiver.getBalance() == receiverInicial){
            return false;
        }

        return true;
    }

    public void receive(CreditCard card, double value){
        card.creditValue(value);
    }

    public boolean pay(CreditCard card, double value) throws InvalidValueException {
        //variaveis de controle
        double valoInicial = card.getBalance();
        card.debitValue(value);

        //confirmação de pagamento
        if(valoInicial != card.getBalance()){

            return true;
        }


        return false;
    }
}

