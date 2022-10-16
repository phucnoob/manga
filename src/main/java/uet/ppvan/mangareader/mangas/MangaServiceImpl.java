package uet.ppvan.mangareader.mangas;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uet.ppvan.mangareader.chapters.Chapter;
import uet.ppvan.mangareader.exceptions.NoSuchElementFound;
import uet.ppvan.mangareader.mangas.enums.Status;
import uet.ppvan.mangareader.mangas.interfaces.MangaRepository;

@Service
@AllArgsConstructor
public class MangaServiceImpl implements uet.ppvan.mangareader.mangas.interfaces.MangaService {
    private final MangaRepository mangaRepository;

    @Override
    public void addNewManga(MangaRequest requestData) {
        mangaRepository.save(MangaServiceImpl.toManga(requestData));
    }

    @Override
    public void updateManga(Integer id, MangaRequest requestData) {
        Manga manga = MangaServiceImpl.toManga(requestData);

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
           .map(MangaServiceImpl::toDTO).getContent();
    }


    @Override
    public MangaRequest getMangaById(Integer id) {
        return mangaRepository.findById(id)
            .map(MangaServiceImpl::toDTO)
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

    private static MangaRequest toDTO(Manga manga) {
        return new MangaRequest(
            manga.getName(),
            manga.getCover(),
            manga.getDescription(),
            manga.getAuthor(),
            manga.getOtherName(),
            manga.getStatus(),
            manga.getGenre()
        );
    }

    private static Manga toManga(MangaRequest mangaRequest) {
        Manga manga = new Manga();
        manga.setName(mangaRequest.name());
        manga.setAuthor(mangaRequest.author());
        manga.setDescription(mangaRequest.description());
        manga.setCover(mangaRequest.cover());
        manga.setStatus(mangaRequest.status());
        manga.setOtherName(mangaRequest.otherName());
        manga.setGenre(mangaRequest.genre());

        return manga;
    }
}
