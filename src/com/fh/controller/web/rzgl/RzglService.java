package com.fh.controller.web.rzgl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.util.DateTimeUtil;
import com.fh.util.PageData;

@Service("rzglService")
public class RzglService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 查询日志信息列表
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findList(Page page) throws Exception{
		return (List<PageData>) dao.findForList("RzglMapper.findlistPage", page);
	}

	public void save(String path, User user) {
		try {
			PageData pd = new PageData();
			pd.put("yhid", user.getUSER_ID());
			pd.put("yhmc", user.getUSERNAME());
			pd.put("path", path);
			pd.put("content",("用户"+user.getUSERNAME()+"执行了"+path+"操作").toString());
			pd.put("czsj", DateTimeUtil.getDateTimeStr());
			dao.save("RzglMapper.save", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
