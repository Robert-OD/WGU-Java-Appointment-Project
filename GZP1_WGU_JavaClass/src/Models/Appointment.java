package Models;

import Utils.TimeDateFormat;

import java.time.ZonedDateTime;

public class Appointment {
    private int appointmentId;
    private Customer customer;
    private User user;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private String url;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;

    public Appointment(int appointmentId, Customer customer, User user, String title, String description, String location, String contact, String type, String url, ZonedDateTime startTime, ZonedDateTime endTime) {
        this.appointmentId = appointmentId;
        this.customer = customer;
        this.user = user;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.url = url;
        this.startTime = ZonedDateTime.from(startTime);
        this.endTime = ZonedDateTime.from(endTime);
    }

    public int getAppointmentId() {
        return appointmentId;
    }
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }
    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }
    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object object){
        if (object instanceof Appointment){
            Appointment comparedAppointment = (Appointment)object;
            if (this.appointmentId == comparedAppointment.getAppointmentId()){
                if (this.customer.equals(comparedAppointment.getCustomer())){
                    if (this.user.equals(comparedAppointment.getUser())){
                        if (this.title.equals(comparedAppointment.getTitle())){
                            if (this.description.equals(comparedAppointment.getDescription())){
                                if (this.location.equals(comparedAppointment.getLocation())){
                                    if (this.contact.equals(comparedAppointment.getContact())){
                                        if (this.type.equals(comparedAppointment.getType())){
                                            if (this.url.equals(comparedAppointment.getUrl())){
                                                if (this.startTime.equals(comparedAppointment.getStartTime())){
                                                    if (this.endTime.equals(comparedAppointment.getEndTime())){
                                                        return true;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
