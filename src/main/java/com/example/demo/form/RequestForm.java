package com.example.demo.form;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class RequestForm {

	@NotBlank(message = "日付を入力してください")
	private String inputDate;
}

