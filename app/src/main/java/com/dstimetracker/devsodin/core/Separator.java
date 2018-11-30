package com.dstimetracker.devsodin.core;

/**
 * Class that represents a separator for the report representation.
 */
public class Separator extends Container {

    /**
     * Method that accepts the visitors.
     *
     * @param visitor The visitor itself
     */
    @Override
    public void accept(final Visitor visitor) {
        ((ReportVisitor) visitor).visitSeparator(this);
    }


    /**
     * Getter for the separator text.
     *
     * @return String with a line.
     */
    public String getText() {
        return "-------------------------------------------------------";
    }
}

