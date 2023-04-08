package com.example.demo.control;

import com.example.demo.model.Countries;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class HelloController implements Initializable {


    @FXML
    private AnchorPane userPanel;
    @FXML
    private AnchorPane locationPanel;
    @FXML
    private AnchorPane educationPanel;

    @FXML
    private ImageView userArrow;
    @FXML
    private ImageView locationArrow;
    @FXML
    private ImageView educationArrow;

    @FXML
    private JFXComboBox<String> comboBoxID;
    @FXML
    private JFXComboBox<String> comboBoxRelation;
    @FXML
    private JFXComboBox<String> comboBoxAcademic;
    @FXML
    private JFXComboBox<String> comboBoxCountries;
    @FXML
    private JFXComboBox<String> comboBoxStates;
    @FXML
    private JFXComboBox<String> comboBoxCities;

    @FXML
    private JFXRadioButton maleRadioButton;
    @FXML
    private JFXRadioButton femaleRadioButton;

    @FXML
    private JFXTextField nameText;
    @FXML
    private JFXTextField lastNameText;
    @FXML
    private JFXTextField idText;
    @FXML
    private JFXTextField degreeText;
    @FXML
    private JFXTextField careerText;

    private Countries countriesManger;

    ObservableList<String> comboIDContent =
            FXCollections.observableArrayList(
                    "C.C.",
                    "C.E.",
                    "T.I.",
                    "R.C."
            );
    ObservableList<String> comboRelationContent =
            FXCollections.observableArrayList(
                    "Soltero",
                    "Casado",
                    "Union libre"
            );
    ObservableList<String> comboAcademicContent =
            FXCollections.observableArrayList(
                    "Primaria",
                    "Secundaria",
                    "Tecnico",
                    "Tecnologia",
                    "Profesional",
                    "Maestria",
                    "Doctorado"
            );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            countriesManger = new Countries();
        } catch (IOException e) {
            e.printStackTrace();
        }

        nameText.addEventFilter(KeyEvent.ANY, handlerLetters);
        lastNameText.addEventFilter(KeyEvent.ANY, handlerLetters);
        idText.addEventFilter(KeyEvent.ANY, handlerNumbers);

        comboBoxID.setItems(comboIDContent);
        comboBoxRelation.setItems(comboRelationContent);
        comboBoxAcademic.setItems(comboAcademicContent);
        comboBoxCountries.setItems(countriesManger.getCountries());

        ToggleGroup group = new ToggleGroup();
        maleRadioButton.setToggleGroup(group);
        femaleRadioButton.setToggleGroup(group);
    }

    public void onExitButtonClicked(){
        Platform.exit();
    }

    public void onUserButtonClicked(){
        changeVisibility(1);
    }

    public void onLocationButtonClicked(){
        changeVisibility(2);
    }

    public void onEducationButtonClicked(){
        changeVisibility(3);
        comboBoxAcademic.requestFocus();
    }

    private void changeVisibility(int currentPane){

        boolean userPane = false;
        boolean locationPane = false;
        boolean educationPane = false;

        switch (currentPane) {
            case 1 -> {if (!userPanel.isVisible()) userPane = true;}
            case 2 -> {if (!locationPanel.isVisible()) locationPane = true;}
            case 3 -> {if (!educationPanel.isVisible()) educationPane = true;}
        }
        userPanel.setVisible(userPane);
        userArrow.setVisible(userPane);

        locationPanel.setVisible(locationPane);
        locationArrow.setVisible(locationPane);

        educationPanel.setVisible(educationPane);
        educationArrow.setVisible(educationPane);
    }

    EventHandler<KeyEvent> handlerLetters = new EventHandler<>() {
        private boolean willConsume = false;
        @Override
        public void handle(KeyEvent keyEvent) {
            if (willConsume) keyEvent.consume();
            if (!keyEvent.getCode().toString().matches("[a-zA-Z]") && keyEvent.getCode() != KeyCode.BACK_SPACE
                    && keyEvent.getCode() != KeyCode.SHIFT) {
                if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
                    willConsume = true;
                } else if (keyEvent.getEventType() == KeyEvent.KEY_RELEASED) {
                    willConsume = false;
                }
            }
        }
    };

    EventHandler<KeyEvent> handlerNumbers = new EventHandler<>() {
        private boolean willConsume = false;
        private final int maxLength = 10;
        @Override
        public void handle(KeyEvent keyEvent) {
            JFXTextField temp = (JFXTextField) keyEvent.getSource();
            if (willConsume) keyEvent.consume();
            if ((!keyEvent.getCode().toString().matches("[0-9]") && keyEvent.getCode() != KeyCode.BACK_SPACE) || temp.getText().length() > maxLength - 1) {
                if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
                    willConsume = true;
                } else if (keyEvent.getEventType() == KeyEvent.KEY_RELEASED) {
                    willConsume = false;
                }
            }
        }
    };

    public void onComboCountriesChanged() {
        comboBoxStates.setDisable(false);
        comboBoxStates.setItems(countriesManger.getStates(comboBoxCountries.getValue()));
    }

    public void onComboStatesChanged() {
        comboBoxCities.setDisable(false);
        comboBoxCities.setItems(countriesManger.getCities(comboBoxStates.getValue()));
    }

    public void onComboAcademicChanged() {
        switch (comboBoxAcademic.getValue()) {
            case "Primaria", "Secundaria" -> degreeText.setDisable(true);
            case "Tecnico", "Tecnologia", "Profesional", "Maestria", "Doctorado" -> degreeText.setDisable(false);
        }
    }

    public void onToggleButtonChanged() {
        if (careerText.isDisable()) {
            careerText.setDisable(false);
        } else {
            careerText.setText("");
            careerText.setDisable(true);
        }
    }

    public void onSaveButtonClicked() {
        if (nameText.getText().isEmpty() || lastNameText.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Guardar Informacion");
            alert.setContentText("Existen campos vacios");
            alert.showAndWait();
        }
    }
}