package uet.ppvan.mangareader.chapters;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uet.ppvan.mangareader.dto.ObjectResponse;
import uet.ppvan.mangareader.dto.ResponseFactory;

@RestController
@RequestMapping("api/v2/chapter/")
@AllArgsConstructor
public class ChapterController {

    private final ChapterService service;

    @GetMapping("/{id}")
    public ResponseEntity<ObjectResponse> getById(
        @PathVariable Integer id
    ) {
        return ResponseEntity.ok(
                new ObjectResponse(
                        ObjectResponse.SUCCESS,
                        "Query successfully.",
                        service.getChapter(id)
                )
        );
    }

    @PostMapping("/add")
    public ResponseEntity<ObjectResponse> addChapter(
            @RequestParam(name = "manga_id") Integer mangaId,
            @RequestBody ChapterRequest request
    ) {
        service.addNewChapter(request, mangaId);

        return ResponseEntity.ok(
            ResponseFactory.success(request)
        );
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
    public ResponseEntity<ObjectResponse> delete(
        @PathVariable Integer id
    ) {
        service.removeChapter(id);
        return ResponseEntity.ok(
            new ObjectResponse(
                ObjectResponse.SUCCESS,
                "Delete chapter successfully",
                ""
            )
        );
    }

}
