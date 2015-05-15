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

        List<Label> labels = (List<Label>) (session.createCriteria(Label.class).list());

        session.close();
        return labels;

    }

    @RequestMapping(value = "/{id}")
    public Label getById(@PathVariable("id") int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Label label = (Label) session.get(Label.class, id);

        session.close();
        return label;
    }

    @RequestMapping(value = "/add")
    public Label add(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "beatportId", required = false) Integer beatportId,
            @RequestParam(value = "beatportUrl", required = false) String beatportUrl,
            @RequestParam(value = "discogsId", required = false) Integer discogsId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Label label = new Label();
        label.setName(name);
        label.setBeatportId(beatportId);
        label.setBeatportUrl(beatportUrl);
        label.setDiscogsId(discogsId);

        session.save(label);
        session.getTransaction().commit();
        session.close();
        return label;
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

        Label label = (Label) session.get(Label.class, id);
        label.setName(name);
        label.setBeatportId(beatportId);
        label.setBeatportUrl(beatportUrl);
        label.setDiscogsId(discogsId);

        session.save(label);
        session.getTransaction().commit();
        session.close();
    }

    @RequestMapping(value = "/delete/{id}")
    public void delete(@PathVariable("id") int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Label label = (Label) session.get(Label.class, id);

        session.delete(label);
        session.getTransaction().commit();
        session.close();
    }

}
