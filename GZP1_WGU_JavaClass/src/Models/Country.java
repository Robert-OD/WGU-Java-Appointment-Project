package Models;

public class Country {
    private int countryId;
    private String countryName;

    public Country(int countryId, String countryName){
        this.countryId = countryId;
        this.countryName = countryName;
    }

    public int getCountryId() {
        return countryId;
    }
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public boolean equals(Object object){
        if (object instanceof Country){
            Country comparedCountry = (Country)object;
            if (this.countryId == comparedCountry.getCountryId()){
                if (this.countryName.equals(comparedCountry.getCountryName())){
                    return true;
                }
            }
        }
        return false;
    }
}
