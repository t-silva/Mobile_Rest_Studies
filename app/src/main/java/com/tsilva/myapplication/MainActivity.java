package com.tsilva.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnBusca = findViewById(R.id.btnBusca);
        final EditText edtBuscaID = findViewById(R.id.edtID);
        final TextView txtID = findViewById(R.id.txtRespID);
        final TextView txtNome = findViewById(R.id.txtRespNome);
        final TextView txtSobrenome = findViewById(R.id.txtRespSobrenome);
        final TextView txtEmail = findViewById(R.id.txtRespEmail);
        final TextView txtGenero = findViewById(R.id.txtRespGenero);
        final TextView txtCidade = findViewById(R.id.txtRespCidade);
        final TextView txtPais = findViewById(R.id.txtRespPais);
        final TextView txtEmpresa = findViewById(R.id.txtRespEmpresa);
        final TextView txtSalario = findViewById(R.id.txtRespSalario);
        final Spinner spnPais = findViewById(R.id.spnPais);
        final Spinner spnFuncPorPais = findViewById(R.id.spnFunc);
        final List<String> funcListPorPais = new ArrayList<>();
        final List<Integer> listIdsPorPais = new ArrayList<>();
        HTTPService service = new HTTPService();
        final List<Funcionarios> funcionariosList;
        try {
            funcionariosList = service.execute().get();
            final List<String> paisList = new ArrayList<>();
            for(int i = 0;i<funcionariosList.size();i++){
                String pais = funcionariosList.get(i).getPais();
                if(!paisList.contains(pais)){
                    paisList.add(pais);
                }
            }
            ArrayAdapter<String> paisAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, paisList);
            spnPais.setAdapter(paisAdapter);
            spnPais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String paisSelected = spnPais.getSelectedItem().toString();
                    listIdsPorPais.clear();
                    funcListPorPais.clear();
                    for(int x = 0; x < funcionariosList.size();x++){

                        if(funcionariosList.get(x).getPais().equals(paisSelected)){
                            funcListPorPais.add(funcionariosList.get(x).getNome());
                            listIdsPorPais.add(funcionariosList.get(x).getId());
                        }
                        Log.i("PASSOU NO FOR",String.valueOf(x));
                    }
                    ArrayAdapter<String> funcPorPaisAdapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,funcListPorPais);
                    spnFuncPorPais.setAdapter(funcPorPaisAdapter);
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            spnFuncPorPais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Integer id = listIdsPorPais.get(i);
                   for(int y = 0; y<funcionariosList.size();y++){
                       if(funcionariosList.get(y).getId() == id) popula(funcionariosList.get(y));
                   }

                }

                private void popula(Funcionarios func) {
                    txtID.setText(String.valueOf(func.getId()));
                    txtNome.setText(func.getNome());
                    txtSobrenome.setText(func.getSobrenome());
                    txtEmail.setText(func.getEmail());
                    txtGenero.setText(func.getGenero());
                    txtCidade.setText(func.getCidade());
                    txtPais.setText(func.getPais());
                    txtEmpresa.setText(func.getEmpresa());
                    txtSalario.setText(String.valueOf(func.getSalario()));
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            btnBusca.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{
                        Integer id = Integer.valueOf(edtBuscaID.getText().toString());
                        Funcionarios func;
                        if(id != null){
                            try{
                                func = busca(id);
                                popula(func);
                            } catch (Exception e) {
                                Toast.makeText(MainActivity.this,"Nao encontrado",Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(MainActivity.this,"ID nao informado",Toast.LENGTH_SHORT).show();
                        edtBuscaID.requestFocus();
                        e.printStackTrace();
                    }



                }
                private Funcionarios busca(Integer id) {
                    for(int i = 0;i<funcionariosList.size();i++){
                        if(funcionariosList.get(i).getId() == id)
                            return funcionariosList.get(i);
                    }
                    return null;
                }
                private void popula(Funcionarios func) {
                    txtID.setText(String.valueOf(func.getId()));
                    txtNome.setText(func.getNome());
                    txtSobrenome.setText(func.getSobrenome());
                    txtEmail.setText(func.getEmail());
                    txtGenero.setText(func.getGenero());
                    txtCidade.setText(func.getCidade());
                    txtPais.setText(func.getPais());
                    txtEmpresa.setText(func.getEmpresa());
                    txtSalario.setText(String.valueOf(func.getSalario()));
                }

            });
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}