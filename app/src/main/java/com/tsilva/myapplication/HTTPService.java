package com.tsilva.myapplication;

import android.os.AsyncTask;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HTTPService extends AsyncTask<Void,Void, List<Funcionarios>> {
    @Override
    protected List<Funcionarios> doInBackground(Void... voids) {
        List<Funcionarios> funcionariosList = new ArrayList<>();
        try {
            URL url = new URL("http://files.cod3r.com.br/curso-js/funcionarios.json");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir Conexão
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept","application/json");
            connection.connect();
            JsonParser jsonParser = new JsonParser();
            InputStream inputStream = (InputStream) connection.getContent();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            JsonElement jsonElement = jsonParser.parse(inputStreamReader);
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            Gson gson = new Gson();
            for (int i = 0;i<jsonArray.size();i++){
                funcionariosList.add(gson.fromJson(jsonArray.get(i),Funcionarios.class));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return funcionariosList;
    }
}
