package com.stoopsartsunlimited.jxbeetool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;


public class JXBeeTool {
	
	static String PROGRAM_NAME = "jxbeetool";
	private static Map<String, Tool> tools = new HashMap<String, Tool>();

	public static void main(String[] args) {
		
		// create tools
		Tool tool;
		tool = new DiscoverTool(args);
		tools.put(tool.getToolName(), tool);
		tool = new GetTool(args);
		tools.put(tool.getToolName(), tool);
		tool = new SendTool(args);
		tools.put(tool.getToolName(), tool);
		tool = new SerialTool(args);
		tools.put(tool.getToolName(), tool);
		
		if (args.length == 0
				|| args[0].equals("-h")
				|| args[0].equals("--help")) {
			printHelp();
			return;
		}

		// check the tool request
		tool = tools.get(args[0]);
		if (tool == null) {
			printHelp();
			return;
		}

		// execute the tool
		try {
			tool.main(ArrayUtils.subarray(args, 2, args.length));
		} catch (InterruptedException e) {
			// just stop
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
	}
	
	
	private static void printHelp() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("usage:  " + JXBeeTool.PROGRAM_NAME + " COMMAND ...\n" +
				"COMMAND:\n");
		List<String> toolNames = new ArrayList<String>(tools.keySet());
		Collections.sort(toolNames);
		for (String name : toolNames) {
			sb.append(String.format("  %s - %s\n", name, tools.get(name).getDescription()));
		}
		System.out.print(sb);
	}

}
