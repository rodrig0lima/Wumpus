/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wumpus;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import wumpus.agente.Agente;
import wumpus.ambiente.Ambiente;

/**
 *
 * @author Fabio
 */
public class Wumpus extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Iniciar");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                Ambiente ambiente = new Ambiente(4,4,3,1);
                Agente agente = new Agente(ambiente);
                  agente.buscarOuro();
//                amb.getPercepcao();
//                amb.avancar();
//                amb.getPercepcao();
//                amb.girar(0);
//                amb.avancar();
//                amb.getPercepcao();
//                amb.girar(1);
//                amb.girar(1);
//                amb.avancar();
//                amb.getPercepcao();
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Mundo do Wumpus!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
