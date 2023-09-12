package project.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "image_gallery")
public class Gallery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition="VARCHAR(200)")
    private String image1;
    @Column(columnDefinition="VARCHAR(200)")
    private String image2;
    @Column(columnDefinition="VARCHAR(200)")
    private String image3;
    @Column(columnDefinition="VARCHAR(200)")
    private String image4;
    @Column(columnDefinition="VARCHAR(200)")
    private String image5;
    @Column(name = "main_image",columnDefinition="VARCHAR(200)")
    private String mainImage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImage4() {
        return image4;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
    }

    public String getImage5() {
        return image5;
    }

    public void setImage5(String image5) {
        this.image5 = image5;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }
}
