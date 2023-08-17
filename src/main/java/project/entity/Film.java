package project.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "film")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Поле не може бути порожнім")
    @Size(max=100, message = "Розмір поля має бути не більше 100 символів")
    @Column(columnDefinition="VARCHAR(100) NOT NULL")
    private String name;
    @NotEmpty(message = "Поле не може бути порожнім")
    @Column(columnDefinition="TEXT NOT NULL")
    private String description;
    private String trailer;
    //@NotEmpty(message = "Поле не може бути порожнім")
    @Column(nullable = false)
    private String type;
    @NotNull(message = "Поле не може бути порожнім")
    @Column(columnDefinition="DATE NOT NULL")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @NotEmpty(message = "Поле не може бути порожнім")
    @Size(max=100, message = "Розмір поля має бути не більше 100 символів")
    @Column(columnDefinition="VARCHAR(100) NOT NULL")
    private String country;
    @NotEmpty(message = "Поле не може бути порожнім")
    @Size(max=60, message = "Розмір поля має бути не більше 60 символів")
    @Column(columnDefinition="VARCHAR(60) NOT NULL")
    private String genre;
    @NotEmpty(message = "Поле не може бути порожнім")
    @Size(max=100, message = "Розмір поля має бути не більше 100 символів")
    @Column(columnDefinition="VARCHAR(100) NOT NULL")
    private String producer;
    @NotEmpty(message = "Поле не може бути порожнім")
    @Size(max=100, message = "Розмір поля має бути не більше 100 символів")
    @Column(columnDefinition="VARCHAR(100) NOT NULL")
    private String director;
    @NotEmpty(message = "Поле не може бути порожнім")
    @Size(max=20, message = "Розмір поля має бути не більше 20 символів")
    @Column(columnDefinition="VARCHAR(20) NOT NULL")
    private String time;
    @NotEmpty(message = "Поле не може бути порожнім")
    @Size(max=10, message = "Розмір поля має бути не більше 10 символів")
    @Column(columnDefinition="VARCHAR(10) NOT NULL")
    private String age;
    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="seo_block_id", referencedColumnName = "id")
    private SeoBlock seoBlock;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="image_gallery_id", referencedColumnName = "id")
    private Gallery imageGallery;

    public Gallery getImageGallery() {
        return imageGallery;
    }

    public void setImageGallery(Gallery imageGallery) {
        this.imageGallery = imageGallery;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public SeoBlock getSeoBlock() {
        return seoBlock;
    }

    public void setSeoBlock(SeoBlock seoBlock) {
        this.seoBlock = seoBlock;
    }
}
