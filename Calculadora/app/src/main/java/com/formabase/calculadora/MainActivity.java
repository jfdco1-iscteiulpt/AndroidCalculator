package com.formabase.calculadora;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.InetSocketAddress;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final String TAG = "MainActivity";

    boolean ultima_tecla_numero=false;

    TextView visor;

    boolean primeiro = true;

    int ultimo_operando=0;
    int operando_corrente=0;

    char oper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        visor = (TextView) findViewById(R.id.tvisor);

        Button[] botoesNumericos = new Button[10];
        botoesNumericos[0] = (Button) findViewById(R.id.b0);
        botoesNumericos[1] = (Button) findViewById(R.id.b1);
        botoesNumericos[2] = (Button) findViewById(R.id.b2);
        botoesNumericos[3] = (Button) findViewById(R.id.b3);
        botoesNumericos[4] = (Button) findViewById(R.id.b4);
        botoesNumericos[5] = (Button) findViewById(R.id.b5);
        botoesNumericos[6] = (Button) findViewById(R.id.b6);
        botoesNumericos[7] = (Button) findViewById(R.id.b7);
        botoesNumericos[8] = (Button) findViewById(R.id.b8);
        botoesNumericos[9] = (Button) findViewById(R.id.b9);


        for (int i = 0; i < 10; ++i){

            final int j = i;

            botoesNumericos[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    processaValor(j);

                }
            });
        }


        //operacoes

        Button[] botoesOperacoes = new Button[6];
        botoesOperacoes[0]=(Button)findViewById(R.id.bsomar);
        botoesOperacoes[1]=(Button)findViewById(R.id.bsubtrair);
        botoesOperacoes[2]=(Button)findViewById(R.id.bdividir);
        botoesOperacoes[3]=(Button)findViewById(R.id.bmultiplicar);
        botoesOperacoes[4]=(Button)findViewById(R.id.blimpar);
        botoesOperacoes[5]=(Button)findViewById(R.id.bigual);


        char[] charOperacoes = {'+','-','/','*','c','='};

        for(int i = 0; i < charOperacoes.length;++i){

            final char operacao = charOperacoes[i];

            botoesOperacoes[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    processaOperacao(operacao);

                }
            });

        }


    }

    private void processaValor(int valor){

        operando_corrente=(operando_corrente*10)+valor;

        visor.setText("" + operando_corrente);

        ultima_tecla_numero=true;


        Log.d(TAG,"operando corrente tem o valor de " + operando_corrente);


    }

    private void processaOperacao(char operacao){

        if(operacao=='c'){

            limpar();

        }else if(operacao== '='){

            if(primeiro != true){

                executarOperacao(oper);

            }

        }else{

            if(primeiro==true){

                ultimo_operando=operando_corrente;
                primeiro=false;

            }else{
                if(ultima_tecla_numero==true)
                executarOperacao(oper);
            }

            oper=operacao;
            operando_corrente=0;


        }

        ultima_tecla_numero=false;
    }

    private void executarOperacao(char oper) {

        int res = 0;

        switch (oper){

            case '+': res = (ultimo_operando+operando_corrente);break;
            case '-': res = (ultimo_operando-operando_corrente);break;
            case '*': res = (ultimo_operando*operando_corrente);break;
            case '/': res = (ultimo_operando/operando_corrente);break;

        }

        visor.setText("" + res);
        ultimo_operando=res;

    }

    private void limpar() {

        operando_corrente=0;
        ultimo_operando=0;
        primeiro=true;
        visor.setText("0");

    }


    protected void onSaveInstanceState(Bundle outState){

        super.onSaveInstanceState(outState);

        String current = visor.getText().toString();

        outState.putString("Valor do visor",current);

        outState.putBoolean("ultima tecla numero",ultima_tecla_numero);
        outState.putBoolean("primeiro digito",primeiro);
        outState.putInt("ultimo operando utilizado",ultimo_operando);
        outState.putInt("Operador corrente",operando_corrente);
        outState.putChar("Operador utilizado",oper);

    }

    protected void onRestoreInstanceState(Bundle savedInstanceState){

        super.onRestoreInstanceState(savedInstanceState);

        String current = savedInstanceState.getString("Valor do visor");

        ultima_tecla_numero=savedInstanceState.getBoolean("ultima tecla numero");
        primeiro=savedInstanceState.getBoolean("primeiro digito");
        ultimo_operando= savedInstanceState.getInt("ultimo operando utilizado");
        operando_corrente= savedInstanceState.getInt("Operador corrente");
        oper=savedInstanceState.getChar("Operador utilizado");

        visor.setText(current);

    }
}
