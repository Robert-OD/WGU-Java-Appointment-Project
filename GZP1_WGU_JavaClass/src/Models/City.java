package Models;

public class City {
    private int cityId;
    private String cityName;
    private Country country;

    public City(int cityId, String cityName, Country country){
        this.cityId = cityId;
        this.cityName = cityName;
        this.country = country;
    }

    public int getCityId() {
        return cityId;
    }
    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Country getCountry() {
        return country;
    }
    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object object){
        if (object instanceof  City){
            City comparedCity = (City)object;
            if (this.cityId == comparedCity.getCityId()){
                if (this.cityName.equals(comparedCity.getCityName())){
                    if (this.country.equals(comparedCity.getCountry())){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
