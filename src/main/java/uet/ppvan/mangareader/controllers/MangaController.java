package uet.ppvan.mangareader.controllers;

import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uet.ppvan.mangareader.dto.ObjectResponse;
import uet.ppvan.mangareader.entities.Manga;
import uet.ppvan.mangareader.repositories.MangaRepository;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/manga")
public class MangaController {

    private final MangaRepository mangaRepository;

    @GetMapping("/{id}")
    public ResponseEntity<ObjectResponse> getById(@PathVariable Integer id) {
        Optional<Manga> foundedManga = mangaRepository.findById(id);
        return foundedManga.map(manga -> ResponseEntity.ok()
            .body(new ObjectResponse(
                "success",
                "Query successfull",
                manga
            ))).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ObjectResponse(
                "failed",
                "Not found manga with id = " + id,
                ""
            )));
    }


    @GetMapping("/all")
    public List<Manga> getAll(
        @RequestParam(required = false, defaultValue = "0") Integer page,
        @RequestParam(required = false, defaultValue = "5") Integer size
    ) {
        return mangaRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    @PostMapping("/add")
    public ResponseEntity<ObjectResponse> post(@RequestBody Manga manga) {
        Manga savedData = mangaRepository.save(manga);
        return ResponseEntity.ok(new ObjectResponse("success", "Manga saved", savedData));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ObjectResponse> update(
        @PathVariable Integer id,
        @RequestBody Manga updateManga
    ) {
        Optional<Manga> founndedManga = mangaRepository.findById(id);
        Manga savedManga = founndedManga.map(manga -> {
            manga.setStatus(updateManga.getStatus());
            manga.setAuthor(updateManga.getAuthor());
            manga.setName(updateManga.getName());
            manga.setDescription(updateManga.getDescription());
            manga.setCover(updateManga.getCover());
            manga.setOtherName(updateManga.getOtherName());

            return mangaRepository.save(manga);
        }).orElseGet(() -> {
            updateManga.setId(id);
            return updateManga;
        });

        return ResponseEntity.ok(
            new ObjectResponse(
                "success",
                "Update manga successfull",
                savedManga
            )
        );
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ObjectResponse> deleteById(
        @PathVariable Integer id
    ) {
        if (mangaRepository.existsById(id)) {
            mangaRepository.deleteById(id);
            return ResponseEntity.ok(
                new ObjectResponse(
                    "success",
                    "Manga deleted successfully",
                    ""
                )
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ObjectResponse(
                    "failed",
                    "Can not found manga with id =" + id,
                    ""
                )
            );
        }
    }
}
