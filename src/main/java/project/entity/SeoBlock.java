package project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "seo_block")
public class SeoBlock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition="VARCHAR(100)")
    private String url;
    @NotEmpty(message = "Поле не може бути порожнім")
    @Column(columnDefinition="VARCHAR(30) NOT NULL")
    private String title;
    @Size(max=45, message = "Розмір поля має бути не більше 45 символів")
    @Column(columnDefinition="VARCHAR(45)")
    private String keywords;
    @NotEmpty(message = "Поле не може бути порожнім")
    @Column(columnDefinition="TINYTEXT NOT NULL")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
