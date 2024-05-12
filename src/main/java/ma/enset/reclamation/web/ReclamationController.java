package ma.enset.reclamation.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ma.enset.reclamation.entities.Reclamation;
import ma.enset.reclamation.repositories.ReclamationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@AllArgsConstructor
public class ReclamationController {
    private ReclamationRepository reclamationRepository;

    @GetMapping(path="/index")
    public String clients(Model model,
                          @RequestParam(name = "page",defaultValue = "0") int page,
                          @RequestParam(name = "size",defaultValue = "5") int size,
                          @RequestParam(name = "keyword",defaultValue = "")String keyword){
        Page<Reclamation> pageReclamations=reclamationRepository.findByTypeContains(keyword, PageRequest.of(page,size));
        model.addAttribute("listReclamations",pageReclamations.getContent());
        model.addAttribute("pages",new int[pageReclamations.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);
        return "reclamations";
    }

    @GetMapping("/delete")
    public String delete(Long numero,String keyword, int page){
        reclamationRepository.deleteById(numero);
        return "redirect:/index?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/")
    public String home(Long numero){
        return "redirect:/index";
    }

    @GetMapping("/reclamations")
    @ResponseBody
    public List<Reclamation> listReclamations(){
        return reclamationRepository.findAll();
    }

    @GetMapping("/formReclamations")
    public String formReclamations(Model model){
        model.addAttribute("reclamation",new Reclamation());
        return "formReclamations";
    }
    @PostMapping("/save")
    public String save(Model model, @Valid Reclamation reclamation , BindingResult bindingResult,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "") String keyword){
        if(bindingResult.hasErrors()) return "formReclamations";
        reclamationRepository.save(reclamation);
        return "redirect:/index?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/editReclamations")
    public String editReclamation(Model model, Long numero,String keyword,int page){
        Reclamation reclamation=reclamationRepository.findById(numero).orElse(null);
        if(reclamation==null) throw new RuntimeException("Reclamation Introuvable");
        model.addAttribute("reclamation",reclamation);
        model.addAttribute("page",page);
        model.addAttribute("keyword",keyword);
        return "editReclamations";
    }
}
