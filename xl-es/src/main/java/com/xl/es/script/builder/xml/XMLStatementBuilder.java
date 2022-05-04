package com.xl.es.script.builder.xml;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.xl.es.script.builder.MapperBuilderAssistant;
import com.xl.es.script.builder.Exception.BuilderException;
import com.xl.es.script.builder.factory.BaseBuilder;
import com.xl.es.script.builder.factory.Configuration;
import com.xl.es.script.builder.xml.dynamic.ChooseSqlNode;
import com.xl.es.script.builder.xml.dynamic.DynamicSqlSource;
import com.xl.es.script.builder.xml.dynamic.ForEachSqlNode;
import com.xl.es.script.builder.xml.dynamic.IfSqlNode;
import com.xl.es.script.builder.xml.dynamic.MixedSqlNode;
import com.xl.es.script.builder.xml.dynamic.SetSqlNode;
import com.xl.es.script.builder.xml.dynamic.SqlNode;
import com.xl.es.script.builder.xml.dynamic.TextSqlNode;
import com.xl.es.script.builder.xml.dynamic.TrimSqlNode;
import com.xl.es.script.builder.xml.dynamic.WhereSqlNode;
import com.xl.es.script.mapping.ScriptCommandType;
import com.xl.es.script.mapping.SqlSource;
import com.xl.es.script.parsing.XNode;


public class XMLStatementBuilder extends BaseBuilder {

	private MapperBuilderAssistant builderAssistant;

	public XMLStatementBuilder(Configuration configuration, MapperBuilderAssistant builderAssistant) {
		super(configuration);
		this.builderAssistant = builderAssistant;
	}

	public void parseStatementNode(XNode context) {
		String id = context.getStringAttribute("id");
		String index = context.getStringAttribute("index");
		String type = context.getStringAttribute("type");
		String parameterType = context.getStringAttribute("parameterType");
		Class<?> parameterTypeClass = resolveClass(parameterType);
		String resultType = context.getStringAttribute("resultType");
		Class<?> resultTypeClass = resolveClass(resultType);
		List<SqlNode> contents = parseDynamicTags(context);
		MixedSqlNode rootSqlNode = new MixedSqlNode(contents);
		SqlSource sqlSource = new DynamicSqlSource(configuration, rootSqlNode);
		String nodeName = context.getNode().getNodeName();
		ScriptCommandType sqlCommandType = ScriptCommandType.valueOf(nodeName.toUpperCase(Locale.ENGLISH));
		builderAssistant.addMappedStatement(id, index,type,sqlSource, parameterTypeClass,  resultTypeClass, sqlCommandType);
	}

