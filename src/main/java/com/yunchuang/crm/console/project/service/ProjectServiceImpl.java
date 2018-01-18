package com.yunchuang.crm.console.project.service;

import com.yunchuang.crm.config.utils.MyUtils;
import com.yunchuang.crm.console.project.dao.IProjectDao;
import com.yunchuang.crm.console.project.model.Project;
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
 * Created by 尹冬飞 on 2018/1/17 14:56
 */
@Transactional(value = "masterTransactionManager", isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Service
public class ProjectServiceImpl implements IProjectService {
	/* 注入dao */
	@Resource
	private IProjectDao projectDao;
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
		String[] excelHeader = {"项目编号", "项目名称", "客户编号", "联系人编号", "业务类别", "备注"};
		String sheetName = "项目表";
		int[] excelHeaderWidth = {100, 100, 100, 100, 100, 100};
		String filename = "项目表导入模版" + MyUtils.getToday() + ".xls";
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
		List<Project> projects = getList(sheet1);
		projectDao.truncateAll();
		addBatch(projects);
		return projects.size();
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
	public void exportToExcel(List<Project> list, HttpServletResponse response, HttpServletRequest request) throws IOException {
		/* 常量 */
		String[] excelHeader = {"项目编号", "项目名称", "客户编号", "联系人编号", "业务类别", "备注"};
		String sheetName = "项目表";
		int[] excelHeaderWidth = {100, 100, 100, 100, 100, 100};
		String filename = "项目表导出" + MyUtils.getToday() + ".xls";
		HSSFWorkbook wb = ExportMethod(list, excelHeader, sheetName, excelHeaderWidth);
		MyUtils.exportExcelToClient(response, request, wb, filename);
	}

	/**
	 * 4.由List<Project>获取HSSFWorkbook
	 */
	private HSSFWorkbook ExportMethod(List<Project> list, String[] excelHeader, String sheetName, int[] excelHeaderWidth) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFRow row;
		HSSFSheet sheet = MyUtils.getSheet(excelHeader, sheetName, excelHeaderWidth, wb);
		// 设置单元格的值
		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow(i + 1);
			Project project = list.get(i);
			for (int j = 0; j < excelHeader.length; j++) {
				HSSFCell cell = row.createCell(j);
				switch (j) {
					case 0:
						/* 0.项目编号 */
						MyUtils.setExcelCellValue(cell, project.getProjectNo());
						break;
					case 1:
						/* 1.项目名称 */
						MyUtils.setExcelCellValue(cell, project.getProjectName());
						break;
					case 2:
						/* 2.客户编号 */
						MyUtils.setExcelCellValue(cell, project.getCustomerNo());
						break;
					case 3:
						/* 3.联系人编号 */
						MyUtils.setExcelCellValue(cell, project.getContactNo());
						break;
					case 4:
						/* 4.业务类别 */
						MyUtils.setExcelCellValue(cell, project.getBusinessCategory());
						break;
					case 5:
						/* 5.备注 */
						MyUtils.setExcelCellValue(cell, project.getRemarks());
						break;
					default:
						break;
				}
			}
		}
		return wb;
	}

	/**
	 * 5.由sheet获取List<Project>
	 *
	 * @throws Exception
	 */
	private List<Project> getList(Sheet sheet1) throws Exception {
		List<Project> projects = new ArrayList<>();
		for (int i = 1; i <= sheet1.getLastRowNum(); i++) {
			try {
				HSSFRow row = (HSSFRow) sheet1.getRow(i);
				HSSFCell cell;
				if (row == null || MyUtils.isBlankRow(row)) {
					continue;
				}
				Project project = new Project();
				for (int j = 0; j <= row.getLastCellNum(); j++) {
					cell = row.getCell(j);
					if (cell == null)
						continue;
					switch (j) {
						case 0:
							/* 0.项目编号 */
							cell.setCellType(CellType.STRING);
							project.setProjectNo(cell.getStringCellValue());
							break;
						case 1:
							/* 1.项目名称 */
							cell.setCellType(CellType.STRING);
							project.setProjectName(cell.getStringCellValue());
							break;
						case 2:
							/* 2.客户编号 */
							cell.setCellType(CellType.STRING);
							project.setCustomerNo(cell.getStringCellValue());
							break;
						case 3:
							/* 3.联系人编号 */
							cell.setCellType(CellType.STRING);
							project.setContactNo(cell.getStringCellValue());
							break;
						case 4:
							/* 4.业务类别 */
							cell.setCellType(CellType.STRING);
							project.setBusinessCategory(cell.getStringCellValue());
							break;
						case 5:
							/* 5.备注 */
							cell.setCellType(CellType.STRING);
							project.setRemarks(cell.getStringCellValue());
							break;
						default:
							break;
					}
				}
				projects.add(project);
			} catch (Exception e) {
				String message = "第" + (i + 1) + "行有错误:" + e.getMessage();
				throw new Exception(message);
			}
		}
		return projects;
	}


	/**
	 * 4.增加
	 *
	 * @param project
	 */
	@Override
	public void add(Project project) {
		projectDao.add(project);
	}

	/**
	 * 5.更改
	 *
	 * @param project
	 */
	@Override
	public void update(Project project) {
		projectDao.update(project);
	}

	/**
	 * 6.删除
	 *
	 * @param ids
	 */
	@Override
	public void delete(Integer[] ids) {
		projectDao.delete(ids);
	}

	/**
	 * 7.前台读取全部
	 */
	@Override
	public List<Project> list() {
		return projectDao.list();
	}

	/**
	 * 8.根据ID读取
	 *
	 * @param id
	 */
	@Override
	public Project loadById(int id) {
		return projectDao.loadById(id);
	}

	/**
	 * 10.查询所有数量
	 */
	@Override
	public int count() {
		return projectDao.count();
	}

	/**
	 * 11.分页读取
	 *
	 * @param iDisplayStart
	 * @param iDisplayLength
	 */
	@Override
	public List<Project> findByConsole(int iDisplayStart, int iDisplayLength) {
		/* 前台选择ALL的时候 */
		if (iDisplayLength == -1) {
			return projectDao.list();
		}
		return projectDao.findByConsole((iDisplayStart + 1), (iDisplayLength + iDisplayStart));
	}

	/**
	 * 12.批量插入
	 *
	 * @param list
	 */
	@Override
	public void addBatch(List<Project> list) {
		SqlSession batchSqlSession = null;
		try {
			batchSqlSession = masterSqlSessionFactory.openSession(ExecutorType.BATCH, false);
			// 每批commit的个数
			int batchCount = 200;
			for (int index = 0; index < list.size(); index++) {
				//就这里需要更改
				projectDao.add(list.get(index));
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
		projectDao.truncateAll();
	}
}
