package ehu.isad;

import java.io.InputStreamReader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import com.google.gson.Gson;

public class ComboBoxExperiments extends Application  {

    //Hasieraketa
    private Txanponak txanpona=null;

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Txanponen prezioa");

        ComboBox comboBox = new ComboBox();
        comboBox.getItems().add("btc");
        comboBox.getItems().add("eth");
        comboBox.getItems().add("ltc");

        comboBox.setEditable(true);

        Text text = new Text();
        Label label=new Label();
        comboBox.getSelectionModel().selectFirst();

        try {
            txanpona = this.readFromUrl(comboBox.getValue().toString());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        text.setText("prezioa: "+txanpona.price);
        text.setX(200);
        text.setY(200);

        label.setVisible(true);
        label.setText("Txanponak: ");

        comboBox.setOnAction(e -> {

            System.out.println(comboBox.getValue());
            try {
                txanpona = this.readFromUrl(comboBox.getValue().toString());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            text.setText("prezioa: "+txanpona.price);

        });

        VBox vbox = new VBox(label,comboBox,text);
        Scene scene = new Scene(vbox, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public Txanponak readFromUrl(String txanpona) throws IOException {
        URL coin;
        Txanponak txanpon1=null;

        try {
            coin = new URL("https://api.gdax.com/products/"+txanpona+"-eur/ticker");
            URLConnection yc = coin.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            String inputLine = in.readLine();

            Gson gson = new Gson();
            txanpon1 = gson.fromJson(inputLine, Txanponak.class);

            in.close();
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        return txanpon1;
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}