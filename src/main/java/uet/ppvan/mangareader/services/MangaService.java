package uet.ppvan.mangareader.services;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uet.ppvan.mangareader.dto.MangaRequest;
import uet.ppvan.mangareader.entities.Chapter;
import uet.ppvan.mangareader.entities.Manga;
import uet.ppvan.mangareader.exceptions.NoSuchElementFound;
import uet.ppvan.mangareader.repositories.MangaRepository;

@Service
@AllArgsConstructor
public class MangaService {
    private final MangaRepository mangaRepository;

    public void addNewManga(MangaRequest requestData) {
        mangaRepository.save(MangaService.toManga(requestData));
    }

    public void updateManga(Integer id, MangaRequest requestData) {
        Manga manga = MangaService.toManga(requestData);

        if (mangaRepository.existsById(id)) {
            manga.setId(id);// update
        } else {
            // add new manga
        }

        mangaRepository.save(manga);
    }

    public void deleteManga(Integer id) {
        mangaRepository.deleteById(id);
    }

    public List<MangaRequest> getAll(int page, int size) {
       return mangaRepository.findAll(PageRequest.of(page, size))
           .map(MangaService::toDTO).getContent();
    }


    public MangaRequest getMangaById(Integer id) {
        return mangaRepository.findById(id)
            .map(MangaService::toDTO)
            .orElseThrow(() -> new NoSuchElementFound(
                String.format("Manga with id = %s not found.", id)
            ));
    }

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
            manga.getStatus()
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

        return manga;
    }
}
