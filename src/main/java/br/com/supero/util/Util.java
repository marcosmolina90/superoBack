package br.com.supero.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.core.MultivaluedMap;

public class Util {

	private static final String PAGE_SIZE = "pageSize";
	private static final String FIRST = "first";
	
	public static Map<String, String> parseFilter(MultivaluedMap<String, String> queryParameters) {
		Map<String, String> filterReturn = new HashMap<>();
		for (Entry<String, List<String>> param : queryParameters.entrySet()) {
			if (param.getKey().equals(PAGE_SIZE) || param.getKey().equals(FIRST))
				continue;
			filterReturn.put(param.getKey(), param.getValue().get(0));
		}
		return filterReturn;
	}
}
