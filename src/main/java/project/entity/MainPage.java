package project.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "main_page")
public class MainPage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition="VARCHAR(30) NOT NULL")
    private String name;
    @Size(min=4,max=15, message = "Розмір поля має бути не менше 4 та не більше 15 символів")
    @Pattern(regexp = "^\\+?[1-9][0-9]{4,15}$", message = "Невірний номер")
    @Column(name = "phone_number1", columnDefinition="VARCHAR(20) UNIQUE")
    private String phone1;
    @Size(min=4,max=15, message = "Розмір поля має бути не менше 4 та не більше 15 символів")
    @Pattern(regexp = "^\\+?[1-9][0-9]{4,15}$", message = "Невірний номер")
    @Column(name = "phone_number2", columnDefinition="VARCHAR(20) UNIQUE")
    private String phone2;
    @NotEmpty(message = "Поле не може бути порожнім")
    @Column(name="seo_text",columnDefinition="TINYTEXT NOT NULL")
    private String seoText;
    @Column(columnDefinition="BOOLEAN NOT NULL")
    private Boolean status;
    @Column(name="creation_date", columnDefinition="DATE NOT NULL")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate creationDate;
    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="seo_block_id", referencedColumnName = "id")
    private SeoBlock seoBlock;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getSeoText() {
        return seoText;
    }

    public void setSeoText(String seoText) {
        this.seoText = seoText;
    }

    public SeoBlock getSeoBlock() {
        return seoBlock;
    }

    public void setSeoBlock(SeoBlock seoBlock) {
        this.seoBlock = seoBlock;
    }
}
