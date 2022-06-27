package com.jeemodel.solution.file.utils;


import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson2.JSON;
import com.jeemodel.bean.enums.code.sub.impl.WarnCodeEnum;
import com.jeemodel.bean.exception.base.IToBeJeeModelException;
import com.jeemodel.bean.rpc.PongTable;
import com.jeemodel.bean.rpc.PongUtils;
import com.jeemodel.solution.file.dto.ExcelSheet;

import lombok.extern.slf4j.Slf4j;

/**
 * 基于EasyExcel做Excel导出工具类
 * @author GuoHonglin
 * 特点：
 * 1、支持多个Excel_Sheet导出
 * 2、支持文件下载并且失败的时候返回json（默认失败了会返回一个有部分数据的Excel）
 */
@Slf4j
public class EasyExcelUtils {

	/**
	 * 文件下载并且失败的时候返回json（默认失败了会返回一个有部分数据的Excel）
	 * @param sheetList
	 * @param exportFileName
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	public static void exportFailedUsingJson(List<ExcelSheet<?>> sheetList, String exportFileName, HttpServletResponse response) throws IOException{
		try {
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setCharacterEncoding("utf-8");
			// 这里URLEncoder.encode可以防止中文乱码 和easyexcel没有关系
			exportFileName = URLEncoder.encode(exportFileName, "UTF-8").replaceAll("\\+", "%20");
			response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + exportFileName + ".xlsx");

			
			
			ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build();
			for (int i = 0; i < sheetList.size(); i++) {
				//Excel的Sheet
				ExcelSheet<?> sheetDate = sheetList.get(i);
				
				//Sheet对应的Java对象
				Class<?> dataClass = sheetDate.getDataClass();
				//Sheet的名称
				String sheetName = sheetDate.getSheetName();

				//Sheet的数据
				List<?> listData = sheetDate.getListData();

				// 指定写用哪个class去写
				WriteSheet writeSheet = EasyExcel.writerSheet(i, sheetName).head(dataClass).build();
				excelWriter.write(listData, writeSheet);
			}
			// finish 会帮忙关闭流
			excelWriter.finish();
		} catch (Exception e) {
			PongTable<?> failPong = null;
			if (e instanceof IToBeJeeModelException) {
				IToBeJeeModelException jmException  = (IToBeJeeModelException) e;
				failPong = PongUtils.analysisException(jmException);
			}else {
				failPong = PongUtils.diy(WarnCodeEnum.W0351,"下载文件失败" + e.getMessage());
			}
			log.warn("Excel文件导出下载出现异常，failPong={}，Exception：",failPong,e);
			
			// 重置response
			response.reset();
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().println(JSON.toJSONString(failPong));
		}
	}
	
}
