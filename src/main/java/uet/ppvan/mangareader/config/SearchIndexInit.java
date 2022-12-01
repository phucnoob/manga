package uet.ppvan.mangareader.config;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.transaction.annotation.Transactional;
import uet.ppvan.mangareader.models.Manga;


/**
 * Only turn on the @Configuration if index has not been created.
 */
//@Configuration
@RequiredArgsConstructor
@Slf4j
public class SearchIndexInit implements ApplicationListener<ApplicationReadyEvent> {


    private final EntityManager entityManager;


    @Override
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("Start to indexes.");

        SearchSession searchSession = Search.session(entityManager);
        MassIndexer indexer = searchSession.massIndexer(Manga.class)
            .threadsToLoadObjects(7);


        try {

            indexer.startAndWait();

        } catch (InterruptedException e) {

            log.warn("Failed to load data from database");

            Thread.currentThread().interrupt();

        }

        log.info("Completed Indexing");
    }
}
