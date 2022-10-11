package uet.ppvan.mangareader.controllers;

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
import org.springframework.web.bind.annotation.RestController;
import uet.ppvan.mangareader.dto.ObjectResponse;
import uet.ppvan.mangareader.entities.Chapter;
import uet.ppvan.mangareader.repositories.ChapterRepository;

@RestController
@RequestMapping("api/v1/manga/*/chapter/")
@AllArgsConstructor
public class ChapterController {

    private final ChapterRepository chapterRepository;

    @GetMapping("/{id}")
    public ResponseEntity<ObjectResponse> getById(
        @PathVariable Integer id
    ) {
        return chapterRepository.findById(id).map(
            chapter -> ResponseEntity.ok(
                new ObjectResponse(
                    ObjectResponse.SUCCESS,
                    "Query successfull",
                    chapter
                )
            )).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ObjectResponse(
                    ObjectResponse.FAILED,
                    "Not such chapter with id = " + id,
                    ""
                )
        ));
    }

    @PostMapping("/add")
    public ResponseEntity<ObjectResponse> addChapter(@RequestBody Chapter chapter) {
        var savedChapter = chapterRepository.save(chapter);

        return ResponseEntity.ok(
            new ObjectResponse(
                ObjectResponse.SUCCESS,
                "Add new chapter success",
                savedChapter
            )
        );
    }

    /**
     * Update if exsist else insert.
     */
    @PutMapping("/{id}/update")
    public ResponseEntity<ObjectResponse> updateChapter(
        @PathVariable Integer id,
        @RequestBody Chapter chapter
    ) {
        return chapterRepository.findById(id)
            .map(
            foundedChapter -> {
                foundedChapter.setName(chapter.getName());
                foundedChapter.setManga(chapter.getManga());
                foundedChapter.setUploadDate(chapter.getUploadDate());
                chapterRepository.save(foundedChapter);
                return ResponseEntity.ok(
                    new ObjectResponse(
                        ObjectResponse.SUCCESS,
                        "Query successfull",
                        chapterRepository
                    )
                );
            }
        ).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            new ObjectResponse(
                ObjectResponse.FAILED,
                "Not fould chapter with id = " + id,
                ""
            )
        ));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ObjectResponse> delete(
        @PathVariable Integer id
    ) {
        chapterRepository.deleteById(id);
        return ResponseEntity.ok(
            new ObjectResponse(
                ObjectResponse.SUCCESS,
                "Delete chapter successfull",
                ""
            )
        );
    }

}
