package com.example.demo.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
	public Optional<DateCalc> findById(int id) {
		return dateMapper.findById(id);
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
	public List<LocalDate> calculationDate(String inputDate) {
		LocalDate selectedDate = LocalDate.parse(inputDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		List<DateCalc> dateCalcs = dateMapper.findAll();

		List<LocalDate> dateCalcResultList = dateCalcs.stream()
		        .map(date -> selectedDate.plusYears(date.getPlusyear()).plusMonths(date.getPlusmonth())
						.plusDays(date.getPlusday()))
		        .collect(Collectors.toList());
		
		return dateCalcResultList;
	}

}
