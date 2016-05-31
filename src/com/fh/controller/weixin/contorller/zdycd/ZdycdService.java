package com.fh.controller.weixin.contorller.zdycd;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.controller.weixin.common.MenuUtil;
import com.fh.controller.weixin.entity.AccessToken;
import com.fh.controller.weixin.util.Constants;
import com.fh.controller.weixin.util.WeixinUtil;
import com.fh.dao.DaoSupport;
import com.fh.util.DateTimeUtil;
import com.fh.util.PageData;
import com.fh.util.Verify;


@Service("zdycdService")
public class ZdycdService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	//======================================================================================
	
	/*
	 * 获取自定义菜单列表
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findList(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ZdycdMapper.findList", pd);
	}
	/*
	* 保存用户
	*/
	public void save(PageData pd)throws Exception{
		dao.save("ZdycdMapper.save", pd);
	}
	
	/**
	 * 查询自定义菜单详细信息
	 * @param pd
	 * @return
	 * @throws Exception 
	 */
	public PageData findInfo(PageData pd) throws Exception {
		return (PageData) dao.findForObject("ZdycdMapper.findInfo", pd);
	}
	
	/**
	 * 更新自定义菜单
	 * @param pd
	 * @throws Exception 
	 */
	public void update(PageData pd) throws Exception {
		dao.update("ZdycdMapper.update", pd);
	}
	
	/**
	 * 删除
	 * @param pd
	 * @throws Exception 
	 */
	public void remove(PageData pd) throws Exception {
		dao.delete("ZdycdMapper.remove", pd);
	}
	
	/**
	 * 设置
	 * @param pd
	 * @throws Exception 
	 */
	public void saveMessage(PageData pd) throws Exception {
		dao.update("ZdycdMapper.saveMessage", pd);
	}

	/**
	 * 更新排序
	 * @param pd
	 * @throws Exception 
	 */
	public void updatePx(PageData pd) throws Exception {
		String[] ids = pd.getString("ids").split(",");
		for (int i = 0; i < ids.length; i++) {
			PageData p = new PageData();
			p.put("id", ids[i]);
			p.put("px", i+"");
			p.put("xgsj", DateTimeUtil.getDateTimeStr());
			this.dao.save("ZdycdMapper.updatePx", p);
		}
	}
	
	
	//菜单同步
	@SuppressWarnings("unchecked")
	public boolean sameMenu(PageData pd) throws Exception {
		boolean bol = false;
		List<PageData> yjcdLs = new ArrayList<PageData>();
		pd.put("pid", "1");
		List<PageData> list = (List<PageData>)dao.findForList("ZdycdMapper.findList", pd);
		if(Verify.verifyIsNotNull(list)){
			for (PageData p : list) {
				PageData mPd = new PageData();
				mPd.put("name", p.getString("cdmc"));
				if(Verify.verifyIsNotNull(p.getString("lx"))){
					if(p.getString("lx").equals("fstw")){
						mPd.put("type", "click");
						mPd.put("key", p.getString("twlx"));
					}else if(p.getString("lx").equals("tzlj")){
						mPd.put("type", "view");
						mPd.put("url", p.getString("url"));
					}
				}
				
				List<PageData> ejcdLs = new ArrayList<PageData>();
				
				pd.put("pid", p.getString("id"));
				List<PageData> ls = (List<PageData>)dao.findForList("ZdycdMapper.findList", pd);
				if(Verify.verifyIsNotNull(ls)){
					for (PageData p1 : ls) {
						PageData mPd2 = new PageData();
						mPd2.put("name", p1.getString("cdmc"));
						if(p1.getString("lx").equals("fstw")){
							mPd2.put("type", "click");
							mPd2.put("key", p1.getString("twlx"));
						}else if(p1.getString("lx").equals("tzlj")){
							mPd2.put("type", "view");
							mPd2.put("url", p1.getString("url"));
						}
						ejcdLs.add(mPd2);
						mPd.put("sub_button", ejcdLs);
					}
				}
				yjcdLs.add(mPd);
			}
		}
		
		PageData pageData = new PageData();
		pageData.put("button", yjcdLs);
		// 调用接口获取凭证
		AccessToken token = WeixinUtil.getAccessToken(Constants.APPID, Constants.SECRET);

		if (null != token) {
			// 创建菜单
			boolean result = MenuUtil.createMenu(pageData, token.getAccessToken());
			bol = result;
		}
		return bol;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
