package uet.ppvan.mangareader.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uet.ppvan.mangareader.dtos.ChapterOverview;
import uet.ppvan.mangareader.dtos.MangaOverview;
import uet.ppvan.mangareader.dtos.MangaRequest;
import uet.ppvan.mangareader.services.MangaService;
import uet.ppvan.mangareader.services.SearchMangaService;
import uet.ppvan.mangareader.utils.ResponseFactory;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@AllArgsConstructor
@RestController
@Validated
@Slf4j
@RequestMapping("/api/v1/mangas")
public class MangaController {
    private final MangaService service;
    private final SearchMangaService searchService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {

        var foundedManga = service.getMangaById(id);
        return ResponseFactory.success(foundedManga);
    }


    @GetMapping("/ajax/search")
    public List<MangaOverview> searchMangaByName(@RequestParam String name) {
        return searchService.searchByName(name);
    }

    @GetMapping("")
    public ResponseEntity<?> getOverview(
        @RequestParam(required = false, defaultValue = "0") @Valid @Min(0) @Max(Integer.MAX_VALUE) Integer page,
        @RequestParam(required = false, defaultValue = "20") @Valid @Min(0) @Max(Integer.MAX_VALUE) Integer size
    ) {
        return ResponseFactory.success(service.getAllOverview(page, size));
    }

    @GetMapping("/popular")
    public ResponseEntity<?> popularManga() {
        return ResponseFactory.success(service.getAllOverview(0, 4));
    }

    @PostMapping("")
    public ResponseEntity<?> post(@RequestBody @Valid MangaRequest manga) {
        Integer newRowID = service.addNewManga(manga);
        return ResponseFactory.success("Manga saved", newRowID);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<?> update(
        @PathVariable Integer id,
        @RequestBody MangaRequest updateManga
    ) {
        service.updateManga(id, updateManga);
        return ResponseFactory.success("Update manga successfully", updateManga);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteById(
        @PathVariable Integer id
    ) {
        service.deleteManga(id);
        return ResponseFactory.success("Manga deleted.", "");
    }

    @Deprecated
    @GetMapping("/{id}/chapters")
    public ResponseEntity<?> chapters(
        @PathVariable Integer id
    ) {
        List<ChapterOverview> chapters = service.getAllChapters(id);
        return ResponseFactory.success(chapters);
    }
}
