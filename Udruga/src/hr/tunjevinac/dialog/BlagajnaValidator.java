package hr.tunjevinac.dialog;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class BlagajnaValidator {

    private static String datumReg = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";

    public static <T extends Control> boolean validirajStandard(T control) {
        if (control instanceof TextField) {
            if (((TextField) control).getText() != null
                    && !((TextField) control).getText().equals(""))
                return true;
        } else if (control instanceof ComboBox<?>) {
            if (((ComboBox<?>) control).getValue() != null)
                return true;
        }

        return false;
    }

    public static <T extends Control> boolean validirajDatum(T control) {
        if (((DatePicker) control).getValue() != null) {
            return true;
        } else if (((DatePicker) control).getEditor().getText()
                .matches(datumReg)) {
            return true;
        }
        return false;
    }
}
