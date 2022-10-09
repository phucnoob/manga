package uet.ppvan.mangareader.controllers;

import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
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
import uet.ppvan.mangareader.dto.MangaDTO;
import uet.ppvan.mangareader.dto.ObjectResponse;
import uet.ppvan.mangareader.entities.Chapter;
import uet.ppvan.mangareader.repositories.MangaRepository;
import uet.ppvan.mangareader.services.MangaService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/manga")
public class MangaController {

    private final MangaRepository mangaRepository;
    private final MangaService mangaService;

    @GetMapping("/{id}")
    public ResponseEntity<ObjectResponse> getById(@PathVariable Integer id) {
        Optional<MangaDTO> foundedManga = mangaService.getMangaById(id);
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
    public List<MangaDTO> getAll(
        @RequestParam(required = false, defaultValue = "0") Integer page,
        @RequestParam(required = false, defaultValue = "5") Integer size
    ) {
        return mangaService.getAll(page, size);
    }

    @PostMapping("/add")
    public ResponseEntity<ObjectResponse> post(@RequestBody MangaDTO manga) {
        mangaService.addNewManga(manga);
        return ResponseEntity.ok(new ObjectResponse("success", "Manga saved", manga));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ObjectResponse> update(
        @PathVariable Integer id,
        @RequestBody MangaDTO updateManga
    ) {
        mangaService.updateManga(id, updateManga);
        return ResponseEntity.ok(
            new ObjectResponse(
                "success",
                "Update manga successfull",
                updateManga
            )
        );
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ObjectResponse> deleteById(
        @PathVariable Integer id
    ) {
        mangaService.deleteManga(id);
        return ResponseEntity.ok(
            new ObjectResponse(
                "success",
                "Manga deleted successfully",
                ""
            )
        );
    }

    @GetMapping("/{id}/chapters")
    public List<Chapter> chapters(
        @PathVariable Integer id
    ) {
        return mangaService.getAllChapters(id);
    }
}
