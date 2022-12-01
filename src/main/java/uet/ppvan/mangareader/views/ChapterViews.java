package uet.ppvan.mangareader.views;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uet.ppvan.mangareader.services.ChapterService;

@Controller
@RequestMapping("/chapter")
@RequiredArgsConstructor
public class ChapterViews {

    private final ChapterService service;

    @GetMapping("/{id}")
    public String getChapterWithId(@PathVariable Integer id, Model model) {
        var chapter = service.getChapter(id);

        model.addAttribute("chapter", chapter);
        return "chapter/chapter-details";
    }
}
