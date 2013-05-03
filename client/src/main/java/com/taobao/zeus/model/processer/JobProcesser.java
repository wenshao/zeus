package com.taobao.zeus.model.processer;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 可以将其他Job作为一个Processer处理单元，嵌入到当前Job中
 * @author zhoufang
 *
 */
public class JobProcesser implements Processer{

	private static final long serialVersionUID = 1L;
	private String jobId;
	private Map<String, String> kvConfig=new HashMap<String, String>();
	@Override
	public String getConfig() {
		JSONObject o=new JSONObject();
		o.put("jobId", jobId);
		JSONObject kv=new JSONObject();
		if(kvConfig!=null){
			for(String key:kvConfig.keySet()){
				if(key.startsWith("instance.")){
					kv.put(key, kvConfig.get(key));
				}
			}
		}
		o.put("kvConfig", kv);
		return o.toString();
	}

	@Override
	public String getId() {
		return "JobProcesser";
	}

	@Override
	public void parse(String config) {
		JSONObject o = JSON.parseObject(config);
		jobId=o.getString("jobId");
		JSONObject kvc=o.getJSONObject("kvConfig");
		Map<String, String> map=new HashMap<String, String>();
		for(String key : kvc.keySet()){
			if(kvc.getString(key.toString())!=null){
				map.put(key.toString(), kvc.getString(key.toString()));
			}
		}
		kvConfig=map;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public Map<String, String> getKvConfig() {
		return kvConfig;
	}

	public void setKvConfig(Map<String, String> kvConfig) {
		this.kvConfig = kvConfig;
	}

}
