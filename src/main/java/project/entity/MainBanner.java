package project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "main_banner")
public class MainBanner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String image;
    @Size(max=100, message = "Розмір поля має бути не більше 100 символів")
    @Column(columnDefinition="VARCHAR(100)")
    private String url;
    @Size(max=100, message = "Розмір поля має бути не більше 100 символів")
    @Column(columnDefinition="VARCHAR(100)")
    private String text;
    private Integer speed;
    @Column(columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean status;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }
}
