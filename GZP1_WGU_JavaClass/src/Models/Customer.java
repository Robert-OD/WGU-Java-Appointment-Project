package Models;

public class Customer {
    private int customerId;
    private String customerName;
    private Address address;
    private Boolean active;

    public Customer(int customerId, String customerName, Address address, Boolean active) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.active = active;
    }

    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }

    public Boolean getActive() {
        return active;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object object){
        if (object instanceof Customer){
            Customer comparedCustomer = (Customer)object;
            if (this.customerId == comparedCustomer.getCustomerId()){
                if (this.customerName.equals(comparedCustomer.getCustomerName())){
                    if (this.address.equals(comparedCustomer.getAddress())){
                        if (this.active.equals(comparedCustomer.getActive())){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
