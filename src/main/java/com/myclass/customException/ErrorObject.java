package com.myclass.customException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
public class ErrorObject {

	public List<Object> fieldErrors;

	public void setMessages(Map<String, String> errorMessageMap) {
		fieldErrors = new ArrayList<Object>();
		for (Map.Entry<String, String> entry : errorMessageMap.entrySet()) {
			//fieldErrors.put("a",entry);
			Map<String, String> m = new HashMap<String, String>();
			m.put("field", entry.getKey());
			m.put("message", entry.getValue());
			fieldErrors.add(m);
		}
	}
}
