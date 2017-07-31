package me.wonwoo.support.jinq;

import me.wonwoo.domain.model.Post;
import me.wonwoo.domain.model.Tag;
import org.jinq.jpa.JPAJinqStream;
import org.jinq.jpa.JinqJPAStreamProvider;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@Component
public class JinqSource {

    private JinqJPAStreamProvider streams;

    @PersistenceUnit
    public void setEntityManagerFactory(
            EntityManagerFactory emf) throws Exception {
        streams = new JinqJPAStreamProvider(emf);
        // initialization here.
        streams.registerAssociationAttribute(Tag.class.getMethod("getTag"), "tag", false);
    }

    public <U> JPAJinqStream<U> streamAll(EntityManager em, Class<U> entity) {
        return streams.streamAll(em, entity);
    }

    public JPAJinqStream<Post> posts(EntityManager em) {
        return this.streamAll(em, Post.class);
    }
}
