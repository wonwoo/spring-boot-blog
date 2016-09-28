//package me.wonwoo.hibernate;
//
//import org.hibernate.search.jpa.FullTextEntityManager;
//import org.hibernate.search.jpa.Search;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.ApplicationListener;
//import org.springframework.stereotype.Component;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//@Component
//public class BuildSearchIndex implements ApplicationListener<ApplicationReadyEvent> {
//
//    // ------------------------
//    // PRIVATE FIELDS
//    // ------------------------
//
//    @PersistenceContext(unitName = "wordPress")
//    private EntityManager entityManager;
//
//
//    // ------------------------
//    // PUBLIC METHODS
//    // ------------------------
//
//    /**
//     * Create an initial Lucene index for the data already present in the
//     * database.
//     * This method is called when Spring's startup.
//     */
//    @Override
//    public void onApplicationEvent(final ApplicationReadyEvent event) {
//        try {
//            FullTextEntityManager fullTextEntityManager =
//                    Search.getFullTextEntityManager(entityManager);
//            fullTextEntityManager.createIndexer().startAndWait();
//        }
//        catch (InterruptedException e) {
//            throw new RuntimeException("An error occurred trying to build the search index: " + e.toString());
//        }
//    }
//}
