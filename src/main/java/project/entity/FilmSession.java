package project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "film_session")
public class FilmSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Поле не може бути порожнім")
    @Column(name="session_date", columnDefinition="DATE NOT NULL")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate sessionDate;
    @NotNull(message = "Поле не може бути порожнім")
    @Column(name="session_time", columnDefinition="TIME NOT NULl")
    @DateTimeFormat(pattern = "hh:mm:ss")
    private LocalTime sessionTime;
    @NotNull(message = "Поле не може бути порожнім")
    @Column(nullable = false)
    private Integer price;
    @Column(columnDefinition="ENUM('3D','2D','IMAX')",nullable = false)
    private String type;
    @ManyToOne
    @JoinColumn(name = "film_id", referencedColumnName = "id")
    private Film film;
    @ManyToOne
    @JoinColumn(name = "hall_id", referencedColumnName = "id")
    private Hall hall;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(LocalDate sessionDate) {
        this.sessionDate = sessionDate;
    }

    public LocalTime getSessionTime() {
        return sessionTime;
    }

    public void setSessionTime(LocalTime sessionTime) {
        this.sessionTime = sessionTime;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }
}
