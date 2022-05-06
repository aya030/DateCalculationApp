package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.DateCalc;
import com.example.demo.service.DateService;

@Controller
@Validated
@RequestMapping("/datecalc")
public class DateController {

	private final DateService dateService;

	public DateController(DateService dateService) {
		this.dateService = dateService;
	}

	/* Top */
	@GetMapping("/index")
	public String index(Model model) {

		List<DateCalc> dateList = dateService.getDateList();
		model.addAttribute("dateList", dateList);

		return "index";
	}

	/* 計算 */
	@GetMapping("/calc")
	public String calc(Model model, @RequestParam("dateinput") String inputDate) {
		List<DateCalc> dateList = dateService.getDateList();
		model.addAttribute("dateList", dateList);

		String standardDate = inputDate.replace('-', '/');
		model.addAttribute("dateinput", standardDate);

		List<String> stringDate = dateService.calculationDate(inputDate);
		model.addAttribute("stringDate", stringDate);

		return "index";
	}

	/* 新規登録 */
	@GetMapping("/new")
	public String newDate(Model model, @ModelAttribute DateCalc dateCalc) {

		model.addAttribute("dateCalc", dateCalc);

		/* plusyear,plusmonth,plusdateの初期値 */
		model.addAttribute("plusyear", "0");
		model.addAttribute("plusmonth", "0");
		model.addAttribute("plusday", "0");
		return "date/new";
	}

	@PostMapping("/new")
	public String create(Model model, @Validated @ModelAttribute DateCalc dateCalc, BindingResult result) {
		
		if (result.hasErrors()) {

			/* plusyear,plusmonth,plusdateの初期値 */
			model.addAttribute("plusyear", "0");
			model.addAttribute("plusmonth", "0");
			model.addAttribute("plusday", "0");
			return "date/new";
		}
		dateService.insertOne(dateCalc);
		return "redirect:/datecalc/index";
	}

	/* 更新 */
	@GetMapping("/edit/id={id}")
	public String edit(@PathVariable("id") int id, Model model) {

		model.addAttribute("dateCalc", dateService.findById(id));
		return "date/edit";
	}

	@PostMapping("/edit/id={id}")
	public String update(Model model, @Validated @ModelAttribute DateCalc dateCalc, BindingResult result) {
		if (result.hasErrors()) {
			return "date/edit";
		}

		dateService.updateOne(dateCalc.getId(), dateCalc.getDateid(), dateCalc.getName(), dateCalc.getPlusyear(),
				dateCalc.getPlusmonth(), dateCalc.getPlusday());
		return "redirect:/datecalc/index";
	}

	/* 削除 */
	@PostMapping("/delete/id={id}")
	public String delete(@PathVariable("id") int id) {
		dateService.deleteOne(id);
		return "redirect:/datecalc/index";
	}

	/*
	 * 例外処理
	 */

	/* 変更・削除ボタンのIDがない時 */
	@ExceptionHandler(NumberFormatException.class)
	public String NumberFormatExceptionHandler(Model model) {
		model.addAttribute("status", "400エラー");
		model.addAttribute("error", "NumberFormatException");
		model.addAttribute("message", "IDが不正です");

		return "error";
	}

}