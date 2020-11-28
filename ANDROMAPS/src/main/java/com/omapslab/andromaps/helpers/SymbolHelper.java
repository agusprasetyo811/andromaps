package com.omapslab.andromaps.helpers;

/**
 * Symbol Helper ()
 *
 * @By Agus Prasetyo | omapslab (agusprasetyo811@gmail.com)
 * -------------------------------------------------------------
 */
public class SymbolHelper {

    public String stringValue = "";
    public String symbol = "";


    /**
     * Set String Price
     *
     * @param value
     * @return
     */
    public SymbolHelper setPrice(String value) {
        try {
            if (value.indexOf(".") > 0) return this;
            if (value.length() == 0) {
                this.stringValue = value;
            } else if (value.length() <= 3) {
                this.stringValue = value;
            }

            String newValue = "";
            String valA = "", valB = "";
            if (value.length() > 3)
                valA = "." + value.substring(value.length() - 3, value.length());
            if (value.length() > 6)
                valB = "." + value.substring(value.length() - 6, value.length());
            int x = value.length() - 1;
            int r = 0;
            while (x >= 0) {
                newValue = value.substring(x, x + 1) + newValue;
                r++;
                if (r == 3 && x != 0) {
                    r = 0;
                    newValue = "." + newValue;
                }
                x--;
            }
            this.stringValue = newValue;
            return this;
        } catch (Exception e) {
            this.stringValue = "";
            return this;
        }
    }

    /**
     * Set String Price
     *
     * @param symbol
     * @return
     */
    public SymbolHelper setSymbol(String symbol) {
        if (symbol.equals("")) {
            this.symbol = "";
        } else {
            this.symbol = symbol + " ";
        }
        return this;
    }

    /**
     * Show
     *
     * @return
     */
    public String show() {
        return symbol + stringValue;
    }

}
