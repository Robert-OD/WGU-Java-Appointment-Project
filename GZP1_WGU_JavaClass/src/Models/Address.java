package Models;

public class Address {
    private int addressId;
    private String address;
    private String address2;
    private City city;
    private String postalCode;
    private String phone;

    public Address(int addressId, String address, String address2, City city, String postalCode, String phone) {
        this.addressId = addressId;
        this.address = address;
        this.address2 = address2;
        this.city = city;
        this.postalCode = postalCode;
        this.phone = phone;
    }

    public int getAddressId() {
        return addressId;
    }
    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public City getCity() {
        return city;
    }
    public void setCity(City city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object object){
        Address comparedAddress = (Address)object;
        if (this.addressId == comparedAddress.getAddressId()){
            if (this.address.equals(comparedAddress.getAddress())){
                if (this.address2.equals(comparedAddress.getAddress2())){
                    if (this.city.equals(comparedAddress.getCity())){
                        if (this.postalCode.equals(comparedAddress.getPostalCode())){
                            if (this.phone.equals(comparedAddress.getPhone())){
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
