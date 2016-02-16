package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.util.Callback;

import java.io.FileNotFoundException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.List;

public class Controller implements Initializable {

    private boolean editingExistingRebate;

    private List<Rebate> rebates;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        editingExistingRebate = false;
        dateReceived.setValue(LocalDate.now());
        try {
            rebates = Rebate.loadAllRebates();
            setListView();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to load Rebates at the moment");
        }
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
    private ListView<Rebate> rebateList;

    @FXML
    public void addNewRebate(ActionEvent event){
        editingExistingRebate = false;
    }

    @FXML
    public void saveRebate(ActionEvent event) {
        if (!editingExistingRebate) {
            Rebate newRebate = new Rebate(firstName.getText(), lastName.getText(), middleInitial.getText(), emailAddress.getText(), phoneNumber.getText(), addressLine1.getText(), addressLine2.getText(), state.getText(), city.getText(), zipCode.getText(),true, dateReceived.getValue());
            // TODO: validate all feilds
            rebates.add(newRebate);
            try {
                Rebate.saveRebates(rebates);
            } catch (FileNotFoundException e) {
                System.out.println("Unable to save rebates now, try again later");
            }
            setListView();
        }
    }

    @FXML
    public void deleteRebate(ActionEvent event) {

    }

    private void setListView() {
        ObservableList<Rebate> items =FXCollections.observableArrayList (
                rebates);
        rebateList.setItems(items);
        rebateList.setCellFactory(new Callback<ListView<Rebate>, ListCell<Rebate>>() {
            @Override
            public ListCell<Rebate> call(ListView<Rebate> param) {
                ListCell<Rebate> cell = new ListCell<Rebate>(){
                    @Override
                    protected void updateItem(Rebate t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(t.getFirstName() + ":" + t.getPhoneNumber());
                        }
                    }
                };
                return cell;
            }
        });
    }

}
