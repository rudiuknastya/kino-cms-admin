package project.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
@Entity
@Table(name = "advertisement")
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Поле не може бути порожнім")
    @Size(max = 40, message = "Розмір поля має бути не більше 40 символів")
    @Column(columnDefinition = "VARCHAR(40) NOT NULL")
    private String name;
    @NotEmpty(message = "Поле не може бути порожнім")
    @Column(columnDefinition = "TINYTEXT NOT NULL")
    private String description;
    @Column(columnDefinition = "BOOLEAN NOT NULL")
    private Boolean status;
    @Column(name="creation_date", columnDefinition="DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate creationDate;
    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "seo_block_id", referencedColumnName = "id")
    private SeoBlock seoBlock;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_gallery_id", referencedColumnName = "id")
    private Gallery imageGallery;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public SeoBlock getSeoBlock() {
        return seoBlock;
    }

    public void setSeoBlock(SeoBlock seoBlock) {
        this.seoBlock = seoBlock;
    }

    public Gallery getImageGallery() {
        return imageGallery;
    }

    public void setImageGallery(Gallery imageGallery) {
        this.imageGallery = imageGallery;
    }
}
