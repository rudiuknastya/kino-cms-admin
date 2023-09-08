package project.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import project.entity.FilmSession;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CriteriaFilmSessionRepository {
    private final EntityManager em;

    public CriteriaFilmSessionRepository(EntityManager em) {
        this.em = em;
    }
    public List<FilmSession> getSessionByCriteriaForToday(String []types, String film){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<FilmSession> cq = cb.createQuery(FilmSession.class);

        Root<FilmSession> filmSession = cq.from(FilmSession.class);
        List<Predicate> predicates = new ArrayList<>();
        Predicate predicateForType = null;
        if(types != null && types.length !=0) {
            Predicate[] typePredicate = new Predicate[types.length];
            for (int i = 0; i < types.length; i++) {
                typePredicate[i] = cb.equal(filmSession.get("type"), types[i]);
            }
            predicateForType = cb.or(typePredicate);
        }
        if (predicateForType != null) {
            predicates.add(predicateForType);
        }
       if(!film.equals("all")){
           Predicate filmPredicate = cb.like(filmSession.get("film").get("name"), "%" + film + "%");
           predicates.add(filmPredicate);
       }
       Predicate datePredicate = cb.equal(filmSession.get("sessionDate"), LocalDate.now());
       predicates.add(datePredicate);
        cq.where(predicates.toArray(new Predicate[]{}));
        TypedQuery<FilmSession> query = em.createQuery(cq);
        return query.getResultList();
    }

    public List<FilmSession> getSessionByCriteriaForTomorrow(String []types, String film){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<FilmSession> cq = cb.createQuery(FilmSession.class);

        Root<FilmSession> filmSession = cq.from(FilmSession.class);
        List<Predicate> predicates = new ArrayList<>();
        Predicate predicateForType = null;
        if(types != null && types.length !=0) {
            Predicate[] typePredicate = new Predicate[types.length];
            for (int i = 0; i < types.length; i++) {
                typePredicate[i] = cb.equal(filmSession.get("type"), types[i]);
            }
            predicateForType = cb.or(typePredicate);
        }
        if (predicateForType != null) {
            predicates.add(predicateForType);
        }
        if(!film.equals("all")){
            Predicate filmPredicate = cb.like(filmSession.get("film").get("name"), "%" + film + "%");
            predicates.add(filmPredicate);
        }
        Predicate datePredicate = cb.equal(filmSession.get("sessionDate"), LocalDate.now().plusDays(1));
        predicates.add(datePredicate);
        cq.where(predicates.toArray(new Predicate[]{}));
        TypedQuery<FilmSession> query = em.createQuery(cq);
        return query.getResultList();
    }

    public List<FilmSession> getSessionByCriteriaForDate(String []types, String film, LocalDate sessionDate){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<FilmSession> cq = cb.createQuery(FilmSession.class);

        Root<FilmSession> filmSession = cq.from(FilmSession.class);
        List<Predicate> predicates = new ArrayList<>();
        Predicate predicateForType = null;
        if(types != null && types.length !=0) {
            Predicate[] typePredicate = new Predicate[types.length];
            for (int i = 0; i < types.length; i++) {
                typePredicate[i] = cb.equal(filmSession.get("type"), types[i]);
            }
            predicateForType = cb.or(typePredicate);
        }
        if (predicateForType != null) {
            predicates.add(predicateForType);
        }
        if(!film.equals("all")){
            Predicate filmPredicate = cb.like(filmSession.get("film").get("name"), "%" + film + "%");
            predicates.add(filmPredicate);
        }
        Predicate datePredicate = cb.equal(filmSession.get("sessionDate"), sessionDate);
        predicates.add(datePredicate);
        cq.where(predicates.toArray(new Predicate[]{}));
        TypedQuery<FilmSession> query = em.createQuery(cq);
        return query.getResultList();
    }
}
