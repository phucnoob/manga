package uet.ppvan.mangareader.services;

import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uet.ppvan.mangareader.dto.MangaDTO;
import uet.ppvan.mangareader.entities.Chapter;
import uet.ppvan.mangareader.entities.Manga;
import uet.ppvan.mangareader.repositories.MangaRepository;

@Service
@AllArgsConstructor
public class MangaService {
    private final MangaRepository mangaRepository;

    public void addNewManga(MangaDTO requestData) {
        mangaRepository.save(MangaService.toManga(requestData));
    }

    public void updateManga(Integer id, MangaDTO requestData) {
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

    public List<MangaDTO> getAll(int page, int size) {
       return mangaRepository.findAll(PageRequest.of(page, size))
           .map(MangaService::toDTO).getContent();
    }


    public Optional<MangaDTO> getMangaById(Integer id) {
        return mangaRepository.findById(id)
            .map(MangaService::toDTO);
    }

    public List<Chapter> getAllChapters(Integer id) {
        return mangaRepository.getReferenceById(id).getChapters();
    }

    private static MangaDTO toDTO(Manga manga) {
        return new MangaDTO(
            manga.getName(),
            manga.getCover(),
            manga.getDescription(),
            manga.getAuthor(),
            manga.getOtherName(),
            manga.getStatus()
        );
    }

    private static Manga toManga(MangaDTO mangaDTO) {
        Manga manga = new Manga();
        manga.setName(mangaDTO.name());
        manga.setAuthor(mangaDTO.author());
        manga.setDescription(mangaDTO.description());
        manga.setCover(mangaDTO.cover());
        manga.setStatus(mangaDTO.status());
        manga.setOtherName(mangaDTO.otherName());

        return manga;
    }
}
