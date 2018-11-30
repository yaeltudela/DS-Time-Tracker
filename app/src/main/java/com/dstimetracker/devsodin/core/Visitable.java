package com.dstimetracker.devsodin.core;

/**
 * Interface used for all the classes who must be visitable.
 */
public interface Visitable {

    /**
     * Entrance method for the visitor.
     *
     * @param visitor The visitor itself
     */
    void accept(Visitor visitor);
}
