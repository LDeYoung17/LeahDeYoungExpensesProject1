package dev.deyoung.entities;

import javax.persistence.*;

@Entity
@Table(name= "expenses")

public class Expenses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="expense_id")
    int expenseId;

    @Column(name ="expense_amount")
    double expenseAmount;

    @Column(name ="reason")
    String reason;

    @Column(name ="date_submitted")
    int dateSubmitted;

    @JoinColumn(name = "employee_id")
    @Column(name ="employee_id")
    int employeeId;

    @JoinColumn(name = "manager_id")
    @Column(name ="manager_id")
    int managerId;

    @Column(name ="director_id")
    @JoinColumn(name = "director_id")
    int directorId;

    @Column(name ="status")
    String status;

    @Column(name ="status_date")
    int statusDate;

    @Column(name ="manager_notes")
    String managerNotes;

    public Expenses() {
    }

    public Expenses(int expenseId, double expenseAmount, String reason, int dateSubmitted, int employeeId, int managerId, int directorId, String status, int statusDate, String managerNotes) {
        this.expenseId = expenseId;
        this.expenseAmount = expenseAmount;
        this.reason = reason;
        this.dateSubmitted = dateSubmitted;
        this.employeeId = employeeId;
        this.managerId = managerId;
        this.directorId = directorId;
        this.status = status;
        this.statusDate = statusDate;
        this.managerNotes = managerNotes;
    }

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    public double getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(double expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(int dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public int getDirectorId() {
        return directorId;
    }

    public void setDirectorId(int directorId) {
        this.directorId = directorId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(int statusDate) {
        this.statusDate = statusDate;
    }

    public String getManagerNotes() {
        return managerNotes;
    }

    public void setManagerNotes(String managerNotes) {
        this.managerNotes = managerNotes;
    }

    @Override
    public String toString() {
        return "Expenses{" +
                "expenseId=" + expenseId +
                ", expenseAmount=" + expenseAmount +
                ", reason='" + reason + '\'' +
                ", dateSubmitted=" + dateSubmitted +
                ", employeeId=" + employeeId +
                ", managerId=" + managerId +
                ", directorId=" + directorId +
                ", status='" + status + '\'' +
                ", statusDate=" + statusDate +
                ", managerNotes='" + managerNotes + '\'' +
                '}';
    }
}
