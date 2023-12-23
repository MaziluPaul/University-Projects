package com.example.a4mazilupaul;


import Domain.Pacient;
import Domain.PacientFactory;
import Domain.Programare;
import Domain.ProgramareFactory;
import Main.Settings;
import Repository.*;
import Service.Service;
import UI.UI;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    TextField idPacientTextField = new TextField();
    TextField numePacientTextField = new TextField();
    TextField prenumePacientTextField = new TextField();
    TextField varstaPacientTextField = new TextField();

    TextField idProgramareTextField = new TextField();
    TextField idPacientProgramareTextField = new TextField();
    TextField dataProgramareTextField = new TextField();
    TextField oraProgramareTextField = new TextField();
    TextField scopProgramareTextField = new TextField();

    @Override
    public void start(Stage stage) throws IOException {
        IRepository<Pacient> pacientRepository = null;
        IRepository<Programare> programareRepository = null;
        Settings setari = Settings.getInstance();

        if (Objects.equals(setari.getRepoType(), "memory")) {
            pacientRepository = new MemoryRepository<>();
            programareRepository = new MemoryRepository<>();
        }

        if (Objects.equals(setari.getRepoType(), "text")) {
            pacientRepository = new TextFileRepository<>(setari.getRepoFile1(), new PacientFactory());
            programareRepository = new TextFileRepository<>(setari.getRepoFile2(), new ProgramareFactory());
        }

        if (Objects.equals(setari.getRepoType(), "binary")) {
            pacientRepository = new BinaryFileRepository<>(setari.getRepoFile1());
            programareRepository = new BinaryFileRepository<>(setari.getRepoFile2());
        }

        if(Objects.equals(setari.getRepoType(), "database")){
            pacientRepository = new PacientDbRepository();
            programareRepository = new ProgramareDbRepository();
        }

        Service service = new Service(pacientRepository, programareRepository);

        HBox mainHorizontalBox = new HBox();
        mainHorizontalBox.setPadding(new Insets(10));

        VBox pacientiVerticalBox = new VBox();
        pacientiVerticalBox.setPadding(new Insets(10));
        mainHorizontalBox.getChildren().add(pacientiVerticalBox);

        ObservableList<Pacient> pacienti = FXCollections.observableArrayList(service.getAllPacienti());
        ObservableList<Programare> programari = FXCollections.observableArrayList(service.getAllProgramari());
        ListView<Pacient> pacientiListView = new ListView<>();
        pacientiListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Pacient pacient = pacientiListView.getSelectionModel().getSelectedItem();
                idPacientTextField.setText(Integer.toString(pacient.getId()));
                numePacientTextField.setText(pacient.getNume());
                prenumePacientTextField.setText(pacient.getPrenume());
                varstaPacientTextField.setText(Integer.toString(pacient.getVarsta()));
            }
        });
        pacientiVerticalBox.getChildren().add(pacientiListView);


        GridPane pacientiGridPane = new GridPane();

        Label idPacientLabel = new Label();
        idPacientLabel.setText("ID: ");
        idPacientLabel.setPadding(new Insets(10, 5, 10, 0));

        Label numePacientLable = new Label();
        numePacientLable.setText("Nume: ");
        numePacientLable.setPadding(new Insets(10, 5, 10, 0));

        Label prenumePacientLable = new Label();
        prenumePacientLable.setText("Prenume: ");
        prenumePacientLable.setPadding(new Insets(10, 5, 10, 0));

        Label varstaPacientLable = new Label();
        varstaPacientLable.setText("Varsta: ");
        varstaPacientLable.setPadding(new Insets(10, 5, 10, 0));

        pacientiGridPane.add(idPacientLabel, 0, 0);
        pacientiGridPane.add(idPacientTextField, 1, 0);

        pacientiGridPane.add(numePacientLable, 0, 1);
        pacientiGridPane.add(numePacientTextField, 1, 1);

        pacientiGridPane.add(prenumePacientLable, 0, 2);
        pacientiGridPane.add(prenumePacientTextField, 1, 2);

        pacientiGridPane.add(varstaPacientLable, 0, 3);
        pacientiGridPane.add(varstaPacientTextField, 1, 3);

        pacientiVerticalBox.getChildren().add(pacientiGridPane);

        HBox pacientButtonsHorizontalBox = new HBox();
        pacientiVerticalBox.getChildren().add(pacientButtonsHorizontalBox);

        Button addPacientButton = new Button("Add pacient");
        addPacientButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    int id = Integer.parseInt(idPacientTextField.getText());
                    String nume = numePacientTextField.getText();
                    String prenume = prenumePacientTextField.getText();
                    int varsta = Integer.parseInt(varstaPacientTextField.getText());

                    service.addPacient(id, nume, prenume, varsta);
                    pacienti.setAll(service.getAllPacienti());
                } catch (Exception e) {
                    Alert errorPopUp = new Alert(Alert.AlertType.ERROR);
                    errorPopUp.setTitle("Error");
                    errorPopUp.setContentText(e.getMessage());
                    errorPopUp.show();
                }
            }
        });
        pacientButtonsHorizontalBox.getChildren().add(addPacientButton);


        Button deletePacientButton = new Button("Delete pacient");
        deletePacientButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    int id = Integer.parseInt(idPacientTextField.getText());

                    service.deletePacient(id);
                    pacienti.setAll(service.getAllPacienti());
                    programari.setAll(service.getAllProgramari());
                } catch (Exception e) {
                    Alert errorPopUp = new Alert(Alert.AlertType.ERROR);
                    errorPopUp.setTitle("Error");
                    errorPopUp.setContentText(e.getMessage());
                    errorPopUp.show();
                }
            }
        });
        pacientButtonsHorizontalBox.getChildren().add(deletePacientButton);

        Button updatePacientButton = new Button("Update pacient");
        updatePacientButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    int id = Integer.parseInt(idPacientTextField.getText());
                    String nume = numePacientTextField.getText();
                    String prenume = prenumePacientTextField.getText();
                    int varsta = Integer.parseInt(varstaPacientTextField.getText());

                    service.updatePacient(id, nume, prenume, varsta);
                    pacienti.setAll(service.getAllPacienti());
                } catch (Exception e) {
                    Alert errorPopUp = new Alert(Alert.AlertType.ERROR);
                    errorPopUp.setTitle("Error");
                    errorPopUp.setContentText(e.getMessage());
                    errorPopUp.show();
                }
            }
        });
        pacientButtonsHorizontalBox.getChildren().add(updatePacientButton);


