package uet.ppvan.mangareader.services.impl;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.scope.SearchScope;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uet.ppvan.mangareader.dtos.MangaOverview;
import uet.ppvan.mangareader.models.Manga;
import uet.ppvan.mangareader.services.MangaService;
import uet.ppvan.mangareader.services.SearchMangaService;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class SearchMangaServiceImpl implements SearchMangaService {

    private final EntityManager entityManager;
    private final MangaService mangaService;

    @Override
    @Transactional
    public List<MangaOverview> searchByName(String name) {
        SearchSession searchSession = Search.session(entityManager);

        SearchScope<Manga> scope = searchSession.scope(Manga.class);

        SearchResult<Integer> result = searchSession.search( scope )
            .select(entity -> entity.id(Integer.class))
            .where( scope.predicate().match()
                .fields( "name", "otherName" )
                .matching( name )
                .toPredicate() )
            .fetch( 10 );

        return result.hits().stream()
            .map(mangaService::getMangaOverviewById)
            .collect(Collectors.toList());
    }
}
