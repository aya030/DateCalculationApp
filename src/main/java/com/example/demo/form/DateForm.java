package com.example.demo.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class DateForm {

	private Integer id;

	@NotBlank(message = "日付IDを入力してください")
	private String dateid;

	@NotBlank(message = "日付名を入力してください")
	private String name;

	@NotNull(message = "1桁以上の数値を入力してください")
	private Integer plusyear;

	@NotNull(message = "1桁以上の数値を入力してください")
	private Integer plusmonth;

	@NotNull(message = "1桁以上の数値を入力してください")
	private Integer plusday;
}

