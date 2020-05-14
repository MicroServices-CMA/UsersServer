package usersMicroService.structures;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Defines the base structure for <code>Country</code> entities.
 * @author Hank
 * @version 1.0
 */
public class Country implements Comparable<Country> {

    static private Map<String, Country> countries = new HashMap<>();

    private String iso; /** 2-letter country code defined in */
    private String code;
    private String name;

    public Country(){
    }

    /**
     * The constructor to create new objects <code>Country</code> using an iso code
     *
     * @param iso the iso code identifying a country following standard <i> ISO-3166 <i>
     * @author hankd
     */
    public Country(String iso) {
        Country c = countries.getOrDefault(iso, new Country(){
            {
                this.setIso("RU");
                this.setCode("643");
                this.setName("The Russian Federation");
            }
        });
        this.iso = c.iso;
        this.code = c.code;
        this.name = c.name;
    }

    static {
        String[] countryCodes = Locale.getISOCountries();
        for (String countryCode : countryCodes) {

            Locale locale = new Locale("", countryCode);
            String iso = locale.getISO3Country();
            String code = locale.getCountry();
            String name = locale.getDisplayCountry();

            countries.put(name, new Country(iso));
        }
    }

    @Override
    public String toString() {
        return "Country{" +
                "iso='" + iso + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int compareTo(Country o) {
        return this.code.compareTo(o.getCode());
    }

    // Getters and Setters

    public String getIso() {
        return iso;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }
}

