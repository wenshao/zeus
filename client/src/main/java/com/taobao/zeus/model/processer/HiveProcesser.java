/**
 * 
 */
package com.taobao.zeus.model.processer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


/**
 * @author gufei.wzy
 * @date 2013-1-11
 */
public class HiveProcesser implements Processer {

	private static final long serialVersionUID = 1L;
	private List<String> outputTables;
	private Integer keepDays;
	private Integer driftPercent;

	@Override
	public String getId() {
		return "hive";
	}

	@Override
	public String getConfig() {
		JSONObject o = new JSONObject();
		if (outputTables != null && outputTables.size() > 0) {
			o.put("outputTables", StringUtils.join(outputTables.toArray(), ','));
		}
		if (keepDays != null && keepDays > 0) {
			o.put("keepDays", keepDays.toString());
		}
		if (driftPercent != null && driftPercent > 0) {
			o.put("driftPercent", driftPercent.toString());
		}
		return o.toString();
	}

	@Override
	public void parse(final String config) {
		JSONObject o = JSON.parseObject(config);
		outputTables = parseOutputTables(o);
		if (o.get("keepDays") != null) {
			keepDays = o.getIntValue("keepDays");
		}
		if (o.get("driftPercent") != null) {
			driftPercent = o.getIntValue("driftPercent");
		}
	}



	private List<String> parseOutputTables(JSONObject o) {
		if (o.get("outputTables") == null) {
			return Collections.emptyList();
		}
		String ots = o.getString("outputTables");
		if (notNullOrEmpty(ots)) {
			return Arrays.asList(StringUtils.split(ots, ','));
		}
		return null;
	}

	private boolean notNullOrEmpty(String ots) {
		return ots != null && !ots.trim().isEmpty();
	}

	public List<String> getOutputTables() {
		return outputTables;
	}

	public void setOutputTables(final List<String> outputTables) {
		this.outputTables = outputTables;
	}

	public Integer getKeepDays() {
		return keepDays;
	}

	public void setKeepDays(final Integer keepDays) {
		this.keepDays = keepDays;
	}

	public Integer getDriftPercent() {
		return driftPercent;
	}

	public void setDriftPercent(final Integer driftPercent) {
		this.driftPercent = driftPercent;
	}


}
