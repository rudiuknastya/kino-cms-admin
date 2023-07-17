package project.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition="VARCHAR(30) NOT NULL")
    private String name;
    @Column(columnDefinition="TINYTEXT NOT NULL")
    private String description;
    @Column(name="video_link", columnDefinition="VARCHAR(100)")
    private String videoLink;
    @Column(name="publication_date", columnDefinition="DATE NOT NULL")
    private LocalDate publicationDate;
    @Column(columnDefinition="BOOLEAN NOT NULL")
    private Boolean status = true;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="seo_block")
    private SeoBlock seoBlock;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="image_gallery")
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
