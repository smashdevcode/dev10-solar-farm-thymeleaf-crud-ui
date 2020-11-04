package learn.solarfarm.controllers.ui;

import learn.solarfarm.data.DataAccessException;
import learn.solarfarm.domain.SolarPanelService;
import learn.solarfarm.models.SolarPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/solarpanels")
public class SolarPanelUIController {
    @Autowired
    SolarPanelService service;

    @GetMapping
    public String get(Model model) throws DataAccessException {
        List<SolarPanel> panels = service.findAll();
        model.addAttribute("panels", panels);
        return "solarpanels/list";
    }
}
