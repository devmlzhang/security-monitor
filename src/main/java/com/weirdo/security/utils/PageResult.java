package com.weirdo.security.utils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.weirdo.security.common.ResultEnum;

import java.util.List;

public class PageResult {

	/**当前页*/
	//@ApiModelProperty(value="当前页",dataType="Integer")
	private int currentPage=1;
	/**每页多少条*/
	//@ApiModelProperty(value="每页多少条",dataType="Integer")
	private int pageSize=10;
	/**状态编码*/
	//@ApiModelProperty(value="状态编码",hidden=true)
	private int errCode=0;
	/**状态信息*/
	//@ApiModelProperty(value="状态信息",hidden=true)
	private String errMsg="success";
	/**总页数*/
	//@ApiModelProperty(value="总页数",hidden=true)
	private int totalCount;
	/**数据*/
	//@ApiModelProperty(value="查询数据",hidden=true)
	private Object data;


	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getErrCode() {
		return errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public PageResult(){}

	public PageResult(ResultEnum enums, int currentPage){
		this.currentPage=currentPage;
		this.errCode=enums.getCode();
		this.errMsg=enums.getValue();
		this.totalCount=0;
		this.pageSize=10;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PageResult(Object data){
		this.data=data;
		if(data instanceof Page){
			PageInfo pageInfo=new PageInfo((List)data);
			this.currentPage=pageInfo.getPageNum();
			this.pageSize=pageInfo.getPageSize();
			this.totalCount=(int)pageInfo.getTotal();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static PageResult LoadResult(Object data,PageResult page){
		page=new PageResult();
		page.setData(data);
		if(data instanceof Page){
			PageInfo pageInfo=new PageInfo((List)data);
			page.setCurrentPage(pageInfo.getPageNum());
			page.setPageSize(pageInfo.getPageSize());
			page.setTotalCount((int)pageInfo.getTotal());
		}
		return page;
	}

	public static PageResult errorPage() {
		return new PageResult(ResultEnum.RESULT_ERROR,1);
	}

}
