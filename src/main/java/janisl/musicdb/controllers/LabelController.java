package janisl.musicdb.controllers;

import janisl.musicdb.HibernateUtil;
import janisl.musicdb.models.Label;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.hibernate.Session;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/label")
public class LabelController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Label> getList() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        List<Label> labels = (List<Label>) (session.createCriteria(Label.class).list());

        session.close();
        return labels;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Label getById(@PathVariable("id") int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Label label = (Label) session.get(Label.class, id);

        session.close();
        return label;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity add(@RequestBody Label label) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        session.save(label);
        session.getTransaction().commit();
        session.close();
        URI locationUri;
        try {
            locationUri = new URI("/label/" + label.getId().toString());
        } catch (URISyntaxException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().location(locationUri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable("id") int id, @RequestBody Label newLabel) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Label label = (Label) session.get(Label.class, id);
        if (label == null)
            return ResponseEntity.notFound().build();
        label.setName(newLabel.getName());
        label.setBeatportId(newLabel.getBeatportId());
        label.setBeatportUrl(newLabel.getBeatportUrl());
        label.setDiscogsId(newLabel.getDiscogsId());

        session.save(label);
        session.getTransaction().commit();
        session.close();
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Label label = (Label) session.get(Label.class, id);
        if (label == null)
            return ResponseEntity.notFound().build();

        session.delete(label);
        session.getTransaction().commit();
        session.close();
        return ResponseEntity.ok().build();
    }

}
