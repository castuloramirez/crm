package com.yunchuang.crm.console.trackingLog.service;

import com.yunchuang.crm.config.utils.MyUtils;
import com.yunchuang.crm.console.trackingLog.dao.ITrackingLogDao;
import com.yunchuang.crm.console.trackingLog.model.TrackingLog;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 尹冬飞 on 2018/1/17 17:12
 */
@Transactional(value = "masterTransactionManager", isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Service
public class TrackingLogServiceImpl implements ITrackingLogService {
	/* 注入dao */
	@Resource
	private ITrackingLogDao trackingLogDao;
	@Resource
	private SqlSessionFactory masterSqlSessionFactory;

	/**
	 * 1.下载模版
	 *
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@Override
	public void Template(HttpServletResponse response, HttpServletRequest request) throws IOException {
		/* 常量 */
		String[] excelHeader = {"日志编号", "客户编号", "项目编号", "日期", "标题", "内容", "创建人openid", "创建人名字", "创建人头像"};
		String sheetName = "追踪日志表";
		int[] excelHeaderWidth = {100, 100, 100, 100, 100, 100, 100, 100, 100};
		String filename = "追踪日志表导入模版" + MyUtils.getToday() + ".xls";
		HSSFWorkbook wb = MyUtils.template(excelHeader, sheetName, excelHeaderWidth);
		MyUtils.exportExcelToClient(response, request, wb, filename);
	}

	/**
	 * 2.导入
	 *
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@Override
	public int importByExcel(MultipartFile file) throws Exception {
		Sheet sheet1 = MyUtils.getSheetByMultipartFile(file);
		List<TrackingLog> trackingLogs = getList(sheet1);
		trackingLogDao.truncateAll();
		addBatch(trackingLogs);
		return trackingLogs.size();
	}

	/**
	 * 3.导出
	 *
	 * @param list
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@Override
	public void exportToExcel(List<TrackingLog> list, HttpServletResponse response, HttpServletRequest request) throws IOException {
		/* 常量 */
		String[] excelHeader = {"日志编号", "客户编号", "项目编号", "日期", "标题", "内容", "创建人openid", "创建人名字", "创建人头像"};
		String sheetName = "追踪日志表";
		int[] excelHeaderWidth = {100, 100, 100, 100, 100, 100, 100, 100, 100};
		String filename = "追踪日志表导出" + MyUtils.getToday() + ".xls";
		HSSFWorkbook wb = ExportMethod(list, excelHeader, sheetName, excelHeaderWidth);
		MyUtils.exportExcelToClient(response, request, wb, filename);
	}

	/**
	 * 4.由List<TrackingLog>获取HSSFWorkbook
	 */
	private HSSFWorkbook ExportMethod(List<TrackingLog> list, String[] excelHeader, String sheetName, int[] excelHeaderWidth) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFRow row;
		HSSFSheet sheet = MyUtils.getSheet(excelHeader, sheetName, excelHeaderWidth, wb);
		// 设置单元格的值
		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow(i + 1);
			TrackingLog trackingLog = list.get(i);
			for (int j = 0; j < excelHeader.length; j++) {
				HSSFCell cell = row.createCell(j);
				switch (j) {
					case 0:
						/* 0.日志编号 */
						MyUtils.setExcelCellValue(cell, trackingLog.getTrackingLogNo());
						break;
					case 1:
						/* 1.项目编号 */
						MyUtils.setExcelCellValue(cell, trackingLog.getProjectNo());
						break;
					case 2:
						/* 2.日期 */
						MyUtils.setExcelCellValue(cell, trackingLog.getCreateTime());
						break;
					case 3:
						/* 3.标题 */
						MyUtils.setExcelCellValue(cell, trackingLog.getTitle());
						break;
					case 4:
						/* 4.内容 */
						MyUtils.setExcelCellValue(cell, trackingLog.getContent());
						break;
					case 5:
						/* 5.创建人openid */
						MyUtils.setExcelCellValue(cell, trackingLog.getFounderOpenid());
						break;
					case 6:
						/* 6.创建人名字 */
						MyUtils.setExcelCellValue(cell, trackingLog.getFounderName());
						break;
					case 7:
						/* 7.创建人头像 */
						MyUtils.setExcelCellValue(cell, trackingLog.getFounderImage());
						break;
					default:
						break;
				}
			}
		}
		return wb;
	}

	/**
	 * 5.由sheet获取List<TrackingLog>
	 *
	 * @throws Exception
	 */
	private List<TrackingLog> getList(Sheet sheet1) throws Exception {
		List<TrackingLog> trackingLogs = new ArrayList<>();
		for (int i = 1; i <= sheet1.getLastRowNum(); i++) {
			try {
				HSSFRow row = (HSSFRow) sheet1.getRow(i);
				HSSFCell cell;
				if (row == null || MyUtils.isBlankRow(row)) {
					continue;
				}
				TrackingLog trackingLog = new TrackingLog();
				for (int j = 0; j <= row.getLastCellNum(); j++) {
					cell = row.getCell(j);
					if (cell == null)
						continue;
					switch (j) {
						case 0:
							/* 0.日志编号 */
							cell.setCellType(CellType.STRING);
							trackingLog.setTrackingLogNo(cell.getStringCellValue());
							break;
						case 1:
							/* 1.项目编号 */
							cell.setCellType(CellType.STRING);
							trackingLog.setProjectNo(cell.getStringCellValue());
							break;
						case 2:
							/* 2.日期 */
							cell.setCellType(CellType.STRING);
							trackingLog.setCreateTime(MyUtils.parseExcelCell(cell));
							break;
						case 3:
							/* 3.标题 */
							cell.setCellType(CellType.STRING);
							trackingLog.setTitle(cell.getStringCellValue());
							break;
						case 4:
							/* 4.内容 */
							cell.setCellType(CellType.STRING);
							trackingLog.setContent(cell.getStringCellValue());
							break;
						case 5:
							/* 5.创建人openid */
							cell.setCellType(CellType.STRING);
							trackingLog.setFounderOpenid(cell.getStringCellValue());
							break;
						case 6:
							/* 6.创建人名字 */
							cell.setCellType(CellType.STRING);
							trackingLog.setFounderName(cell.getStringCellValue());
							break;
						case 7:
							/* 7.创建人头像 */
							cell.setCellType(CellType.STRING);
							trackingLog.setFounderImage(cell.getStringCellValue());
							break;
						default:
							break;
					}
				}
				trackingLogs.add(trackingLog);
			} catch (Exception e) {
				String message = "第" + (i + 1) + "行有错误:" + e.getMessage();
				throw new Exception(message);
			}
		}
		return trackingLogs;
	}

	/**
	 * 4.增加
	 *
	 * @param trackingLog
	 */
	@Override
	public void add(TrackingLog trackingLog) {
		trackingLogDao.add(trackingLog);
	}

	/**
	 * 5.更改
	 *
	 * @param trackingLog
	 */
	@Override
	public void update(TrackingLog trackingLog) {
		trackingLogDao.update(trackingLog);
	}

	/**
	 * 6.删除
	 *
	 * @param ids
	 */
	@Override
	public void delete(Integer[] ids) {
		trackingLogDao.delete(ids);
	}

	/**
	 * 7.前台读取全部
	 */
	@Override
	public List<TrackingLog> list() {
		return trackingLogDao.list();
	}

	/**
	 * 8.根据ID读取
	 *
	 * @param id
	 */
	@Override
	public TrackingLog loadById(int id) {
		return trackingLogDao.loadById(id);
	}

	/**
	 * 10.查询所有数量
	 */
	@Override
	public int count() {
		return trackingLogDao.count();
	}

	/**
	 * 11.分页读取
	 *
	 * @param iDisplayStart
	 * @param iDisplayLength
	 */
	@Override
	public List<TrackingLog> findByConsole(int iDisplayStart, int iDisplayLength) {
		/* 前台选择ALL的时候 */
		if (iDisplayLength == -1) {
			return trackingLogDao.list();
		}
		return trackingLogDao.findByConsole((iDisplayStart + 1), (iDisplayLength + iDisplayStart));
	}

	/**
	 * 12.批量插入
	 *
	 * @param list
	 */
	@Override
	public void addBatch(List<TrackingLog> list) {
		SqlSession batchSqlSession = null;
		try {
			batchSqlSession = masterSqlSessionFactory.openSession(ExecutorType.BATCH, false);
			// 每批commit的个数
			int batchCount = 200;
			for (int index = 0; index < list.size(); index++) {
				//就这里需要更改
				trackingLogDao.add(list.get(index));
				if (index != 0 && index % batchCount == 0) {
					batchSqlSession.commit();
				}
			}
			batchSqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (batchSqlSession != null) {
				batchSqlSession.close();
			}
		}
	}

	/**
	 * 13.清除表,不支持回滚
	 */
	@Override
	public void truncateAll() {
		trackingLogDao.truncateAll();
	}
}
