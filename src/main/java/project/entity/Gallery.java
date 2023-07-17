package project.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "image_gallery")
public class Gallery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition="VARCHAR(60)")
    private String image1;
    @Column(columnDefinition="VARCHAR(60)")
    private String image2;
    @Column(columnDefinition="VARCHAR(60)")
    private String image3;
    @Column(columnDefinition="VARCHAR(60)")
    private String image4;
    @Column(columnDefinition="VARCHAR(60)")
    private String image5;
    @Column(name = "main_image",columnDefinition="VARCHAR(60)")
    private String mainImage;

}
