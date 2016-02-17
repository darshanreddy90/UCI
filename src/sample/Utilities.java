package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

/**
 * Created by dxr141430 on 2/16/2016.
 *
 * This class contains helper functions to assist the controller with some initializing or utility functins
 */
public class Utilities {
    /*
    * adds length constraints to the text field provided
    *
    * @param: textField to which length constraint need to be set
    * @param: length restriction of length
    * */
    public static void addLenghtConstraintToTextField(TextField textField, int length) {
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                if(newValue.length() > length) {
                    textField.setText(newValue.substring(0, length));
                }
            }
        });
    }
}
