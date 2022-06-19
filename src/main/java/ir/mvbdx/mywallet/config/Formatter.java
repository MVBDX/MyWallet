package ir.mvbdx.mywallet.config;

import java.text.NumberFormat;
import java.util.Locale;

public class Formatter {
    public static String getCurrencyFormat(Double value) {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.GERMAN);
        nf.setGroupingUsed(true);
        return "﷼ " + nf.format(value);
    }
}
