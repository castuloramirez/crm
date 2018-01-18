package com.yunchuang.crm.config.message;

import java.util.List;

/**
 * @author 尹冬飞
 * @date 2015-07-27 10:23:07
 */
public class Tmessage {
	private Tfrom from;
	private List<Tto> to;
	private int type;
	//private Tmsg6 msg;
	private Tmsg2 msg;

	public Tfrom getFrom() {
		return from;
	}

	public void setFrom(Tfrom from) {
		this.from = from;
	}

	public List<Tto> getTo() {
		return to;
	}

	public void setTo(List<Tto> to) {
		this.to = to;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

/*	public Tmsg6 getMsg() {
		return msg;
	}

	public void setMsg(Tmsg6 msg) {
		this.msg = msg;
	}*/

	public Tmsg2 getMsg() {
		return msg;
	}

	public void setMsg(Tmsg2 msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "Tmessage [from=" + from + ", to=" + to + ", type=" + type + ", msg=" + msg + "]";
	}

}
