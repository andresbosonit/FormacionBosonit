package com.example.lotrjavafx;

import com.example.lotrjavafx.Entitys.Beast;
import com.example.lotrjavafx.Entitys.Hero;
import com.example.lotrjavafx.Entitys.Type;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class View {
    @FXML
    private TextField heroName;
    @FXML
    private ComboBox<Type> heroType;
    @FXML
    private TextField heroHp;
    @FXML
    private TextField heroArm;
    @FXML
    private TextField beastName;
    @FXML
    private ComboBox<Type> beastType;
    @FXML
    private TextField beastHp;
    @FXML
    private TextField beastArm;
    @FXML
    private ListView<Hero> heroListView;
    @FXML
    private ListView<Beast> beastListView;
    @FXML
    private TextArea textArea;
    Controller controller;

    public View() {
        this.controller = new Controller();
    }

    @FXML
    public void initialize() {
        ObservableList<Type> observableHeroListType = FXCollections.observableArrayList(Type.ELFO, Type.HUMANO, Type.HOBBIT);
        heroType.setItems(observableHeroListType);
        heroType.setValue(Type.ELFO);
        ObservableList<Type> observableBeastListType = FXCollections.observableArrayList(Type.ORCO, Type.TRASGO);
        beastType.setItems(observableBeastListType);
        beastType.setValue(Type.ORCO);
        this.textArea.setEditable(false);
    }

    @FXML
    public void addHero() {
        Hero hero = this.controller.createHero(heroName.getText(), heroType.getValue(), heroHp.getText(), heroArm.getText());
        if (hero != null) {
            heroListView = this.controller.addHero(hero,heroListView);
            Util.createAlert(Alert.AlertType.INFORMATION, "Atención", "El héroe ha sido añadido a la lucha").showAndWait();
        } else {
            Util.createAlert(Alert.AlertType.WARNING, "Advertencia", "Por favor, ingresa información válida para el héroe.").showAndWait();
        }
    }

    @FXML
    public void addBeast() {
        Beast beast = this.controller.createBeast(beastName.getText(), beastType.getValue(), beastHp.getText(), beastArm.getText());
        if (beast != null) {
            beastListView = this.controller.addBeast(beast,beastListView);
            Util.createAlert(Alert.AlertType.INFORMATION, "Atención", "La bestia ha sido añadido a la lucha").showAndWait();
        } else {
            Util.createAlert(Alert.AlertType.WARNING, "Advertencia", "Por favor, ingresa información válida para la bestia.").showAndWait();
        }
    }

    @FXML
    public void deleteHero() {
        Hero selectedHero = heroListView.getSelectionModel().getSelectedItem();
        if (selectedHero != null) {
            heroListView = this.controller.deleteHero(selectedHero, heroListView);
        } else {
            Util.createAlert(Alert.AlertType.WARNING, "Advertencia", "Por favor, selecciona un héroe para eliminar.").showAndWait();
        }
    }

    @FXML
    public void deleteBeast() {
        Beast selectedBeast = beastListView.getSelectionModel().getSelectedItem();
        if (selectedBeast != null) {
            beastListView = this.controller.deleteBeast(selectedBeast, beastListView);
        } else {
            Util.createAlert(Alert.AlertType.WARNING, "Advertencia", "Por favor, selecciona un bestia para eliminar.").showAndWait();
        }
    }

    @FXML
    public void upHero() {
        Hero selectedHero = heroListView.getSelectionModel().getSelectedItem();
        if (selectedHero != null) {
            int index = heroListView.getSelectionModel().getSelectedIndex();
            if (index > 0) {
                heroListView = this.controller.upHero(selectedHero, heroListView, index);
            } else {
                Util.createAlert(Alert.AlertType.WARNING, "Advertencia", "El héroe ya es el primero en la lista.").showAndWait();
            }
        } else {
            Util.createAlert(Alert.AlertType.WARNING, "Advertencia", "Por favor, selecciona un héroe para mover.").showAndWait();
        }
    }

    @FXML
    public void upBeast() {
        Beast selectedBeast = beastListView.getSelectionModel().getSelectedItem();
        if (selectedBeast != null) {
            int index = beastListView.getSelectionModel().getSelectedIndex();
            if (index > 0) {
                beastListView = this.controller.upBeast(selectedBeast, beastListView, index);
            } else {
                Util.createAlert(Alert.AlertType.WARNING, "Advertencia", "La bestia ya es el primero en la lista.").showAndWait();
            }
        } else {
            Util.createAlert(Alert.AlertType.WARNING, "Advertencia", "Por favor, selecciona una bestia para mover.").showAndWait();
        }
    }

    @FXML
    public void downHero() {
        Hero selectedHero = heroListView.getSelectionModel().getSelectedItem();
        if (selectedHero != null) {
            int index = heroListView.getSelectionModel().getSelectedIndex();
            int lastIndex = heroListView.getItems().size() - 1;
            if (index > lastIndex) {
                heroListView = this.controller.downHero(selectedHero, heroListView, index);
            } else {
                Util.createAlert(Alert.AlertType.WARNING, "Advertencia", "El héroe ya es el último en la lista.").showAndWait();
            }
        } else {
            Util.createAlert(Alert.AlertType.WARNING, "Advertencia", "Por favor, selecciona un héroe para mover.").showAndWait();
        }
    }
    @FXML
    public void downBeast() {
        Beast selectedBeast = beastListView.getSelectionModel().getSelectedItem();
        if (selectedBeast != null) {
            int index = beastListView.getSelectionModel().getSelectedIndex();
            int lastIndex = heroListView.getItems().size() - 1;
            if (index > lastIndex) {
                beastListView = this.controller.downBeast(selectedBeast, beastListView, index);
            } else {
                Util.createAlert(Alert.AlertType.WARNING, "Advertencia", "La bestia ya es la última en la lista.").showAndWait();
            }
        } else {
            Util.createAlert(Alert.AlertType.WARNING, "Advertencia", "Por favor, selecciona una bestia para mover.").showAndWait();
        }
    }

    @FXML
    public void fight() {
        this.textArea.setText("");
        String result = this.controller.fight(heroListView,beastListView);
        this.textArea.setText(result);
    }
}
