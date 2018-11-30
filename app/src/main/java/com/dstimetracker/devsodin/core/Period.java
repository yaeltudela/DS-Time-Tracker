package com.dstimetracker.devsodin.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Fragment of time delimited by two dates.
 */
class Period {

    private static final Logger LOGGER = LoggerFactory.getLogger(Period.class);
    private final Date startDate;
    private final Date endDate;

    /**
     * Constructor for the Period class. It takes 2 Dates
     * and set up to start and end respectively.
     *
     * @param start Date when the Period starts
     * @param end   Date when the Period ends.
     */
    Period(final Date start, final Date end) {
        this.startDate = start;
        this.endDate = end;

        Period.LOGGER.info("New Period created");

    }

    /**
     * Getter for the startDate field.
     *
     * @return Date with the Period startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Getter for the endDate field.
     *
     * @return Date with the Period endDate
     */
    public Date getEndDate() {
        return endDate;
    }

}
