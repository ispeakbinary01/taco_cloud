package isb.tacocloud;

import java.util.Arrays;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import isb.tacocloud.tacoingredient.TacoIngredient;
import isb.tacocloud.taco.Taco;
import isb.tacocloud.tacoingredient.TacoIngredient.Type;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/design")
public class DesignTacoController {
    static String design = "design";

    private List<TacoIngredient> filterByType(List<TacoIngredient> ting, Type type) {
        return ting.stream()
        .filter(x -> x.getType().equals(type))
        .collect(Collectors.toList());
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        List<TacoIngredient> ingredients = Arrays.asList(
            new TacoIngredient("FLTO", "Flour Tortilla", Type.WRAP),
            new TacoIngredient("COTO", "Corn Tortilla", Type.WRAP),
            new TacoIngredient("GRBF", "Ground Beef", Type.PROTEIN),
            new TacoIngredient("CARN", "Carnitas", Type.PROTEIN),
            new TacoIngredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
            new TacoIngredient("LETC", "Lettuce", Type.VEGGIES),
            new TacoIngredient("CHED", "Cheddar", Type.CHEESE),
            new TacoIngredient("JACK", "Monterrey Jack", Type.CHEESE),
            new TacoIngredient("SLSA", "Salsa", Type.SAUCE),
            new TacoIngredient("SRCR", "Sour Cream", Type.SAUCE)
        );

        Type[] types = TacoIngredient.Type.values();
        for(Type type: types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
        model.addAttribute(design, new Taco());
    }

    @GetMapping
    public String showDesignForm(Model model) {
        model.addAttribute(design, new Taco());
        return design;
    }

    @PostMapping
    public String processDesign(@Valid @ModelAttribute("design") Taco taco, Errors errors) {
        if(errors.hasErrors()) {
            return design;
        }
        log.info("The taco: " + taco);

        return "redirect:/orders/current";
    }
}
