package uet.ppvan.mangareader.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uet.ppvan.mangareader.dtos.ChapterRequest;
import uet.ppvan.mangareader.services.ChapterService;
import uet.ppvan.mangareader.utils.ResponseFactory;

@RestController
@RequestMapping("api/v1/chapters/")
@AllArgsConstructor
public class ChapterController {

    private final ChapterService service;

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
        @PathVariable Integer id
    ) {
        return ResponseFactory.success(service.getChapter(id));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addChapter(
        @RequestParam(name = "manga_id") Integer mangaId,
        @RequestBody ChapterRequest request
    ) {
        service.addNewChapter(request, mangaId);

        return ResponseFactory.success(request);
    }

    /**
     * Update
     */
    @PutMapping("/update")
    public ResponseEntity<?> updateChapter(
        @RequestParam(name = "id") Integer id,
        @RequestBody ChapterRequest chapter
    ) {
        service.updateChapter(chapter, id);
        return ResponseFactory.success("Chapter updated.", chapter);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(
        @PathVariable Integer id
    ) {
        service.removeChapter(id);
        return ResponseFactory.success("Delete chapter successfully", "");
    }

}
