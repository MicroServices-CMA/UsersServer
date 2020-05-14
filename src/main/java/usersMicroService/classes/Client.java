package usersMicroService.classes;

import usersMicroService.models.Clients;

import java.io.Serializable;
import java.util.Objects;

/**
 * Defines the base structure for <code>Client</code> entities.
 *
 * @author Ханк
 * @version 1.0
 */
public class Client implements Comparable<Client>, Serializable, Cloneable {

    private static int incrementClient;

    static {
        incrementClient = Clients.getPeopleTable().size();
    }

    private String clientId;
    private Passport passport;
    private String phone;
    private String email;
    private PhysicalAddress address;

    public Client() {
        incrementClient++;
        this.clientId = String.valueOf(incrementClient);
    }

    public Client(Passport passport, String phone, String email, PhysicalAddress address) {
        this.passport = passport;
        this.phone = phone;
        this.email = email;
        this.address = address;

        incrementClient++;
        this.clientId = String.valueOf(incrementClient);
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId=" + clientId +
                ", passport=" + passport +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                '}';
    }

    @Override
    public int compareTo(Client o) {
        return (clientId.equals(o.clientId)) ? 0 : 1;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client client = (Client) o;
        return getClientId().equals(client.getClientId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, passport, phone, email, address);
    }

    // Getters and Setters

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PhysicalAddress getAddress() {
        return address;
    }

    public void setAddress(PhysicalAddress address) {
        this.address = address;
    }
}
