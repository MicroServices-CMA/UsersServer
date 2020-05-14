package usersMicroService.classes;

import java.util.Date;

/**
 * Defines the base structure for <code>Visit</code> entities.
 *
 * @author Ханк
 */
public class Visit {

    private Date startDate;
    private Date endDate;
    private String purpose;

    public Visit() {
    }

    public Visit(Date startDate, Date endDate, String purpose) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.purpose = purpose;
    }

    @Override
    public String toString() {
        return "Visit{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", purpose=" + purpose +
                '}';
    }

    // Getters and Setters

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

}

