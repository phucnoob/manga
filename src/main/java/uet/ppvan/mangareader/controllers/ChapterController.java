package uet.ppvan.mangareader.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uet.ppvan.mangareader.dtos.ChapterRequest;
import uet.ppvan.mangareader.dtos.SuccessResponse;
import uet.ppvan.mangareader.services.ChapterService;
import uet.ppvan.mangareader.utils.ResponseFactory;

@RestController
@RequestMapping("api/v1/chapters/")
@AllArgsConstructor
public class ChapterController {

    private final ChapterService service;

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse> getById(
        @PathVariable Integer id
    ) {
        return ResponseEntity.ok(
                new SuccessResponse(
                        SuccessResponse.SUCCESS,
                        "Query successfully.",
                        service.getChapter(id)
                )
        );
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
    public ResponseEntity<Object> updateChapter(
            @RequestParam(name = "id") Integer id,
            @RequestBody ChapterRequest chapter
    ) {
        service.updateChapter(chapter, id);
        return ResponseEntity.ok(ResponseFactory.success("Chapter updated.", chapter));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<SuccessResponse> delete(
        @PathVariable Integer id
    ) {
        service.removeChapter(id);
        return ResponseEntity.ok(
            new SuccessResponse(
                SuccessResponse.SUCCESS,
                "Delete chapter successfully",
                ""
            )
        );
    }

}
