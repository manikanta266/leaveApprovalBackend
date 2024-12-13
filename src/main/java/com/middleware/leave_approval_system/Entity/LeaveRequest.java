package com.middleware.leave_approval_system.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.middleware.leave_approval_system.Exception.ResourceNotFoundException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;



@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String employeeId;
    private String firstName;
    private String lastName;


    private String email;
    private String position;
    private String phone;

    private String managerId;
    private String managerName;
    private String managerEmail;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate leaveStartDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate leaveEndDate;

    private String leaveReason;

    @Enumerated(EnumType.STRING)
    private LeaveType leaveType;
    private Double duration;
    private String durationType;
    private String comments;

    private String medicalDocument;

    @Enumerated(EnumType.STRING)
    private LeaveStatus leaveStatus;

    public enum LeaveStatus {
        PENDING,
        APPROVED,
        REJECTED
    }

    public enum LeaveType{
        SICK,
        VACATION,
        CASUAL,
        MARRIAGE,
        PATERNITY,
        MATERNITY,
        OTHERS
    }


    public void calculateDuration(List<LocalDate> nationalHolidays) {
        if (leaveStartDate == null || leaveEndDate == null) {
            throw new ResourceNotFoundException("Leave start and end dates cannot be null.");
        }

        if (leaveEndDate.isBefore(leaveStartDate)) {
            throw new ResourceNotFoundException("Leave end date cannot be before the start date.");
        }

        // Calculate business days excluding weekends and national holidays
        double businessDays = calculateBusinessDays(leaveStartDate, leaveEndDate, nationalHolidays);

        // Each business day is equivalent to 8 working hours
        int workingHoursPerDay = 8;
        int totalWorkingHours = (int) businessDays * workingHoursPerDay;

        // Store the calculated duration
        this.duration =  businessDays; // Total business days
        this.durationType = "Days"; // Indicates the unit used
    }

    public double calculateBusinessDays(LocalDate startDate, LocalDate endDate, List<LocalDate> nationalHolidays) {
        return startDate.datesUntil(endDate.plusDays(1)) // Inclusive of endDate
                .filter(date -> !isWeekend(date) && !nationalHolidays.contains(date))
                .count();
    }

    private boolean isWeekend(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    public LocalDate getLeaveStartDate() {
        return leaveStartDate;
    }

    public void setLeaveStartDate(LocalDate leaveStartDate) {
        this.leaveStartDate = leaveStartDate;
    }

    public LocalDate getLeaveEndDate() {
        return leaveEndDate;
    }

    public void setLeaveEndDate(LocalDate leaveEndDate) {
        this.leaveEndDate = leaveEndDate;
    }

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public String getDurationType() {
        return durationType;
    }

    public void setDurationType(String durationType) {
        this.durationType = durationType;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getMedicalDocument() {
        return medicalDocument;
    }

    public void setMedicalDocument(String medicalDocument) {
        this.medicalDocument = medicalDocument;
    }

    public LeaveStatus getLeaveStatus() {
        return leaveStatus;
    }

    public void setLeaveStatus(LeaveStatus leaveStatus) {
        this.leaveStatus = leaveStatus;
    }
}
