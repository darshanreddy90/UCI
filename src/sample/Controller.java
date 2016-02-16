package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private boolean editingExistingRebate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        editingExistingRebate = false;
    }

    @FXML
    private Button newRebateBtn;

    @FXML
    private TextField firstName;

    @FXML
    private TextField middleInitial;

    @FXML
    private TextField lastName;

    @FXML
    private TextField emailAddress;

    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField addressLine1;

    @FXML
    private TextField addressLine2;

    @FXML
    private TextField city;

    @FXML
    private TextField zipCode;

    @FXML
    private TextField state;

    @FXML
    private ToggleGroup proofGroup;

    @FXML
    private DatePicker dateReceived;

    @FXML
    public void addNewRebate(ActionEvent event){
        editingExistingRebate = false;
    }

    @FXML
    public void saveRebate(ActionEvent event) {
        if (!editingExistingRebate) {
            Rebate newRebate = new Rebate(firstName.getText(), lastName.getText(), middleInitial.getText(), emailAddress.getText(), phoneNumber.getText(), addressLine1.getText(), addressLine2.getText(), state.getText(), city.getText(), zipCode.getText(),true, dateReceived.getValue());
            // TODO: validate all feilds
            System.out.println("done");
        }
    }

    @FXML
    public void deleteRebate(ActionEvent event) {

    }

}
