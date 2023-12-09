package com.liyang.luckySheet.mapper;


import com.liyang.luckySheet.entity.ExcelWorkSheet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <h2>协同Excel - excel工作表 - mapper接口</h2>
 * <p>
 *
 * </p>
 *
 * @author 作者<1 9 2 2 8 0 2 3 5 2 @ qq.com>
 * @since 2023年11月24日 12:10
 */
@Mapper
public interface ExcelWorkSheetMapper {
	
	/**
	 * 根据表格唯一标识符查询Sheet是否存在
	 *
	 * @return null 表示不存在
	 */
	Integer existByGridKey(@Param("gridKey") String gridKey);
	
	/**
	 * 保存
	 *
	 * @param entity {@link ExcelWorkSheet}
	 */
	void save(@Param("entity") ExcelWorkSheet entity);
	
	/**
	 * 根据ID修改Sheet数据
	 *
	 * @param id    ID
	 * @param sheet sheet json数据
	 */
	void updateSheetById(@Param("id") Integer id, @Param("sheet") String sheet);
	
	/**
	 * 根据 表格唯一标识获取sheet列表数据，只返回Sheet Json字符串数据
	 *
	 * @param gridKey 表格唯一标识
	 * @return List<String>
	 */
	List<String> getDataListByGridKey(@Param("gridKey") String gridKey);
	
	/**
	 * 根据 表格唯一标识获取列表
	 *
	 * @param gridKey 表格唯一标识
	 * @return List<ExcelWorkSheet>
	 */
	List<ExcelWorkSheet> getListByGridKey(@Param("gridKey") String gridKey);
}
