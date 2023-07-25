package project.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Поле не може бути порожнім")
    @Size(max=30, message = "Розмір поля має бути не більше 30 символів")
    @Column(columnDefinition="VARCHAR(30) NOT NULL")
    private String name;
    @NotEmpty(message = "Поле не може бути порожнім")
    @Column(columnDefinition="TINYTEXT NOT NULL")
    private String description;
    @Size(max=100, message = "Розмір поля має бути не більше 100 символів")
    @Column(name="video_link", columnDefinition="VARCHAR(100)")
    private String videoLink;
    @NotNull(message = "Поле не може бути порожнім")
    @Column(name="publication_date", columnDefinition="DATE NOT NULL")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate publicationDate;
    @Column(columnDefinition="BOOLEAN NOT NULL")
    private Boolean status;
    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="seo_block_id", referencedColumnName = "id")
    private SeoBlock seoBlock;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="image_gallery_id", referencedColumnName = "id")
    private Gallery imageGallery;

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

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
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
