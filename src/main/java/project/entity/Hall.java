package project.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "hall")
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Поле не може бути порожнім")
    @Column(nullable = false)
    private Integer number;
    @NotEmpty(message = "Поле не може бути порожнім")
    @Column(columnDefinition="TEXT NOT NULL")
    private String description;
    @Column(name = "schema_image")
    private String schemaImage;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="image_gallery_id", referencedColumnName = "id")
    private Gallery imageGallery;
    @Column(name="creation_date", columnDefinition="DATE NOT NULL")
    private LocalDate creationDate;
    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="seo_block_id", referencedColumnName = "id", nullable = false)
    private SeoBlock seoBlock;
    @ManyToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;
    @OneToMany(mappedBy ="hall", cascade = CascadeType.ALL)
    private List<FilmSession> filmSessions;

    public List<FilmSession> getFilmSessions() {
        return filmSessions;
    }

    public void setFilmSessions(List<FilmSession> filmSessions) {
        this.filmSessions = filmSessions;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSchemaImage() {
        return schemaImage;
    }

    public void setSchemaImage(String schemaImage) {
        this.schemaImage = schemaImage;
    }

    public Gallery getImageGallery() {
        return imageGallery;
    }

    public void setImageGallery(Gallery imageGallery) {
        this.imageGallery = imageGallery;
    }

    public SeoBlock getSeoBlock() {
        return seoBlock;
    }

    public void setSeoBlock(SeoBlock seoBlock) {
        this.seoBlock = seoBlock;
    }
}
