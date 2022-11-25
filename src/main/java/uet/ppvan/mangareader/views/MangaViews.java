package uet.ppvan.mangareader.views;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/manga")
@Controller
public class MangaViews {

    @GetMapping("/{id}")
    public String getManga(@PathVariable Integer id, Model model) {
        model.addAttribute("id", id);

        return "manga/details";
    }

}
