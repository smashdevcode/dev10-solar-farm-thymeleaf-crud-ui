package learn.solarfarm.controllers.ui;

import learn.solarfarm.data.DataAccessException;
import learn.solarfarm.domain.Result;
import learn.solarfarm.domain.SolarPanelService;
import learn.solarfarm.models.Material;
import learn.solarfarm.models.SolarPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
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

    @GetMapping("/add")
    public String add(@ModelAttribute("panel") SolarPanel panel, Model model) {
        model.addAttribute("maxRowColumn", SolarPanelService.MAX_ROW_COLUMN);
        model.addAttribute("maxInstallationYear", SolarPanelService.getMaxInstallationYear());

        // Set default values on the model...
        panel.setRow(1);
        panel.setColumn(1);
        panel.setMaterial(Material.POLY_SI);

        return "solarpanels/create";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("panel") @Valid SolarPanel panel, BindingResult result, Model model) throws DataAccessException {
        if (result.hasErrors()) {
            model.addAttribute("maxRowColumn", SolarPanelService.MAX_ROW_COLUMN);
            model.addAttribute("maxInstallationYear", SolarPanelService.getMaxInstallationYear());
            return "solarpanels/create";
        }

        Result<SolarPanel> serviceResult = service.create(panel);

        // If I still had manual validations in my service, I'd need to manually map
        // the service result errors to the binding result errors like this...
//        if (!serviceResult.isSuccess()) {
//            for (String message : serviceResult.getMessages()) {
//                result.addError(new ObjectError("panel", message));
//            }
//            model.addAttribute("maxRowColumn", SolarPanelService.MAX_ROW_COLUMN);
//            model.addAttribute("maxInstallationYear", SolarPanelService.getMaxInstallationYear());
//            return "solarpanels/create";
//        }

        return "redirect:/solarpanels";
    }
}
