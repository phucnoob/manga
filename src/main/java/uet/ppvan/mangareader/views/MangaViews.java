package uet.ppvan.mangareader.views;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uet.ppvan.mangareader.services.MangaService;

@RequestMapping("/manga")
@Controller
@RequiredArgsConstructor
public class MangaViews {

    private final MangaService service;

    @GetMapping("/{id}")
    public String getManga(@PathVariable Integer id, Model model) {

        var manga = service.getMangaById(id);

        model.addAttribute("manga", manga);

        return "manga/details";
    }

}
