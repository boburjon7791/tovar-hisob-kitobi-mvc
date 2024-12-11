package com.example.tovar_hisob_kitobi_mvc.implementation.home.controller;

import com.example.tovar_hisob_kitobi_mvc.base.common.ApiResponse;
import com.example.tovar_hisob_kitobi_mvc.base.controller.BaseControllerMVC;
import com.example.tovar_hisob_kitobi_mvc.implementation.home.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Year;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final HomeService homeService;
    public static final String _prixodSumma="prixodSumma";
    public static final String _prixodSummaByCreated="prixodSummaByCreated";
    public static final String _rasxodSumma="rasxodSumma";
    public static final String _rasxodSummaByCreated="rasxodSummaByCreated";
    public static final String _vozvratSumma="vozvratSumma";
    public static final String _vozvratSummaByCreated="vozvratSummaByCreated";
    public static final String _type="type";
    public static final String _types="types";
    public static final String _total="total";
    public static final String _year="year";
    public static final String _years="years";

    @GetMapping
    public String home(Model model, @RequestParam(required = false, defaultValue = "0") int year, @RequestParam(required = false, defaultValue = HomeController._prixodSumma) String type){
        int nowYear = Year.now().getValue();
        year=year == 0 ? nowYear : year;

        List<Integer> years = IntStream.rangeClosed(nowYear - 10, nowYear).boxed().sorted(Comparator.reverseOrder()).collect(Collectors.toCollection(LinkedList::new));
        int finalYear = year;
        years.removeIf(y -> y == finalYear);
        years.addFirst(year);
        model.addAttribute(_years, years);

        ApiResponse<?> response = homeService.home(type, year, model);
        model.addAttribute(BaseControllerMVC._response, response);

        List<String> types=new ArrayList<>(){{
            add(_prixodSumma);
            add(_prixodSummaByCreated);
            add(_rasxodSumma);
            add(_rasxodSummaByCreated);
            add(_vozvratSumma);
            add(_vozvratSummaByCreated);
        }};
        types.removeIf(t -> t.equals(type));
        types.addFirst(type);
        model.addAttribute(_types, types);
        model.addAttribute(_type, type);
        model.addAttribute(_year, year);

        return type.endsWith("ByCreated")?"home/created-by":"home/home";
    }

    /*@GetMapping("/home")
    @ResponseBody
    public ApiResponse<?> home(@RequestParam(required = false, defaultValue = "0") int year, @RequestParam(required = false, defaultValue = HomeController._prixodSumma) String type){
        return homeService.yearlySumma(type, year == 0 ? Year.now().getValue() : year);
    }

    @GetMapping("/created-by")
    @ResponseBody
    public ApiResponse<?> createdBy(Model model, @RequestParam(required = false, defaultValue = "0") int year, @RequestParam(required = false, defaultValue = HomeController._prixodSumma) String type){
        return homeService.yearlySummaByCreated(type, year==0? Year.now().getValue():year);
    }*/


}