	private List<SqlNode> parseDynamicTags(XNode node) {
		List<SqlNode> contents = new ArrayList<SqlNode>();
		NodeList children = node.getNode().getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			XNode child = node.newXNode(children.item(i));
			String nodeName = child.getNode().getNodeName();
			if (child.getNode().getNodeType() == Node.CDATA_SECTION_NODE || child.getNode().getNodeType() == Node.TEXT_NODE) {
				String data = child.getStringBody("");
				contents.add(new TextSqlNode(data));
			} else {
				NodeHandler handler = nodeHandlers.get(nodeName);
				if (handler == null) {
					throw new BuilderException("Unknown element <" + nodeName + "> in SQL statement.");
				}
				handler.handleNode(child, contents);

			}
		}
		return contents;
	}

	private Map<String, NodeHandler> nodeHandlers = new HashMap<String, NodeHandler>() {
		private static final long serialVersionUID = 7123056019193266281L;
		{
			put("include", new IncludeNodeHandler());
			put("trim", new TrimHandler());
			put("where", new WhereHandler());
			put("set", new SetHandler());
			put("foreach", new ForEachHandler());
			put("if", new IfHandler());
			put("choose", new ChooseHandler());
			put("when", new IfHandler());
			put("otherwise", new OtherwiseHandler());
		}
	};

	private interface NodeHandler {
		void handleNode(XNode nodeToHandle, List<SqlNode> targetContents);
	}

	private class IncludeNodeHandler implements NodeHandler {
		public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
			String refid = nodeToHandle.getStringAttribute("refid");
			refid = builderAssistant.applyCurrentNamespace(refid);
			XNode includeNode = configuration.getSqlFragments().get(refid);
			if (includeNode == null) {
				String nsrefid = builderAssistant.applyCurrentNamespace(refid);
				includeNode = configuration.getSqlFragments().get(nsrefid);
				if (includeNode == null) {
					throw new BuilderException("Could not find SQL statement to include with refid '" + refid + "'");
				}
			}
			MixedSqlNode mixedSqlNode = new MixedSqlNode(contents(includeNode));
			targetContents.add(mixedSqlNode);
		}

		private List<SqlNode> contents(XNode includeNode) {
			return parseDynamicTags(includeNode);
		}
	}

	private class TrimHandler implements NodeHandler {
		public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
			List<SqlNode> contents = parseDynamicTags(nodeToHandle);
			MixedSqlNode mixedSqlNode = new MixedSqlNode(contents);
			String prefix = nodeToHandle.getStringAttribute("prefix");
			String prefixOverrides = nodeToHandle.getStringAttribute("prefixOverrides");
			String suffix = nodeToHandle.getStringAttribute("suffix");
			String suffixOverrides = nodeToHandle.getStringAttribute("suffixOverrides");
			TrimSqlNode trim = new TrimSqlNode(configuration, mixedSqlNode, prefix, prefixOverrides, suffix, suffixOverrides);
			targetContents.add(trim);
		}
	}

	private class WhereHandler implements NodeHandler {
		public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
			List<SqlNode> contents = parseDynamicTags(nodeToHandle);
			MixedSqlNode mixedSqlNode = new MixedSqlNode(contents);
			WhereSqlNode where = new WhereSqlNode(configuration, mixedSqlNode);
			targetContents.add(where);
		}
	}

	private class SetHandler implements NodeHandler {
		public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
			List<SqlNode> contents = parseDynamicTags(nodeToHandle);
			MixedSqlNode mixedSqlNode = new MixedSqlNode(contents);
			SetSqlNode set = new SetSqlNode(configuration, mixedSqlNode);
			targetContents.add(set);
		}
	}

	private class ForEachHandler implements NodeHandler {
		public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
			List<SqlNode> contents = parseDynamicTags(nodeToHandle);
			MixedSqlNode mixedSqlNode = new MixedSqlNode(contents);
			String collection = nodeToHandle.getStringAttribute("collection");
			String item = nodeToHandle.getStringAttribute("item");
			String index = nodeToHandle.getStringAttribute("index");
			String open = nodeToHandle.getStringAttribute("open");
			String close = nodeToHandle.getStringAttribute("close");
			String separator = nodeToHandle.getStringAttribute("separator");
			ForEachSqlNode forEachSqlNode = new ForEachSqlNode(configuration, mixedSqlNode, collection, index, item, open, close, separator);
			targetContents.add(forEachSqlNode);
		}
	}

	private class IfHandler implements NodeHandler {
		public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
			List<SqlNode> contents = parseDynamicTags(nodeToHandle);
			MixedSqlNode mixedSqlNode = new MixedSqlNode(contents);
			String test = nodeToHandle.getStringAttribute("test");
			IfSqlNode ifSqlNode = new IfSqlNode(mixedSqlNode, test);
			targetContents.add(ifSqlNode);
		}
	}

	private class OtherwiseHandler implements NodeHandler {
		public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
			List<SqlNode> contents = parseDynamicTags(nodeToHandle);
			MixedSqlNode mixedSqlNode = new MixedSqlNode(contents);
			targetContents.add(mixedSqlNode);
		}
	}

	private class ChooseHandler implements NodeHandler {
		public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
			List whenSqlNodes = new ArrayList<SqlNode>();
			List<SqlNode> otherwiseSqlNodes = new ArrayList<SqlNode>();
			handleWhenOtherwiseNodes(nodeToHandle, whenSqlNodes, otherwiseSqlNodes);
			SqlNode defaultSqlNode = getDefaultSqlNode(otherwiseSqlNodes);
			ChooseSqlNode chooseSqlNode = new ChooseSqlNode((List<IfSqlNode>) whenSqlNodes, defaultSqlNode);
			targetContents.add(chooseSqlNode);
		}

		private void handleWhenOtherwiseNodes(XNode chooseSqlNode, List<SqlNode> ifSqlNodes, List<SqlNode> defaultSqlNodes) {
			List<XNode> children = chooseSqlNode.getChildren();
			for (XNode child : children) {
				String nodeName = child.getNode().getNodeName();
				NodeHandler handler = nodeHandlers.get(nodeName);
				if (handler instanceof IfHandler) {
					handler.handleNode(child, ifSqlNodes);
				} else if (handler instanceof OtherwiseHandler) {
					handler.handleNode(child, defaultSqlNodes);
				}
			}
		}

		private SqlNode getDefaultSqlNode(List<SqlNode> defaultSqlNodes) {
			SqlNode defaultSqlNode = null;
			if (defaultSqlNodes.size() == 1) {
				defaultSqlNode = defaultSqlNodes.get(0);
			} else if (defaultSqlNodes.size() > 1) {
				throw new BuilderException("Too many default (otherwise) elements in choose statement.");
			}
			return defaultSqlNode;
		}
	}

}
