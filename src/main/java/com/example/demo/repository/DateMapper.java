package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.DateCalc;

@Mapper
public interface DateMapper {

	// 全件取得
	public List<DateCalc> findAll();

	// 1件取得
	public Optional<DateCalc> findById(int id);

	// 登録
	public void insertOne(DateCalc date);

	// 更新
	public void updateOne(@Param("id") int id, @Param("dateid") String dateid, @Param("name") String name,
			@Param("plusyear") int plusyear, @Param("plusmonth") int plusmonth, @Param("plusday") int plusday);

	// 削除
	public Integer deleteOne(int id);

}
