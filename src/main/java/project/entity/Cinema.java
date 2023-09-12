package project.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;
@Entity
@Table(name = "cinema")
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Поле не може бути порожнім")
    @Size(max=30, message = "Розмір поля має бути не більше 30 символів")
    @Column(columnDefinition="VARCHAR(30) NOT NULL")
    private String name;
    @NotEmpty(message = "Поле не може бути порожнім")
    @Column(columnDefinition="TEXT NOT NULL")
    private String description;
    @NotEmpty(message = "Поле не може бути порожнім")
    @Column(columnDefinition="TINYTEXT NOT NULL")
    private String facilities;
    private String logo;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="image_gallery_id", referencedColumnName = "id")
    private Gallery imageGallery;
    @OneToMany(mappedBy ="cinema", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Hall> halls;
    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="seo_block_id", referencedColumnName = "id")
    private SeoBlock seoBlock;

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

    public String getFacilities() {
        return facilities;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Gallery getImageGallery() {
        return imageGallery;
    }

    public void setImageGallery(Gallery imageGallery) {
        this.imageGallery = imageGallery;
    }

    public List<Hall> getHalls() {
        return halls;
    }

    public void setHalls(List<Hall> halls) {
        this.halls = halls;
    }

    public SeoBlock getSeoBlock() {
        return seoBlock;
    }

    public void setSeoBlock(SeoBlock seoBlock) {
        this.seoBlock = seoBlock;
    }
}
