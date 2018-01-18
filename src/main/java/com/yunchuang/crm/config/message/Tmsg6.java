package com.yunchuang.crm.config.message;

import java.util.List;

/**
 * @author 尹冬飞
 * @date 2015-07-27 10:25:42
 */
public class Tmsg6 {
/*	  "model":"排版展现模板，格式为整型",
	  "todo":"是否推送待办消息,格式为整型,默认1=推送到待办消息;0=推送原公共号消息",
	  "list":"发布信息列表，格式为包含发布信息JSON对象的JSON数组"*/
	private int model=2;
	private int todo=0;
	private List<Tlist> list;
	
	public int getModel() {
		return model;
	}
	public void setModel(int model) {
		this.model = model;
	}
	public int getTodo() {
		return todo;
	}
	public void setTodo(int todo) {
		this.todo = todo;
	}
	public List<Tlist> getList() {
		return list;
	}
	public void setList(List<Tlist> list) {
		this.list = list;
	}
	
	@Override
	public String toString() {
		return "Tmsg [model=" + model + ", todo=" + todo + ", list=" + list + "]";
	}
}
