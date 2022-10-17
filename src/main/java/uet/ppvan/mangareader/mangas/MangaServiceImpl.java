package uet.ppvan.mangareader.mangas;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uet.ppvan.mangareader.chapters.Chapter;
import uet.ppvan.mangareader.exceptions.NoSuchElementFound;
import uet.ppvan.mangareader.mangas.genres.GenreEntity;
import uet.ppvan.mangareader.mangas.genres.GenreRepository;
import uet.ppvan.mangareader.mangas.interfaces.MangaRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MangaServiceImpl implements uet.ppvan.mangareader.mangas.interfaces.MangaService {
    private final MangaRepository mangaRepository;
    private final GenreRepository genreRepository;

    @Override
    public void addNewManga(MangaRequest requestData) {
        mangaRepository.save(toManga(requestData));
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
       return mangaRepository.findAll(PageRequest.of(page, size))
           .map(this::toDTO).getContent();
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
    public List<Chapter> getAllChapters(Integer id) {
        return mangaRepository.findById(id)
            .map(Manga::getChapters)
            .orElseThrow(() -> new NoSuchElementFound(
                String.format("Manga with id = %s not found.", id)
            ));
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
        var genres = mangaRequest.genre().stream()
            .map(genreRepository::findGenreEntityByGenre)
            .collect(Collectors.toSet());

        manga.setGenres(genres);

        return manga;
    }
}
