package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.io.FileNotFoundException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.List;
/**
 * This is the controller for the UI and all the binding with UI components defined in the .fxml view file happens here
 * */
public class Controller implements Initializable {
    // This flag indicated whether existing Rebate is being edited or new Rebate is being created
    private boolean editingExistingRebate;
    //Holds the current list of all the reabates that are saved in the file
    private List<Rebate> rebates;
    //The rebate being edited currently from the rebate list
    private Rebate selectedRebate;
    //Following are bindings to all the UI elements from fxml file that are required

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
    private Label errorLabel;

    @FXML
    private Label alreadyExistsError;

    @FXML
    private RadioButton yesRadioBtn;

    @FXML
    private RadioButton noRadioBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        editingExistingRebate = false;
        //initialize the datepicker to current date
        dateReceived.setValue(LocalDate.now());
        try {
            //Load all rebates from text file and populate the list view
            rebates = Rebate.loadAllRebates();
            if (rebates != null || rebates.size() == 0) {
                setListView();
            }
            //set lenght restrictions
            setLengthLimitsForAllTextFields();
            // Add change listener to the listView so that selected Rebate is populated in the edit view
            rebateList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Rebate>() {

                @Override
                public void changed(ObservableValue<? extends Rebate> observable, Rebate oldValue, Rebate newValue) {
                    if (newValue!=null) {
                        selectedRebate = newValue;
                        populateEditingViewWithSelectedRebate();
                        clearErrors();
                    }
                }
            });
        } catch (FileNotFoundException e) {
            System.out.println("Unable to load Rebates at the moment");
        }
    }
    /**
    * For all the text fields in the form sets the length limits using helper function from Utilities class
    * */
    private void setLengthLimitsForAllTextFields() {
        Utilities.addLenghtConstraintToTextField(firstName,20);
        Utilities.addLenghtConstraintToTextField(lastName,20);
        Utilities.addLenghtConstraintToTextField(middleInitial,1);
        Utilities.addLenghtConstraintToTextField(addressLine1,35);
        Utilities.addLenghtConstraintToTextField(addressLine2,35);
        Utilities.addLenghtConstraintToTextField(city,25);
        Utilities.addLenghtConstraintToTextField(state,2);
        Utilities.addLenghtConstraintToTextField(zipCode,9);
        Utilities.addLenghtConstraintToTextField(phoneNumber,21);
        Utilities.addLenghtConstraintToTextField(emailAddress,60);
    }

    @FXML
    public void addNewRebate(ActionEvent event){
        clearErrors();
        editingExistingRebate = false;
        rebateList.getSelectionModel().clearSelection();
        clearEditingView();
    }
    /**
     * Clears all the editing fields and resets the date picker to current date
     * */
    private void clearEditingView() {
        firstName.clear();
        lastName.clear();
        middleInitial.clear();
        addressLine2.clear();
        addressLine1.clear();
        city.clear();
        state.clear();
        zipCode.clear();
        phoneNumber.clear();
        emailAddress.clear();
        dateReceived.setValue(LocalDate.now());
        clearErrors();
    }

    @FXML
    public void saveRebate(ActionEvent event) {

        if(!validateAllFields()) {
            setErrors();
            return;
        }
        if (!editingExistingRebate) {
            Iterator<Rebate> rebateIterator = rebates.iterator();
            while (rebateIterator.hasNext()) {
                Rebate current = rebateIterator.next();
                if (current.firstName.equalsIgnoreCase(firstName.getText()) && current.lastName.equalsIgnoreCase(lastName.getText()) && current.middleInitial.equalsIgnoreCase(middleInitial.getText())) {
                    showDuplicateError();
                    return;
                }
            }
            boolean isProofAtchd = false;
            if (yesRadioBtn.isSelected()) {
                isProofAtchd = true;
            }
            Rebate newRebate = new Rebate(firstName.getText(),  middleInitial.getText(), lastName.getText(), addressLine1.getText(), addressLine2.getText(), city.getText(), state.getText(), zipCode.getText(), phoneNumber.getText(), emailAddress.getText(), isProofAtchd, dateReceived.getValue());
            // TODO: validate all feilds
            rebates.add(newRebate);
            clearEditingView();


        } else {
            populateObjectFromEditingFields();

        }
        try {
            Rebate.saveRebates(rebates);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save rebates now, try again later");
        }
        setListView();
    }


    private boolean validateAllFields() {

        if(isEmpty(firstName.getText()) || isEmpty(lastName.getText()) || isEmpty(addressLine1.getText()) || isEmpty(city.getText()) || isEmpty(state.getText()) || isEmpty(zipCode.getText()) || isEmpty(phoneNumber.getText()) || isEmpty(emailAddress.getText()) ) {
            return false;
        }
        return true;
    }

    public boolean isEmpty(String str) {
        return str.length()>0 ? false: true;
    }

    @FXML
    public void deleteRebate(ActionEvent event) {
        rebates.remove(selectedRebate);
        editingExistingRebate = false;

        try {
            Rebate.saveRebates(rebates);
            setListView();
            clearEditingView();
        } catch (FileNotFoundException e) {
            System.out.println("Couldnt save changes at this time");
        }
    }

    @FXML
    public void discardEdits(ActionEvent event) {
        clearErrors();
        if (editingExistingRebate) {
            populateEditingViewWithSelectedRebate();
        } else {
            clearEditingView();
        }
    }
    /*
    * Populate the values of selected rebate into all the editable fields for editing
    * */
    private void populateEditingViewWithSelectedRebate() {
        editingExistingRebate = true;
        firstName.setText(selectedRebate.getFirstName());
        middleInitial.setText(selectedRebate.getMiddleInitial());
        lastName.setText(selectedRebate.getLastName());
        addressLine1.setText(selectedRebate.getAddressLine1());
        addressLine2.setText(selectedRebate.getAddressLine2());
        city.setText(selectedRebate.getCity());
        state.setText(selectedRebate.getState());
        zipCode.setText(selectedRebate.getZipCode());
        phoneNumber.setText(selectedRebate.getPhoneNumber());
        emailAddress.setText(selectedRebate.getEmailAddress());
        // TODO: handle togglign radio buttons
        dateReceived.setValue(selectedRebate.getDateReceived());

        if(selectedRebate.isProofOfPurchaseAttached()) {
            yesRadioBtn.setSelected(true);
        } else {
            noRadioBtn.setSelected(true);
        }
    }

    private void populateObjectFromEditingFields() {
        selectedRebate.setFirstName(firstName.getText());
        selectedRebate.setLastName(lastName.getText());
        selectedRebate.setMiddleInitial(middleInitial.getText());
        selectedRebate.setAddressLine1(addressLine1.getText());
        selectedRebate.setAddressLine2(addressLine2.getText());
        selectedRebate.setCity(city.getText());
        selectedRebate.setState(state.getText());
        selectedRebate.setZipCode(zipCode.getText());
        selectedRebate.setDateReceived(dateReceived.getValue());
        selectedRebate.setEmailAddress(emailAddress.getText());
        selectedRebate.setPhoneNumber(phoneNumber.getText());

    }

    /*
    * Populates the listview with the rebate list
    * */
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
                            String spacing = "                                        ";
                            String visible = t.getFirstName() + " " + t.getLastName();
                            visible = visible+spacing.substring(visible.length());
                            visible = visible+t.getPhoneNumber();
                            setText(visible);
                        }
                    }
                };
                return cell;
            }
        });
    }

    private void setErrors() {
        errorLabel.setVisible(true);
    }

    private void showDuplicateError() {
        alreadyExistsError.setVisible(true);
    }
    private void clearErrors() {
        errorLabel.setVisible(false);
        alreadyExistsError.setVisible(false);

    }

}
