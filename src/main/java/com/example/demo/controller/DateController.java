package com.example.demo.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.example.demo.form.DateForm;
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

		model.addAttribute("dateList", dateService.getDateList());

		return "index";
	}

	/* 計算 */
	@GetMapping("/calc")
	public String calc(Model model, @RequestParam("dateinput") String inputDate) {

		model.addAttribute("dateList", dateService.getDateList());
		model.addAttribute("selectedDate", inputDate.replace('-', '/'));

		List<LocalDate> dateCalcResultList = dateService.calculationDate(inputDate);
		List<String> dateCalcResultStrList = new ArrayList<String>();
		for (LocalDate dateCalcResult : dateCalcResultList) {
			/* localData型をString型に変換 */
			String stringDate = dateCalcResult.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
			dateCalcResultStrList.add(stringDate);
		}
		model.addAttribute("stringDate", dateCalcResultStrList);

		return "index";
	}

	/* 新規登録 */
	@GetMapping("/new")
	public String newDate(Model model, @ModelAttribute DateForm dateForm) {

		model.addAttribute("dateCalc", dateForm);

		/* plusyear,plusmonth,plusdateの初期値 */
		model.addAttribute("plusyear", "0");
		model.addAttribute("plusmonth", "0");
		model.addAttribute("plusday", "0");
		return "date/new";
	}

	@PostMapping("/new")
	public String create(Model model, @Validated @ModelAttribute DateForm dateForm, BindingResult result) {

		if (result.hasErrors()) {

			/* plusyear,plusmonth,plusdateの初期値 */
			model.addAttribute("plusyear", "0");
			model.addAttribute("plusmonth", "0");
			model.addAttribute("plusday", "0");
			return "date/new";
		}
		dateService.insertOne(dateForm);
		return "redirect:/datecalc/index";
	}

	/* 更新 */
	@GetMapping("/edit/id={id}")
	public String edit(@PathVariable("id") int id, Model model) {

		Optional<DateCalc> date = dateService.findById(id);
		if (date.isPresent()) {
			DateCalc dateCalc = date.get();
			model.addAttribute("dateForm", dateCalc);
			return "date/edit";
		} else {
			return "error";
		}
	}

	@PostMapping("/edit/id={id}")
	public String update(Model model, @PathVariable("id") int id, @Validated @ModelAttribute DateForm dateForm,
			BindingResult result) {

		if (result.hasErrors()) {
			return "date/edit";
		}
		Optional<DateCalc> date = dateService.findById(id);
		if (date.isPresent()) {
			dateService.updateOne(id, dateForm.getDateid(), dateForm.getName(), dateForm.getPlusyear(),
					dateForm.getPlusmonth(), dateForm.getPlusday());
			return "redirect:/datecalc/index";
		} else {
			model.addAttribute("message", "idが不正です");
			return "error/404";
		}
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

	/* 日付が入力されなかった時 */
	@ExceptionHandler(DateTimeParseException.class)
	public String DateTimeParseExceptionHandler(Model model) {
		// status -> 500エラー
		// error -> DateTimeParseException
		model.addAttribute("errormessage", "* 日付を入力してください");
		return "index";
	}

	/* 変更・削除ボタンのIDがない時 */
	@ExceptionHandler(NumberFormatException.class)
	public String NumberFormatExceptionHandler(Model model) {
		// status -> 400エラー
		// error -> NumberFormatException
		return "redirect:/datecalc/index";
	}
}