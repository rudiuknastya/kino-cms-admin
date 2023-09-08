package project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Поле не може бути порожнім")
    @Size(max=25, message = "Розмір поля має бути не більше 25 символів")
    @Column(name = "first_name", columnDefinition="VARCHAR(25) NOT NULL")
    private String firstName;
    @NotEmpty(message = "Поле не може бути порожнім")
    @Size(max=30, message = "Розмір поля має бути не більше 30 символів")
    @Column(name = "last_name", columnDefinition="VARCHAR(30) NOT NULL")
    private String lastName;
    @NotEmpty(message = "Поле не може бути порожнім")
    @Size(max=25, message = "Розмір поля має бути не більше 25 символів")
    @Column(columnDefinition="VARCHAR(25) NOT NULL")
    private String pseudonym;
    @NotEmpty(message = "Поле не може бути порожнім")
    @Email(regexp = "[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+\\.[a-z]{2,3}", message = "Невірний формат email")
    @Column(columnDefinition="VARCHAR(30) NOT NULL UNIQUE")
    private String email;
    //@NotEmpty(message = "Поле не може бути порожнім")
    //@Size(min=5, max=15, message = "Розмір поля має бути від 5 до 15 символів")
    @Column(columnDefinition="NOT NULL UNIQUE")
    private String password;
    @Column(name = "card_number", columnDefinition="VARCHAR(20) UNIQUE")
    @Size(min=13, max=19, message = "Розмір поля має бути від 13 до 19 символів")
    private String cardNumber;
    @Column(columnDefinition="ENUM('male','female') NOT NULL")
    private String sex;
    @NotEmpty(message = "Поле не може бути порожнім")
    @Size(min=4,max=15, message = "Розмір поля має бути не менше 4 та не більше 15 символів")
    @Pattern(regexp = "^\\+?[1-9][0-9]{4,15}$", message = "Невірний номер")
    @Column(name = "phone_number", columnDefinition="VARCHAR(20) NOT NULL UNIQUE")
    private String phoneNumber;
    //@NotNull(message = "Поле не може бути порожнім")
    @Column(name = "birth_date", columnDefinition="DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    //@NotNull
    @Column(columnDefinition="VARCHAR(40) NOT NULL")
    private String city;
    //@NotEmpty(message = "Поле не може бути порожнім")
    @Size(max=100, message = "Розмір поля має бути не більше 100 символів")
    @Column(columnDefinition="VARCHAR(100)")
    private String address;
    //@NotNull
    @Column(columnDefinition="ENUM('ukr','eng')")
    private String language;

    @Column(name = "registration_date", columnDefinition="DATE NOT NULL")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate registrationDate;
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPseudonym() {
        return pseudonym;
    }

    public void setPseudonym(String pseudonym) {
        this.pseudonym = pseudonym;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
