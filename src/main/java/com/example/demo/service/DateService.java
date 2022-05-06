package com.example.demo.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.example.demo.entity.DateCalc;
import com.example.demo.repository.DateMapper;

@Service
public class DateService {
	private final DateMapper dateMapper;

	public DateService(DateMapper dateMapper) {
		this.dateMapper = dateMapper;
	}

	// 全件取得
	public List<DateCalc> getDateList() {
		return dateMapper.findAll();
	}

	// 1件取得
	public DateCalc findById(Integer id) {
		DateCalc dateCalc = new DateCalc();
		dateCalc.setId(id);
		return dateMapper.findById(dateCalc);
	}

	// 新規登録
	public void insertOne(@Validated DateCalc dateCalc) {
		dateMapper.insertOne(dateCalc);
	}

	// 更新
	public void updateOne(Integer id, String dateid, String name, int plusyear, int plusmonth, int plusday) {
		dateMapper.updateOne(id, dateid, name, plusyear, plusmonth, plusday);
	}

	// 削除
	public void deleteOne(int id) {
		dateMapper.deleteOne(id);
	}

	// 計算
	public List<String> dataCalc(String inputDate) {
		List<String> dateCalcResultList = new ArrayList<String>();
		LocalDate selectedDate = LocalDate.parse(inputDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		List<DateCalc> dateCalcs = dateMapper.findAll();

		for (DateCalc date : dateCalcs) {
			String stringDate = "yyyy/MM/dd";
			LocalDate dateCalc = selectedDate.plusYears(date.getPlusyear()).plusMonths(date.getPlusmonth())
					.plusDays(date.getPlusday());
			stringDate = dateCalc.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
			dateCalcResultList.add(stringDate);
		}
		return dateCalcResultList;
	}

}
