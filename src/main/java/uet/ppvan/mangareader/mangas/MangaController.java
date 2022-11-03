package uet.ppvan.mangareader.mangas;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uet.ppvan.mangareader.chapters.ChapterOverview;
import uet.ppvan.mangareader.comons.ResponseFactory;
import uet.ppvan.mangareader.comons.SuccessResponse;
import uet.ppvan.mangareader.mangas.interfaces.MangaService;
import uet.ppvan.mangareader.search.SearchMangaService;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@AllArgsConstructor
@RestController
@Validated
@RequestMapping("/api/v1/manga")
public class MangaController {
    private final MangaService service;
    private final SearchMangaService searchService;

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse> getById(@PathVariable Integer id) {
        MangaRequest foundedManga = service.getMangaById(id);
        return ResponseEntity.ok(
                ResponseFactory.success(foundedManga)
        );
    }


    @GetMapping("/ajax/search")
    public List<MangaOverview> searchMangaByName(@RequestParam String name) {
        return searchService.searchByName(name);
    }

    @GetMapping({ "/all", "/latest" })
    public ResponseEntity<SuccessResponse> getOverview(
        @RequestParam(required = false, defaultValue = "0") @Valid @Min(0) @Max(Integer.MAX_VALUE) Integer page,
        @RequestParam(required = false, defaultValue = "5") @Valid @Min(0) @Max(Integer.MAX_VALUE) Integer size,
        @RequestParam(required = false, defaultValue = "false") @Valid Boolean details
    ) {
        if (details) {
            return ResponseEntity.ok(
                ResponseFactory.success(service.getAll(page, size))
            );
        } else {
            return ResponseEntity.ok(
                ResponseFactory.success(service.getAllOverview(page, size))
            );
        }
    }
    @PostMapping("/add")
    public ResponseEntity<SuccessResponse> post(@RequestBody @Valid MangaRequest manga) {
        Integer newRowID = service.addNewManga(manga);
        return ResponseEntity.ok(
                ResponseFactory.success("Manga saved", newRowID)
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SuccessResponse> update(
        @PathVariable Integer id,
        @RequestBody MangaRequest updateManga
    ) {
        service.updateManga(id, updateManga);
        return ResponseEntity.ok(
            ResponseFactory.success("Update manga successfully", updateManga)
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<SuccessResponse> deleteById(
        @PathVariable Integer id
    ) {
        service.deleteManga(id);
        return ResponseEntity.ok(
            ResponseFactory.success("Manga deleted.", "")
        );
    }

    @GetMapping("/{id}/chapters")
    public ResponseEntity<SuccessResponse> chapters(
        @PathVariable Integer id
    ) {
        List<ChapterOverview> chapters = service.getAllChapters(id);
        return ResponseEntity.ok(
                ResponseFactory.success(chapters)
        );
    }
}
