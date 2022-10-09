package uet.ppvan.mangareader.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uet.ppvan.mangareader.dto.ObjectResponse;
import uet.ppvan.mangareader.repositories.ChapterRepository;
import uet.ppvan.mangareader.repositories.MangaRepository;

@RestController
@RequestMapping("api/v1/manga/{mangaId}/chapter/")
@AllArgsConstructor
public class ChapterController {

    private final ChapterRepository chapterRepository;

    @GetMapping("/{id}")
    public ResponseEntity<ObjectResponse> getById(
        @PathVariable String mangaId,
        @PathVariable Integer id
    ) {
        return chapterRepository.findById(id).map(
            chapter -> ResponseEntity.ok(
                new ObjectResponse(
                    "success",
                    "Query successfull",
                    chapter
                )
            )).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ObjectResponse(
                    "failed",
                    "Not such chapter with id = " + id,
                    ""
                )
        ));
    }


}
