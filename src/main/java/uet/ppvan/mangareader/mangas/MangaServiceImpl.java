package uet.ppvan.mangareader.mangas;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uet.ppvan.mangareader.chapters.ChapterOverview;
import uet.ppvan.mangareader.chapters.ChapterRepository;
import uet.ppvan.mangareader.comons.exceptions.NoSuchElementFound;
import uet.ppvan.mangareader.mangas.genres.GenreEntity;
import uet.ppvan.mangareader.mangas.genres.GenreRepository;
import uet.ppvan.mangareader.mangas.interfaces.MangaRepository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MangaServiceImpl implements uet.ppvan.mangareader.mangas.interfaces.MangaService {
    private final MangaRepository mangaRepository;
    private final GenreRepository genreRepository;

    private final ChapterRepository chapterRepository;

    @Override
    public Integer addNewManga(MangaRequest requestData) {
        Manga manga = toManga(requestData);
        mangaRepository.save(manga);

        return manga.getId();
    }

    @Override
    public void updateManga(Integer id, MangaRequest requestData) {
        Manga manga = toManga(requestData);

        if (mangaRepository.existsById(id)) {
            manga.setId(id);// update
        } else {
            // add new manga
        }

        mangaRepository.save(manga);
    }

    @Override
    public void deleteManga(Integer id) {
        mangaRepository.deleteById(id);
    }

    @Override
    public List<MangaRequest> getAll(int page, int size) {
       return mangaRepository.findAll(PageRequest.of(page, size,
               Sort.by("lastUpdate").descending()))
                    .map(this::toDTO).getContent();
    }

    @Override
    public List<MangaOverview> getAllOverview(int page, int size) {
        return mangaRepository.findAllOverview(PageRequest.of(page, size,
                Sort.by("lastUpdate").descending()))
            .getContent();
    }

    @Override
    public MangaRequest getMangaById(Integer id) {
        return mangaRepository.findById(id)
            .map(this::toDTO)
            .orElseThrow(() -> new NoSuchElementFound(
                String.format("Manga with id = %s not found.", id)
            ));
    }

    @Override
    public MangaOverview getMangaOverviewById(Integer id) {
        return mangaRepository.findOverviewById(id)
            .orElseThrow(() -> new NoSuchElementFound(
                String.format("Manga with id = %s not found.", id)
            ));
    }

    @Override
    public List<ChapterOverview> getAllChapters(Integer id) {
        return chapterRepository.findByManga_Id(id);
    }


    private MangaRequest toDTO(Manga manga) {
        return new MangaRequest(
            manga.getName(),
            manga.getCover(),
            manga.getDescription(),
            manga.getAuthor(),
            manga.getOtherName(),
            manga.getStatus(),
            manga.getGenres().stream().map(GenreEntity::getGenre).collect(Collectors.toSet())
        );
    }

    private Manga toManga(MangaRequest mangaRequest) {
        Manga manga = new Manga();
        manga.setName(mangaRequest.name());
        manga.setAuthor(mangaRequest.author());
        manga.setDescription(mangaRequest.description());
        manga.setCover(mangaRequest.cover());
        manga.setStatus(mangaRequest.status());
        manga.setOtherName(mangaRequest.otherName());
        manga.setLastUpdate(LocalDateTime.now(ZoneOffset.UTC));
        var genres = mangaRequest.genres().stream()
            .map(g -> {
                System.out.println(g);
                return genreRepository.findGenreEntityByGenre(g);
            })
            .collect(Collectors.toSet());

        manga.setGenres(genres);

        return manga;
    }
}
