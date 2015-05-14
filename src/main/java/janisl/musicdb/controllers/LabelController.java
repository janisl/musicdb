package janisl.musicdb.controllers;

import janisl.musicdb.HibernateUtil;
import janisl.musicdb.models.Label;
import java.util.List;
import org.hibernate.Session;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/label")
public class LabelController {

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Label> getList() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        List<Label> artists = (List<Label>) (session.createCriteria(Label.class).list());

        session.close();
        return artists;

    }

    @RequestMapping(value = "/{id}")
    public Label getById(@PathVariable("id") int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Label artist = (Label) session.get(Label.class, id);

        session.close();
        return artist;
    }

    @RequestMapping(value = "/add")
    public void add(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "beatportId") Integer beatportId,
            @RequestParam(value = "beatportUrl") String beatportUrl,
            @RequestParam(value = "discogsId") Integer discogsId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Label artist = new Label();
        artist.setName(name);
        artist.setBeatportId(beatportId);
        artist.setBeatportUrl(beatportUrl);
        artist.setDiscogsId(discogsId);

        session.save(artist);
        session.getTransaction().commit();
        session.close();
    }

    @RequestMapping(value = "/update/{id}")
    public void update(
            @PathVariable("id") int id,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "beatportId") Integer beatportId,
            @RequestParam(value = "beatportUrl") String beatportUrl,
            @RequestParam(value = "discogsId") Integer discogsId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Label artist = (Label) session.get(Label.class, id);
        artist.setName(name);
        artist.setBeatportId(beatportId);
        artist.setBeatportUrl(beatportUrl);
        artist.setDiscogsId(discogsId);

        session.save(artist);
        session.getTransaction().commit();
        session.close();
    }

    @RequestMapping(value = "/delete/{id}")
    public void delete(@PathVariable("id") int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Label artist = (Label) session.get(Label.class, id);

        session.delete(artist);
        session.getTransaction().commit();
        session.close();
    }

}
