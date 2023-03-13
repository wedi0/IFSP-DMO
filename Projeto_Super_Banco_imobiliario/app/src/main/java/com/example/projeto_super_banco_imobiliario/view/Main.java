package com.example.projeto_super_banco_imobiliario.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Spinner textCard1;
    private Spinner textCard2;
    private Spinner textCard3;
    private Spinner textCard4;

    private EditText textValue;

    private EditText textValueOpe;
    private Button buttonOpe;
    private Button buttonTra;
    private Button buttonValor;
    private RadioGroup grupoOp;
    private TextView mostrarSaldo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        textCard1 = findViewById(R.id.cartdsSpiner);
        textCard2 = findViewById(R.id.cartdsSpinerReceiver);
        textCard3 = findViewById(R.id.cartdsSpinerOper);
        textCard4 = findViewById(R.id.cartdsSpinerVal);
        textValue = findViewById(R.id.text_valor);
        textValueOpe = findViewById(R.id.textValor2);
        buttonOpe = findViewById(R.id.buttonOpe);
        buttonTra = findViewById(R.id.transference);
        buttonValor = findViewById(R.id.MostrarSaldo);
        grupoOp = findViewById(R.id.convert_options);
        mostrarSaldo = findViewById(R.id.text_view_converted);
        buttonOpe.setOnClickListener(this);
        buttonTra.setOnClickListener(this);
        buttonValor.setOnClickListener(this);

        //criação dos cartões
        StarBank.getInstance().startCreditCards();

        //Opções dos Spinners
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.cardId, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        textCard1.setAdapter(adapter);
        textCard2.setAdapter(adapter);
        textCard3.setAdapter(adapter);
        textCard4.setAdapter(adapter);


    }


    @Override
    public void onClick(View view) throws NumberFormatException {

        int cardId;
        double value;
        String textV;

        //ButtonsListeners
        //Transferências
        if(view == buttonTra){

            int cardId2;


            textV = textValue.getText().toString();
            cardId = textCard1.getSelectedItemPosition();
            cardId2 = textCard2.getSelectedItemPosition();

            //Tratamento de NumberFormatException
            try {
                value = Double.parseDouble(textV);
            } catch (NumberFormatException e){
                throw new RuntimeException("Error");
            }

            //recuperação dos cartões
            CreditCard player = StarBank.getInstance().getCard(cardId);
            CreditCard receiver = StarBank.getInstance().getCard(cardId2);

            //chamada de transferencia e tratamento de erros
            try {
                StarBank.getInstance().transfer(player, receiver, value);
                Toast.makeText(MainActivity.this, "Transferencia Realizada.", Toast.LENGTH_SHORT).show();

            } catch (InvalidValueException e) {
                Toast.makeText(MainActivity.this, "Saúdo Insuficente.", Toast.LENGTH_SHORT).show();
            }

            textValue.setText("");


        }

        if(view == buttonOpe){

            textV = textValueOpe.getText().toString();
            //recuperação de Id

            cardId = textCard3.getSelectedItemPosition();

            //Tratamento de NumberFormatException

            try {
                value = Double.parseDouble(textV);
            } catch (NumberFormatException e){
                throw new RuntimeException("Error");
            }

            //Recuperação de cartão
            CreditCard card = StarBank.getInstance().getCard(cardId);

            //leitura de RadioButton

            switch (grupoOp.getCheckedRadioButtonId()){

                //opcão pagar e tratamento de erros caso saudo insuficiente
                case R.id.pagar:

                    try {
                        StarBank.getInstance().pay(card, value);
                        Toast.makeText(MainActivity.this, "Pagamento realizado.", Toast.LENGTH_SHORT).show();

                    } catch (InvalidValueException e) {
                        Toast.makeText(MainActivity.this, "Saúdo insuficiente.", Toast.LENGTH_SHORT).show();
                    }

                    textValueOpe.setText("");

                    break;

                //opcão receber

                case R.id.receber:

                    StarBank.getInstance().receive(card,value);
                    Toast.makeText(MainActivity.this, "Valor recebido.", Toast.LENGTH_SHORT).show();

                    textValueOpe.setText("");


                    break;

                //opcão Rodada completa
                case R.id.complet_rodad:

                    StarBank.getInstance().roundCompleted(card, value);
                    Toast.makeText(MainActivity.this, "Valor recebido.", Toast.LENGTH_SHORT).show();

                    textValueOpe.setText("");


                    break;

            }

        }

        //Visualização do saúdo dos cartões
        if(view == buttonValor){

            cardId = textCard4.getSelectedItemPosition();
            CreditCard cardValor = StarBank.getInstance().getCard(cardId);


            mostrarSaldo.setText(String.format("$ %.2f", cardValor.getBalance()));

        }

    }



}
