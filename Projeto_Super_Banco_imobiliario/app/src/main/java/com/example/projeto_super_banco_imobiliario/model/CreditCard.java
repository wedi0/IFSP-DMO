package com.example.projeto_super_banco_imobiliario.model;

public class CreditCard {

    private static int LAST_ID = 1;
    private int id;
    private double balance;

    public CreditCard(){
        this.setId(LAST_ID+1);
        LAST_ID++;
        this.setBalance(15000);
    }

    //função de crédito
    public void creditValue(double value) {

        setBalance(getBalance() + value);

    }

    //função de debito
    public void debitValue(double value) throws InvalidValueException {
        //verificar se o saldo é maior que o valor debitado
        if (value > getBalance() ){

            throw new InvalidValueException("Saldo insuficiente");

        }else {

            setBalance(getBalance() - value);

        }

    }

    public double getBalance(){
        return balance;
    }

    public void setId( int id){
        this.id=id;
    }

    public void setBalance( double balance){
        this.balance = balance;
    }



}
