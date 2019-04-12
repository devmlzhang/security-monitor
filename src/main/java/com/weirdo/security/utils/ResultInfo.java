package com.weirdo.security.utils;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.ListUtils;
import com.weirdo.security.common.Constans;
import java.io.Serializable;
import java.util.List;

/**
 *
 *@Author :ML.Zhang
 *@Date :2018/10/16
 *@Description :layui数据返回结果封装
 */
public class ResultInfo<T> implements Serializable {

    private static final long serialVersionUID = -2042618546543630713L;

    /**
     * 错误信息
     */
    private String msg;
    /**
     * 错误号
     */
    private String code = Constans.NOT_ERROR;
    /**
     * 返回数据
     */
    private T data;
    /**
     * 总记录数
     */
    private int count;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @SuppressWarnings({"unchecked","rawtypes"})
	public static <T> ResultInfo ResultPage(T t) {
    	if(t instanceof Page) {
			PageInfo pageInfo=new PageInfo((List)t);
    		return new ResultInfo(t,(int)(pageInfo.getTotal()));
    	}else {
    		return new ResultInfo(ListUtils.EMPTY_LIST,0);
    	}
    }

    @SuppressWarnings({"rawtypes"})
    public static ResultInfo errorPage(){
    	return new ResultInfo("查询错误");
    }

    @SuppressWarnings({"rawtypes"})
    public static ResultInfo error(){
    	return new ResultInfo("查询错误");
    }

    @SuppressWarnings({"unchecked","rawtypes"})
    public static ResultInfo success(Object obj){
    	return new ResultInfo(obj);
    }

    public ResultInfo() {
    }

    public ResultInfo(T data) {
        this.data = data;
    }

    public ResultInfo(T data, int count) {
        this.data = data;
        this.count = count;
    }

    public ResultInfo(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultInfo(String msg) {
        this.code = Constans.YES_ERROR;
        this.msg = msg;
    }



}