/// ---------------- GUI Programari ----------------------
        VBox programariVerticalBox = new VBox();
        programariVerticalBox.setPadding(new Insets(10));
        mainHorizontalBox.getChildren().add(programariVerticalBox);

//        ObservableList<Programare> programari = FXCollections.observableArrayList(service.getAllProgramari());
        ListView<Programare> programariListView = new ListView<>();
        programariListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Programare programare = programariListView.getSelectionModel().getSelectedItem();
                idProgramareTextField.setText(Integer.toString(programare.getId()));
                idPacientProgramareTextField.setText(Integer.toString(programare.getPacient().getId()));
                dataProgramareTextField.setText(programare.getData());
                oraProgramareTextField.setText(programare.getOra());
                scopProgramareTextField.setText(programare.getScop());
            }
        });
        programariVerticalBox.getChildren().add(programariListView);


        GridPane programariGridPane = new GridPane();

        Label idProgramareLabel = new Label();
        idProgramareLabel.setText("ID: ");
        idProgramareLabel.setPadding(new Insets(10, 5, 10, 0));

        Label idPacientProgramareLable = new Label();
        idPacientProgramareLable.setText("ID pacient: ");
        idPacientProgramareLable.setPadding(new Insets(10, 5, 10, 0));

        Label dataProgramareLable = new Label();
        dataProgramareLable.setText("Data: ");
        dataProgramareLable.setPadding(new Insets(10, 5, 10, 0));

        Label oraProgramareLable = new Label();
        oraProgramareLable.setText("Ora: ");
        oraProgramareLable.setPadding(new Insets(10, 5, 10, 0));

        Label scopProgramareLable = new Label();
        scopProgramareLable.setText("Scop: ");
        scopProgramareLable.setPadding(new Insets(10, 5, 10, 0));

        programariGridPane.add(idProgramareLabel, 0, 0);
        programariGridPane.add(idProgramareTextField, 1, 0);

        programariGridPane.add(idPacientProgramareLable, 0, 1);
        programariGridPane.add(idPacientProgramareTextField, 1, 1);

        programariGridPane.add(dataProgramareLable, 0, 2);
        programariGridPane.add(dataProgramareTextField, 1, 2);

        programariGridPane.add(oraProgramareLable, 0, 3);
        programariGridPane.add(oraProgramareTextField, 1, 3);

        programariGridPane.add(scopProgramareLable, 0, 4);
        programariGridPane.add(scopProgramareTextField, 1, 4);

        programariVerticalBox.getChildren().add(programariGridPane);

        HBox programareButtonsHorizontalBox = new HBox();
        programariVerticalBox.getChildren().add(programareButtonsHorizontalBox);

        Button addProgramareButton = new Button("Add programare");
        addProgramareButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    int id = Integer.parseInt(idProgramareTextField.getText());
                    int id_pacient = Integer.parseInt(idPacientProgramareTextField.getText());
                    String data = dataProgramareTextField.getText();
                    String ora = oraProgramareTextField.getText();
                    String scop = scopProgramareTextField.getText();

                    service.addProgramare(id, id_pacient, data, ora, scop);
                    programari.setAll(service.getAllProgramari());
                } catch (Exception e) {
                    Alert errorPopUp = new Alert(Alert.AlertType.ERROR);
                    errorPopUp.setTitle("Error");
                    errorPopUp.setContentText(e.getMessage());
                    errorPopUp.show();
                }
            }
        });
        programareButtonsHorizontalBox.getChildren().add(addProgramareButton);


        Button deleteProgramareButton = new Button("Delete programare");
        deleteProgramareButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    int id = Integer.parseInt(idProgramareTextField.getText());

                    service.deleteProgramare(id);
                    programari.setAll(service.getAllProgramari());
                } catch (Exception e) {
                    Alert errorPopUp = new Alert(Alert.AlertType.ERROR);
                    errorPopUp.setTitle("Error");
                    errorPopUp.setContentText(e.getMessage());
                    errorPopUp.show();
                }
            }
        });
        programareButtonsHorizontalBox.getChildren().add(deleteProgramareButton);


        Button updateProgramareButton = new Button("Update programare");
        updateProgramareButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    int id = Integer.parseInt(idProgramareTextField.getText());
                    int id_pacient = Integer.parseInt(idPacientProgramareTextField.getText());
                    String data = dataProgramareTextField.getText();
                    String ora = oraProgramareTextField.getText();
                    String scop = scopProgramareTextField.getText();

                    service.updateProgramare(id, id_pacient, data, ora, scop);
                    programari.setAll(service.getAllProgramari());
                } catch (Exception e) {
                    Alert errorPopUp = new Alert(Alert.AlertType.ERROR);
                    errorPopUp.setTitle("Error");
                    errorPopUp.setContentText(e.getMessage());
                    errorPopUp.show();
                }
            }
        });
        programareButtonsHorizontalBox.getChildren().add(updateProgramareButton);


        pacientiListView.setItems(pacienti);
        programariListView.setItems(programari);
        Scene scene = new Scene(mainHorizontalBox, 700, 500);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}